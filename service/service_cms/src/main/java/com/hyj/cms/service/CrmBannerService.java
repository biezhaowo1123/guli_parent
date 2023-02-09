package com.hyj.cms.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.cms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author test.java
 * @since 2023-02-02
 */
public interface CrmBannerService extends IService<CrmBanner> {
	List<CrmBanner> getAllBanner();
}
