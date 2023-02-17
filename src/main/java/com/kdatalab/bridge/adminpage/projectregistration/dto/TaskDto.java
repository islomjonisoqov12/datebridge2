package com.kdatalab.bridge.adminpage.projectregistration.dto;

import com.kdatalab.bridge.user.dto.UserDto;
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
    private UserDto user;
    private String loginIdName;
    private Integer taskDtlProg;
    private Character taskDtlStat;
    private String regUser;
    private LocalDateTime regDt;
    private String qcId;
    private UserDto checker;

}
