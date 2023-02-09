package com.hyj.edu.controller;


import com.hyj.commonutils.R;
import com.hyj.edu.client.VodClient;
import com.hyj.edu.entity.Video;
import com.hyj.edu.service.VideoService;
import com.hyj.servicebase.handler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/eduservice/video")
public class VideoController {
	@Autowired
	private VideoService videoService;

	@Autowired
	private VodClient vodClient;
	@PostMapping("/addVideo")
	public R addVideo(@RequestBody Video video) {
		System.out.println(video);
		videoService.save(video);
		return R.ok();
	}

	@DeleteMapping("/deleteVideo/{id}")
	public R deleteVideo(@PathVariable String id) {
		Video video = videoService.getById(id);
		System.out.println(video);
		String videoCourseId = video.getVideoSourceId();
		if (!StringUtils.isEmpty(videoCourseId)) {
			vodClient.deleteVideo(videoCourseId);
		}
		videoService.removeById(id);
		return R.ok();
	}

	@PostMapping("/updateVideo")
	public R updateVideo(@RequestBody Video video) {
		videoService.updateById(video);
		return R.ok();
	}

	@GetMapping("/getVideo/{id}")
	public R getVideo(@PathVariable String id) {
		Video video = videoService.getById(id);
		return R.ok().data("video", video);
	}
}

