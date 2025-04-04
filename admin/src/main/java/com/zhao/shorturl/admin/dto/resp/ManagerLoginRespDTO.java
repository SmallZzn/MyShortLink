package com.zhao.shorturl.admin.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: 小赵
 * @DateTime: 2025/3/23 13:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerLoginRespDTO {

    /**
     * 用户Token
     */
    private String token;
}