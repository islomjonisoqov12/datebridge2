package com.kdatalab.bridge.adminpage.projectregistration.controller;

import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.AWSService;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.io.IOException;

@Controller
@RequestMapping("/admin/project-registration")
public class ProjectRegistrationController {

    private final ProjectListService projectListService;

    private final ProjectRegistrationService projectRegistrationService;
    private final UserMapper userService;

    private final AWSService awsService;

    public ProjectRegistrationController(ProjectListService projectListService, ProjectRegistrationService projectRegistrationService, UserMapper userService, AWSService awsService) {
        this.projectListService = projectListService;
        this.projectRegistrationService = projectRegistrationService;
        this.userService = userService;
        this.awsService = awsService;
    }


    @GetMapping
    public String projectRegistration(Model model) {
        List<String> projectTypes = projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", new ProjectRegistrationDto());
        return "admin/projectRegistration";
    }

    @PostMapping
    public String projectRegistration(ProjectRegistrationDto dto) throws IOException {
        Integer projectId = projectRegistrationService.createProject(dto);
        return "redirect:/admin/project-registration/step-2/"+projectId;
    }

    @GetMapping("/step-2/{projectId}")
    @PreAuthorize("isFullyAuthenticated()")
    public String getStep2Page(@PathVariable Long projectId, Model model) {
        List<TaskDto> projectWithTasks = projectRegistrationService.getProjectWithTasks(projectId);
        List<UserDto> users = userService.selectUserByQcChk('N');
        List<UserDto> admins = userService.selectUserByQcChk('Y');
        model.addAttribute("tasks", projectWithTasks);
        model.addAttribute("users", users);
        model.addAttribute("admins", admins);
        return "adminpage/projectRegistration2";
    }

}
