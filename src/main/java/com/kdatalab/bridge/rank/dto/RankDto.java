package com.kdatalab.bridge.rank.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

/**
 * 랭킹 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class RankDto extends CommonDto {

    private String loginId;
    private Integer sPoint;
    private Integer cmpCnt;

    private String schLoginId;

}
