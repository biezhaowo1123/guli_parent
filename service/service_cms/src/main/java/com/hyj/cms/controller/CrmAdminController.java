package com.hyj.cms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.cms.entity.CrmBanner;
import com.hyj.cms.service.CrmBannerService;
import com.hyj.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2023-02-02
 */
@RestController
@RequestMapping("/servicecms/admin")
public class CrmAdminController {
	@Autowired
	private CrmBannerService crmBannerService;
	@GetMapping("/pageBannaer/{page}/{limit}")
	public R pageBannaer(@PathVariable long page, @PathVariable long limit) {
		Page<CrmBanner> crmBannerPage = new Page<>(page, limit);
		crmBannerService.page(crmBannerPage, null);
		List<CrmBanner> records = crmBannerPage.getRecords();
		long total = crmBannerPage.getTotal();
		return R.ok().data("items", records).data("total", total);
	}

	@PostMapping("/saveBanner")
	public R saveBanner(@RequestBody CrmBanner crmBanner) {
		crmBannerService.save(crmBanner);
		return R.ok();
	}

	@DeleteMapping("/{id}")
	private R deleteBanner(@PathVariable long id) {
		crmBannerService.removeById(id);
		return R.ok();
	}

	@PostMapping("/updateBanner")
	public R updateBanner(@RequestBody CrmBanner crmBanner) {
		crmBannerService.updateById(crmBanner);
		return R.ok();
	}
}

