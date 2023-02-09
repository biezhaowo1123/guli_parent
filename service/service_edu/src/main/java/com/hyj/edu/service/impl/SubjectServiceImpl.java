package com.hyj.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.edu.entity.Subject;
import com.hyj.edu.entity.excel.SubjectData;
import com.hyj.edu.entity.subject.OneSubject;
import com.hyj.edu.entity.subject.TwoSubject;
import com.hyj.edu.listener.SubjectExcelListener;
import com.hyj.edu.mapper.SubjectMapper;
import com.hyj.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author test.java
 * @since 2022-10-02
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void saveSubject(MultipartFile file, SubjectService subjectService) {

        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<Subject> oneSubject = new QueryWrapper<>();
        oneSubject.eq("parent_id", "0");
        List<Subject> oneSubjectList = baseMapper.selectList(oneSubject);

        QueryWrapper<Subject> twoSubject = new QueryWrapper<>();
        twoSubject.ne("parent_id", "0");
        List<Subject> twoSubjectList = baseMapper.selectList(twoSubject);

        ArrayList<OneSubject> finalSubject = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            Subject subject = oneSubjectList.get(i);
            OneSubject oneFinalSubject = new OneSubject();
            BeanUtils.copyProperties(subject, oneFinalSubject);


            ArrayList<TwoSubject> twoFinalSubjects = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                Subject subjects = twoSubjectList.get(j);
                if (subject.getId().equals(subjects.getParentId())) {
                    TwoSubject twoFinalSubject = new TwoSubject();
                    BeanUtils.copyProperties(subjects, twoFinalSubject);
                    twoFinalSubjects.add(twoFinalSubject);
                }
            }
            oneFinalSubject.setChildren(twoFinalSubjects);
            finalSubject.add(oneFinalSubject);
        }

        return finalSubject;
    }
}
