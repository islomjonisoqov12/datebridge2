package com.kdatalab.bridge.adminpage.projectlist.projection;

public interface Task {
    Integer getTaskId();
    String getTaskName();
    String getUserId();
    String getUserName();
    String getQcId();
    String getQcName();
    String getTaskStatus();
    Integer getCntImageInTask();
    Integer getCntImageDoneAndApproved();
}
