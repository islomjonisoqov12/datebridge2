package com.kdatalab.bridge.adminpage.projectlist.service;

import com.kdatalab.bridge.adminpage.projectlist.projection.Project;
import com.kdatalab.bridge.adminpage.projectlist.repository.ProjectListRepository;
import com.kdatalab.bridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectListService {

    private final ProjectListRepository projectListRepository;

    private final UserService userService;
    @Override
    public List<String> getProjectTypes() {
        return projectListRepository.getProjectTypes();
    }

    @Override
    public List<Project> getProjectList(String type, String projectType) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String qcId = authentication.getName();
        return projectListRepository.getProjects(type, projectType, qcId);
    }

    @Override
    public Project getProjectDetails(Integer projectId) {
        return projectListRepository.getProjectDetails(projectId);
    }
}
