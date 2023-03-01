package com.kdatalab.bridge.adminpage.projectlist.projection;

import java.util.Date;

public interface Project {

    int getProjectId();
    String getProjectName();
    String getProjectType();
    String getProjectContent();
    Integer getProjectTime();
    String getStatus();
    Integer getPoint();
    Integer getTaskUnit();
    Date getStartDate();
    Date getEndDate();
    boolean getChecked();
}
