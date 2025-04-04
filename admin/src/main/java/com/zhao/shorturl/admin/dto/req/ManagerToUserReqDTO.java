package com.zhao.shorturl.admin.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shorturl.admin.dao.entity.ShortLinkDO;
import lombok.Data;

import java.util.List;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/23 16:39
 */
@Data
public class ManagerToUserReqDTO extends Page<ShortLinkDO> {

    /**
     * ID
     */
    private Long id;

    /**
     * 管理员名称
     */
    private String managerName;

    /**
     * 用户/公司名称
     */
    private String username;

    /**
     * 用户类型 0：个人用户 1：公司
     */
    private Integer userType;

    /**
     * 模糊查询用户名
     */
    private String likeUserName;

    /**
     * 分组id集合
     */
    private List<String> gidList;

    /**
     * 排序标识
     */
    private String orderTag;
}
