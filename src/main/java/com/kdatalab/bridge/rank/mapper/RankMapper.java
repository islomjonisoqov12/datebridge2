package com.kdatalab.bridge.rank.mapper;

import com.kdatalab.bridge.rank.dto.RankDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 랭킹 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface RankMapper {

    /**
     * 메인페이지 상위 주간 랭킹 Top5 조회
     *
     * @return
     * @throws Exception
     */
    List<RankDto> selectRankTop5List() throws Exception;

    /**
     * 랭킹정보 총 카운트 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    int selectRankTotalCount(RankDto params) throws Exception;

    /**
     * 랭킹정보 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<RankDto> selectRankList(RankDto params) throws Exception;

}
