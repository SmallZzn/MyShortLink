

package com.zhao.shortlink.gateway.config;

import lombok.Data;

import java.util.List;

/**
 * 过滤器配置
 * 小赵
 */
@Data
public class Config {

    /**
     * 白名单前置路径
     */
    private List<String> whitePathList;
}
