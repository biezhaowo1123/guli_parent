package com.hyj.ucenter.service;

import com.hyj.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.ucenter.entity.vo.LoginVo;
import com.hyj.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author test.java
 * @since 2023-02-04
 */
public interface MemberService extends IService<Member> {

	String login(LoginVo loginVo);

	void register(RegisterVo registerVo);

	Member getByOpenid(String openid);

	Integer countRegisterByDay(String day);
}
