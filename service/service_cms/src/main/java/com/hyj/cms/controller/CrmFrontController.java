package com.hyj.cms.controller;

import com.hyj.cms.entity.CrmBanner;
import com.hyj.cms.service.CrmBannerService;
import com.hyj.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicecms/front")
public class CrmFrontController{
	@Autowired
	private CrmBannerService crmBannerService;

	@GetMapping("/getAllBanner")
	public R getAllBanner() {
		List<CrmBanner> list = crmBannerService.getAllBanner();
		return R.ok().data("list", list);
	}
}