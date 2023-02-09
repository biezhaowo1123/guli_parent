package com.hyj.edu.service;

import com.hyj.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author test.java
 * @since 2022-10-02
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file, SubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
