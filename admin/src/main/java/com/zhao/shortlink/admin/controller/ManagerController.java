

package com.zhao.shortlink.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shortlink.admin.common.convention.result.Result;
import com.zhao.shortlink.admin.common.convention.result.Results;
import com.zhao.shortlink.admin.dao.entity.ManagerDO;
import com.zhao.shortlink.admin.dto.req.ManagerReqDTO;
import com.zhao.shortlink.admin.dto.resp.ManagerLoginRespDTO;
import com.zhao.shortlink.admin.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 管理员控制层
 * 小赵
 */
@RestController
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    /**
     * 根据用户名查询管理员信息
     */
    @GetMapping("/short-link/admin/v1/manager/{username}")
    public Result<ManagerDO> getManagerByUsername(@PathVariable("username") String username) {
        return Results.success(managerService.getManagerByUsername(username));
    }

    /**
     * 查询用户名是否存在
     */
    @GetMapping("/short-link/admin/v1/manager/has-username")
    public Result<Boolean> hasUsername(@RequestParam("username") String username) {
        return Results.success(managerService.hasUsername(username));
    }

    /**
     * 创建管理员
     */
    @PostMapping("/short-link/admin/v1/manager")
    public Result<Void> createManager(@RequestBody ManagerDO manager) {
        managerService.createManager(manager);
        return Results.success();
    }

    /**
     * 更新管理员信息
     */
    @PutMapping("/short-link/admin/v1/manager")
    public Result<Void> updateManager(@RequestBody ManagerDO manager) {
        managerService.updateManager(manager);
        return Results.success();
    }

    /**
     * 删除管理员
     */
    @DeleteMapping("/short-link/admin/v1/manager")
    public Result<Void> deleteManager(@RequestParam("username") String username) {
        managerService.deleteByUsername(username);
        return Results.success();
    }

    /**
     * 分页查询管理员列表
     */
    @GetMapping("/short-link/admin/v1/manager/page")
    public Result<IPage<ManagerDO>> pageManagerList(ManagerReqDTO manageDTO) {
        return Results.success(managerService.pageManagerList(manageDTO));
    }

    /**
     * 登录
     */
    @PostMapping("/short-link/admin/v1/manager/login")
    public Result<ManagerLoginRespDTO> login(@RequestBody ManagerReqDTO manageDTO) {
        return Results.success(managerService.login(manageDTO));
    }

    /**
     * 检查用户是否登录
     */
    @GetMapping("/short-link/admin/v1/manager/check-login")
    public Result<Boolean> checkLogin(@RequestParam("username") String username, @RequestParam("token") String token) {
        return Results.success(managerService.checkLogin(username, token));
    }
} 