package com.kdatalab.bridge.adminpage.projectregistration.service;

import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.adminpage.projectregistration.mapper.ProjectRegistrationMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProjectRegistrationService {
    private final ProjectRegistrationMapper mapper;

    public ProjectRegistrationService(ProjectRegistrationMapper mapper) {
        this.mapper = mapper;
    }

    public List<TaskDto> getProjectWithTasks(Long projectId) {
        return mapper.selectTasksByProjectId(projectId);
    }


}
