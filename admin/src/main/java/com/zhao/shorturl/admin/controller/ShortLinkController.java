

package com.zhao.shorturl.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shorturl.admin.common.convention.result.Result;
import com.zhao.shorturl.admin.common.convention.result.Results;
import com.zhao.shorturl.admin.remote.ShortLinkActualRemoteService;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkCreateReqDTO;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkPageReqDTO;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkUpdateReqDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkCreateRespDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkPageRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接后管控制层
 * 小赵
 */
@RestController(value = "shortLinkControllerByAdmin")
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    /**
     * 创建短链接
     */
    @PostMapping("/short-url/admin/v1/create")
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return shortLinkActualRemoteService.createShortLink(requestParam);
    }

    /**
     * 修改短链接
     */
    @PostMapping("/short-url/admin/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkActualRemoteService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/short-url/admin/v1/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return shortLinkActualRemoteService.pageShortLink(requestParam.getGid(), requestParam.getOrderTag(), requestParam.getCurrent(), requestParam.getSize());
    }
}
