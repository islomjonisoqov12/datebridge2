package com.kdatalab.bridge.adminpage.projectregistration.controller;

import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
import com.kdatalab.bridge.adminpage.projectregistration.dto.ProjectRegistrationDto;
import com.kdatalab.bridge.adminpage.projectregistration.service.AWSService;
import com.kdatalab.bridge.adminpage.projectregistration.service.ProjectRegistrationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProjectRegistrationController {

    private final ProjectListService projectListService;

    private final ProjectRegistrationService projectRegistrationService;

    private final AWSService awsService;

    public ProjectRegistrationController(ProjectListService projectListService, ProjectRegistrationService projectRegistrationService, AWSService awsService) {
        this.projectListService = projectListService;
        this.projectRegistrationService = projectRegistrationService;
        this.awsService = awsService;
    }


    @RequestMapping(value = "/admin/project-registration", method = RequestMethod.GET)
    public String projectRegistration(Model model) {
        List<String> projectTypes =  projectListService.getProjectTypes();
        model.addAttribute("projectTypes", projectTypes);
        model.addAttribute("project", new ProjectRegistrationDto());
        return "admin/projectRegistration";
    }

    @RequestMapping(value = "/admin/project-registration", method = RequestMethod.POST)
    @ResponseBody
    public Integer projectRegistration(ProjectRegistrationDto dto){
        projectRegistrationService.createProject(dto);
        return 1;//"admin/projectRegistration";
    }


    @RequestMapping(value = "/admin/project-registration/saved-file", method = RequestMethod.POST)
    public String savedFile(@RequestParam(value = "projectId") Integer projectId,
                            @RequestParam(value = "files")MultipartFile[] file) throws IOException {

        String regUser = "yanghee";
        projectRegistrationService.attachFileToTask(projectId,regUser, file);
        return "admin/projectRegistration";
    }
}
