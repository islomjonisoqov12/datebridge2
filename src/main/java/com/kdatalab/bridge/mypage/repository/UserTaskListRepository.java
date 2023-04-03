package com.kdatalab.bridge.mypage.repository;

import com.kdatalab.bridge.mypage.model.RootEntity;
import com.kdatalab.bridge.mypage.model.UserTaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTaskListRepository extends JpaRepository<RootEntity, Integer> {

    @Query(value = "call list_tasks_by_project_and_user(?1, ?2)", nativeQuery = true)
    List<UserTaskList> getUserTaskList(Integer projectId, String loginId);
}
