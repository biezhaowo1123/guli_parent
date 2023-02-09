package com.hyj.edu.controller;


import com.hyj.commonutils.R;
import com.hyj.edu.client.VodClient;
import com.hyj.edu.entity.Chapter;
import com.hyj.edu.entity.chapter.ChapterVo;
import com.hyj.edu.entity.vo.CourseInfoVo;
import com.hyj.edu.service.ChapterService;
import com.hyj.edu.service.CourseService;
import com.hyj.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduservice/chapter")
public class ChapterController {
	@Autowired
	private ChapterService chapterService;

	@GetMapping("/getChapter/{courseId}")
	public R getChapter(@PathVariable String courseId) {
		List<ChapterVo> list = chapterService.getAllChapterById(courseId);
		return R.ok().data("AllChapter", list);
	}

	@PostMapping("/addChapter")
	public R addChapter(@RequestBody Chapter chapter) {
		chapterService.save(chapter);
		return R.ok();
	}

	@GetMapping("/getChapterById/{chapterId}")
	public R getChapterById(@PathVariable String chapterId) {
		Chapter chapter = chapterService.getById(chapterId);
		return R.ok().data("chapter", chapter);
	}

	@PostMapping("/updateChapter")
	public R updateChapter(@RequestBody Chapter chapter) {
		chapterService.updateById(chapter);
		return R.ok();
	}

	@DeleteMapping("deleteChapter/{chapterId}")
	public R deleteChapter(@PathVariable String chapterId) {
		Boolean flag = chapterService.deleteChapter(chapterId);
		return R.ok().data("flag", flag);
	}
}

