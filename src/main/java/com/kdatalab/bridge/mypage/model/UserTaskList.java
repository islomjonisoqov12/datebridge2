package com.kdatalab.bridge.mypage.model;

public interface UserTaskList {
    Integer getTaskId();
    Integer getTaskProgress();
    String getLoginId();
    String getQcId();
    String getStatus();
    Integer getTotalImageInTask();
    Integer getTotalImageDoneAndApprovedInTask();
}
