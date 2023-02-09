package com.hyj.edu.controller;


import com.hyj.commonutils.R;
import com.hyj.edu.entity.subject.OneSubject;
import com.hyj.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author test.java
 * @since 2022-10-02
 */
@RestController
@RequestMapping("/eduservice/subject")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);

        return R.ok();
    }

    @GetMapping("/getAllSubject")
    public R getAllSubject() {
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list", list);
    }
}

