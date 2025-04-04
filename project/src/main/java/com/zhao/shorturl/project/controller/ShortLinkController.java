

package com.zhao.shorturl.project.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shorturl.project.common.convention.result.Result;
import com.zhao.shorturl.project.common.convention.result.Results;
import com.zhao.shorturl.project.dto.req.ManagerToReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkCreateReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkPageReqDTO;
import com.zhao.shorturl.project.dto.req.ShortLinkUpdateReqDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkCreateRespDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.zhao.shorturl.project.dto.resp.ShortLinkPageRespDTO;
import com.zhao.shorturl.project.handler.CustomBlockHandler;
import com.zhao.shorturl.project.service.ShortLinkService;
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
    @PostMapping("/short-url/v1/create")
    @SentinelResource(
            value = "create_short-url", //规则名称
            blockHandler = "createShortLinkBlockHandlerMethod",//不满足规则调用的类中的方法
            blockHandlerClass = CustomBlockHandler.class //不满足规则调用的类
    )
    public Result<ShortLinkCreateRespDTO> createShortLink(@RequestBody ShortLinkCreateReqDTO requestParam) {
        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 修改短链接
     */
    @PostMapping("/short-url/v1/update")
    public Result<Void> updateShortLink(@RequestBody ShortLinkUpdateReqDTO requestParam) {
        shortLinkService.updateShortLink(requestParam);
        return Results.success();
    }

    /**
     * 分页查询短链接
     */
    @GetMapping("/short-url/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam) {
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }

    /**
     * 查询管理员管理的短链接
     */
    @PostMapping("/short-url/v1/getManagerShortLink")
    public Result<IPage<ShortLinkPageRespDTO>> getManagerShortLink(@RequestBody ManagerToReqDTO requestParam) {
        return Results.success(shortLinkService.getManagerShortLink(requestParam));
    }

    /**
     * 查询短链接分组内数量
     */
    @GetMapping("/short-url/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam") List<String> requestParam) {
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }
}
