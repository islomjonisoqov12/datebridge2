package com.kdatalab.bridge.adminpage.projectlist.service;

import com.kdatalab.bridge.adminpage.projectlist.projection.Task;

import java.util.List;

public interface TaskListService {
    List<Task> getTaskList(Integer projectId);

    Integer getOverallProgressRateByProject(Integer projectId);

    Integer getTotalPointByProject(Integer projectId);
}
