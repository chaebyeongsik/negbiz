/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.request.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.core.compare.CompareViewVO;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.cntnts.CmsCntntsService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.request.CmsRequestMapper;
import zesinc.intra.cms.request.CmsRequestService;
import zesinc.intra.cms.request.domain.CmsRequestVO;
import zesinc.intra.cms.support.CmsSupport;
import zesinc.web.support.pager.Pager;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 컨텐츠발행요청 정보 서비스 구현 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-10.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */

@Service("opCmsRequestService")
public class CmsRequestServiceImpl extends EgovAbstractServiceImpl implements CmsRequestService {

    @Resource(name = "opCmsRequestDao")
    private CmsRequestMapper opCmsRequestDao;

    @Resource(name = "opCmsCntntsService")
    private CmsCntntsService opCmsCntntsService;

    @Override
    public CmsRequestVO selectCmsRequest(CmsRequestVO cmsRequestVo) {

        CmsRequestVO dataVo = opCmsRequestDao.selectCmsRequest(cmsRequestVo);
        // 승인정보 한글명 설정
        if(Validate.isNotEmpty(dataVo.getAprvSttsNo())) {
            dataVo.setConfmSttusNm(CmsSupport.getConfmSttusNm(dataVo.getAprvSttsNo()));
        }

        return dataVo;
    }

    @Override
    public CmsCntntsCompareVO compareScreenCntnts(CmsCntntsCompareVO compareVo) {

        return opCmsCntntsService.compareScreenCntnts(compareVo);
    }

    @Override
    public List<CompareViewVO> compareSourceCntnts(CmsCntntsCompareVO compareVo) {

        Integer leftCntntsSn = compareVo.getInteger("q_leftCntntsSn");
        Integer rightCntntsSn = compareVo.getInteger("q_rightCntntsSn");

        if(Validate.isEmpty(leftCntntsSn) || Validate.isEmpty(rightCntntsSn)) {
        	rightCntntsSn = compareVo.getInteger("q_contsSn");
            leftCntntsSn = 1;
            if(rightCntntsSn > 1) {
                leftCntntsSn = rightCntntsSn - 1;
            }

            compareVo.addParam("q_leftCntntsSn", leftCntntsSn);
            compareVo.addParam("q_rightCntntsSn", rightCntntsSn);
        }

        return opCmsCntntsService.compareSourceCntnts(compareVo);
    }

    @Override
    public Pager<CmsRequestVO> selectCmsRequestPageList(CmsRequestVO cmsRequestVo) {

        String aprvSttsNo = cmsRequestVo.getString("q_aprvSttsNo");
        if(Validate.isEmpty(aprvSttsNo)) {
            cmsRequestVo.addParam("q_aprvSttsNo", "C1020");
        }

        List<CmsRequestVO> dataList = opCmsRequestDao.selectCmsRequestList(cmsRequestVo);
        Integer totalNum = opCmsRequestDao.selectCmsRequestListCount(cmsRequestVo);
        // 승인정보 한글명 설정
        for(CmsRequestVO dataVo : dataList) {
            if(Validate.isNotEmpty(dataVo.getAprvSttsNo())) {
                dataVo.setConfmSttusNm(CmsSupport.getConfmSttusNm(dataVo.getAprvSttsNo()));
            }
        }

        return new Pager<CmsRequestVO>(dataList, cmsRequestVo, totalNum);
    }

    @Override
    public Integer updateCmsRequest(CmsRequestVO cmsRequestVo) {
        Integer updateCnt = 0;

        CmsRequestVO dataVo = selectCmsRequest(cmsRequestVo);

        if(dataVo.getAprvSttsNo().equals("C1020")) {
            // 회수정보 등록
            updateCnt = opCmsRequestDao.updateCmsRequest(cmsRequestVo);
        }

        return updateCnt;
    }

}
