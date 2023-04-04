package com.kdatalab.bridge.adminpage.projectlist.dto;

import com.kdatalab.bridge.adminpage.projectlist.projection.Task;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import lombok.Data;

import java.util.List;

@Data
public class TaskListDto {
    Integer overallProgressRateByProject;
    Integer totalPointByProject;
    ProjectRegistrationDto projectInfo;
    List<Task> taskList;
}
