

package com.zhao.shortlink.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.shortlink.admin.common.convention.result.Result;
import com.zhao.shortlink.admin.dao.entity.GroupDO;
import com.zhao.shortlink.admin.dao.entity.ManagerDO;
import com.zhao.shortlink.admin.dao.entity.ManagerToUserDO;
import com.zhao.shortlink.admin.dao.entity.UserDO;
import com.zhao.shortlink.admin.dao.mapper.ManagerToUserMapper;
import com.zhao.shortlink.admin.dto.req.ManagerToUserDTO;
import com.zhao.shortlink.admin.dto.req.UserDTO;
import com.zhao.shortlink.admin.dto.resp.ManagerToUserRespDTO;
import com.zhao.shortlink.admin.remote.ShortLinkActualRemoteService;
import com.zhao.shortlink.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.zhao.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkBaseStatesRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import com.zhao.shortlink.admin.service.GroupService;
import com.zhao.shortlink.admin.service.ManagerService;
import com.zhao.shortlink.admin.service.ManagerToUserService;
import com.zhao.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.zhao.shortlink.admin.common.constant.RedisCacheConstant.MANAGER_LOGIN_KEY;
import static com.zhao.shortlink.admin.common.constant.RedisCacheConstant.USER_LOGIN_KEY;

/**
 * 管理员与用户关联关系接口实现层
 * 小赵
 */
@Service
@RequiredArgsConstructor
public class ManagerToUserServiceImpl extends ServiceImpl<ManagerToUserMapper, ManagerToUserDO> implements ManagerToUserService {

    private final UserService userService;

    private final GroupService groupService;

    private final ManagerService managerService;

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void createManagerToUser(ManagerToUserDO managerToUserDO) {
        baseMapper.insert(managerToUserDO);
    }

    @Override
    public void deleteById(Long id) {
        baseMapper.deleteById(id);
    }

    @Override
    public List<String> listByManagerName(String managerName) {
        ManagerDO manager = managerService.getManagerByUsername(managerName);

        List<String> res;
        if (manager.getIsSuper() != 1) {
            LambdaQueryWrapper<ManagerToUserDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ManagerToUserDO::getManagerName, managerName);
            List<ManagerToUserDO> managerToUserDOS = baseMapper.selectList(queryWrapper);
            res = managerToUserDOS.stream().map(ManagerToUserDO::getUsername).toList();
        } else {
            LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
            List<GroupDO> list = groupService.list(queryWrapper);
            res = list.stream().map(GroupDO::getUsername).distinct().toList();
        }
        return res;
    }

    @Override
    public List<ManagerToUserDO> listByUserName(String userName) {
        LambdaQueryWrapper<ManagerToUserDO> queryWrapper = Wrappers.lambdaQuery(ManagerToUserDO.class)
                .eq(ManagerToUserDO::getUsername, userName);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<ManagerToUserDO> pageList(ManagerToUserDO managerToUserDO) {
        LambdaQueryWrapper<ManagerToUserDO> queryWrapper = Wrappers.lambdaQuery(ManagerToUserDO.class)
                .like(StrUtil.isNotBlank(managerToUserDO.getManagerName()), ManagerToUserDO::getManagerName, managerToUserDO.getManagerName())
                .like(StrUtil.isNotBlank(managerToUserDO.getUsername()), ManagerToUserDO::getUsername, managerToUserDO.getUsername())
                .orderByDesc(ManagerToUserDO::getCreateTime);

        Page<ManagerToUserDO> page = new Page<>(
                managerToUserDO.getCurrent() != null ? managerToUserDO.getCurrent() : 1,
                managerToUserDO.getSize() != null ? managerToUserDO.getSize() : 10
        );
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public ManagerToUserRespDTO getAssignedList(ManagerToUserDTO managerToUserDTO) {
        //查找已分配的列表
        LambdaQueryWrapper<ManagerToUserDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(StrUtil.isNotBlank(managerToUserDTO.getManagerName()), ManagerToUserDO::getManagerName, managerToUserDTO.getManagerName());
        queryWrapper1.eq(ObjectUtil.isNotEmpty(managerToUserDTO.getUserType()), ManagerToUserDO::getUserType, managerToUserDTO.getUserType());
        queryWrapper1.like(StrUtil.isNotBlank(managerToUserDTO.getLikeUserName()), ManagerToUserDO::getUsername, managerToUserDTO.getLikeUserName());
        List<ManagerToUserDO> managerToUserDOS = baseMapper.selectList(queryWrapper1);

        //查找全部用户列表
        LambdaQueryWrapper<UserDO> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(!ObjectUtil.isEmpty(managerToUserDTO.getUserType()), UserDO::getUserType, managerToUserDTO.getUserType());
        queryWrapper2.like(StrUtil.isNotBlank(managerToUserDTO.getLikeUserName()), UserDO::getUsername, managerToUserDTO.getLikeUserName());
        List<UserDO> userDOS = userService.list(queryWrapper2);
        List<ManagerToUserDO> allManagerToUserDOS = new ArrayList<>();
        userDOS.forEach(userDO -> {
            ManagerToUserDO dto = new ManagerToUserDO();
            dto.setUsername(userDO.getUsername());
            dto.setUserType(userDO.getUserType());
            allManagerToUserDOS.add(dto);
        });

        //查找未分配列表
        Set<String> assignedUsernames = managerToUserDOS.stream()
                .map(ManagerToUserDO::getUsername)
                .collect(Collectors.toSet());
        // 过滤 allManagerToUserDOS，移除 username 存在于 managerToUserDOS 中的元素
        List<ManagerToUserDO> unManagerToUserDOS = allManagerToUserDOS.stream()
                .filter(dto -> !assignedUsernames.contains(dto.getUsername())).toList();

        ManagerToUserRespDTO res = new ManagerToUserRespDTO();
        res.setAssignedList(managerToUserDOS);
        res.setUnAssignedList(unManagerToUserDOS);
        res.setAssignedTotal(managerToUserDOS.size());
        res.setUnassignedTotal(unManagerToUserDOS.size());
        return res;
    }

    @Override
    public Result<Page<ShortLinkPageRespDTO>> getManagerShortLink(ManagerToUserDTO managerToUserDTO) {
        List<String> groupIds = getGidListByUsername(managerToUserDTO.getUsername());

        managerToUserDTO.setGidList(groupIds);
        Result<Page<ShortLinkPageRespDTO>> pageResult = shortLinkActualRemoteService.getManagerShortLink(managerToUserDTO);
        return pageResult;
    }

    @Override
    public void deleteManagerToUser(String managerName, String username) {
        LambdaUpdateWrapper<ManagerToUserDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(ManagerToUserDO::getManagerName, managerName);
        updateWrapper.eq(ManagerToUserDO::getUsername, username);
        baseMapper.delete(updateWrapper);
    }

    @Override
    public IPage<UserDO> pageUserList(UserDTO requestParam) {
        //查找管理员信息
        ManagerDO manager = managerService.getManagerByUsername(requestParam.getManagerName());

        LambdaQueryWrapper<UserDO> queryWrapper = Wrappers.lambdaQuery(UserDO.class)
                .like(StrUtil.isNotBlank(requestParam.getUsername()), UserDO::getUsername, requestParam.getUsername())
                .eq(requestParam.getDelFlag() != null, UserDO::getDelFlag, requestParam.getDelFlag())
                .eq(UserDO::getUserType, requestParam.getUserType())
                .orderByDesc(UserDO::getCreateTime);

        //判断是不是超级管理员
        if (manager.getIsSuper() != 1) {
            //查找管理的公司/个人用户
            LambdaQueryWrapper<ManagerToUserDO> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ManagerToUserDO::getManagerName, requestParam.getManagerName());
            wrapper.eq(ManagerToUserDO::getUserType, requestParam.getUserType());
            List<ManagerToUserDO> list = baseMapper.selectList(wrapper);
            List<String> usernameList = list.stream().map(ManagerToUserDO::getUsername).toList();
            queryWrapper.in(UserDO::getUsername, usernameList);
        }

        Page<UserDO> page = new Page<>(requestParam.getCurrent(), requestParam.getSize());
        try {
            page = userService.getBaseMapper().selectPage(page, queryWrapper);
        } catch (Exception e) {
            page = new Page<>(requestParam.getCurrent(), requestParam.getSize());
        }
        return page;
    }


    @Override
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsAccess(ShortLinkStatsReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsAccess(requestParam);
    }

    @Override
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsArea(ShortLinkStatsReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsArea(requestParam);
    }

    @Override
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsHours(ShortLinkStatsReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsHours(requestParam);

    }

    @Override
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsWeek(ShortLinkStatsReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsWeek(requestParam);
    }

    @Override
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsUser(ShortLinkStatsReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsUser(requestParam);
    }

    @Override
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        List<String> groupIds = getGidListByUsername(requestParam.getUsername());
        requestParam.setGidList(groupIds);
        return shortLinkActualRemoteService.groupShortLinkStatsRecord(requestParam);
    }

    @Override
    public ShortLinkBaseStatesRespDTO getBaseStatsInfo() {
        // 在线用户数
        int loginUserNum = 0;
        Set<String> userKeys = stringRedisTemplate.keys(USER_LOGIN_KEY + "*");

        if (!(userKeys == null || userKeys.isEmpty())) {
            loginUserNum = userKeys.size();
        }

        // 在线管理员数
        int loginManagerNum = 0;
        Set<String> managerKeys = stringRedisTemplate.keys(MANAGER_LOGIN_KEY + "*");

        if (!(managerKeys == null || managerKeys.isEmpty())) {
            loginManagerNum = managerKeys.size();
        }

        //今日新增用户数
        Integer todayNewUser = userService.selectTodayNewUser();

        //今日新增用户数
        Integer yesterdayNewUser = userService.selectYesterdayNewUser();

        //获取基础访问信息
        ShortLinkBaseStatesRespDTO shortLinkBaseStatesRespDTO = shortLinkActualRemoteService.getBaseStatsInfo();
        shortLinkBaseStatesRespDTO.setOnlineUser(loginUserNum);
        shortLinkBaseStatesRespDTO.setOnlineManager(loginManagerNum);
        shortLinkBaseStatesRespDTO.setTodayNewUser(todayNewUser);
        shortLinkBaseStatesRespDTO.setYesterdayNewUser(yesterdayNewUser);

        return shortLinkBaseStatesRespDTO;
    }

    /**
     * 更具用户名获取所有分组id
     *
     * @param username
     * @return
     */
    public List<String> getGidListByUsername(String username) {
        LambdaQueryWrapper<GroupDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupDO::getUsername, username);
        queryWrapper.eq(GroupDO::getDelFlag, 0);
        List<GroupDO> list = groupService.list(queryWrapper);
        List<String> groupIds = list.stream().map(GroupDO::getGid).toList();
        return groupIds;
    }
}
