package com.kdatalab.bridge.adminpage.projectlist.service;

import com.kdatalab.bridge.adminpage.projectlist.projection.Project;
import java.util.List;

public interface ProjectListService {

    List<String> getProjectTypes();

    List<Project> getProjectList(String type, String projectType);
}
