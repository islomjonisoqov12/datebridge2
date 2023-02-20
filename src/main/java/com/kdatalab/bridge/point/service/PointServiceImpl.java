package com.kdatalab.bridge.point.service;

import com.kdatalab.bridge.point.PointRepository;
import com.kdatalab.bridge.point.dto.PointDto;
import com.kdatalab.bridge.point.mapper.PointMapper;
import com.kdatalab.bridge.point.model.PointHistory;
import com.kdatalab.bridge.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 포인트 Service
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {

    @Autowired
    private PointMapper pointMapper;

    private final PointRepository pointRepository;

    /**
     * 포인트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<PointDto> selectPointList(UserDto params) throws Exception{
        List<PointDto> pointDtoList = pointMapper.selectPointList(params);

        return pointDtoList;
    }

    /**
     * 포인트 부여
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void savePointInfo(PointDto params) throws Exception {
        pointMapper.savePointInfo(params);
    }

    /**
     * List of point by user
     * @param loginId
     * @return List of PointHistory
     */
    @Override
    public List<PointHistory> getPointHistory(String loginId) {
        return pointRepository.findByLoginId(loginId);
    }

    /**
     *
     * @param loginId
     * @return total point by user id
     */
    @Override
    public Integer getTotalPointByLoginId(String loginId) {
        return pointRepository.getTotalPointByLoginId(loginId);
    }
}
