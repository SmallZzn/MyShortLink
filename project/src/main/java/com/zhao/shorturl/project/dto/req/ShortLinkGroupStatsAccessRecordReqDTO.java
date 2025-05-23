

package com.zhao.shorturl.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shorturl.project.dao.entity.LinkAccessLogsDO;
import lombok.Data;

/**
 * 分组短链接监控访问记录请求参数
 * 小赵
 */
@Data
public class ShortLinkGroupStatsAccessRecordReqDTO extends Page<LinkAccessLogsDO> {

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
}
