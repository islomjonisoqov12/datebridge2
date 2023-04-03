package com.kdatalab.bridge.adminpage.projectregistration.repository;

import com.kdatalab.bridge.adminpage.projectregistration.dto.TaskDto;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface ProjectRegistrationRepository extends JpaRepository<RootEntity, Integer> {


    @Query(value = "{call generate_tasks(?1,?2,?3)}", nativeQuery = true)
    void saveTbTaskDtl(Integer projectId, String regUser, Integer taskUnit);

    @Query(value = "{call save_tb_edu_mst(?1,?2,?3,?4,?5,?6,?7,?8,?9)}", nativeQuery = true)
    Integer saveProject(String projectType, String projectName, String projectContent, Integer workDateInMinutes, LocalDate projectStartDate,
                        LocalDate projectEndDate, Integer pointPerImage, Integer taskUnit, String regUser);

    @Query(value = "{call save_tb_att(?1,?2,?3,?4,?5,?6,?7)}", nativeQuery = true)
    void saveTbAtt(Integer projectId, Integer taskId, String fileName, String filePath, Long fileSize, String fileExt, String regUser);

    @Query(value = "select DTL_SEQ from TB_TASK_DTL where EDU_SEQ = ?1", nativeQuery = true)
    List<Integer> getTasksByProjectId(Integer projectId);

    @Query(value = "{call update_tb_edu_mst(?1,?2,?3,?4,?5,?6,?7)}", nativeQuery = true)
    void updateProject(Integer projectId, String projectName, String projectContent, Integer workDateInMinutes, LocalDate projectStartDate, LocalDate projectEndDate, String userId);

    @Query(value = "select count(*) from TB_EDU_MST where (EDU_SEQ != :projectId or :projectId is null) and lower(SUBJECT) = :projectName ", nativeQuery = true)
    int existProjectName(Integer projectId, String projectName);

    @Query(value = "select EDU_SEQ, EDU_TYPE, SUBJECT, CONTENT, TIME, START_DATE, END_DATE, POINT, TASK_UNIT, (select count(*) from TB_ATT t where t.EDU_SEQ = ?1) as FILE_COUNT " +
                     "from TB_EDU_MST where EDU_SEQ = ?1", nativeQuery = true)
    Map<String,Object> getProjectDetails(Integer projectId);

    @Query(value = "{call task_assigned(:tasks, :name)}", nativeQuery = true)
    int saveAssignedUsers(String tasks, String name);
    @Query(value = "select count(*) " +
                     "from TB_TASK_DTL " +
                    "where EDU_SEQ = ?1 " +
                      "and TASK_DTL_PROG > 0", nativeQuery = true)
    int getWorkingTaskCount(Integer projectId);

    @Query(value = "call delete_project(?1)", nativeQuery = true)
    void deleteProject(Integer projectId);

    @Query(nativeQuery = true, value = "select distinct " +
            "            TT.dtl_seq, " +
            "            SUBSTRING_INDEX(TA.PATH, '/', -1) as \"taskName\", " +
            "            TT.edu_seq, " +
            "            TT.login_id, " +
            "            (select tbu.NAME from TB_USER tbu where tbu.LOGIN_ID = TT.LOGIN_ID) as user, " +
            "            TT.task_dtl_prog, " +
            "            TT.task_dtl_stat, " +
            "            TT.reg_user, " +
            "            TT.reg_dt, " +
            "            TT.qc_id, " +
            "            (select tbch.NAME from TB_USER tbch where tbch.LOGIN_ID = TT.QC_ID) as checker " +
            "        from TB_TASK_DTL TT   JOIN TB_ATT TA on TT.DTL_SEQ = TA.DTL_SEQ AND TT.EDU_SEQ = TA.EDU_SEQ " +
            "            where TT.EDU_SEQ = :projectId")
    List<TaskDto> selectTasksByProjectId(Long projectId);
}
