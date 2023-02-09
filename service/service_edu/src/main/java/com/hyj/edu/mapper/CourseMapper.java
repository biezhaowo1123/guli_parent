package com.hyj.edu.mapper;

import com.hyj.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyj.edu.entity.vo.CoursePublishVo;
import com.hyj.edu.entity.vo.frontvo.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
public interface CourseMapper extends BaseMapper<Course> {
	public CoursePublishVo getCourseInfo(String id);

	CourseWebVo getBaseCourseInfo(String id);
}
