

package com.zhao.shortlink.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.shortlink.admin.common.convention.exception.ClientException;
import com.zhao.shortlink.admin.common.convention.exception.ServiceException;
import com.zhao.shortlink.admin.common.enums.UserErrorCodeEnum;
import com.zhao.shortlink.admin.dao.entity.UserDO;
import com.zhao.shortlink.admin.dao.mapper.UserMapper;
import com.zhao.shortlink.admin.dto.req.UserDTO;
import com.zhao.shortlink.admin.dto.req.UserLoginReqDTO;
import com.zhao.shortlink.admin.dto.req.UserRegisterReqDTO;
import com.zhao.shortlink.admin.dto.req.UserUpdateReqDTO;
import com.zhao.shortlink.admin.dto.resp.UserLoginRespDTO;
import com.zhao.shortlink.admin.dto.resp.UserRespDTO;
import com.zhao.shortlink.admin.service.GroupService;
import com.zhao.shortlink.admin.service.ManagerService;
import com.zhao.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.zhao.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;
import static com.zhao.shortlink.admin.common.enums.UserErrorCodeEnum.*;

/**
 * 用户接口实现层
 * 小赵
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final RBloomFilter<String> userRegisterCachePenetrationBloomFilter;
    private final RedissonClient redissonClient;
    private final StringRedisTemplate stringRedisTemplate;
    private final GroupService groupService;
    private final ManagerService managerService;

    @Override
    public UserRespDTO getUserByUsername(String username) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, username);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ServiceException(UserErrorCodeEnum.USER_NULL);
        }
        UserRespDTO result = new UserRespDTO();
        BeanUtils.copyProperties(userDO, result);
        return result;
    }

    @Override
    public Boolean hasUsername(String username) {
        return !userRegisterCachePenetrationBloomFilter.contains(username);
    }

    @Override
    public void register(UserRegisterReqDTO requestParam) {
        if (!hasUsername(requestParam.getUsername())) {
            throw new ClientException(USER_NAME_EXIST);
        }
//        RLock lock = redissonClient.getLock(LOCK_USER_REGISTER_KEY + requestParam.getUsername());
//        if (lock.tryLock()) {
//            throw new ClientException(USER_NAME_EXIST);
//        }
        try {
            int inserted = baseMapper.insert(BeanUtil.toBean(requestParam, UserDO.class));
            if (inserted < 1) {
                throw new ClientException(USER_SAVE_ERROR);
            }
            userRegisterCachePenetrationBloomFilter.add(requestParam.getUsername());
            groupService.saveGroup(requestParam.getUsername(), "默认分组");
        } catch (DuplicateKeyException ex) {
            throw new ClientException(USER_EXIST);
        }
//        finally {
//            lock.unlock();
//        }
    }

    @Override
    public void update(UserUpdateReqDTO requestParam) {
        // 验证当前用户名是否为登录用户
//        if (!Objects.equals(requestParam.getUsername(), UserContext.getUsername())) {
//            throw new ClientException(USER_EXIST);
//        }
        LambdaUpdateWrapper<UserDO> updateWrapper = Wrappers.lambdaUpdate(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername());
        baseMapper.update(BeanUtil.toBean(requestParam, UserDO.class), updateWrapper);
    }

    @Override
    public UserLoginRespDTO login(UserLoginReqDTO requestParam) {
        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .eq(UserDO::getUsername, requestParam.getUsername())
                .eq(UserDO::getPassword, requestParam.getPassword())
                .eq(UserDO::getDelFlag, 0);
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new ClientException("用户不存在");
        }
        // 允许用户多端登录
        Map<Object, Object> hasLoginMap = stringRedisTemplate.opsForHash().entries(USER_LOGIN_KEY + requestParam.getUsername());
        if (CollUtil.isNotEmpty(hasLoginMap)) {
            stringRedisTemplate.expire(USER_LOGIN_KEY + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
            String token = hasLoginMap.keySet().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElseThrow(() -> new ClientException("用户登录错误"));
            return new UserLoginRespDTO(token);
        }
        /**
         * Hash
         * Key：login_用户名
         * Value：
         *  Key：token标识
         *  Val：JSON 字符串（用户信息）
         */
        String uuid = UUID.randomUUID().toString();
        stringRedisTemplate.opsForHash().put(USER_LOGIN_KEY + requestParam.getUsername(), uuid, JSON.toJSONString(userDO));
        stringRedisTemplate.expire(USER_LOGIN_KEY + requestParam.getUsername(), 30L, TimeUnit.MINUTES);
        return new UserLoginRespDTO(uuid);
    }

    @Override
    public Boolean checkLogin(String username, String token) {
        return stringRedisTemplate.opsForHash().get(USER_LOGIN_KEY + username, token) != null;
    }

    @Override
    public void logout(String username, String token) {
        if (checkLogin(username, token)) {
            stringRedisTemplate.delete(USER_LOGIN_KEY + username);
            return;
        }
        throw new ClientException("用户Token不存在或用户未登录");
    }

    @Override
    public Boolean checkPassword(UserDTO userDTO) {
        LambdaQueryWrapper<UserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserDO::getUsername, userDTO.getUsername());
        UserDO userDO = baseMapper.selectOne(queryWrapper);
        return userDO.getPassword().equals(userDTO.getPassword());
    }

    @Override
    public Integer selectTodayNewUser() {
        return baseMapper.selectTodayNewUser();
    }

    @Override
    public Integer selectYesterdayNewUser() {
        return baseMapper.selectYesterdayNewUser();
    }
}
