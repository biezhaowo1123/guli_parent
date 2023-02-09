package com.hyj.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hyj.commonutils.R;
import com.hyj.edu.entity.Teacher;
import com.hyj.edu.entity.vo.TeacherQuery;
import com.hyj.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-09-19
 */
@Api("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询全部导师")
    @GetMapping("/findAll")
    public R findAll() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation("逻辑删除导师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean flag = teacherService.removeById(id);

        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

    @ApiOperation("讲师分页查询")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long limit) {
        Page<Teacher> teacherPage = new Page<>(current, limit);

        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        teacherService.page(teacherPage, wrapper);
        long total = teacherPage.getTotal();
        List<Teacher> records = teacherPage.getRecords();
        Map map = new HashMap();
        map.put("total", total);
        map.put("records", records);

        return R.ok().data(map);
    }

    @ApiOperation("讲师分页带条件查询")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable Long current, @PathVariable Long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> pageCondition = new Page<>(current, limit);
        //QueryWrapper,构建
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        //多条件组合查询，动态sql
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，拼接条件
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }

        wrapper.orderByDesc("gmt_create");

        //调用方法，实现分页查询
        teacherService.page(pageCondition, wrapper);

        long total = pageCondition.getTotal();//获取总记录数
        List<Teacher> records = pageCondition.getRecords();//获取分页后的list集合
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return R.ok().data(map);

    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R add(@RequestBody Teacher teacher) {
        boolean save = teacherService.save(teacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacherById(@PathVariable String id) {
        Teacher teacher = teacherService.getById(id);

        return R.ok().data("teacher", teacher);
    }
    @ApiOperation("修改")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher) {
        boolean flag = teacherService.updateById(teacher);
        if (flag == true) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}

