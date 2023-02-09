package com.hyj.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.hyj.order.entity.TOrder;
import com.hyj.order.entity.TPayLog;
import com.hyj.order.mapper.TPayLogMapper;
import com.hyj.order.service.TOrderService;
import com.hyj.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.order.utils.HttpClientUtils;
import com.hyj.servicebase.handler.GuliException;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2023-02-08
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {
	@Autowired
	private TOrderService orderService;
	@Override
	public Map createNative(String orderNo) {
		try {
			//1.根据订单号查询订单信息
			QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("order_no", orderNo);
			TOrder order = orderService.getOne(queryWrapper);

			//2.使用map设置生成二维码需要的参数

			Map m = new HashMap<>();
			//设置支付参数
			m.put("appid", "wx74862e0dfcf69954");
			m.put("mch_id", "1558950191");
			m.put("nonce_str", WXPayUtil.generateNonceStr());
			m.put("body", order.getCourseTitle());
			m.put("out_trade_no", orderNo);
			m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
			m.put("spbill_create_ip", "127.0.0.1");
			m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
			m.put("trade_type", "NATIVE");

			//3.发送httpclient请求，传递参数
			HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");

			client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
			client.setHttps(true);
			//执行请求发送
			client.post();

			//4.得到发送请求返回结果
			//返回的内容是xml格式
			String xml = client.getContent();
			Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

			//封装返回结果集
			Map map = new HashMap<>();
			map.put("out_trade_no", orderNo);
			map.put("course_id", order.getCourseId());
			map.put("total_fee", order.getTotalFee());
			map.put("result_code", resultMap.get("result_code"));//返回二维码状态码
			map.put("code_url", resultMap.get("code_url"));//二维码地址

			System.out.println(map);

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new GuliException(20001, "获取二维码失败");
		}
	}

	@Override
	public Map<String, String> queryPayStatus(String orderNo) {
		try {
			//1、封装参数
			Map m = new HashMap<>();
			m.put("appid", "wx74862e0dfcf69954");
			m.put("mch_id", "1558950191");
			m.put("out_trade_no", orderNo);
			m.put("nonce_str", WXPayUtil.generateNonceStr());

			//2.设置请求
			HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
			client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
			client.setHttps(true);
			client.post();
			//3、返回第三方的数据
			String xml = client.getContent();
			Map<String, String> resultmap = WXPayUtil.xmlToMap(xml);

			return resultmap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateOrderStatus(Map<String, String> map) {
		String orderNo = map.get("out_trade_no");
		//根据订单id查询订单信息
		QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
		wrapper.eq("order_no",orderNo);
		TOrder order = orderService.getOne(wrapper);
		Integer status = order.getStatus();

		if (status==1){return;}
		order.setStatus(1);
		orderService.updateById(order);

		//记录支付日志
		TPayLog payLog = new TPayLog();
		payLog.setOrderNo(orderNo);
		payLog.setPayTime(new Date());
		payLog.setPayType(1);//支付类型
		payLog.setTotalFee(order.getTotalFee());//总金额(分)
		payLog.setTradeState(map.get("trade_state"));//支付状态
		payLog.setTransactionId(map.get("transaction_id"));
		payLog.setAttr(JSONObject.toJSONString(map));

		baseMapper.insert(payLog);
	}
}
