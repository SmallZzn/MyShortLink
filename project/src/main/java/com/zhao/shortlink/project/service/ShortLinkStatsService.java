

package com.zhao.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shortlink.project.dto.req.ShortLinkGroupStatsAccessRecordReqDTO;
import com.zhao.shortlink.project.dto.req.ShortLinkGroupStatsReqDTO;
import com.zhao.shortlink.project.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.zhao.shortlink.project.dto.req.ShortLinkStatsReqDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkBaseStatesRespDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkStatsRespDTO;

/**
 * 短链接监控接口层
 * 小赵
 */
public interface ShortLinkStatsService {

    /**
     * 获取单个短链接监控数据
     *
     * @param requestParam 获取短链接监控数据入参
     * @return 短链接监控数据
     */
    ShortLinkStatsRespDTO oneShortLinkStats(ShortLinkStatsReqDTO requestParam);

    /**
     * 获取分组短链接监控数据
     *
     * @param requestParam 获取分组短链接监控数据入参
     * @return 分组短链接监控数据
     */
    ShortLinkStatsRespDTO groupShortLinkStats(ShortLinkGroupStatsReqDTO requestParam);

    /**
     * 访问单个短链接指定时间内访问记录监控数据
     *
     * @param requestParam 获取短链接监控访问记录数据入参
     * @return 访问记录监控数据
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam);

    /**
     * 访问分组短链接指定时间内访问记录监控数据
     *
     * @param requestParam 获取分组短链接监控访问记录数据入参
     * @return 分组访问记录监控数据
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> groupShortLinkStatsAccessRecord(ShortLinkGroupStatsAccessRecordReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问曲线
     *
     * @param requestParam
     * @return
     */
    ShortLinkStatsRespDTO groupShortLinkStatsAccess(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内地区分布
     *
     * @param requestParam
     * @return
     */
    ShortLinkStatsRespDTO groupShortLinkStatsArea(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内24小时分布
     *
     * @param requestParam
     * @return
     */
    ShortLinkStatsRespDTO groupShortLinkStatsHours(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访一周分布
     *
     * @param requestParam
     * @return
     */
    ShortLinkStatsRespDTO groupShortLinkStatsWeek(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内用户信息
     *
     * @param requestParam
     * @return
     */
    ShortLinkStatsRespDTO groupShortLinkStatsUser(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问记录
     *
     * @param requestParam
     * @return
     */
    IPage<ShortLinkStatsAccessRecordRespDTO> groupShortLinkStatsRecord(ShortLinkStatsAccessRecordReqDTO requestParam);

    /**
     * 获取基础访问信息
     *
     * @return
     */
    ShortLinkBaseStatesRespDTO getBaseStatsInfo();
}
