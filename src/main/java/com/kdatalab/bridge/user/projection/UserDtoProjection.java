package com.kdatalab.bridge.user.projection;

public interface UserDtoProjection {

     String getLoginId();
     String getPassword();
     String getName();
     String getEMail();
     String getQcChk();
     String getTel();
     String getBirthDt();
     String getGenderCd();
     Integer getNowpoint();
     String getLoginIp();
     Character getStatus();

    default String getBirthDtFormat() {
        if (getBirthDt() == null) {
            return "-";
        }
        return getBirthDt().substring(0, 4) + "-" + getBirthDt().substring(4, 6) + "-" + getBirthDt().substring(6);
    }

    default String getEmailStart() {
        if (getEMail() == null || getEMail().indexOf("@") < 2) {
            return null;
        }
        return getEMail().substring(0, getEMail().indexOf("@"));
    }

    default String getEmailLast() {
        if (getEMail() == null || getEMail().indexOf("@") < 2) {
            return null;
        }
        return getEMail().substring(getEMail().indexOf("@") + 1);
    }

    default boolean getEmailStatus() {
        String emailLast = getEmailLast();
        if (emailLast == null) {
            return false;
        }
        return (emailLast.equalsIgnoreCase("naver.com") || emailLast.equalsIgnoreCase("hanmail.net"));
    }


}
