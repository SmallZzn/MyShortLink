package com.zhao.shortlink.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhao.shortlink.admin.common.convention.result.Result;
import com.zhao.shortlink.admin.dao.entity.BlackListDO;
import com.zhao.shortlink.admin.remote.ShortLinkActualRemoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 黑名单管理控制层
 * 小赵
 */
@RestController(value = "blackListControllerByAdmin")
@RequiredArgsConstructor
public class BlackListController {

    private final ShortLinkActualRemoteService shortLinkActualRemoteService;

    /**
     * 新增黑名单
     */
    @PostMapping("/short-link/admin/v1/black-list/add")
    public Result<Void> addBlackList(@RequestBody BlackListDO requestParam) {
        return shortLinkActualRemoteService.addBlackList(requestParam);
    }

    /**
     * 删除黑名单
     */
    @PutMapping("/short-link/admin/v1/black-list/update")
    public Result<Void> updateBlackList(@RequestBody BlackListDO requestParam) {
        return shortLinkActualRemoteService.updateBlackList(requestParam);
    }

    /**
     * 分页查询黑名单
     */
    @GetMapping("/short-link/admin/v1/black-list/page")
    public Result<Page<BlackListDO>> pageBlackList(BlackListDO requestParam) {
        return shortLinkActualRemoteService.pageBlackList(requestParam.getCurrent(),
                requestParam.getSize(),
                requestParam.getBlackName());
    }
}
