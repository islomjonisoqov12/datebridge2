package com.kdatalab.bridge.edu.dto;

import com.kdatalab.bridge.sub.paging.CommonSubDto;
import lombok.Data;

import java.util.List;

/**
 * 교육 Master 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class EduDto extends CommonSubDto {

    private Integer eduSeq;
    private String eduType;
    private String subject;
    private String content;
    private String agree;
    private Integer time;
    private Integer point;
    private String startDate;
    private String endDate;
    private String eduActiveYn;
    private String regUser;
    private String regDt;
    private String modUser;
    private String modDt;
    private String schSubject = "";
    private String schEduType = "";
    private String schActiveYn = "";

    private String timeStr;
    private String seDate;

    private String loginId;

    private List<EduAttDto> attList;

}
