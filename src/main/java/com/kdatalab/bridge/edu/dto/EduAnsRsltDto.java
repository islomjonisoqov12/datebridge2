package com.kdatalab.bridge.edu.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

/**
 * 교육 정답 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class EduAnsRsltDto extends CommonDto {

    private Integer resultAnsSeq;
    private Integer eduSeq;
    private Integer queSeq;
    private String result;

}
