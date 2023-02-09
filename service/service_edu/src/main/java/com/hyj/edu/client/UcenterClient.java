package com.hyj.edu.client;

import com.hyj.commonutils.vo.UcenterMember;
import com.hyj.edu.client.impl.UcenterImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Component
@FeignClient(name = "service-ucenter", fallback = UcenterImpl.class)
public interface UcenterClient {
	@PostMapping("/ucenter/member/getMember/{id}")
	public UcenterMember getMember(@PathVariable("id") String id);
}
