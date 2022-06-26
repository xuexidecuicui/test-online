package com.exam.util;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.exam.entity.FillQuestion;
import com.exam.entity.PaperManage;
import com.exam.service.FillQuestionService;
import com.exam.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FillReadListener extends AnalysisEventListener<FillQuestion> {
    @Autowired
    public FillQuestionService fillQuestionService;

    @Override
    public void invoke(FillQuestion fillQuestion, AnalysisContext analysisContext) {

        int add = fillQuestionService.add(fillQuestion);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
