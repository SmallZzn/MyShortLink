

package com.zhao.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shortlink.project.dao.entity.ShortLinkDO;
import lombok.Data;

/**
 * 短链接分页请求参数
 * 小赵
 */
@Data
public class ShortLinkPageReqDTO extends Page<ShortLinkDO> {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 排序标识
     */
    private String orderTag;

    /**
     * 模糊查询短链接（原始短链/短链接）
     */
    private String likeShortLink;

    /**
     * 用户名
     */
    private String username;
}
