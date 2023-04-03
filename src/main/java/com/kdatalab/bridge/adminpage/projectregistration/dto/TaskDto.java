package com.kdatalab.bridge.adminpage.projectregistration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private Long dtlSeq;
    private String taskName;
    private Long eduSeq;
    private String loginId;
    private String user;
    private Integer taskDtlProg;
    private Character taskDtlStat;
    private String regUser;
    private LocalDateTime regDt;
    private String qcId;
    private String checker;

}
