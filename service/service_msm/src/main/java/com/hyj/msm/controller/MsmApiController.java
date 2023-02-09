package com.hyj.msm.controller;

import com.hyj.commonutils.R;
import com.hyj.commonutils.RandomUtils;
import com.hyj.msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/servicemsm/msm")
public class MsmApiController {
	@Autowired
	private MsmService msmService;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@GetMapping(value = "/send/{phone}")
	public R code(@PathVariable String phone) throws Exception {
		String code = redisTemplate.opsForValue().get(phone);
		if (!StringUtils.isEmpty(code)) return R.ok();
		code = RandomUtils.getFourBitRandom();
		Map<String, Object> param = new HashMap<>();
		param.put("code", code);
		String templateCode = "SMS_154950909";
		boolean isSend = msmService.send(phone, templateCode, param);
		if (isSend) {
			redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
			return R.ok();
		} else {
			return R.error().message("发送短信失败");
		}
	}
}
