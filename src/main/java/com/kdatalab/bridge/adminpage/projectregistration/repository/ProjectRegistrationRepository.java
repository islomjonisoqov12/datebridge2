package com.kdatalab.bridge.adminpage.projectregistration.repository;

import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRegistrationRepository extends JpaRepository<RootEntity, Integer> {


    @Query(value = "{call generate_tasks(?1,?2,?3)}", nativeQuery = true)
    void saveTbTaskDtl(Integer projectId, String regUser, Integer taskUnit);

    @Query(value = "{call save_tb_edu_mst(?1,?2,?3,?4,?5,?6,?7,?8)}", nativeQuery = true)
    void saveProject(String projectType, String projectName, String projectContent, String workDateInMinutes, String projectStartDate,
                     String projectEndDate, Integer pointPerImage, Integer taskUnit);

    @Query(value = "{call save_tb_att(?1,?2,?3,?4,?5,?6,?7,?8)}", nativeQuery = true)
    void saveTbAtt(Integer projectId, Integer taskId, String fileName, String filePath, Long fileSize, String fileExt, String regUser, String regDate, String modUser, String modDate);
}
