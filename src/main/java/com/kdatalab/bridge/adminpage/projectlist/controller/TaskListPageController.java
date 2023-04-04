package com.kdatalab.bridge.adminpage.projectlist.controller;

import com.kdatalab.bridge.adminpage.projectlist.dto.TaskListDto;
import com.kdatalab.bridge.adminpage.projectlist.projection.Task;
import com.kdatalab.bridge.adminpage.projectlist.service.TaskListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static org.springframework.web.bind.annotation.RequestMethod.OPTIONS;

@CrossOrigin(origins = "*", maxAge = 3600, exposedHeaders = "*",methods = {POST, GET, PUT, PATCH, DELETE, OPTIONS}, allowedHeaders = "*")
@RestController
@RequestMapping("/task-list")
@RequiredArgsConstructor
@Tag(name = "Task List for admin", description = "The Task List API. Contains all the operations that can be performed on a task list.")
public class TaskListPageController extends BaseController {

    private final TaskListService taskListService;

    private final ProjectRegistrationService projectRegistrationService;

    @GetMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get task list for task list in admin page")
    public ResponseEntity<TaskListDto> taskList(@PathVariable("projectId") Integer projectId) {
        List<Task> taskList = taskListService.getTaskList(projectId);
        Integer overallProgressRateByProject = taskListService.getOverallProgressRateByProject(projectId);
        ProjectRegistrationDto projectDetailsById = projectRegistrationService.getProjectDetailsById(projectId);
        Integer totalPointByProject = taskListService.getTotalPointByProject(projectId);
        TaskListDto taskListDto = new TaskListDto();
        taskListDto.setTaskList(taskList);
        taskListDto.setOverallProgressRateByProject(overallProgressRateByProject);
        taskListDto.setProjectInfo(projectDetailsById);
        taskListDto.setTotalPointByProject(totalPointByProject);
        return ResponseEntity.ok(taskListDto);
    }
}
