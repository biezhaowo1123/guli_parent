package com.hyj.edu.client.impl;

import com.hyj.commonutils.R;
import com.hyj.edu.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodClientImpl implements VodClient {
	@Override
	public R deleteVideo(String videoId) {
		return R.error().message("删除视频失败了");
	}

	@Override
	public R deleteMoreVideos(List<String> videolist) {
		return R.error().message("删除视频失败了");
	}
}
