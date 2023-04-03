package com.kdatalab.bridge.join.mapper;

import com.kdatalab.bridge.join.dto.JoinDto;
import com.kdatalab.bridge.mypage.model.RootEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 회원가입 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */
@Repository
public interface JoinRepository  extends JpaRepository<RootEntity, Integer> {

    /**
     * 회원 가입 처리
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "INSERT INTO TB_USER " +
            "( LOGIN_ID " +
            ", PASSWORD " +
            ", NAME " +
            ", E_MAIL " +
            ", TEL " +
            ", STATUS " +
            ", SNS_INFO " +
            ", BIRTH_DT " +
            ", GENDER_CD " +
            ", DUPL_INFO " +
            ", REG_USER " +
            ", REG_DT " +
            ", MOD_USER " +
            ", MOD_DT ) " +
            "VALUES " +
            "( :loginId " +
            ", :password " +
            ", :name " +
            ", :email " +
            ", :tel " +
            ", IF(:isAdmin, 'N', 'S') " +
            ", :snsInfo " +
            ", :birthDt " +
            ", :genderCd " +
            ", :duplInfo " +
            ", 'system' " +
            ", NOW() " +
            ", 'system' " +
            ", NOW() ) " +
            "on DUPLICATE KEY UPDATE " +
            "NAME = :name, " +
            "E_MAIL = :email, " +
            "TEL = :tel, " +
            "SNS_INFO = :snsInfo, " +
            "BIRTH_DT = :birthDt, " +
            "GENDER_CD = :genderCd ")
    void saveUserInfo(String loginId, String password, String name, String email, String tel, boolean isAdmin, String snsInfo, String birthDt, String genderCd, String duplInfo) throws Exception;

    /**
     * 최초 가입시 0포인트 부여
     *
     * @param params
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "INSERT INTO TB_POINT " +
            "( LOGIN_ID " +
            ", NOWPOINT " +
            ", LASTUPDDE ) " +
            "VALUES " +
            "( :loginId " +
            ", 0 " +
            ", NOW() )")
    void saveUserPointInfo(String loginId) throws Exception;

    /**
     * 본인 인증시 중복 가입 방지
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select 1")
    int getUserDuplInfo(JoinDto params) throws Exception;

    /**
     * 이메일 중복 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select 1")
    int selectEmailDuplList(JoinDto params) throws Exception;

    /**
     * 전화번호 중복 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Query(nativeQuery = true, value = "select 1")
    int selectTelDuplList(JoinDto params) throws Exception;

}
