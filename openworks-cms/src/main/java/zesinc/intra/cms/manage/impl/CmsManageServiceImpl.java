/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.manage.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.core.compare.CompareViewVO;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.cntnts.CmsCntntsService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.intra.cms.manage.CmsManageMapper;
import zesinc.intra.cms.manage.CmsManageService;
import zesinc.intra.cms.manage.domain.CmsManageVO;
import zesinc.intra.cms.meta.CmsMetaMapper;
import zesinc.intra.cms.support.CmsSupport;
import zesinc.web.support.pager.Pager;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴승인/반려 서비스 구현 클레스
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

@Service("opCmsManageService")
public class CmsManageServiceImpl extends EgovAbstractServiceImpl implements CmsManageService {

    @Resource(name = "opCmsManageDao")
    private CmsManageMapper opCmsManageDao;

    @Resource(name = "opCmsMetaDao")
    private CmsMetaMapper opCmsMetaDao;

    @Resource(name = "opCmsCntntsService")
    private CmsCntntsService opCmsCntntsService;

    @Override
    public CmsManageVO selectCmsManage(CmsManageVO cmsManageVo) {

        CmsManageVO dataVo = opCmsManageDao.selectCmsManage(cmsManageVo);
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
    public Pager<CmsManageVO> selectCmsManagePageList(CmsManageVO cmsManageVo) {

        String aprvSttsNo = cmsManageVo.getString("q_aprvSttsNo");
        if(Validate.isEmpty(aprvSttsNo)) {
            cmsManageVo.addParam("q_aprvSttsNo", "C1020");
        }

        List<CmsManageVO> dataList = opCmsManageDao.selectCmsManageList(cmsManageVo);
        Integer totalNum = opCmsManageDao.selectCmsManageListCount(cmsManageVo);
        // 승인정보 한글명 설정
        for(CmsManageVO dataVo : dataList) {
            if(Validate.isNotEmpty(dataVo.getAprvSttsNo())) {
                dataVo.setConfmSttusNm(CmsSupport.getConfmSttusNm(dataVo.getAprvSttsNo()));
            }
        }

        return new Pager<CmsManageVO>(dataList, cmsManageVo, totalNum);
    }

    @Override
    public Integer updateCmsManage(CmsManageVO cmsManageVo) {
        Integer updateCnt = 0;

        CmsManageVO dataVo = selectCmsManage(cmsManageVo);

        if(dataVo.getAprvSttsNo().equals("C1020") && cmsManageVo.getAprvSttsNo().equals("C1040")) {
            cmsManageVo.setAplcnYn("Y");

            opCmsManageDao.updateCmsCntntsApplcAt(cmsManageVo);

            // 관리자URL 설정
            StringBuilder sb = new StringBuilder();
            sb.append(CmsSupport.ADMIN_URL);
            sb.append("?q_siteSn=");
            sb.append(cmsManageVo.getSiteSn());
            sb.append("&amp;q_userMenuEngNm=");
            sb.append(cmsManageVo.getUserMenuEngNm());
            sb.append("&amp;q_contsSn=");
            sb.append(cmsManageVo.getContsSn());
            cmsManageVo.setMngrMenuUrlAddr(sb.toString());
            // 사용자URL 설정
            cmsManageVo.setUserMenuUrlAddr(dataVo.getBscPathNm() + cmsManageVo.getUserMenuEngNm() + ".jsp");

            // 신규 등록 컨텐츠 정보로 메뉴관리 정보 수정
            opCmsManageDao.updateCmsCntntsInfo(cmsManageVo);
            // 승인정보 등록
            updateCnt = opCmsManageDao.updateCmsManage(cmsManageVo);

        } else if(dataVo.getAprvSttsNo().equals("C1020") && cmsManageVo.getAprvSttsNo().equals("C1030")) {
            cmsManageVo.setAplcnYn("N");
            // 반려정보 등록
            updateCnt = opCmsManageDao.updateCmsManage(cmsManageVo);
        }

        return updateCnt;
    }

    @Override
    public Boolean publishCmsCntnts(CmsManageVO cmsManageVo) {
        // 원본
        CmsCntntsVO cmsCntntsVo = new CmsCntntsVO();
        cmsCntntsVo.setParamMap(cmsManageVo.getParamMap());

        return opCmsCntntsService.publishCmsCntnts(cmsCntntsVo);
    }

}
