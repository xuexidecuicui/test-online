package com.exam.service;

import com.exam.entity.PaperManage;
import io.swagger.models.auth.In;

import java.util.List;

public interface PaperService {

    List<PaperManage> findAll();

    List<PaperManage> findById(Integer paperId);

    int add(PaperManage paperManage);

    boolean deletePaper(Integer paperId);

    boolean deleteQuestion(Integer questionId);
}
