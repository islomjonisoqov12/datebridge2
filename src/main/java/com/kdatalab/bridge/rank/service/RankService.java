package com.kdatalab.bridge.rank.service;

import com.kdatalab.bridge.rank.dto.RankDto;

import java.util.List;

/**
 * 랭킹 Service Interface
 * @author Enclouds
 * @since 2020.12.11
 */

public interface RankService {

    /**
     * 메인페이지 상위 주간 랭킹 Top5 조회
     *
     * @return
     * @throws Exception
     */
    List<RankDto> selectRankTop5List() throws Exception;

    /**
     * 랭킹정보 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<RankDto> selectRankList(RankDto params) throws Exception;

}
