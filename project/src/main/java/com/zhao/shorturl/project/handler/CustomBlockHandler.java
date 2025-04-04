

package com.zhao.shorturl.project.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.zhao.shorturl.project.common.convention.result.Result;
import com.zhao.shorturl.project.dto.req.ShortLinkCreateReqDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkCreateRespDTO;

/**
 * 自定义流控策略
 * 小赵
 */
public class CustomBlockHandler {

    public static Result<ShortLinkCreateRespDTO> createShortLinkBlockHandlerMethod(ShortLinkCreateReqDTO requestParam, BlockException exception) {
        return new Result<ShortLinkCreateRespDTO>().setCode("B100000").setMessage("当前访问网站人数过多，请稍后再试...");
    }
}
