package com.kdatalab.bridge.adminpage.projectlist.repository;

import com.kdatalab.bridge.adminpage.projectlist.projection.Project;
import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectListRepository extends JpaRepository<RootEntity, Long> {

    @Query(value = "select distinct t.EDU_TYPE " +
                     "from TB_EDU_MST t ", nativeQuery = true)
    List<String> getProjectTypes();

    @Query(value = "call list_projects_by_admin(?1, ?2, ?3)", nativeQuery = true)
    List<Project> getProjects(String type, String projectType, String qcId);

    @Query(value = "select t.EDU_SEQ projectId, t.EDU_TYPE projectType, t.SUBJECT projectName, t.CONTENT content, t.TIME projectTime, t.START_DATE startDate, t.END_DATE endDate, t.POINT point, t.TASK_UNIT taskUnit " +
                     "from TB_EDU_MST t " +
                    "where t.EDU_SEQ = ?1", nativeQuery = true)
    Project getProjectDetails(Integer projectId);
}
