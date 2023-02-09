package com.hyj.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.commonutils.JWTUtils;
import com.hyj.commonutils.R;
import com.hyj.commonutils.vo.CourseWebVoOrder;
import com.hyj.edu.client.OrderClient;
import com.hyj.edu.entity.Chapter;
import com.hyj.edu.entity.Course;
import com.hyj.edu.entity.chapter.ChapterVo;
import com.hyj.edu.entity.vo.frontvo.CourseQueryVo;
import com.hyj.edu.entity.vo.frontvo.CourseWebVo;
import com.hyj.edu.service.ChapterService;
import com.hyj.edu.service.CourseService;
import com.hyj.edu.service.TeacherService;
import com.hyj.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
	@Autowired
	private CourseService courseService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private ChapterService chapterService;

	@Autowired
	private VideoService videoService;

	@Autowired
	private OrderClient orderClient;

	@PostMapping("/getFrontCourseList/{page}/{limit}")
	public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
								@RequestBody(required = false) CourseQueryVo courseQueryVo) {
		Page<Course> coursePage = new Page<>(page, limit);
		Map<String, Object> map = courseService.getFrontCourseList(coursePage, courseQueryVo);
		return R.ok().data(map);
	}

	//远程调用根据课程id获得课程信息
	@PostMapping("/getCourseInfoOrder/{id}")
	public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
		CourseWebVo courseInfo = courseService.baseCourseInfo(id);
		CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
		BeanUtils.copyProperties(courseInfo, courseWebVoOrder);
		return courseWebVoOrder;
	}

	@GetMapping("getFrontCourseInfo/{courseId}")
	public R getFrontCourseInfo(@PathVariable String courseId,HttpServletRequest request){
		//根据课程id，编写sql语句查询课程信息
		CourseWebVo courseWebVo = courseService.baseCourseInfo(courseId);
		//根据课程id，查询章节和小节
		List<ChapterVo> chapterVoList = chapterService.getAllChapterById(courseId);
		//根据课程Id和用户ID查询订单表中是否支付
		String memberId = JWTUtils.getMemberIdByJwtToken(request);//取出用户id
		boolean buyCourse = orderClient.isBuyCourse(courseId, memberId);
		return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVoList).data("isBuy",buyCourse);
	}
}
