package com.hyj.edu.client.impl;

import com.hyj.edu.client.OrderClient;
import org.springframework.stereotype.Component;

@Component
public class OrderFile implements OrderClient {
	@Override
	public boolean isBuyCourse(String memberid, String id) {
		return false;
	}
}
