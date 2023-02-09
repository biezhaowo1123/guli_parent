package com.hyj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.edu.entity.vo.CourseInfoVo;
import com.hyj.edu.entity.vo.CoursePublishVo;
import com.hyj.edu.entity.vo.frontvo.CourseQueryVo;
import com.hyj.edu.entity.vo.frontvo.CourseWebVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
public interface CourseService extends IService<Course> {

    String saveCourse(CourseInfoVo courseInfoVo);

	CourseInfoVo getCourseById(String courseId);

	void updateCourseById(CourseInfoVo courseInfoVo);

	CoursePublishVo getCourseInfoById(String courseId);

	void deleteCourse(String id);

	List<Course> getIndex();

	Map<String, Object> getFrontCourseList(Page<Course> coursePage, CourseQueryVo courseQueryVo);

	CourseWebVo baseCourseInfo(String id);

	public void updatePageViewCount(String id);
}
