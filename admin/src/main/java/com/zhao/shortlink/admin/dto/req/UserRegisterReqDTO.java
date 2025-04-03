

package com.zhao.shortlink.admin.dto.req;

import lombok.Data;

/**
 * 用户注册请求参数
 * 小赵
 */
@Data
public class UserRegisterReqDTO {

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
     * 用户类型
     */
    private Integer userType;
}
