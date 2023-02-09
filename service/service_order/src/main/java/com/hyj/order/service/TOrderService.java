package com.hyj.order.service;

import com.hyj.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author test.java
 * @since 2023-02-08
 */
public interface TOrderService extends IService<TOrder> {

	String createOrders(String courseId, String memberId);
}
