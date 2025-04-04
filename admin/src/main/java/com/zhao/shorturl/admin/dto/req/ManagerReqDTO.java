package com.zhao.shorturl.admin.dto.req;

import com.zhao.shorturl.admin.dao.entity.ManagerDO;
import lombok.Data;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/23 13:06
 */
@Data
public class ManagerReqDTO extends ManagerDO {
    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页大小
     */
    private Long size = 10L;
}

