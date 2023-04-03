package com.kdatalab.bridge.adminpage.projectregistration.controller;

import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskAssignedDto;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.AWSService;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import com.kdatalab.bridge.base.BaseController;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.repository.UserRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class ProjectRegistrationController extends BaseController {

    private final ProjectListService projectListService;

    private final ProjectRegistrationService projectRegistrationService;
    private final UserRepository userService;

    private final AWSService awsService;

    public ProjectRegistrationController(ProjectListService projectListService, ProjectRegistrationService projectRegistrationService, UserRepository userService, AWSService awsService) {
        this.projectListService = projectListService;
        this.projectRegistrationService = projectRegistrationService;
        this.userService = userService;
        this.awsService = awsService;
    }

    @GetMapping("/project-registration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(Model model) {
        List<String> projectTypes = projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", new ProjectRegistrationDto());
        model.addAttribute("step", 1);
        return "admin/projectRegistration";
    }



    @GetMapping("/project-registration/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(@PathVariable(value = "projectId") Integer projectId, Model model) {
        ProjectRegistrationDto projectRegistrationDto = projectRegistrationService.getProjectDetailsById(projectId);;
        List<String> projectTypes = projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", projectRegistrationDto);
        model.addAttribute("step", 1);
        return "admin/projectRegistration";
    }

    @PostMapping("/project-registration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String projectRegistration(ProjectRegistrationDto dto) throws Exception {
        List<MultipartFile> collect = dto.getFiles().stream().filter(multipartFile -> multipartFile.getSize() > 0).collect(Collectors.toList());
        dto.setFiles(collect);
        if(dto.getProjectId() == null && dto.getTaskUnit() > dto.getFiles().size()) {
            return "redirect:/project-registration/error";
        }
        Integer projectId = projectRegistrationService.createProject(dto);
        return "redirect:/project-registration-step-2?projectId="+projectId+(dto.getProjectId()==null?"":"&edit=true");
    }

    @GetMapping("/project-registration-step-2")
    @PreAuthorize("hasRole('ADMIN')")
    public HttpEntity<TaskAssignedDto> getStep2Page(@RequestParam Long projectId, @RequestParam(required = false, defaultValue = "false") Boolean edit){
        List<TaskDto> projectWithTasks = projectRegistrationService.getProjectWithTasks(projectId);
        List<UserDto> users = userService.selectUserByQcChk('N');
        List<UserDto> admins = userService.selectUserByQcChk('Y');
        return ResponseEntity.ok(new TaskAssignedDto(projectWithTasks, users, admins, edit));
    }
    @GetMapping("/project-registration/search/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDto> searchUser(@PathVariable Character status, @RequestParam String search){
        return userService.searchUser(status, search.toLowerCase());
    }

    @PostMapping(path = "/project-registration-step-2")
    @Secured(value = {"ROLE_ADMIN"})
    public String saveStep2Project(@RequestBody TaskAssignedDto dto, Authentication authentication, @RequestParam int projectId) {
        projectRegistrationService.saveAssignedUsers(dto.getTasks(), authentication.getName(), projectId);
        return "saved";
    }

    /** to provide unique project name validation
     *
     * @param projectName
     * @return if exist already projectName return true, else return false
     */
    @GetMapping("/project-registration/existProjectName")
    public boolean existProjectName(@RequestParam(required = false) Integer projectId,@RequestParam String projectName) {
        return projectRegistrationService.existProjectName(projectId, projectName);
    }

    @GetMapping("/project-registration/delete/{projectId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Map<String, Object> deleteProject(@PathVariable Integer projectId) {
        Map<String, Object> result = new HashMap<>();
        if(projectRegistrationService.getWorkingTaskCount(projectId) > 0) {
            result.put("msg", "error");
            return result;
        }
        projectRegistrationService.deleteProject(projectId);
        result.put("msg", "success");
        return result;
    }
}
