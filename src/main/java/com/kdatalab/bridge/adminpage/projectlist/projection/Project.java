package com.kdatalab.bridge.adminpage.projectlist.projection;

import java.time.LocalDateTime;
import java.util.Date;

public interface Project {

    int getProjectId();

    String getProjectName();

    String getProjectType();

    String getStatus();

    Date getStartDate();

    Date getEndDate();

    boolean getChecked();
}
