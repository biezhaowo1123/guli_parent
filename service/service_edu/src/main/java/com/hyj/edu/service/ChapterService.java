package com.hyj.edu.service;

import com.hyj.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getAllChapterById(String courseId);

	Boolean deleteChapter(String chapterId);

	void deleteChapterByCourseId(String id);
}
