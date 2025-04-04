package com.zhao.shorturl.project.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.zhao.shorturl.project.common.database.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 黑名单实体
 * 小赵
 */
@Data
@Builder
@TableName("t_black_list")
@NoArgsConstructor
@AllArgsConstructor
public class BlackListDO extends BaseDO {

    /**
     * ID
     */
    private Long id;

    /**
     * 黑名单域名
     */
    private String blackName;

    /**
     * 禁用原因
     */
    private String reason;

    /**
     * 当前页
     */
    @TableField(exist = false)
    private Long current;

    /**
     * 每页大小
     */
    @TableField(exist = false)
    private Long size;
} 