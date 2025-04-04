

package com.zhao.shorturl.project.dto.req;

import lombok.Data;

/**
 * 回收站保存功能
 * 小赵
 */
@Data
public class RecycleBinSaveReqDTO {

    /**
     * 分组标识
     */
    private String gid;

    /**
     * 全部短链接
     */
    private String fullShortUrl;
}
