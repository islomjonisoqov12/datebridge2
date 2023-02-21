package com.kdatalab.bridge.mypage.repository;

import com.kdatalab.bridge.mypage.model.Project;
import com.kdatalab.bridge.mypage.model.ProjectDetail;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPageRepository extends JpaRepository<RootEntity, Integer> {

    @Query(value = "{call list_projects_by_user(?1, ?2)}", nativeQuery = true)
    List<Project> getProjectListByUser(String userId, String projectStatus);

    @Query(value = "{call project_info_by_user(?1)}", nativeQuery = true)
    ProjectDetail getProjectInfoByUser(String userId);
}
