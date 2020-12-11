package com.kdatalab.bridge.edu.dto;

import lombok.Data;

/**
 * 교육 첨부 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class EduAttDto {

    private Integer attSeq;
    private Integer eduSeq;
    private String name;
    private String path;
    private Integer size;
    private String ext;

}
