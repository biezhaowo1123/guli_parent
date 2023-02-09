package com.hyj.edu.client;

import com.hyj.commonutils.R;
import com.hyj.edu.client.impl.VodClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@FeignClient(name = "service-vod", fallback = VodClientImpl.class)
public interface VodClient {
	@DeleteMapping("/eduvod/video/{videoId}")
	public R deleteVideo(@PathVariable("videoId") String videoId);

	@DeleteMapping("/eduvod/video/deleteMoreVideos")
	public R deleteMoreVideos(@RequestParam("videoList") List<String> videolist);
}
