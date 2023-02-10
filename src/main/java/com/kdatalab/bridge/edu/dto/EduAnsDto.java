package com.kdatalab.bridge.edu.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

/**
 * 교육 문항 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class EduAnsDto extends CommonDto {

    private Integer ansSeq;
    private Integer queSeq;
    private String subject;

}
