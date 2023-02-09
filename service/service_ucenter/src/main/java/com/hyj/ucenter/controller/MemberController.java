package com.hyj.ucenter.controller;


import com.hyj.commonutils.JWTUtils;
import com.hyj.commonutils.R;
import com.hyj.commonutils.vo.UcenterMember;
import com.hyj.commonutils.vo.UcenterMemberOrder;
import com.hyj.ucenter.entity.Member;
import com.hyj.ucenter.entity.vo.LoginVo;
import com.hyj.ucenter.entity.vo.RegisterVo;
import com.hyj.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2023-02-04
 */
@RestController
@RequestMapping("/ucenter/member")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@PostMapping("/login")
	public R login(@RequestBody LoginVo loginVo) {
		String token = memberService.login(loginVo);
		return R.ok().data("token", token);
	}

	@PostMapping("/register")
	public R register(@RequestBody RegisterVo registerVo){
		memberService.register(registerVo);
		return R.ok();
	}

	@GetMapping("/getMemberInfo")
	public R getMemberInfo(HttpServletRequest request) {
		String id = JWTUtils.getMemberIdByJwtToken(request);
		Member member = memberService.getById(id);
		return R.ok().data("userInfo", member);
	}

	@PostMapping("/getMember/{id}")
	public UcenterMember getMember(@PathVariable("id") String id) {
		Member member = memberService.getById(id);
		UcenterMember ucenterMember = new UcenterMember();
		BeanUtils.copyProperties(member, ucenterMember);
		return ucenterMember;
	}

	//根据用户id获取客户信息
	@PostMapping("/getUserInfoOrder/{id}")
	public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
		Member member = memberService.getById(id);
		//把UcenterMember复制为UcenterMemberOrder对象
		UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
		BeanUtils.copyProperties(member,ucenterMemberOrder);
		return ucenterMemberOrder;
	}

	@GetMapping(value = "countregister/{day}")
	public R registerCount(
			@PathVariable String day){
		Integer count = memberService.countRegisterByDay(day);
		return R.ok().data("countRegister", count);
	}

}

