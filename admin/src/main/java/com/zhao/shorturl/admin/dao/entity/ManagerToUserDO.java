

package com.zhao.shorturl.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.shorturl.admin.common.database.BaseDO;
import lombok.Data;

/**
 * 管理员与用户关联关系持久层实体
 * 小赵
 */
@Data
@TableName("t_manager_to_user")
public class ManagerToUserDO extends BaseDO {

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
     * 当前页
     */
    @TableField(exist = false)
    private Long current;

    /**
     * 每页大小
     */
    @TableField(exist = false)
    private Long size;
} 