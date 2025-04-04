package com.zhao.shorturl.admin.dto.req;

import lombok.Data;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/19 23:48
 */
@Data
public class UserReqDTO {

    /**
     * id
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
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页大小
     */
    private Long size = 10L;

    /**
     * 删除标识
     */
    private Integer delFlag;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 管理员名称
     */
    private String managerName;
}
