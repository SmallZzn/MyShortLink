

package com.zhao.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shortlink.project.dao.entity.LinkAccessLogsDO;
import lombok.Data;

import java.util.List;

/**
 * 短链接监控访问记录请求参数
 * 小赵
 */
@Data
public class ShortLinkStatsAccessRecordReqDTO extends Page<LinkAccessLogsDO> {

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
