package com.kdatalab.bridge.mypage.service;

import com.kdatalab.bridge.mypage.model.Project;
import com.kdatalab.bridge.mypage.model.ProjectDetail;
import com.kdatalab.bridge.mypage.repository.MyPageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MyPageRepository myPageRepository;

    @Override
    public List<Project> getProjectList(String userId, String projectStatus) throws Exception {
        if (projectStatus == null || projectStatus.equals("")) {
            projectStatus = "INPROGRESS";
        }
        List<Project> projectListByUser = myPageRepository.getProjectListByUser(userId, projectStatus);
        return projectListByUser;
    }

    @Override
    public ProjectDetail getProjectDetail(String  loginId) {
        return myPageRepository.getProjectInfoByUser(loginId);
    }
}
