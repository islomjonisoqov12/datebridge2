package com.kdatalab.bridge.join.dto;

import com.kdatalab.bridge.board.dto.CommonDto;
import lombok.Data;

/**
 * 회원가입 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class JoinDto extends CommonDto {

    private String loginId;
    private String password;
    private String name;
    private String birthDt;
    private String email;
    private String genderCd;
    private String EncodeData;
    private String tel;
    private String snsInfo;
    private String duplInfo;
    private String connInfo;
    private boolean isAdmin;

    public void setAdmin(String admin) {
        isAdmin = admin!=null && !admin.isEmpty();
    }
}
