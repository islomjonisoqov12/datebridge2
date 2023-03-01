package com.kdatalab.bridge.adminpage.projectlist.projection;

import java.time.LocalDate;

public interface Project {

    int getProjectId();
    String getProjectName();
    String getProjectType();
    String getProjectContent();
    Integer getProjectTime();
    String getStatus();
    LocalDate getStartDate();
    LocalDate getEndDate();
    Integer getPoint();
    Integer getTaskUnit();
    boolean getChecked();
}
