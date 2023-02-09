package com.hyj.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.commonutils.R;
import com.hyj.edu.entity.Course;
import com.hyj.edu.entity.Teacher;
import com.hyj.edu.service.CourseService;
import com.hyj.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {
	@Autowired
	private TeacherService teacherService;

	@Autowired
	private CourseService courseService;
	@GetMapping("/index")
	public R index() {
		List<Teacher> teacherList = teacherService.getIndex();
		List<Course> courseList = courseService.getIndex();
		return R.ok().data("teacherList", teacherList).data("courseList", courseList);
	}
}
