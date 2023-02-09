package com.hyj.edu.service;

import com.hyj.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

	void deleteDescriptionByCourseId(String id);
}
