package com.zhao.shortlink.admin.dto.req;

import com.zhao.shortlink.admin.dao.entity.ManagerDO;
import lombok.Data;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/23 13:06
 */
@Data
public class ManagerDTO extends ManagerDO {
    /**
     * 当前页
     */
    private Long current = 1L;

    /**
     * 每页大小
     */
    private Long size = 10L;
}

