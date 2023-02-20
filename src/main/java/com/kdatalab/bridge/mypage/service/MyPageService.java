package com.kdatalab.bridge.mypage.service;

import com.kdatalab.bridge.mypage.model.Project;
import com.kdatalab.bridge.mypage.model.ProjectDetail;

import java.util.List;


public interface MyPageService {
    List<Project> getProjectList(String userId, String projectStatus) throws Exception;
    ProjectDetail getProjectDetail(String loginId);
}
