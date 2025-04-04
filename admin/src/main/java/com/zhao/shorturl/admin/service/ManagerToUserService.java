

package com.zhao.shorturl.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.shorturl.admin.common.convention.result.Result;
import com.zhao.shorturl.admin.dao.entity.ManagerToUserDO;
import com.zhao.shorturl.admin.dao.entity.UserDO;
import com.zhao.shorturl.admin.dto.req.ManagerToUserReqDTO;
import com.zhao.shorturl.admin.dto.req.UserReqDTO;
import com.zhao.shorturl.admin.dto.resp.ManagerToUserRespDTO;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkBaseStatesRespDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkStatsRespDTO;

import java.util.List;

/**
 * 管理员与用户关联关系接口层
 * 小赵
 */
public interface ManagerToUserService extends IService<ManagerToUserDO> {

    /**
     * 创建管理员与用户关联关系
     *
     * @param managerToUserDO 管理员与用户关联信息
     */
    void createManagerToUser(ManagerToUserDO managerToUserDO);

    /**
     * 根据ID删除管理员与用户关联关系
     *
     * @param id 关联关系ID
     */
    void deleteById(Long id);

    /**
     * 根据管理员名称查询关联的用户列表
     *
     * @param managerName 管理员名称
     * @return 关联的用户列表
     */
    List<String> listByManagerName(String managerName);

    /**
     * 根据用户名称查询关联的管理员列表
     *
     * @param userName 用户名称
     * @return 关联的管理员列表
     */
    List<ManagerToUserDO> listByUserName(String userName);

    /**
     * 分页查询管理员与用户关联关系列表
     *
     * @param managerToUserDO 查询条件
     * @return 分页列表
     */
    IPage<ManagerToUserDO> pageList(ManagerToUserDO managerToUserDO);

    /**
     * 获取分配列表
     *
     * @param managerToUserDTO
     * @return
     */
    ManagerToUserRespDTO getAssignedList(ManagerToUserReqDTO managerToUserDTO);

    /**
     * 查看管理员管理的短链接
     *
     * @param managerToUserDTO
     */
    Result<Page<ShortLinkPageRespDTO>> getManagerShortLink(ManagerToUserReqDTO managerToUserDTO);

    /**
     * 删除管理员与用户关联关系
     * @param managerName
     * @param username
     */
    void deleteManagerToUser(String managerName, String username);

    /**
     * 分页查询管理的用户列表
     * @param userDTO
     * @return
     */
    IPage<UserDO> pageUserList(UserReqDTO userDTO);

    /**
     * 查看用某一用户指定时间内访问信息
     * @param requestParam
     * @return
     */
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsAccess(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内地区分布
     * @param requestParam
     * @return
     */
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsArea(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内24小时分布
     * @param requestParam
     * @return
     */
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsHours(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间一周分布
     * @param requestParam
     * @return
     */
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsWeek(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问用户信息
     * @param requestParam
     * @return
     */
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsUser(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问记录
     * @param requestParam
     * @return
     */
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsRecord(ShortLinkStatsAccessRecordReqDTO requestParam);

    /**
     * 获取基础统计信息
     * @return
     */
    ShortLinkBaseStatesRespDTO getBaseStatsInfo();
}