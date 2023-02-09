package com.hyj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.edu.entity.CourseDescription;
import com.hyj.edu.mapper.CourseDescriptionMapper;
import com.hyj.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-12
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

	@Override
	public void deleteDescriptionByCourseId(String id) {
		baseMapper.deleteById(id);
	}
}
