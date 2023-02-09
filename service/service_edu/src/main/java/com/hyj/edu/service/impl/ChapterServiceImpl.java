package com.hyj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.edu.entity.Chapter;
import com.hyj.edu.entity.Video;
import com.hyj.edu.entity.chapter.ChapterVo;
import com.hyj.edu.entity.chapter.VideoVo;
import com.hyj.edu.mapper.ChapterMapper;
import com.hyj.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.edu.service.VideoService;
import com.hyj.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
	@Autowired
	private VideoService videoService;

	@Override
	public List<ChapterVo> getAllChapterById(String courseId) {
		ArrayList<ChapterVo> finalChapterList = new ArrayList<>();
		QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
		chapterQueryWrapper.eq("course_id", courseId);
		List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		videoQueryWrapper.eq("course_id", courseId);
		List<Video> videoList = videoService.list(videoQueryWrapper);

		for (int i = 0; i < chapterList.size(); i++) {
			Chapter chapter = chapterList.get(i);
			ChapterVo chapterVo = new ChapterVo();
			BeanUtils.copyProperties(chapter, chapterVo);

			ArrayList<VideoVo> videoVos = new ArrayList<>();
			for (int j = 0; j < videoList.size(); j++) {
				Video video = videoList.get(j);

				if (video.getChapterId().equals(chapter.getId())) {
					VideoVo videoVo = new VideoVo();
					BeanUtils.copyProperties(video, videoVo);
					videoVos.add(videoVo);
				}
			}
			chapterVo.setChildren(videoVos);
			finalChapterList.add(chapterVo);
		}
		return finalChapterList;
	}

	@Override
	public Boolean deleteChapter(String chapterId) {
		QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
		videoQueryWrapper.eq("chapter_id", chapterId);
		int count = videoService.count(videoQueryWrapper);
		if (count > 0) {
			throw new GuliException(20001, "不能删除");
		} else {
			int i = baseMapper.deleteById(chapterId);
			return i > 0;
		}
	}

	@Override
	public void deleteChapterByCourseId(String id) {
		QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
		wrapper.eq("course_id", id);
		baseMapper.delete(wrapper);
	}
}
