package com.kdatalab.bridge.mypage.controller;

import com.kdatalab.bridge.adminpage.projectlist.service.TaskListService;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.mypage.service.UserTaskListService;
import com.kdatalab.bridge.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TaskListUserPageController extends BaseController {

    private final ProjectRegistrationService projectRegistrationService;

    private final UserTaskListService userTaskListService;

    private final TaskListService taskListService;
    private final UserService userService;

    @GetMapping("/user-task-list/{projectId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String taskList(@PathVariable("projectId") Integer projectId, Model model) throws Exception {
        String loginId = userService.getUserName();
        model.addAttribute("userTaskList", userTaskListService.getUserTaskList(projectId, loginId));
        model.addAttribute("projectInfo", projectRegistrationService.getProjectDetailsById(projectId));
        model.addAttribute("overallProgress", taskListService.getOverallProgressRateByProject(projectId));
        return "user/taskList";
    }
}
