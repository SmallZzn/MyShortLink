

package com.zhao.shortlink.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.shortlink.admin.dao.entity.ManagerDO;
import com.zhao.shortlink.admin.dto.req.ManagerDTO;
import com.zhao.shortlink.admin.dto.resp.ManagerLoginRespDTO;

/**
 * 管理员接口层
 * 小赵
 */
public interface ManagerService extends IService<ManagerDO> {

    /**
     * 根据用户名查询管理员信息
     *
     * @param username 用户名
     * @return 管理员信息
     */
    ManagerDO getManagerByUsername(String username);

    /**
     * 查询用户名是否存在
     *
     * @param username 用户名
     * @return 用户名存在返回 True，不存在返回 False
     */
    Boolean hasUsername(String username);

    /**
     * 创建管理员
     *
     * @param manager 管理员信息
     */
    void createManager(ManagerDO manager);

    /**
     * 更新管理员信息
     *
     * @param manager 管理员信息
     */
    void updateManager(ManagerDO manager);

    /**
     * 根据用户名删除管理员
     *
     * @param username 用户名
     */
    void deleteByUsername(String username);

    /**
     * 分页查询管理员列表
     *
     * @param manageDTO 查询条件
     * @return 管理员分页列表
     */
    IPage<ManagerDO> pageManagerList(ManagerDTO manageDTO);

    /**
     * 登录
     * @param manageDTO
     * @return
     */
    ManagerLoginRespDTO login(ManagerDTO manageDTO);

    /**
     * 检查是否登录
     * @param username
     * @param token
     * @return
     */
    Boolean checkLogin(String username, String token);


}