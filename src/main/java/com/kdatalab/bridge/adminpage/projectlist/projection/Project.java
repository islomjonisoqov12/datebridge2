package com.kdatalab.bridge.adminpage.projectlist.projection;

public interface Project {

    int getProjectId();
    String getProjectName();
    String getProjectType();
    String getStatus();
    String getStartDate();
    String getEndDate();
    boolean getChecked();
}
