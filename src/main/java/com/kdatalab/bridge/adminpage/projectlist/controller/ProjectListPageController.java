package com.kdatalab.bridge.adminpage.projectlist.controller;

import com.kdatalab.bridge.adminpage.projectlist.projection.Project;
import com.kdatalab.bridge.adminpage.projectlist.service.ProjectListService;
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
@RequiredArgsConstructor
@Tag(name = "Project List for admin", description = "The Project List API. Contains all the operations that can be performed on a project list.")
public class ProjectListPageController extends BaseController {

    private final ProjectListService projectListService;

    /**
     * This method is used to get the project list for project list in admin page
     * @param type
     * @param projectType
     * @return List of projects by user
     */
    @RequestMapping (value = "/project-list", method = RequestMethod.GET)
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get project list for project list in admin page")
    public ResponseEntity<List<Project>> projectList(@RequestParam(value = "type", required = false) String type,
                                      // type = '1' =>only need check project, type<>'1'=> all project
                                      @RequestParam(value = "projectType", required = false) String projectType) throws Exception {
        List<Project> projectList = projectListService.getProjectList(type, projectType);
        return ResponseEntity.ok(projectList);
    }

    /**
     * This method return list of project types for using in project list in admin page
     *
     * @return List of project types
     */
    @ResponseBody
    @RequestMapping (value = "/project-types", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get project types")
    public List<String> projectTypes() {
        List<String> projectTypes = projectListService.getProjectTypes();
        return projectTypes;
    }

//    @GetMapping("/project-list/{projectId}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public String projectList(@PathVariable("projectId") Integer projectId, Model model) {
//        Project project = projectListService.getProjectDetails(projectId);
//        model.addAttribute("project", project);
//        return "admin/project-list";
//    }
}
