package com.kdatalab.bridge.mypage.service;

import com.kdatalab.bridge.mypage.model.UserTaskList;

import java.util.List;

public interface UserTaskListService {
    List<UserTaskList> getUserTaskList(Integer projectId, String loginId);
}
