package com.hyj.order.controller;


import com.hyj.commonutils.R;
import com.hyj.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2023-02-08
 */
@RestController
@RequestMapping("/orderservice/log")
public class TPayLogController {
	@Autowired
	private TPayLogService payService;
	/**
	 * 生成二维码
	 *
	 * @return
	 */
	@GetMapping("/createNative/{orderNo}")
	public R createNative(@PathVariable String orderNo) {
		Map map = payService.createNative(orderNo);
		return R.ok().data(map);
	}

	@GetMapping("/queryPayStatus/{orderNo}")
	public R queryPayStatus(@PathVariable String orderNo) {
		//调用查询接口
		Map<String, String> map = payService.queryPayStatus(orderNo);
		System.out.println(map);
		if (map == null) {//出错
			return R.error().message("支付出错");
		}
		if (map.get("result_code").equals("SUCCESS")) {//如果成功
			payService.updateOrderStatus(map);
			return R.ok().message("支付成功");
		}
		return R.ok().code(25000).message("支付中");
	}
}

