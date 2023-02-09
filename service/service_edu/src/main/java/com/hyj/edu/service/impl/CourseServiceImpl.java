package com.hyj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.edu.entity.Course;
import com.hyj.edu.entity.CourseDescription;
import com.hyj.edu.entity.Video;
import com.hyj.edu.entity.vo.CourseInfoVo;
import com.hyj.edu.entity.vo.CoursePublishVo;
import com.hyj.edu.entity.vo.frontvo.CourseQueryVo;
import com.hyj.edu.entity.vo.frontvo.CourseWebVo;
import com.hyj.edu.mapper.CourseMapper;
import com.hyj.edu.service.ChapterService;
import com.hyj.edu.service.CourseDescriptionService;
import com.hyj.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.edu.service.VideoService;
import com.hyj.servicebase.handler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;
    @Override
    public String saveCourse(CourseInfoVo courseInfoVo) {

        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);

        if (insert == 0) {
            throw new GuliException(20001, "添加失败");
        }

        String courseId = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(courseId);
        courseDescriptionService.save(courseDescription);
        return courseId;
    }

    @Override
    public CourseInfoVo getCourseById(String courseId) {
        Course course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseById(CourseInfoVo courseInfoVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update == 0) {
            throw new GuliException(20001, "修改失败");
        }
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCourseInfoById(String courseId) {
        CoursePublishVo courseInfo = baseMapper.getCourseInfo(courseId);
        return courseInfo;
    }

    @Override
    public void deleteCourse(String id) {
        videoService.deleteVideoByCourseId(id);
        chapterService.deleteChapterByCourseId(id);
        courseDescriptionService.deleteDescriptionByCourseId(id);
        baseMapper.deleteById(id);
    }
    @Cacheable(value = "course", key = "'selectIndexList'")
    @Override
    public List<Course> getIndex() {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id");
        courseQueryWrapper.last("limit 8");
        List<Course> courseList = baseMapper.selectList(courseQueryWrapper);
        return courseList;
    }

    @Override
    public Map<String, Object> getFrontCourseList(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectParentId())) {
            wrapper.eq("subject_parent_id", courseQueryVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getSubjectId())) {
            wrapper.eq("subject_id", courseQueryVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseQueryVo.getBuyCountSort())) {
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getGmtCreateSort())) {
            wrapper.orderByDesc("gmt_create");
        }
        if (!StringUtils.isEmpty(courseQueryVo.getPriceSort())) {
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(coursePage, wrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("items", coursePage.getRecords());
        map.put("total", coursePage.getTotal());
        map.put("pages", coursePage.getPages());
        map.put("current", coursePage.getCurrent());
        map.put("size", coursePage.getSize());
        map.put("hasNext", coursePage.hasNext());
        map.put("hasPrevious", coursePage.hasPrevious());
        return map;
    }

    @Override
    public CourseWebVo baseCourseInfo(String id) {
        CourseWebVo baseCourseInfo = baseMapper.getBaseCourseInfo(id);
        this.updatePageViewCount(baseCourseInfo.getId());
        return baseCourseInfo;
    }

    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
    }


}
