

package com.zhao.shortlink.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhao.shortlink.project.common.convention.exception.ServiceException;
import com.zhao.shortlink.project.dao.entity.BlackListDO;
import com.zhao.shortlink.project.dao.mapper.BlackListMapper;
import com.zhao.shortlink.project.dto.req.BlacklistPageReqDTO;
import com.zhao.shortlink.project.service.BlackListService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 黑名单接口实现层
 * 小赵
 */
@Service
public class BlackListServiceImpl extends ServiceImpl<BlackListMapper, BlackListDO> implements BlackListService {

    @Override
    public void addBlackList(BlackListDO requestParam) {
        LambdaQueryWrapper<BlackListDO> queryWrapper = Wrappers.lambdaQuery(BlackListDO.class)
                .eq(BlackListDO::getBlackName, requestParam.getBlackName())
                .eq(BlackListDO::getDelFlag, 0);
        List<BlackListDO> blackListDOS = baseMapper.selectList(queryWrapper);
        if (blackListDOS.size() > 0) {
            throw new ServiceException("该域名已存在");
        }
        baseMapper.insert(requestParam);
    }

    @Override
    public void updateBlackList(BlackListDO requestParam) {
        LambdaUpdateWrapper<BlackListDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(BlackListDO::getBlackName, requestParam.getBlackName());
        baseMapper.update(requestParam, updateWrapper);
    }

    @Override
    public IPage<BlackListDO> pageBlackList(BlacklistPageReqDTO requestParam) {
        LambdaQueryWrapper<BlackListDO> queryWrapper = Wrappers.lambdaQuery(BlackListDO.class)
                .like(StringUtils.isNotBlank(requestParam.getBlackName()), BlackListDO::getBlackName, requestParam.getBlackName())
                .eq(BlackListDO::getDelFlag, 0);
        return baseMapper.selectPage(requestParam, queryWrapper);
    }
}
