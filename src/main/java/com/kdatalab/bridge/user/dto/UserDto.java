package com.kdatalab.bridge.user.dto;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 사용자 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class UserDto {

    private String loginId;
    private String password;
    private String name;
    private String eMail;
    private String tel;
    private String birthDt;
    private String genderCd;
    private Integer nowpoint;
    private String loginIp;
    private Character status;

    public String getBirthDtFormat(){
        if (birthDt == null) {
            return "-";
        }
        return birthDt.substring(0,4) +"-"+ birthDt.substring(4, 6) +"-"+ birthDt.substring(6);
    }

    public String getEmailStart(){
        return eMail.substring(0, eMail.indexOf("@"));
    }
    public String getEmailLast(){
        return eMail.substring(eMail.indexOf("@")+1);
    }

    public boolean getEmailStatus(){
        String emailLast = getEmailLast();
        return (emailLast.equalsIgnoreCase("naver.com") || emailLast.equalsIgnoreCase("hanmail.net"));
    }

}
