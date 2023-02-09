package com.hyj.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.commonutils.vo.CourseWebVoOrder;
import com.hyj.commonutils.vo.UcenterMemberOrder;
import com.hyj.order.client.EduClient;
import com.hyj.order.client.UcenterClient;
import com.hyj.order.entity.TOrder;
import com.hyj.order.mapper.TOrderMapper;
import com.hyj.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.order.utils.OrderNoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2023-02-08
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
	@Autowired
	private EduClient eduClient;

	@Autowired
	private UcenterClient ucenterClient;

	@Autowired
	private TOrderService tOrderService;
	@Override
	public String createOrders(String courseId, String memberId) {
		//远程调用根据课程id获得课程信息
		CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

		//远程调用根据用户id获得用户信息
		UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);

		//创建order对象，向order中设置需要的值
		TOrder order = new TOrder();
		order.setOrderNo(OrderNoUtils.getOrderNo());
		order.setCourseId(courseId);
		order.setCourseTitle(courseInfoOrder.getTitle());
		order.setCourseCover(courseInfoOrder.getCover());
		order.setTeacherName(courseInfoOrder.getTeacherName());
		order.setTotalFee(courseInfoOrder.getPrice());
		order.setMemberId(memberId);
		order.setMobile(userInfoOrder.getMobile());
		order.setNickname(userInfoOrder.getNickname());
		order.setStatus(0);
		order.setPayType(1);
		baseMapper.insert(order);

		//返回订单号
		return order.getOrderNo();
	}

	@GetMapping("isBuyCourse/{memberid}/{id}")
	public boolean isBuyCourse(@PathVariable String memberid,
							   @PathVariable String id) {
		//订单状态是1表示支付成功
		int count = tOrderService.count(new QueryWrapper<TOrder>().eq("member_id",
				memberid).eq("course_id", id).eq("status", 1));
		if(count>0) {
			return true;
		} else {
			return false;
		}
	}
}
