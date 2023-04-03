package com.kdatalab.bridge.point.mapper;

import com.kdatalab.bridge.mypage.model.RootEntity;
import com.kdatalab.bridge.point.dto.PointDto;
import com.kdatalab.bridge.user.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 포인트 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */
@Repository
public interface PointMapper  extends JpaRepository<RootEntity, Integer> {

    /**
     * 포인트 상위 5명 조회
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select 1")
    List<PointDto> selectPointUserListTop5(PointDto param) throws Exception;

    /**
     * 사용자 포인트 조회
     *
     * @param param
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "SELECT POINT_ID " +
            ", LOGIN_ID " +
            ", NOWPOINT " +
            "FROM TB_POINT " +
            "WHERE 1=1 " +
            "AND LOGIN_ID = :loginId")
    List<Object[]> selectPointList(String loginId) throws Exception;

    /**
     * 포인트 부여
     *
     * @param params
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select 1")
    void savePointInfo(PointDto params) throws Exception;

}
