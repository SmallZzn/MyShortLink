package com.zhao.shorturl.project.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/27 15:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortLinkBaseStatesRespDTO {
    /**
     * 在线用户人数
     */
    private Integer onlineUser;

    /**
     * 在线管理员人数
     */
    private Integer onlineManager;

    /**
     * 今日新增用户数
     */
    private Integer todayNewUser;

    /**
     * 昨日新增用户数
     */
    private Integer yesterdayNewUser;

    /**
     * 今日访问人次
     */
    private Integer todayPv;

    /**
     * 今日访问人数
     */
    private Integer todayUv;

    /**
     *昨日访问人次
     */
    private Integer yesterdayPv;

    /**
     * 昨日访问人数
     */
    private Integer yesterdayUv;

    /**
     * 基础访问详情
     */
    private List<ShortLinkStatsAccessDailyRespDTO> daily;
}
