package com.kdatalab.bridge.rank.mapper;

import com.kdatalab.bridge.mypage.model.RootEntity;
import com.kdatalab.bridge.rank.dto.RankDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 랭킹 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */
@Repository
public interface RankRepository  extends JpaRepository<RootEntity, Integer> {

    /**
     * 메인페이지 상위 주간 랭킹 Top5 조회
     *
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT TAB.* " +
            "FROM ( " +
            "SELECT CONCAT(LEFT(LOGIN_ID, 3), '####') AS LOGIN_ID " +
            ", SUM(NOWPOINT) AS S_POINT " +
            ", COUNT(*) AS CMP_CNT " +
            "FROM TB_POINT " +
            "WHERE 1=1 " +
            "AND date_format(LASTUPDDE,'%Y-%m-%d') BETWEEN (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 0 )) AND  (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 6 )) " +
            "GROUP BY LOGIN_ID " +
            ") TAB " +
            "ORDER BY TAB.S_POINT DESC " +
            "LIMIT 0, 5")
    List<RankDto> selectRankTop5List() throws Exception;

    /**
     * 랭킹정보 총 카운트 조회
     *
     * @return
     * @throws Exception
     */
    //create procedure
    @Query(nativeQuery = true, value = "SELECT COUNT(*) " +
            "FROM ( " +
            "SELECT CONCAT(LEFT(LOGIN_ID, 3), '####') AS LOGIN_ID " +
            ", SUM(NOWPOINT) AS S_POINT " +
            ", COUNT(*) AS CMP_CNT " +
            "FROM TB_POINT " +
            "WHERE 1=1 " +
            "AND date_format(LASTUPDDE,'%Y-%m-%d') BETWEEN (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 0 )) AND  (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 6 )) " +
            "AND (LOGIN_ID != null and LOGIN_ID like concat('%', :schLoginId, '%')) " +
            "GROUP BY LOGIN_ID " +
            ") TAB " +
            "ORDER BY TAB.S_POINT DESC")
    int selectRankTotalCount(String schLoginId) throws Exception;

    /**
     * 랭킹정보 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT TAB.* " +
            "FROM ( " +
            "SELECT CONCAT(LEFT(LOGIN_ID, 3), '####') AS LOGIN_ID " +
            ", SUM(NOWPOINT) AS S_POINT " +
            ", COUNT(*) AS CMP_CNT " +
            "FROM TB_POINT " +
            "WHERE 1=1 " +
            "AND date_format(LASTUPDDE,'%Y-%m-%d') BETWEEN (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 0 )) AND  (SELECT ADDDATE(CURDATE(), - WEEKDAY(CURDATE()) + 6 )) " +
            "AND LOGIN_ID like '%${schLoginId}%' " +
            "GROUP BY LOGIN_ID " +
            ") TAB " +
            "ORDER BY TAB.S_POINT DESC " +
            "LIMIT " +
            ":firstRecordIndex, :recordsPerPage")
    List<RankDto> selectRankList(int firstRecordIndex, int recordsPerPage) throws Exception;

}
