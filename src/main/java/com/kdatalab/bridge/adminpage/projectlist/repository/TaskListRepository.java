package com.kdatalab.bridge.adminpage.projectlist.repository;

import com.kdatalab.bridge.adminpage.projectlist.projection.Task;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskListRepository extends JpaRepository<RootEntity,Integer> {

    @Query(value = "call list_tasks_by_project(?1)", nativeQuery = true)
    List<Task> getTaskList(Integer projectId);

    @Query(value = "select AVG(TASK_DTL_PROG) from TB_TASK_DTL where EDU_SEQ = ?1", nativeQuery = true)
    Integer getOverallProgressRateByProject(Integer projectId);

    @Query(value = "select sum(COMPTPOINT) from TB_EDU_RESULT where EDU_SEQ = ?1", nativeQuery = true)
    Integer getTotalPointByProject(Integer projectId);
}
