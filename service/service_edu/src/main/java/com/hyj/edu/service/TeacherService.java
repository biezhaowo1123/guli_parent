package com.hyj.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-09-19
 */
public interface TeacherService extends IService<Teacher> {

	List<Teacher> getIndex();

	Map<String, Object> getTeacherFrontList(Page<Teacher> teacherPage);
}
