package com.hyj.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hyj.edu.entity.Subject;
import com.hyj.edu.entity.excel.SubjectData;
import com.hyj.edu.service.SubjectService;
import com.hyj.servicebase.handler.GuliException;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    public SubjectService subjectService;

    public SubjectExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }
    public SubjectExcelListener() {
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }

        Subject existOne = this.existOne(subjectService, subjectData.getOneSubjectName());
        if (existOne == null) {
            existOne = new Subject();
            existOne.setParentId("0");
            existOne.setTitle(subjectData.getOneSubjectName());
            subjectService.save(existOne);
        }
        String pid = existOne.getId();
        Subject existTwo = this.existTwo(subjectService, subjectData.getTwoSubjectName(), pid);
        if (existTwo == null) {
            existTwo = new Subject();
            existTwo.setTitle(subjectData.getTwoSubjectName());
            existTwo.setParentId(pid);
            subjectService.save(existTwo);
        }
    }

    private Subject existOne(SubjectService subjectService, String name) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", "0");
        Subject subject = subjectService.getOne(wrapper);
        return subject;
    }

    private Subject existTwo(SubjectService subjectService, String name, String pid) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        Subject subject = subjectService.getOne(wrapper);
        return subject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
