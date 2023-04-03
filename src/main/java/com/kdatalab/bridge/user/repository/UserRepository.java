package com.kdatalab.bridge.user.repository;

import com.kdatalab.bridge.mypage.model.RootEntity;
import com.kdatalab.bridge.user.dto.UserDto;
import com.kdatalab.bridge.user.projection.UserDtoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<RootEntity, Integer> {

    @Transactional
    @Modifying
    @Query(value = "{call update_account_info(:username, :newPassword, :newEmail)}", nativeQuery = true)
    void updateAccountInfo(String username, String newPassword, String newEmail);

    @Modifying
    @Transactional
    @Query(value = "update TB_USER set STATUS = :status, MOD_DT = now(), MOD_USER = :user where LOGIN_ID = :name", nativeQuery = true)
    void updateStatus(String name, char status, String user);

    @Query(value = "select QC_CHK from TB_USER where LOGIN_ID = :username", nativeQuery = true)
    String getQcChk(String username);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "update TB_USER SET STATUS = :s, QC_CHK = :qc, MOD_USER = :name, MOD_DT = now() where LOGIN_ID = :loginId")
    void saveStatus(char s, char qc, String loginId, String name);

    @Query(nativeQuery = true, value = "SELECT TU.login_id , TU.password ,TU.QC_CHK , TU.name , TU.e_mail , SUM(TP.NOWPOINT) AS nowpoint FROM TB_USER TU , TB_POINT TP WHERE 1=1 AND TU.login_id = TP.login_id AND TU.login_id = :loginId")
    UserDto selectUserInfo(String loginId);

    @Query(nativeQuery = true, value = "SELECT TU.login_id as \"loginId\" " +
            ", TU.password " +
            ", TU.name " +
            ", TU.e_mail as \"eMail\" " +
            ", SUM(TP.NOWPOINT) AS nowpoint " +
            ", TU.TEL " +
            ", TU.BIRTH_DT as birthdt" +
            ", TU.GENDER_CD as gendercd" +
            ", TU.STATUS as status " +
            "FROM TB_USER TU join TB_POINT TP on TU.LOGIN_ID = TP.LOGIN_ID " +
            "WHERE 1=1 " +
            "AND TU.login_id = :loginId")
    UserDtoProjection selectAllUserInfo(String loginId);

    @Query(nativeQuery = true, value = "SELECT TU.login_id " +
            ", TU.password " +
            ", TU.name " +
            ", TU.e_mail " +
            ", SUM(TP.NOWPOINT) AS nowpoint " +
            ", TU.TEL " +
            ", TU.BIRTH_DT " +
            ", TU.GENDER_CD " +
            ", TU.STATUS " +
            "FROM TB_USER TU join TB_POINT TP on  TU.login_id = TP.login_id " +
            "WHERE TU.QC_CHK = :n and TU.STATUS = 'S' " +
            "group by TU.LOGIN_ID")
    List<UserDto> selectUserByQcChk(char n);

    @Query(nativeQuery = true, value = "SELECT TU.login_id " +
            ", TU.name " +
            ", TU.e_mail " +
            ", TU.TEL " +
            ", TU.BIRTH_DT " +
            ", TU.GENDER_CD " +
            ", TU.STATUS " +
            "FROM TB_USER TU " +
            "WHERE TU.QC_CHK = :s and lower(TU.NAME) like concat('%', :q, '%') and TU.STATUS = 'S' " +
            "group by TU.LOGIN_ID")
    List<UserDto> searchUser( char s, String q);

    @Query(nativeQuery = true,
            value = "SELECT TU.login_id " +
            " , TU.name " +
            " , TU.e_mail " +
            " , TU.TEL " +
            " , TU.BIRTH_DT " +
            " , TU.GENDER_CD " +
            " , TU.STATUS " +
            "FROM TB_USER TU " +
            "WHERE TU.STATUS = :n")
    List<UserDto> selectUserByStatus(char n);

    @Query(nativeQuery = true, value = "select 1")
    public void saveLoginHist(UserDto params) throws Exception;

}