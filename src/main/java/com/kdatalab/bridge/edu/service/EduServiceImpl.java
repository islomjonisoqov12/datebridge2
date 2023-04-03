package com.kdatalab.bridge.edu.service;

import com.kdatalab.bridge.edu.dto.*;
import com.kdatalab.bridge.edu.repository.EduRepository;
import com.kdatalab.bridge.point.dto.PointDto;
import com.kdatalab.bridge.point.service.PointService;
import com.kdatalab.bridge.sub.paging.PaginationSubInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 교육 Service
 * @author Enclouds
 * @since 2020.12.11
 */

@Service
public class EduServiceImpl implements EduService{

    @Autowired
    private EduRepository eduRepository;

    @Autowired
    private PointService pointService;

    /**
     * Main Page 사용 교육 Top 8
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<EduDto> selectEduListTop8(EduDto params) throws Exception {
        List<EduDto> eduList = eduRepository.selectEduListTop8(params);

        return eduList;
    }

    /**
     * 교육 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<EduDto> selectEduList(EduDto params) throws Exception {
        int eduTotalCount = eduRepository.selectEduTotalCount(params);

        PaginationSubInfo paginationInfo = new PaginationSubInfo(params);
        paginationInfo.setTotalRecordCount(eduTotalCount);
        params.setPaginationSubInfo(paginationInfo);

        List<EduDto> eduList = eduRepository.selectEduList(params);

        return eduList;
    }

    /**
     * 교육 상세정보 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public EduDto selectEduInfo(EduDto params) throws Exception{
        EduDto eduInfo = eduRepository.selectEduInfo(params);
        List<EduAttDto> attList = eduRepository.selectEduAttList(params);
        eduInfo.setAttList(attList);

        return eduInfo;
    }

    /**
     * 교육 문제 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<EduQueDto> selectEduQueList(EduDto params) throws Exception {
        List<EduQueDto> eduQueList = eduRepository.selectEduQueList(params);

        for(int i=0 ; i<eduQueList.size() ; i++){
            EduQueDto eduQueInfo = (EduQueDto)eduQueList.get(i);
            List<EduAnsDto> eduAnsList = eduRepository.selectEduAnsList(eduQueInfo);

            eduQueInfo.setEduAnsList(eduAnsList);
        }

        return eduQueList;
    }

    /**
     * 교육 정답 List 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public List<EduAnsRsltDto> selectEduAnsRsltList(EduDto params) throws Exception {
        List<EduAnsRsltDto> eduAnsRsltList = eduRepository.selectEduAnsRsltList(params);

        return eduAnsRsltList;
    }

    /**
     * 교육 완료 처리
     *
     * @param params
     * @throws Exception
     */
    @Override
    public void saveEduInfo(EduDto params) throws Exception {
        eduRepository.saveEduInfo(params);

        PointDto pointDto = new PointDto();
        pointDto.setLoginId(params.getLoginId());
        pointDto.setPoint(params.getPoint());

        pointService.savePointInfo(pointDto);
    }

    /**
     * 교육 이수 여부 판단 조회
     *
     * @param params
     * @return
     * @throws Exception
     */
    @Override
    public int compEduCount(EduDto params) throws Exception {
        return eduRepository.compEduCount(params);
    }
}
