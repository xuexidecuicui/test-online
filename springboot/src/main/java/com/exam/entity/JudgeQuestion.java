package com.exam.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

//判断题实体类
@Data
public class JudgeQuestion {
    @ExcelIgnore
    private Integer questionId;
    @ExcelProperty("考试科目")
    private String subject;
    @ExcelProperty("题目")
    private String question;
    @ExcelProperty("答案")
    private String answer;
    @ExcelProperty("难度等级")
    private String level;
    @ExcelProperty("所属章节")
    private String section;
    @ExcelProperty("分数")
    private Integer score;
    @ExcelProperty("题目解析")
    private String analysis; //题目解析
}