package com.kdatalab.bridge.adminpage.projectregistration.controller;

import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/project-registration")
public class ProjectRegistrationController{

    private final ProjectRegistrationService service;
    private final UserMapper userService;

    public ProjectRegistrationController(ProjectRegistrationService service, UserMapper userService){
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/step-2/{projectId}")
    @PreAuthorize("isFullyAuthenticated()")
    public String getStep2Page(@PathVariable Long projectId, Model model){
        List<TaskDto> projectWithTasks = service.getProjectWithTasks(projectId);
        List<UserDto> users = userService.selectUserByQcChk('N');
        List<UserDto> admins = userService.selectUserByQcChk('Y');
        model.addAttribute("tasks", projectWithTasks);
        model.addAttribute("users", users);
        model.addAttribute("admins", admins);
        return "adminpage/projectRegistration2";
    }

}