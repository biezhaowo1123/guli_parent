package com.hyj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.commonutils.R;
import com.hyj.edu.entity.Course;
import com.hyj.edu.entity.Teacher;
import com.hyj.edu.entity.vo.CourseInfoVo;
import com.hyj.edu.entity.vo.CoursePublishVo;
import com.hyj.edu.entity.vo.CourseQuery;
import com.hyj.edu.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/eduservice/course")
public class CourseController {
	@Autowired
	private CourseService courseService;

	@PostMapping("/addCourse")
	public R addCourse(@RequestBody CourseInfoVo courseInfoVo) {
		String id = courseService.saveCourse(courseInfoVo);
		return R.ok().data("courseId", id);
	}

	@PostMapping("/updateCourse")
	public R updateCourse(@RequestBody CourseInfoVo courseInfoVo) {
		courseService.updateCourseById(courseInfoVo);
		return R.ok();
	}

	@GetMapping("/getCourse/{courseId}")
	public R getCourse(@PathVariable String courseId) {
		CourseInfoVo courseInfoVo = courseService.getCourseById(courseId);
		return R.ok().data("courseInfoVo", courseInfoVo);
	}

	@GetMapping("/getCourseInfo/{courseId}")
	public R getCourseInfo(@PathVariable String courseId) {
		CoursePublishVo coursePublishVo = courseService.getCourseInfoById(courseId);
		return R.ok().data("coursePublishVo", coursePublishVo);
	}

	@PostMapping("/publishCourse/{id}")
	public R publishCourse(@PathVariable String id) {
		Course course = new Course();
		course.setId(id);
		course.setStatus("Normal");
		courseService.updateById(course);
		return R.ok();
	}

	@PostMapping("/getAllCourse/{current}/{limit}")
	public R getAllCourse(@PathVariable Long current, @PathVariable Long limit, @RequestBody CourseQuery courseQuery) {
		Page<Course> coursePage = new Page<>(current, limit);
		QueryWrapper<Course> wrapper = new QueryWrapper<>();
		String title = courseQuery.getTitle();
		String status = courseQuery.getStatus();
		//判断条件是否为空，拼接条件
		if (!StringUtils.isEmpty(title)) {
			wrapper.like("title", title);
		}
		if (!StringUtils.isEmpty(status)) {
			wrapper.eq("status", status);
		}
		//调用方法，实现分页查询
		courseService.page(coursePage, wrapper);
		long total = coursePage.getTotal();//获取总记录数
		List<Course> records = coursePage.getRecords();//获取分页后的list集合
		HashMap<String, Object> map = new HashMap<>();
		map.put("total", total);
		map.put("rows", records);
		return R.ok().data(map);
	}

    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return R.ok();
    }

}

