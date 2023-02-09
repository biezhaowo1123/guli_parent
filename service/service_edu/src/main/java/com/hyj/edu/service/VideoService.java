package com.hyj.edu.service;

import com.hyj.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
public interface VideoService extends IService<Video> {

	void deleteVideoByCourseId(String id);
}
