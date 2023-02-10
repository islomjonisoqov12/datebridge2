package com.kdatalab.bridge.edu.mapper;

import com.kdatalab.bridge.edu.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 교육 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface EduMapper {

    List<EduDto> selectEduListTop8(EduDto param) throws Exception;

    int selectEduTotalCount(EduDto params) throws Exception;

    List<EduDto> selectEduList(EduDto params) throws Exception;

    EduDto selectEduInfo(EduDto params) throws Exception;

    List<EduQueDto> selectEduQueList(EduDto params) throws Exception;

    List<EduAnsDto> selectEduAnsList(EduQueDto params) throws Exception;

    List<EduAttDto> selectEduAttList(EduDto params) throws Exception;

    List<EduAnsRsltDto> selectEduAnsRsltList(EduDto params) throws Exception;

    void saveEduInfo(EduDto params) throws Exception;

    int compEduCount(EduDto params) throws Exception;

}
