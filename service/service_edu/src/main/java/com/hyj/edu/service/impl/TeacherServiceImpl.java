package com.hyj.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.edu.entity.Teacher;
import com.hyj.edu.mapper.TeacherMapper;
import com.hyj.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-09-19
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
	@Cacheable(value = "teacher", key = "'selectIndexList'")
	@Override
	public List<Teacher> getIndex() {
		QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
		teacherQueryWrapper.orderByDesc("id");
		teacherQueryWrapper.last("limit 4");
		List<Teacher> teacherList = baseMapper.selectList(teacherQueryWrapper);
		return teacherList;
	}

	@Override
	public Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage) {
		baseMapper.selectPage(teacherPage, new QueryWrapper<Teacher>().orderByDesc("id"));
		HashMap<String, Object> map = new HashMap<>();
		map.put("items", teacherPage.getRecords());
		map.put("total", teacherPage.getTotal());
		map.put("pages", teacherPage.getPages());
		map.put("current", teacherPage.getCurrent());
		map.put("size", teacherPage.getSize());
		map.put("hasNext", teacherPage.hasNext());
		map.put("hasPrevious", teacherPage.hasPrevious());
		return map;
	}
}
