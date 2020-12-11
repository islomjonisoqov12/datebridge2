package com.kdatalab.bridge.point.mapper;

import com.kdatalab.bridge.point.dto.PointDto;
import com.kdatalab.bridge.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 포인트 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface PointMapper {

    /**
     * 포인트 상위 5명 조회
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<PointDto> selectPointUserListTop5(PointDto param) throws Exception;

    /**
     * 사용자 포인트 조회
     *
     * @param param
     * @return
     * @throws Exception
     */
    List<PointDto> selectPointList(UserDto param) throws Exception;

    /**
     * 포인트 부여
     *
     * @param params
     * @throws Exception
     */
    void savePointInfo(PointDto params) throws Exception;

}
