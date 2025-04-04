

package com.zhao.shorturl.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shorturl.project.common.convention.result.Result;
import com.zhao.shorturl.project.common.convention.result.Results;
import com.zhao.shorturl.project.dto.req.ShortLinkGroupStatsAccessRecordReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkGroupStatsReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkStatsReqDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkBaseStatesRespDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkStatsRespDTO;
import com.zhao.shorturl.project.service.ShortLinkStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接监控控制层
 * 小赵
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {

    private final ShortLinkStatsService shortLinkStatsService;

    /**
     * 访问单个短链接指定时间内监控数据
     */
    @GetMapping("/short-url/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.oneShortLinkStats(requestParam));
    }

    /**
     * 访问分组短链接指定时间内监控数据
     */
    @GetMapping("/short-url/v1/stats/group")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStats(requestParam));
    }

    /**
     * 访问单个短链接指定时间内访问记录监控数据
     */
    @GetMapping("/short-url/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        return Results.success(shortLinkStatsService.shortLinkStatsAccessRecord(requestParam));
    }

    /**
     * 访问分组短链接指定时间内访问记录监控数据
     */
    @GetMapping("/short-url/v1/stats/access-record/group")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsAccessRecord(requestParam));
    }

    /**
     * 查看用某一用户指定时间内访问曲线
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsAccess")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsAccess(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsAccess(requestParam));
    }

    /**
     * 查看用某一用户指定时间内地区分布
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsArea")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsArea(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsArea(requestParam));
    }

    /**
     * 查看用某一用户指定时间内24小时分布
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsHours")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsHours(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsHours(requestParam));
    }

    /**
     * 查看用某一用户指定时间一周分布
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsWeek")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsWeek(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsWeek(requestParam));
    }

    /**
     * 查看用某一用户指定时间内访问用户信息
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsUser")
    public Result<ShortLinkStatsRespDTO> groupShortLinkStatsUser(@RequestBody ShortLinkStatsReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsUser(requestParam));
    }

    /**
     * 查看用某一用户指定时间内访问记录
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsRecord")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsRecord(@RequestBody ShortLinkStatsAccessRecordReqDTO requestParam) {
        return Results.success(shortLinkStatsService.groupShortLinkStatsRecord(requestParam));
    }

    /**
     * 获取基础访问信息
     */
    @GetMapping("/short-url/v1/stats/access-record/getBaseStatsInfo")
    public ShortLinkBaseStatesRespDTO getBaseStatsInfo() {
        return shortLinkStatsService.getBaseStatsInfo();
    }
}