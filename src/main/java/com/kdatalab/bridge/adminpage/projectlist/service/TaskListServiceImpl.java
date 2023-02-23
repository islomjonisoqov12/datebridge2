package com.kdatalab.bridge.adminpage.projectlist.service;

import com.amazonaws.services.simpleworkflow.flow.ActivitySchedulingOptions;
import com.kdatalab.bridge.adminpage.projectlist.projection.Task;
import com.kdatalab.bridge.adminpage.projectlist.repository.TaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;
    @Override
    public List<Task> getTaskList(Integer projectId) {
        return taskListRepository.getTaskList(projectId);
    }

    @Override
    public Integer getOverallProgressRateByProject(Integer projectId) {
        return taskListRepository.getOverallProgressRateByProject(projectId);
    }

    @Override
    public Integer getTotalPointByProject(Integer projectId) {
        return taskListRepository.getTotalPointByProject(projectId);
    }
}
