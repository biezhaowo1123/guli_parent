package com.hyj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.commonutils.R;
import com.hyj.edu.entity.Course;
import com.hyj.edu.entity.Teacher;
import com.hyj.edu.service.CourseService;
import com.hyj.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;
	@GetMapping("/getTeacherFrontList/{page}/{limit}")
	public R getTeacherFrontList(@PathVariable long page, @PathVariable long limit) {
		Page<Teacher> teacherPage = new Page<>(page, limit);
		Map<String, Object> map = teacherService.getTeacherFrontList(teacherPage);
		return R.ok().data(map);
	}

	@GetMapping("/getTeacherFrontInfo/{id}")
	public R getTeacherFrontInfo(@PathVariable long id) {
		Teacher teacher = teacherService.getById(id);
		List<Course> courseList = courseService.list(new QueryWrapper<Course>().eq("teacher_id", id));
		return R.ok().data("teacher", teacher).data("courseList", courseList);
	}
}
