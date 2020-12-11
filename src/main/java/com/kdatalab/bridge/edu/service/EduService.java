package com.kdatalab.bridge.edu.service;

import com.kdatalab.bridge.edu.dto.EduAnsRsltDto;
import com.kdatalab.bridge.edu.dto.EduDto;
import com.kdatalab.bridge.edu.dto.EduQueDto;

import java.util.List;

/**
 * 교육 Service Interface
 * @author Enclouds
 * @since 2020.12.11
 */

public interface EduService {

    /**
     * Main Page 사용 교육 Top 8
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<EduDto> selectEduListTop8(EduDto params) throws Exception;

    /**
     * 교육 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<EduDto> selectEduList(EduDto params) throws Exception;

    /**
     * 교육 상세정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    EduDto selectEduInfo(EduDto params) throws Exception;

    /**
     * 교육 문제 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<EduQueDto> selectEduQueList(EduDto params) throws Exception;

    /**
     * 교육 정답 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    List<EduAnsRsltDto> selectEduAnsRsltList(EduDto params) throws Exception;

    /**
     * 교육 완료 처리
     *
     * @param params
     * @throws Exception
     */
    void saveEduInfo(EduDto params) throws Exception;

    /**
     * 교육 이수 여부 판단 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    int compEduCount(EduDto params) throws Exception;

}
