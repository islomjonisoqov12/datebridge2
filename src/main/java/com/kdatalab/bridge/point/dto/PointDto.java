package com.kdatalab.bridge.point.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

/**
 * 포인트 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class PointDto extends CommonDto {

    private String loginId;
    private Integer point;
    private Integer compEduCnt;

}
