

package com.zhao.shorturl.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shorturl.admin.common.convention.result.Result;
import com.zhao.shorturl.admin.common.convention.result.Results;
import com.zhao.shorturl.admin.dto.req.RecycleBinRecoverReqDTO;
import com.zhao.shorturl.admin.dto.req.RecycleBinRemoveDTO;
import com.zhao.shorturl.admin.dto.req.RecycleBinSaveReqDTO;
import com.zhao.shorturl.admin.remote.ShortLinkActualRemoteService;
import com.zhao.shorturl.admin.remote.dto.req.ShortLinkRecycleBinPageReqDTO;
import com.zhao.shorturl.admin.remote.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shorturl.admin.service.RecycleBinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回收站管理控制层
 * 小赵
 */
@RestController(value = "recycleBinControllerByAdmin")
@RequiredArgsConstructor
public class RecycleBinController {

    private final RecycleBinService recycleBinService;
    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    /**
     * 保存回收站
     */
    @PostMapping("/short-url/admin/v1/recycle-bin/save")
    public Result<Void> saveRecycleBin(@RequestBody RecycleBinSaveReqDTO requestParam) {
        shortLinkActualRemoteService.saveRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 分页查询回收站短链接
     */
    @GetMapping("/short-url/admin/v1/recycle-bin/page")
    public Result<Page<ShortLinkPageRespDTO>> pageShortLink(ShortLinkRecycleBinPageReqDTO requestParam) {
        return recycleBinService.pageRecycleBinShortLink(requestParam);
    }

    /**
     * 恢复短链接
     */
    @PostMapping("/short-url/admin/v1/recycle-bin/recover")
    public Result<Void> recoverRecycleBin(@RequestBody RecycleBinRecoverReqDTO requestParam) {
        shortLinkActualRemoteService.recoverRecycleBin(requestParam);
        return Results.success();
    }

    /**
     * 移除短链接
     */
    @PostMapping("/short-url/admin/v1/recycle-bin/remove")
    public Result<Void> removeRecycleBin(@RequestBody RecycleBinRemoveDTO requestParam) {
        shortLinkActualRemoteService.removeRecycleBin(requestParam);
        return Results.success();
    }
}
