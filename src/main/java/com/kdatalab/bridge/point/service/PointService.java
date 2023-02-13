package com.kdatalab.bridge.point.service;

import com.kdatalab.bridge.point.dto.PointDto;
import com.kdatalab.bridge.point.model.PointHistory;
import com.kdatalab.bridge.user.dto.UserDto;

import java.util.List;

/**
 * 포인트 Service Interface
 * @author Enclouds
 * @since 2020.12.11
 */

public interface PointService {

    /**
     * 포인트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<PointDto> selectPointList(UserDto params) throws Exception;

    /**
     * 포인트 부여
     *
     * @param params
     * @throws Exception
     */
    void savePointInfo(PointDto params) throws Exception;

    /**
     * List of point by user
     * @param loginId
     * @return List of PointHistory
     */
    List<PointHistory> getPointHistory(String loginId);

}
