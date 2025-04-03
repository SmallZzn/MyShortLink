

package com.zhao.shortlink.project.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shortlink.project.common.convention.result.Result;
import com.zhao.shortlink.project.common.convention.result.Results;
import com.zhao.shortlink.project.dto.req.*;
import com.zhao.shortlink.project.dto.resp.ShortLinkBatchCreateRespDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkCreateRespDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.zhao.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shortlink.project.handler.CustomBlockHandler;
import com.zhao.shortlink.project.service.ShortLinkService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 * 小赵
 */
@RestController
@RequiredArgsConstructor
public class ShortLinkController {

    private final ShortLinkService shortLinkService;

    /**
     * 短链接跳转原始链接
     */
    @GetMapping("/{short-uri}")
    public void restoreUrl(@PathVariable("short-uri") String shortUri, ServletRequest request, ServletResponse response) {
        shortLinkService.restoreUrl(shortUri, request, response);
    }

    /**
     * 创建短链接
     */
    @PostMapping("/short-link/v1/create")
    @SentinelResource(
            value = "create_short-link", //规则名称
            blockHandler = "createShortLinkBlockHandlerMethod",//不满足规则调用的类中的方法
            blockHandlerClass = CustomBlockHandler.class //不满足规则调用的类
    )
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 批量创建短链接
     */
    @PostMapping("/short-link/v1/create/batch")
    public Result<ShortLinkBatchCreateRespDTO> batchCreateShortLink(@RequestBody ShortLinkBatchCreateReqDTO requestParam) {
        return Results.success(shortLinkService.batchCreateShortLink(requestParam));
    }

    /**
     * 修改短链接
     */
    @PostMapping("/short-link/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/short-link/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
     * 查询管理员管理的短链接
     * @param requestParam
     * @return
     */
    @PostMapping("/short-link/v1/getManagerShortLink")
    public Result<IPage<ShortLinkPageRespDTO>> getManagerShortLink(@RequestBody ManagerToUserDTO requestParam) {
        return Results.success(shortLinkService.getManagerShortLink(requestParam));
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/short-link/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam) {
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }
}
