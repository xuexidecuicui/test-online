package com.exam.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.builder.ExcelReaderSheetBuilder;
import com.exam.entity.*;
import com.exam.service.*;
import com.exam.util.ApiResultHandler;
import com.exam.util.FillReadListener;
import com.exam.vo.Item;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

@RestController
public class ItemController {

    @Autowired
    MultiQuestionService multiQuestionService;

    @Autowired
    FillQuestionService fillQuestionService;

    @Autowired
    JudgeQuestionService judgeQuestionService;

    @Autowired
    PaperService paperService;
    @Autowired
    FillReadListener fillReadListener;

    @Autowired
    ExamManageService examManageService;
    @PostMapping("/item")
    public ApiResult ItemController(@RequestBody Item item) {
        // 选择题
        Integer changeNumber = item.getChangeNumber();
        // 填空题
        Integer fillNumber = item.getFillNumber();
        // 判断题
        Integer judgeNumber = item.getJudgeNumber();
        //出卷id
        Integer paperId = item.getPaperId();

        // 选择题数据库获取
        List<Integer>  changeNumbers = multiQuestionService.findBySubject(item.getSubject(), changeNumber);
        if(changeNumbers==null){
            return ApiResultHandler.buildApiResult(400,"选择题数据库获取失败",null);
        }
        for (Integer number : changeNumbers) {
            PaperManage paperManage = new PaperManage(paperId,1,number);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"选择题组卷保存失败",null);
        }

        // 填空题
        List<Integer> fills = fillQuestionService.findBySubject(item.getSubject(), fillNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(400,"填空题数据库获取失败",null);
        for (Integer fillNum : fills) {
            PaperManage paperManage = new PaperManage(paperId,2,fillNum);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"填空题题组卷保存失败",null);
        }
        // 判断题
        List<Integer> judges = judgeQuestionService.findBySubject(item.getSubject(), judgeNumber);
        if(fills==null)
            return ApiResultHandler.buildApiResult(400,"判断题数据库获取失败",null);
        for (Integer judge : judges) {
            PaperManage paperManage = new PaperManage(paperId,3,judge);
            int index = paperService.add(paperManage);
            if(index==0)
                return ApiResultHandler.buildApiResult(400,"判断题题组卷保存失败",null);
        }

        List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(paperId);
        List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(paperId);     //填空题题库 2
        List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(paperId);   //判断题题库 3
        int sizeM = multiQuestionRes.size();
        int sizeF = fillQuestionsRes.size();
        int sizeJ = judgeQuestionRes.size();
        int totalScore = (sizeF+sizeJ+sizeM)*2;
        ExamManage examManage = new ExamManage();
        examManage.setPaperId(paperId);
        examManage.setTotalScore(totalScore);
        examManageService.updateScore(examManage);
          return ApiResultHandler.buildApiResult(200,"试卷组卷成功",null);
    }
    @PostMapping("upload")
    public ApiResult uploadController(@RequestParam MultipartFile file,String paperId){
        System.out.println("-------------"+paperId);
        Class<FillQuestion> fillQuestionClass = FillQuestion.class;
        try {
            EasyExcel.read(file.getInputStream(), fillQuestionClass, new AnalysisEventListener<FillQuestion>() {
                @Override
                public void invoke(FillQuestion fillQuestion, AnalysisContext analysisContext) {
                    int add = fillQuestionService.add(fillQuestion);
                    System.out.println(fillQuestion.getQuestionId());
                    PaperManage pm = new PaperManage();
                    pm.setPaperId(Integer.valueOf(paperId));
                    pm.setQuestionId(fillQuestion.getQuestionId());
                    pm.setQuestionType(2);
                    paperService.add(pm);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet("填空题").doRead();

            EasyExcel.read(file.getInputStream(), MultiQuestion.class, new AnalysisEventListener<MultiQuestion>() {
                @Override
                public void invoke(MultiQuestion multiQuestion, AnalysisContext analysisContext) {
                    int add = multiQuestionService.add(multiQuestion);
                    PaperManage pm = new PaperManage();
                    pm.setPaperId(Integer.valueOf(paperId));
                    pm.setQuestionId(multiQuestion.getQuestionId());
                    pm.setQuestionType(1);
                    paperService.add(pm);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet("选择题").doRead();

            EasyExcel.read(file.getInputStream(), JudgeQuestion.class, new AnalysisEventListener<JudgeQuestion>() {
                @Override
                public void invoke(JudgeQuestion JudgeQuestion, AnalysisContext analysisContext) {
                    int add = judgeQuestionService.add(JudgeQuestion);
                    PaperManage pm = new PaperManage();
                    pm.setPaperId(Integer.valueOf(paperId));
                    pm.setQuestionId(JudgeQuestion.getQuestionId());
                    System.out.println(JudgeQuestion.getQuestionId());
                    pm.setQuestionType(3);
                    paperService.add(pm);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                }
            }).sheet("判断题").doRead();
            List<MultiQuestion> multiQuestionRes = multiQuestionService.findByIdAndType(Integer.valueOf(paperId));
            List<FillQuestion> fillQuestionsRes = fillQuestionService.findByIdAndType(Integer.valueOf(paperId));     //填空题题库 2
            List<JudgeQuestion> judgeQuestionRes = judgeQuestionService.findByIdAndType(Integer.valueOf(paperId));   //判断题题库 3
            int sizeM = multiQuestionRes.size();
            int sizeF = fillQuestionsRes.size();
            int sizeJ = judgeQuestionRes.size();
            int totalScore = (sizeF+sizeJ+sizeM)*2;
            ExamManage examManage = new ExamManage();
            examManage.setPaperId(Integer.valueOf(paperId));
            examManage.setTotalScore(totalScore);
            examManageService.updateScore(examManage);
            file.getInputStream().close();
            return ApiResultHandler.buildApiResult(200,"上传成功",null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiResultHandler.buildApiResult(400,"失败",null);
    }
}
