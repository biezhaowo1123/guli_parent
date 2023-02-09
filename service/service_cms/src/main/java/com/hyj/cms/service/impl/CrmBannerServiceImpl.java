package com.hyj.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.cms.entity.CrmBanner;
import com.hyj.cms.mapper.CrmBannerMapper;
import com.hyj.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2023-02-02
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {
	@Cacheable(value = "banner", key = "'selectIndexList'")
	@Override
	public List<CrmBanner> getAllBanner() {
		QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("id");
		wrapper.last("limit 2");
		List<CrmBanner> list = baseMapper.selectList(wrapper);
		return list;
	}
}
