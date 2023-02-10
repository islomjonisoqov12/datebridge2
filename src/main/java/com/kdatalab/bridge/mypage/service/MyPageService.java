package com.kdatalab.bridge.mypage.service;

import com.kdatalab.bridge.mypage.model.Project;
import com.kdatalab.bridge.mypage.model.ProjectDetail;

import java.util.List;
import java.util.Map;

public interface MyPageService {
    List<Project> getProjectList(String userId);
    ProjectDetail getProjectDetail(String loginId);
}
