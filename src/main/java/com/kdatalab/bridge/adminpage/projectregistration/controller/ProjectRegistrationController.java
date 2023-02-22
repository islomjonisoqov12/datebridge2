package com.kdatalab.bridge.adminpage.projectregistration.controller;

import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskAssignedDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.AWSService;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.mapper.UserMapper;
import org.springframework.security.access.prepost.PreAuthorize;


import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/admin/project-registration")
public class ProjectRegistrationController extends BaseController {

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
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(Model model) {
        List<String> projectTypes = projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", new ProjectRegistrationDto());
        return "admin/projectRegistration";
    }


    @GetMapping("/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(@PathVariable(value = "projectId") Integer projectId, Model model) {
        ProjectRegistrationDto projectRegistrationDto = projectRegistrationService.getProjectDetailsById(projectId);;
        List<String> projectTypes = projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", projectRegistrationDto);
        return "admin/projectRegistration";
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(ProjectRegistrationDto dto) throws Exception {
        if(dto.getProjectId() == null && dto.getTaskUnit() > dto.getFiles().size()) {
            return "redirect:/admin/project-registration/error";
        }
        Integer projectId = projectRegistrationService.createProject(dto);
        return "redirect:/admin/project-registration/step-2/"+projectId+(dto.getProjectId()==null?"":"?edit=true");
    }

    @GetMapping("/step-2/{projectId}")
    @PreAuthorize("isFullyAuthenticated()")
    public String getStep2Page(@PathVariable Long projectId, @RequestParam(required = false, defaultValue = "false") Boolean edit, Model model){
        List<TaskDto> projectWithTasks = projectRegistrationService.getProjectWithTasks(projectId);
        List<UserDto> users = userService.selectUserByQcChk('N');
        List<UserDto> admins = userService.selectUserByQcChk('Y');
        model.addAttribute("form", new TaskAssignedDto(projectWithTasks, users, admins, edit));
        model.addAttribute("projectId", projectId);
        return "admin/projectRegistration2";
    }

    @PostMapping("/step-2/{projectId}")
//    @PreAuthorize(value = "isAuthenticated()")
    public String saveStep2Project(@ModelAttribute(name = "form") TaskAssignedDto dto, Authentication authentication, @PathVariable int projectId) {
        projectRegistrationService.saveAssignedUsers(dto, authentication.getName(), projectId);
        return "redirect:admin/project-list";
    }

    /** to provide unique project name validation
     *
     * @param projectName
     * @return if exist already projectName return true, else return false
     */
    @GetMapping("/existProjectName")
    @ResponseBody
    public boolean existProjectName(@RequestParam String projectName) {
        return projectRegistrationService.existProjectName(projectName);
    }

    @GetMapping("/delete/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public String deleteProject(@PathVariable Integer projectId) {
        projectRegistrationService.deleteProject(projectId);
        return "redirect:/admin/project-list";
    }
}
