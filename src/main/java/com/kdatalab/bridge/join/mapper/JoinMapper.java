package com.kdatalab.bridge.join.mapper;

import com.kdatalab.bridge.join.dto.JoinDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 회원가입 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface JoinMapper {

    /**
     * 회원 가입 처리
     *
     * @param params
     * @return
     * @throws Exception
     */
    int saveUserInfo(JoinDto params) throws Exception;

    /**
     * 최초 가입시 0포인트 부여
     *
     * @param params
     * @throws Exception
     */
    void saveUserPointInfo(JoinDto params) throws Exception;

    /**
     * 본인 인증시 중복 가입 방지
     *
     * @param params
     * @return
     * @throws Exception
     */
    int getUserDuplInfo(JoinDto params) throws Exception;

    /**
     * 이메일 중복 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    int selectEmailDuplList(JoinDto params) throws Exception;

    /**
     * 전화번호 중복 체크
     *
     * @param params
     * @return
     * @throws Exception
     */
    int selectTelDuplList(JoinDto params) throws Exception;

}
