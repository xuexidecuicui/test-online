package com.exam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.exam.entity.ApiResult;
import com.exam.entity.FillQuestion;
import com.exam.entity.JudgeQuestion;
import com.exam.entity.MultiQuestion;
import com.exam.service.*;
import com.exam.util.ApiResultHandler;
import com.exam.vo.AnswerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    MultiQuestionService multiQuestionService;

    @Autowired
    FillQuestionService fillQuestionService;

    @Autowired
    JudgeQuestionService judgeQuestionService;

    @Autowired
    PaperService paperService;
    @GetMapping("/answers/{page}/{size}")
    public ApiResult findAllQuestion(@PathVariable("page") Integer page, @PathVariable("size") Integer size){
       Page<AnswerVO> answerVOPage = new Page<>(page,size);
       IPage<AnswerVO> answerVOIPage = answerService.findAll(answerVOPage);
       return ApiResultHandler.buildApiResult(200,"查询所有题库",answerVOIPage);
    }
    @PostMapping("/answers/{questionId}/{type}")
    public ApiResult delete(@PathVariable("questionId") Integer questionId,@PathVariable("type") String type){
        System.out.println(questionId);
        System.out.println(type);
        ApiResult paper = getApiResult(questionId,type);
        return paper;
    }

    private ApiResult getApiResult(Integer questionId,String type) {
        if(type.equals("选择题")){
            boolean multi = multiQuestionService.selectQuestionId(questionId);
            if (multi){
                int deleteMulti = multiQuestionService.delete(questionId);
                if (deleteMulti!=0){
                    boolean paper= paperService.deleteQuestion(questionId);
                    return ApiResultHandler.buildApiResult(200,"删除成功",paper);
                }
            }
        }else if (type.equals("填空题")){
            Integer fill = fillQuestionService.selectQuestionId(questionId);
            if (fill!=null){
                int deleteFill = fillQuestionService.delete(questionId);
                System.out.println(deleteFill);
                if (deleteFill!=0){
                    boolean paper= paperService.deleteQuestion(questionId);
                    return ApiResultHandler.buildApiResult(200,"删除成功",paper);
                }
            }
        }else {
            boolean judge = judgeQuestionService.selectQuestionId(questionId);
            if (judge){
                int deleteJudge = judgeQuestionService.delete(questionId);
                if (deleteJudge!=0){
                    boolean paper= paperService.deleteQuestion(questionId);
                    return ApiResultHandler.buildApiResult(200,"删除成功",paper);
                }
            }
        }
        return ApiResultHandler.buildApiResult(200,"删除失败",false);
    }
}
