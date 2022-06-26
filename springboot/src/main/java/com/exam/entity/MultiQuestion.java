package com.exam.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

// 选择题实体
@Data
public class MultiQuestion {
    @ExcelIgnore
    private Integer questionId;
    @ExcelProperty("考试科目")
    private String subject;
    @ExcelProperty("所属章节")
    private String section;
    @ExcelProperty("选项A")
    private String answerA;
    @ExcelProperty("选项B")
    private String answerB;
    @ExcelProperty("选项C")
    private String answerC;
    @ExcelProperty("选项D")
    private String answerD;
    @ExcelProperty("题目")
    private String question;
    @ExcelProperty("难度等级")
    private String level;
    @ExcelProperty("正确答案")
    private String rightAnswer;
    @ExcelProperty("题目解析")
    private String analysis; //题目解析
    @ExcelProperty("分数")
    private Integer score;

}