

package com.zhao.shorturl.admin.remote.dto.req;

import lombok.Data;

import java.util.List;

/**
 * 短链接监控请求参数
 * 小赵
 */
@Data
public class ShortLinkStatsReqDTO {

    /**
     * 完整短链接
     */
    private String fullShortUrl;

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

    /**
     * 分组id集合
     */
    private List<String> gidList;

    /**
     * 用户名/公司名
     */
    private String username;
}
