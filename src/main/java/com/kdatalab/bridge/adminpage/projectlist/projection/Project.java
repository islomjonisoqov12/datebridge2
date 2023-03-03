package com.kdatalab.bridge.adminpage.projectlist.projection;

import java.util.Date;

public interface Project {

    int getProjectId();

    String getProjectName();

    String getProjectType();

    String getStatus();

    Date getStartDate();

    Date getEndDate();

    String getProjectContent();

    Integer getProjectTime();

    Integer getPoint();

    Integer getTaskUnit();

    boolean getChecked();
}
