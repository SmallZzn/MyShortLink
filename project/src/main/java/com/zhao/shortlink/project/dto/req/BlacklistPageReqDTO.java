

package com.zhao.shortlink.project.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shortlink.project.dao.entity.BlackListDO;
import lombok.Data;

/**
 * 回收站短链接分页请求参数
 * 小赵
 */
@Data
public class BlacklistPageReqDTO extends Page<BlackListDO> {

    private String blackName;
}
