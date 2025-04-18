

package com.zhao.shorturl.admin.remote.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

/**
 * 短链接分页请求参数
 * 小赵
 */
@Data
public class ShortLinkPageReqDTO extends Page {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序标识
     */
    private String orderTag;

    /**
     * 管理员名称
     */
    private String managerName;

    /**
     * 用户名
     */
    private String username;
}
