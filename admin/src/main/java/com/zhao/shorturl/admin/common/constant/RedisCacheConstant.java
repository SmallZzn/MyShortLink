

package com.zhao.shorturl.admin.common.constant;

/**
 * 短链接后管 Redis 缓存常量类
 * 小赵
 */
public class RedisCacheConstant {

    /**
     * 用户注册分布式锁
     */
    public static final String LOCK_USER_REGISTER_KEY = "short-url:lock_user-register:";

    /**
     * 分组创建分布式锁
     */
    public static final String LOCK_GROUP_CREATE_KEY = "short-url:lock_group-create:%s";

    /**
     * 用户登录缓存标识
     */
    public static final String USER_LOGIN_KEY = "short-url:login:";

    /**
     * 管理员登录缓存标识
     */
    public static final String MANAGER_LOGIN_KEY = "short-url:loginManager:";
}
