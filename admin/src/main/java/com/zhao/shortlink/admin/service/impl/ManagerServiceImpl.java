

package com.zhao.shortlink.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.shortlink.admin.common.convention.exception.ClientException;
import com.zhao.shortlink.admin.common.convention.exception.ServiceException;
import com.zhao.shortlink.admin.dao.entity.ManagerDO;
import com.zhao.shortlink.admin.dao.mapper.ManagerMapper;
import com.zhao.shortlink.admin.dto.req.ManagerDTO;
import com.zhao.shortlink.admin.dto.resp.ManagerLoginRespDTO;
import com.zhao.shortlink.admin.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhao.shortlink.admin.common.constant.RedisCacheConstant.MANAGER_LOGIN_KEY;
import static com.zhao.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 管理员接口实现层
 * 小赵
 */
@Service
@RequiredArgsConstructor
public class ManagerServiceImpl extends ServiceImpl<ManagerMapper, ManagerDO> implements ManagerService {

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public ManagerDO getManagerByUsername(String username) {
        LambdaQueryWrapper<ManagerDO> queryWrapper = Wrappers.lambdaQuery(ManagerDO.class)
                .eq(ManagerDO::getUsername, username);
        ManagerDO managerDO = baseMapper.selectOne(queryWrapper);
        if (managerDO == null) {
            throw new ServiceException("管理员不存在");
        }
        return managerDO;
    }

    @Override
    public Boolean hasUsername(String username) {
        LambdaQueryWrapper<ManagerDO> queryWrapper = Wrappers.lambdaQuery(ManagerDO.class)
                .eq(ManagerDO::getUsername, username);
        Long count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    @Override
    public void createManager(ManagerDO manager) {
        if (hasUsername(manager.getUsername())) {
            throw new ClientException("用户名已存在");
        }
        baseMapper.insert(manager);
    }

    @Override
    public void updateManager(ManagerDO manager) {
        LambdaUpdateWrapper<ManagerDO> updateWrapper = Wrappers.lambdaUpdate(ManagerDO.class)
                .eq(ManagerDO::getUsername, manager.getUsername());
        baseMapper.update(manager, updateWrapper);
    }

    @Override
    public void deleteByUsername(String username) {
        LambdaUpdateWrapper<ManagerDO> updateWrapper = Wrappers.lambdaUpdate(ManagerDO.class)
                .eq(ManagerDO::getUsername, username);
        baseMapper.delete(updateWrapper);
    }

    @Override
    public IPage<ManagerDO> pageManagerList(ManagerDTO manageDTO) {
        LambdaQueryWrapper<ManagerDO> queryWrapper = Wrappers.lambdaQuery(ManagerDO.class)
                .like(StrUtil.isNotBlank(manageDTO.getUsername()), ManagerDO::getUsername, manageDTO.getUsername())
                .eq(manageDTO.getDelFlag() != null, ManagerDO::getDelFlag, manageDTO.getDelFlag())
                .orderByDesc(ManagerDO::getCreateTime);

        Page<ManagerDO> page = new Page<>(manageDTO.getCurrent(), manageDTO.getSize());
        page = baseMapper.selectPage(page, queryWrapper);

        return page;
    }

    @Override
    public ManagerLoginRespDTO login(ManagerDTO manageDTO) {
        LambdaQueryWrapper<ManagerDO> queryWrapper = Wrappers.lambdaQuery(ManagerDO.class)
                .eq(ManagerDO::getUsername, manageDTO.getUsername())
                .eq(ManagerDO::getPassword, manageDTO.getPassword())
                .eq(ManagerDO::getDelFlag, 0);
        ManagerDO managerDO = baseMapper.selectOne(queryWrapper);
        if (managerDO == null) {
            throw new ClientException("管理员不存在");
        }
        // 允许用户多端登录
        Map<Object, Object> hasLoginMap = stringRedisTemplate.opsForHash().entries(MANAGER_LOGIN_KEY + manageDTO.getUsername());
        if (CollUtil.isNotEmpty(hasLoginMap)) {
            stringRedisTemplate.expire(USER_LOGIN_KEY + manageDTO.getUsername(), 30L, TimeUnit.MINUTES);
            String token = hasLoginMap.keySet().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElseThrow(() -> new ClientException("管理员登录错误"));
            return new ManagerLoginRespDTO(token);
        }
        /**
         * Hash
         * Key：login_用户名
         * Value：
         *  Key：token标识
         *  Val：JSON 字符串（用户信息）
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(MANAGER_LOGIN_KEY + manageDTO.getUsername(), uuid, JSON.toJSONString(managerDO));
        stringRedisTemplate.expire(MANAGER_LOGIN_KEY + manageDTO.getUsername(), 30L, TimeUnit.MINUTES);
        return new ManagerLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(MANAGER_LOGIN_KEY + username, token) != null;
    }
}