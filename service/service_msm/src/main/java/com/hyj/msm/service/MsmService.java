package com.hyj.msm.service;

import java.util.Map;

public interface MsmService {

	boolean send(String phone, String templateCode, Map<String, Object> param) throws Exception;
}
