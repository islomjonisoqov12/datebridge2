package com.kdatalab.bridge.adminpage.projectlist.service;

import com.kdatalab.bridge.adminpage.projectlist.projection.Project;
import com.kdatalab.bridge.adminpage.projectlist.repository.ProjectListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectListService {

    private final ProjectListRepository projectListRepository;
    @Override
    public List<String> getProjectTypes() {
        return projectListRepository.getProjectTypes();
    }

    @Override
    public List<Project> getProjectList(String type, String projectType) {
        return projectListRepository.getProjects(type, projectType);
    }

    @Override
    public Project getProjectDetails(Integer projectId) {
        return projectListRepository.getProjectDetails(projectId);
    }
}
