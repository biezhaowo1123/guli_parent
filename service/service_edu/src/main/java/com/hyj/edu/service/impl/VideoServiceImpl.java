package com.hyj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.edu.client.VodClient;
import com.hyj.edu.entity.Video;
import com.hyj.edu.mapper.VideoMapper;
import com.hyj.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
	@Autowired
	private VodClient vodClient;
	@Override
	public void deleteVideoByCourseId(String id) {
		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		videoQueryWrapper.eq("course_id", id);
		videoQueryWrapper.select("video_source_id");
		List<Video> videoList = baseMapper.selectList(videoQueryWrapper);
		List<String> list = new ArrayList<>();
		for (int i = 0; i < videoList.size(); i++) {
			Video video  = videoList.get(i);
			String videoId = video.getVideoSourceId();
			if (!StringUtils.isEmpty(videoId)) {
				list.add(videoId);
			}
		}
		if (list.size() > 0) {
			vodClient.deleteMoreVideos(list);
		}
		QueryWrapper<Video> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id", id);
		baseMapper.delete(wrapper);
	}
}
