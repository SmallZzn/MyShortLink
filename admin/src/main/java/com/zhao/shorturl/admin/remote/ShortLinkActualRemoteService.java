package com.zhao.shorturl.admin.remote;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shorturl.admin.common.convention.result.Result;
import com.zhao.shorturl.admin.dao.entity.BlackListDO;
import com.zhao.shorturl.admin.dto.req.ManagerToUserReqDTO;
import com.zhao.shorturl.admin.dto.req.RecycleBinRecoverReqDTO;
import com.zhao.shorturl.admin.dto.req.RecycleBinRemoveDTO;
import com.zhao.shorturl.admin.dto.req.RecycleBinSaveReqDTO;
import com.zhao.shorturl.admin.remote.dto.req.*;
import com.zhao.shorturl.admin.remote.dto.resp.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接中台远程调用服务
 * 小赵
 */
@FeignClient(value = "short-url-project", url = "${aggregation.remote-url:}")
public interface ShortLinkActualRemoteService {

    /**
     * 创建短链接
     *
     * @param requestParam 创建短链接请求参数
     * @return 短链接创建响应
     */
    @PostMapping("/short-url/v1/create")
    Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam);

    /**
     * 修改短链接
     *
     * @param requestParam 修改短链接请求参数
     */
    @PostMapping("/short-url/v1/update")
    void updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam);

    /**
     * 分页查询短链接
     *
     * @param gid      分组标识
     * @param orderTag 排序类型
     * @param current  当前页
     * @param size     当前数据多少
     * @return 查询短链接响应
     */
    @GetMapping("/short-url/v1/page")
    Result<Page<ShortLinkPageRespDTO>> pageShortLink(@RequestParam("gid") String gid,
                                                     @RequestParam("orderTag") String orderTag,
                                                     @RequestParam("current") Long current,
                                                     @RequestParam("size") Long size);

    /**
     * 分页管理员的管理的短链接
     *
     * @return 查询短链接响应
     */
    @PostMapping("/short-url/v1/getManagerShortLink")
    Result<Page<ShortLinkPageRespDTO>> getManagerShortLink(@RequestBody ManagerToUserReqDTO managerToUserDTO);

    /**
     * 查询分组短链接总量
     *
     * @param requestParam 分组短链接总量请求参数
     * @return 查询分组短链接总量响应
     */
    @GetMapping("/short-url/v1/count")
    Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam);

    /**
     * 根据 URL 获取标题
     *
     * @param url 目标网站地址
     * @return 网站标题
     */
    @GetMapping("/short-url/v1/title")
    Result<String> getTitleByUrl(@RequestParam("url") String url);

    /**
     * 保存回收站
     *
     * @param requestParam 请求参数
     */
    @PostMapping("/short-url/v1/recycle-bin/save")
    void saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam);

    /**
     * 分页查询回收站短链接
     *
     * @param gidList 分组标识集合
     * @param current 当前页
     * @param size    当前数据多少
     * @return 查询短链接响应
     */
    @GetMapping("/short-url/v1/recycle-bin/page")
    Result<Page<ShortLinkPageRespDTO>> pageRecycleBinShortLink(@RequestParam("gidList") List<String> gidList,
                                                               @RequestParam("current") Long current,
                                                               @RequestParam("size") Long size);

    /**
     * 恢复短链接
     *
     * @param requestParam 短链接恢复请求参数
     */
    @PostMapping("/short-url/v1/recycle-bin/recover")
    void recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam);

    /**
     * 移除短链接
     *
     * @param requestParam 短链接移除请求参数
     */
    @PostMapping("/short-url/v1/recycle-bin/remove")
    void removeRecycleBin(@RequestBody RecycleBinRemoveDTO requestParam);


    /**
     * 访问单个短链接指定时间内监控数据
     *
     * @param fullShortUrl 完整短链接
     * @param gid          分组标识
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return 短链接监控信息
     */
    @GetMapping("/short-url/v1/stats")
    Result<ShortLinkStatsRespDTO> oneShortLinkStats(@RequestParam("fullShortUrl") String fullShortUrl,
                                                    @RequestParam("gid") String gid,
                                                    @RequestParam("startDate") String startDate,
                                                    @RequestParam("endDate") String endDate);

    /**
     * 访问分组短链接指定时间内监控数据
     *
     * @param gid       分组标识
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 分组短链接监控信息
     */
    @GetMapping("/short-url/v1/stats/group")
    Result<ShortLinkStatsRespDTO> groupShortLinkStats(@RequestParam("gid") String gid,
                                                      @RequestParam("startDate") String startDate,
                                                      @RequestParam("endDate") String endDate);

    /**
     * 访问单个短链接指定时间内监控访问记录数据
     *
     * @param fullShortUrl 完整短链接
     * @param gid          分组标识
     * @param startDate    开始时间
     * @param endDate      结束时间
     * @return 短链接监控访问记录信息
     */
    @GetMapping("/short-url/v1/stats/access-record")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(@RequestParam("fullShortUrl") String fullShortUrl,
                                                                               @RequestParam("gid") String gid,
                                                                               @RequestParam("startDate") String startDate,
                                                                               @RequestParam("endDate") String endDate);

    /**
     * 访问分组短链接指定时间内监控访问记录数据
     *
     * @param gid       分组标识
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 分组短链接监控访问记录信息
     */
    @GetMapping("/short-url/v1/stats/access-record/group")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsAccessRecord(@RequestParam("gid") String gid,
                                                                                    @RequestParam("startDate") String startDate,
                                                                                    @RequestParam("endDate") String endDate);

    /**
     * 查看用某一用户指定时间内访问信息
     *
     * @param requestParam
     * @return
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsAccess")
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsAccess(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内地区分布
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsArea")
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsArea(ShortLinkStatsReqDTO requestParam);

    /**
     * 查查看用某一用户指定时间内24小时分布
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsHours")
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsHours(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间一周分布
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsWeek")
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsWeek(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问用户信息
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsUser")
    Result<ShortLinkStatsRespDTO> groupShortLinkStatsUser(ShortLinkStatsReqDTO requestParam);

    /**
     * 查看用某一用户指定时间内访问记录
     */
    @PostMapping("/short-url/v1/stats/access-record/groupShortLinkStatsRecord")
    Result<Page<ShortLinkStatsAccessRecordRespDTO>> groupShortLinkStatsRecord(ShortLinkStatsAccessRecordReqDTO requestParam);

    /**
     * 获取基础访问信息
     */
    @GetMapping("/short-url/v1/stats/access-record/getBaseStatsInfo")
    ShortLinkBaseStatesRespDTO getBaseStatsInfo();

    /**
     * 新增黑名单
     */
    @PostMapping("/short-url/v1/black-list/add")
    Result<Void> addBlackList(@RequestBody BlackListDO requestParam);

    /**
     * 删除黑名单
     */
    @PutMapping("/short-url/v1/black-list/update")
    Result<Void> updateBlackList(@RequestBody BlackListDO requestParam);

    /**
     * 分页查询黑名单
     */
    @GetMapping("/short-url/v1/black-list/page")
    Result<Page<BlackListDO>> pageBlackList(@RequestParam("current") Long current,
                                             @RequestParam("size") Long size,
                                             @RequestParam(value = "blackName", required = false) String blackName);
}
