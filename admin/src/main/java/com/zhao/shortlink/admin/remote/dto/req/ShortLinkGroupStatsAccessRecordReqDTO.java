

package com.zhao.shortlink.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.List;

/**
 * 分组短链接监控访问记录请求参数
 * 小赵
 */
@Data
public class ShortLinkGroupStatsAccessRecordReqDTO extends Page {

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
