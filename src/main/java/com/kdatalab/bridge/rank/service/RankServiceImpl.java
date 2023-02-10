package com.kdatalab.bridge.rank.service;

import com.kdatalab.bridge.board.paging.PaginationInfo;
import com.kdatalab.bridge.rank.dto.RankDto;
import com.kdatalab.bridge.rank.mapper.RankMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 랭킹 Service
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
public class RankServiceImpl implements RankService{

    @Autowired
    private RankMapper rankMapper;

    /**
     * 메인페이지 상위 주간 랭킹 Top5 조회
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<RankDto> selectRankTop5List() throws Exception{
        List<RankDto> rankTop5List = rankMapper.selectRankTop5List();
        return rankTop5List;
    }

    /**
     * 랭킹정보 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<RankDto> selectRankList(RankDto params) throws Exception{
        int rankTotalCount = rankMapper.selectRankTotalCount(params);

        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount(rankTotalCount);
        params.setPaginationInfo(paginationInfo);

        List<RankDto> rankList = rankMapper.selectRankList(params);
        return rankList;
    }

}
