

package com.zhao.shorturl.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.shorturl.admin.common.database.BaseDO;
import lombok.Data;

/**
 * 管理员持久层实体
 * 小赵
 */
@Data
@TableName("t_manager")
public class ManagerDO extends BaseDO {

    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String mail;

    /**
     * 注销时间戳
     */
    private Long deletionTime;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 是否为超级管理员 0：否 1：是
     */
    private Integer isSuper;
} 