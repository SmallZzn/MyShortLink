

package com.zhao.shortlink.project.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhao.shortlink.project.dao.entity.BlackListDO;
import com.zhao.shortlink.project.dto.req.BlacklistPageReqDTO;

/**
 * 黑名单接口层
 * 小赵
 */
public interface BlackListService extends IService<BlackListDO> {

    void addBlackList(BlackListDO requestParam);

    void updateBlackList(BlackListDO requestParam);

    IPage<BlackListDO> pageBlackList(BlacklistPageReqDTO requestParam);
}
