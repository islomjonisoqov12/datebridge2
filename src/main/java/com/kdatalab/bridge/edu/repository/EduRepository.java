package com.kdatalab.bridge.edu.repository;

import com.kdatalab.bridge.edu.dto.*;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 교육 Mapper
 *
 * @author Enclouds
 * @since 2020.12.11
 */
@Repository
public interface EduRepository extends JpaRepository<RootEntity, Integer> {

    //create procedure
    @Query(nativeQuery = true, value = "SELECT TAB.*" +
            "FROM (" +
            "SELECT edu_seq" +
            ", edu_type" +
            ", subject" +
            ", content" +
            ", agree" +
            ", time" +
            ", point" +
            ", start_date" +
            ", end_date" +
            ", CASE WHEN now() between START_DATE and END_DATE THEN 'Y' ELSE 'N' END AS edu_active_yn" +
            ", reg_dt" +
            "FROM TB_EDU_MST" +
            "WHERE 1=1" +
            ") TAB" +
            "WHERE 1=1 " +
            "ORDER BY TAB.reg_dt desc" +
            "LIMIT 0, 8")
    List<EduDto> selectEduListTop8(EduDto param) throws Exception;

    //create procedure
    @Query(nativeQuery = true, value = "SELECT count(*)\n" +
            "FROM (\n" +
            "SELECT edu_seq\n" +
            ", edu_type\n" +
            ", subject\n" +
            ", content\n" +
            ", agree\n" +
            ", time\n" +
            ", point\n" +
            ", start_date\n" +
            ", end_date\n" +
            ", CASE WHEN now() between START_DATE and END_DATE THEN 'Y' ELSE 'N' END AS edu_active_yn\n" +
            ", reg_dt\n" +
            "FROM TB_EDU_MST\n" +
            "WHERE 1=1 ")
    int selectEduTotalCount(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    List<EduDto> selectEduList(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    EduDto selectEduInfo(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    List<EduQueDto> selectEduQueList(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    List<EduAnsDto> selectEduAnsList(EduQueDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    List<EduAttDto> selectEduAttList(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    List<EduAnsRsltDto> selectEduAnsRsltList(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    void saveEduInfo(EduDto params) throws Exception;

    @Query(nativeQuery = true, value = "select 1")
    int compEduCount(EduDto params) throws Exception;

}
