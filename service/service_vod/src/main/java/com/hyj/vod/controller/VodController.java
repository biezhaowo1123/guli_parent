package com.hyj.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.hyj.commonutils.R;
import com.hyj.vod.service.VodService;
import com.hyj.vod.utils.AliYunVodUtils;
import com.hyj.vod.utils.ConstantPropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController()
@RequestMapping("/eduvod/video")
public class VodController {
	@Autowired
	private VodService vodService;
	@PostMapping("/uploadVideo")
	public R uploadVideo(MultipartFile file) {

		String video = vodService.uploadVideo(file);
		return R.ok().data("videoId", video);
	}

	@DeleteMapping("{videoId}")
	public R deleteVideo(@PathVariable() String videoId) {
		vodService.removeVideo(videoId);
		return R.ok();
	}

	@DeleteMapping("/deleteMoreVideos")
	public R deleteMoreVideos(@RequestParam("videoList")List<String> videolist) {
		vodService.removeMoreVideos(videolist);
		return R.ok();
	}

	@GetMapping("getPlayAuth/{videoId}")
	public R getVideoPlayAuth(@PathVariable("videoId") String videoId) throws
			Exception {
		//获取阿里云存储相关常量
		String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
		String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
		//初始化
		DefaultAcsClient client = AliYunVodUtils.initVodClient(accessKeyId,
				accessKeySecret);
		//请求
		GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
		request.setVideoId(videoId);
		//响应
		GetVideoPlayAuthResponse response = client.getAcsResponse(request);
		//得到播放凭证
		String playAuth = response.getPlayAuth();
		//返回结果
		return R.ok().message("获取凭证成功").data("playAuth", playAuth);
	}
}
