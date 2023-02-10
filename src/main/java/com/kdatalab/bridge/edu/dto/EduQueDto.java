package com.kdatalab.bridge.edu.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

import java.util.List;

/**
 * 교육 문제 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class EduQueDto extends CommonDto {

    private Integer queSeq;
    private Integer eduSeq;
    private String subject;
    private Integer answer;
    private List<EduAnsDto> eduAnsList;

    private List ansList;

}
