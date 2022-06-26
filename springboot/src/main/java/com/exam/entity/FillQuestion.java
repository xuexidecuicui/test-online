package com.exam.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.beans.Transient;

//填空题实体类



public class FillQuestion {
    @ExcelIgnore
    private Integer questionId;
    @ExcelProperty("考试科目")
    private String subject;
    @ExcelProperty("题目")
    private String question;
    @ExcelProperty("正确答案")
    private String answer;
    @ExcelProperty("分数")
    private Integer score;
    @ExcelProperty("难度等级")
    private String level;
    @ExcelProperty("所属章节")
    private String section;
    @ExcelProperty("题目解析")
    private String analysis; //题目解析

    public FillQuestion() {
    }

    public FillQuestion(Integer questionId, String subject, String question, String answer, Integer score, String level, String section, String analysis) {
        this.questionId = questionId;
        this.subject = subject;
        this.question = question;
        this.answer = answer;
        this.score = score;
        this.level = level;
        this.section = section;
        this.analysis = analysis;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
