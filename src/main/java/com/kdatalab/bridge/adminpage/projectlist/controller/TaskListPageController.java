package com.kdatalab.bridge.adminpage.projectlist.controller;

import com.kdatalab.bridge.adminpage.projectlist.projection.Task;
import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
import com.kdatalab.bridge.adminpage.projectlist.service.TaskListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin_task-list")
@RequiredArgsConstructor
public class TaskListPageController extends BaseController {

    private final TaskListService taskListService;

    private final ProjectRegistrationService projectRegistrationService;

    @GetMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String taskList(@PathVariable("projectId") Integer projectId, Model model) {
        List<Task> taskList = taskListService.getTaskList(projectId);
        Integer overallProgressRateByProject = taskListService.getOverallProgressRateByProject(projectId);
        ProjectRegistrationDto projectDetailsById = projectRegistrationService.getProjectDetailsById(projectId);
        Integer totalPointByProject = taskListService.getTotalPointByProject(projectId);

        model.addAttribute("totalPointByProject", totalPointByProject);
        model.addAttribute("avgRate", overallProgressRateByProject);
        model.addAttribute("projectInfo", projectDetailsById);
        model.addAttribute("taskList", taskList);
        return "admin/task-list";
    }
}
