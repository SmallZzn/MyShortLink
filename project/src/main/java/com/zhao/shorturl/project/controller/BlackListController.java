package com.zhao.shorturl.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zhao.shorturl.project.common.convention.result.Result;
import com.zhao.shorturl.project.common.convention.result.Results;
import com.zhao.shorturl.project.dao.entity.BlackListDO;
import com.zhao.shorturl.project.dto.req.BlacklistPageReqDTO;
import com.zhao.shorturl.project.service.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 黑名单管理控制层
 * 小赵
 */
@RestController
@RequiredArgsConstructor
public class BlackListController {

    private final BlackListService blackListService;

    /**
     * 新增黑名单
     */
    @PostMapping("/short-url/v1/black-list/add")
    public Result<Void> addBlackList(@RequestBody BlackListDO requestParam) {
        blackListService.addBlackList(requestParam);
        return Results.success();
    }

    /**
     * 删除黑名单
     */
    @PutMapping("/short-url/v1/black-list/update")
    public Result<Void> updateBlackList(@RequestBody BlackListDO requestParam) {
        blackListService.updateBlackList(requestParam);
        return Results.success();

    }

    /**
     * 分页查询黑名单
     */
    @GetMapping("/short-url/v1/black-list/page")
    public Result<IPage<BlackListDO>> pageBlackList(BlacklistPageReqDTO requestParam) {
        return Results.success(blackListService.pageBlackList(requestParam));
    }
}
