package com.kdatalab.bridge.mypage.service;

import com.kdatalab.bridge.mypage.model.UserTaskList;
import com.kdatalab.bridge.mypage.repository.UserTaskListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTaskListServiceImpl implements UserTaskListService {
    UserTaskListRepository userTaskListRepository;

    @Override
    public List<UserTaskList> getUserTaskList(Integer projectId, String loginId) {
        return userTaskListRepository.getUserTaskList(projectId, loginId);
    }
}
