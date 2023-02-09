package com.hyj.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.commonutils.JWTUtils;
import com.hyj.commonutils.MD5;
import com.hyj.servicebase.handler.GuliException;
import com.hyj.ucenter.entity.Member;
import com.hyj.ucenter.entity.vo.LoginVo;
import com.hyj.ucenter.entity.vo.RegisterVo;
import com.hyj.ucenter.mapper.MemberMapper;
import com.hyj.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2023-02-04
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Override
	public String login(LoginVo loginVo) {
		String mobile = loginVo.getMobile();
		String password = loginVo.getPassword();

		if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
			throw new GuliException(20001, "账号或密码为空");
		}
		//获取会员
		Member user = baseMapper.selectOne(new QueryWrapper<Member>().eq("mobile", mobile));
		if (null == user) {
			throw new GuliException(20001, "error");
		}
		//校验密码
		if (!MD5.encrypt(password).equals(user.getPassword())) {
			throw new GuliException(20001, "error");
		}
		//校验是否被禁用
		if (user.getIsDisabled()) {
			throw new GuliException(20001, "error");
		}
		//使用JWT生成token字符串
		String token = JWTUtils.getJwtToken(user.getId(), user.getNickname());
		return token;
	}

	@Override
	public void register(RegisterVo registerVo) {
		String nickname = registerVo.getNickname();
		String mobile = registerVo.getMobile();
		String password = registerVo.getPassword();
		String code = registerVo.getCode();
		//校验参数
		if (StringUtils.isEmpty(mobile) ||
				StringUtils.isEmpty(mobile) ||
				StringUtils.isEmpty(password) ||
				StringUtils.isEmpty(code)) {
			throw new GuliException(20001, "error");
		}
		//校验校验验证码
		//从redis获取发送的验证码
		String mobileCode = redisTemplate.opsForValue().get(mobile);
		if (!code.equals(mobileCode)) {
			throw new GuliException(20001, "error");
		}
		//查询数据库中是否存在相同的手机号码
		Integer count = baseMapper.selectCount(new
				QueryWrapper<Member>().eq("mobile", mobile));
		if (count.intValue() > 0) {
			throw new GuliException(20001, "error");
		}
		//添加注册信息到数据库
		Member member = new Member();
		member.setNickname(nickname);
		member.setMobile(registerVo.getMobile());
		member.setPassword(MD5.encrypt(password));
		member.setIsDisabled(false);
		member.setAvatar("https://hyj-file.oss-cn-hangzhou.aliyuncs.com/2023/01/31/9882b642873f4331b140e170f705524c_file.png");
		this.save(member);
	}

	@Override
	public Member getByOpenid(String openid) {
		QueryWrapper<Member> wrapper = new QueryWrapper<>();
		wrapper.eq("openid", openid);
		Member member = baseMapper.selectOne(wrapper);
		return member;
	}

	@Override
	public Integer countRegisterByDay(String day) {
		return baseMapper.selectRegisterCount(day);
	}
}
