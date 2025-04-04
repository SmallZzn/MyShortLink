

package com.zhao.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shortlink.admin.common.convention.result.Result;
import com.zhao.shortlink.admin.common.convention.result.Results;
import com.zhao.shortlink.admin.dao.entity.ManagerToUserDO;
import com.zhao.shortlink.admin.dao.entity.UserDO;
import com.zhao.shortlink.admin.dto.req.ManagerToUserReqDTO;
import com.zhao.shortlink.admin.dto.req.UserReqDTO;
import com.zhao.shortlink.admin.dto.resp.ManagerToUserRespDTO;
import com.zhao.shortlink.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.zhao.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkBaseStatesRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.zhao.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import com.zhao.shortlink.admin.service.ManagerToUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员与用户关联关系控制层
 * 小赵
 */
@RestController
@RequiredArgsConstructor
public class ManagerToUserController {

    private final ManagerToUserService managerToUserService;

    /**
     * 创建管理员与用户关联关系
     */
    @PostMapping("/short-link/admin/v1/manager-to-user")
    public Result<Void> createManagerToUser(@RequestBody ManagerToUserDO managerToUserDO) {
        managerToUserService.createManagerToUser(managerToUserDO);
        return Results.success();
    }

    /**
     * 删除管理员与用户关联关系
     */
    @PostMapping("/short-link/admin/v1/manager-to-user/delete")
    public Result<Void> deleteManagerToUser(@RequestBody ManagerToUserDO managerToUserDO) {
        managerToUserService.deleteManagerToUser(managerToUserDO.getManagerName(), managerToUserDO.getUsername());
        return Results.success();
    }

    /**
     * 根据管理员名称查询关联的用户列表
     */
    @GetMapping("/short-link/admin/v1/manager-to-user/manager/{managerName}")
    public Result<List<String>> listByManagerName(@PathVariable("managerName") String managerName) {
        return Results.success(managerToUserService.listByManagerName(managerName));
    }

    /**
     * 根据用户名称查询关联的管理员列表
     */
    @GetMapping("/short-link/admin/v1/manager-to-user/user/{userName}")
    public Result<List<ManagerToUserDO>> listByUserName(@PathVariable("userName") String userName) {
        return Results.success(managerToUserService.listByUserName(userName));
    }

    /**
     * 分页查询管理员与用户关联关系列表
     */
    @GetMapping("/short-link/admin/v1/manager-to-user/page")
    public Result<IPage<ManagerToUserDO>> pageList(ManagerToUserDO managerToUserDO) {
        return Results.success(managerToUserService.pageList(managerToUserDO));
    }

    /**
     * 查询管理员与用户关联关系列表
     */
    @GetMapping("/short-link/admin/v1/manager-to-user/list")
    public Result<List<ManagerToUserDO>> list(ManagerToUserDO managerToUserDO) {
        LambdaQueryWrapper<ManagerToUserDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ManagerToUserDO::getManagerName, managerToUserDO.getManagerName());
        return Results.success(managerToUserService.list(queryWrapper));
    }

    /**
     * 查询分配列表
     */
    @PostMapping("/short-link/admin/v1/manager-to-user/getAssignedList")
    public Result<ManagerToUserRespDTO> getAssignedList(@RequestBody ManagerToUserReqDTO managerToUserDTO) {
        return Results.success(managerToUserService.getAssignedList(managerToUserDTO));
    }

    /**
     * 分页查询管理的用户列表
     */
    @GetMapping("/short-link/admin/v1/manager-to-user/pageUserList")
    public Result<IPage<UserDO>> pageUserList(UserReqDTO userDTO) {
        return Results.success(managerToUserService.pageUserList(userDTO));
    }

    /**
     * 查看管理员管理的短链接
     */
    @PostMapping("/short-link/v1/manager-to-user/getManagerShortLink")
    public Result<Page<ShortLinkPageRespDTO>> getManagerShortLink(@RequestBody ManagerToUserReqDTO managerToUserDTO) {
        return managerToUserService.getManagerShortLink(managerToUserDTO);
    }

    /**
     * 查看用某一用户指定时间内访问曲线
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsAccess")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsAccess(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsAccess(requestParam);
    }

    /**
     * 查看用某一用户指定时间内访问地区分布
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsArea")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsArea(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsArea(requestParam);
    }

    /**
     * 查看用某一用户指定时间内24小时分布
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsHours")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsHours(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsHours(requestParam);
    }

    /**
     * 查看用某一用户指定时间一周分布
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsWeek")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsWeek(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsWeek(requestParam);
    }

    /**
     * 查看用某一用户指定时间内访问用户信息
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsUser")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsUser(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsUser(requestParam);
    }

    /**
     * 查看用某一用户指定时间内访问记录
     */
    @PostMapping("/short-link/v1/manager-to-user/groupShortLinkStatsRecord")
    public Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsRecord(@RequestBody ShortLinkStatsAccessRecordReqDTO requestParam) {
        return managerToUserService.groupShortLinkStatsRecord(requestParam);
    }

    /**
     * 获取基础统计信息
     */
    @GetMapping("/short-link/v1/manager-to-user/groupShortLinkStatsRecord")
    public Result<ShortLinkBaseStatesRespDTO> getBaseStatsInfo() {
        return Results.success(managerToUserService.getBaseStatsInfo());
    }
}