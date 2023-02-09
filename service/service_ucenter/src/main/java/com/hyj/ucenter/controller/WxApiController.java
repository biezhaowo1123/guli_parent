package com.hyj.ucenter.controller;

import com.google.gson.Gson;
import com.hyj.commonutils.JWTUtils;
import com.hyj.servicebase.handler.GuliException;
import com.hyj.ucenter.entity.Member;
import com.hyj.ucenter.service.MemberService;
import com.hyj.ucenter.utils.ConstantPropertiesUtil;
import com.hyj.ucenter.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {
	@Autowired
	private MemberService memberService;

	@GetMapping("callback")
	public String callback(String code, String state) throws Exception {
		//向认证服务器发送请求换取access_token
		String baseAccessTokenUrl =
				"https://api.weixin.qq.com/sns/oauth2/access_token" +
						"?appid=%s" +
						"&secret=%s" +
						"&code=%s" +
						"&grant_type=authorization_code";
		String accessTokenUrl = String.format(baseAccessTokenUrl,
				ConstantPropertiesUtil.WX_OPEN_APP_ID,
				ConstantPropertiesUtil.WX_OPEN_APP_SECRET,
				code);
		String accessTokenInfo = HttpClientUtil.get(accessTokenUrl);

		//解析json字符串
		Gson gson = new Gson();
		HashMap map = gson.fromJson(accessTokenInfo, HashMap.class);
		String accessToken = (String) map.get("access_token");
		String openid = (String) map.get("openid");
		Member member = memberService.getByOpenid(openid);
		if (member == null) {
			System.out.println("新用户注册");
		//访问微信的资源服务器，获取用户信息
			String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
					"?access_token=%s" +
					"&openid=%s";
			String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
			String resultUserInfo = null;
			try {
				resultUserInfo = HttpClientUtil.get(userInfoUrl);
				System.out.println("resultUserInfo==========" + resultUserInfo);
			} catch (Exception e) {
				throw new GuliException(20001, "获取用户信息失败");
			}
			//解析json
			HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo,
					HashMap.class);
			String nickname = (String) mapUserInfo.get("nickname");
			String headimgurl = (String) mapUserInfo.get("headimgurl");
			//向数据库中插入一条记录
			member = new Member();
			member.setNickname(nickname);
			member.setOpenid(openid);
			member.setAvatar(headimgurl);
			memberService.save(member);
		}
		String token = JWTUtils.getJwtToken(member.getId(),member.getNickname());
		//存入cookie
		//CookieUtils.setCookie(request, response, "guli_jwt_token", token);
		//因为端口号不同存在跨域问题，cookie不能跨域，所以这里使用url重写
		return "redirect:http://localhost:3000?token=" + token;
	}

	@GetMapping("/login")
	public String getWxCode() {
		// 微信开放平台授权baseUrl
		String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
				"?appid=%s" +
				"&redirect_uri=%s" +
				"&response_type=code" +
				"&scope=snsapi_login" +
				"&state=%s" +
				"#wechat_redirect";
		// 回调地址
		String redirectUrl = ConstantPropertiesUtil.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
		try {
			redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
		} catch (UnsupportedEncodingException e) {
			throw new GuliException(20001, e.getMessage());
		}
		// 防止csrf攻击（跨站请求伪造攻击）
		//String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
		String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
		// 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
		//键："wechar-open-state-" + httpServletRequest.getSession().getId()
		//值：satte
		//过期时间：30分钟

		//生成qrcodeUrl
		String qrcodeUrl = String.format(
				baseUrl,
				ConstantPropertiesUtil.WX_OPEN_APP_ID,
				redirectUrl,
				state);
		return "redirect:" + qrcodeUrl;
	}
}
