/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.core.compare.CompareView;
import zesinc.core.compare.CompareViewVO;
import zesinc.core.compare.impl.CompareViewImpl;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.intra.cms.cntnts.CmsCntntsMapper;
import zesinc.intra.cms.cntnts.CmsCntntsService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.intra.cms.meta.CmsMetaMapper;
import zesinc.intra.cms.meta.domain.CmsMetaVO;
import zesinc.intra.cms.support.CmsSupport;
import zesinc.intra.cms.support.ConfmSttus;
import zesinc.intra.cms.support.PublishUtil;
import zesinc.web.support.pager.Pager;
import zesinc.web.vo.cache.CmsCacheVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴컨텐츠 정보 서비스 구현 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-01.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Service("opCmsCntntsService")
public class CmsCntntsServiceImpl extends EgovAbstractServiceImpl implements CmsCntntsService {

    @Resource(name = "opCmsCntntsDao")
    private CmsCntntsMapper opCmsCntntsDao;
    @Resource(name = "opCmsMetaDao")
    private CmsMetaMapper opCmsMetaDao;

    @Override
    public CmsCacheVO selectUserMenuPreview(CmsCntntsVO cmsCntntsVo) {

        return opCmsCntntsDao.selectUserMenuPreview(cmsCntntsVo);
    }

    @Override
    public CmsCntntsVO selectCmsCntnts(CmsCntntsVO cmsCntntsVo) {

        CmsCntntsVO dataVo = opCmsCntntsDao.selectCmsCntnts(cmsCntntsVo);
        if(Validate.isNotEmpty(dataVo)) {
            // 승인정보 한글명 설정
            if(Validate.isNotEmpty(dataVo.getAprvSttsNo())) {
                dataVo.setConfmSttusNm(CmsSupport.getConfmSttusNm(dataVo.getAprvSttsNo()));
            }
        }
        return dataVo;
    }

    @Override
    public Pager<CmsCntntsVO> selectCmsCntntsPageList(CmsCntntsVO cmsCntntsVo) {

        List<CmsCntntsVO> dataList = opCmsCntntsDao.selectCmsCntntsList(cmsCntntsVo);
        Integer totalNum = opCmsCntntsDao.selectCmsCntntsListCount(cmsCntntsVo);
        // 승인정보 한글명 설정
        for(CmsCntntsVO dataVo : dataList) {
            if(Validate.isNotEmpty(dataVo.getAprvSttsNo())) {
                dataVo.setConfmSttusNm(CmsSupport.getConfmSttusNm(dataVo.getAprvSttsNo()));
            }
        }

        return new Pager<CmsCntntsVO>(dataList, cmsCntntsVo, totalNum);
    }

    @Override
    public Integer insertCmsCntnts(CmsCntntsVO cmsCntntsVo) {

        // 작성중 상태
        cmsCntntsVo.setAprvSttsNo(ConfmSttus.C1010.name());
        // 미적용 상태
        cmsCntntsVo.setAplcnYn("N");
        // 컨텐츠 등록
        Integer insertCnt = opCmsCntntsDao.insertCmsCntnts(cmsCntntsVo);

        return insertCnt;
    }

    @Override
    public Integer updateCmsCntnts(CmsCntntsVO cmsCntntsVo) {
        Integer updateCnt = 0;

        cmsCntntsVo.setContsSn(cmsCntntsVo.getInteger("q_contsSn"));

        CmsVO cmsVo = opCmsCntntsDao.selectCms(cmsCntntsVo);
        CmsCntntsVO dataVo = opCmsCntntsDao.selectCmsCntnts(cmsCntntsVo);

        // 잘못된 파라미터 인경우
        if(Validate.isNotEmpty(cmsVo) && Validate.isNotEmpty(dataVo)) {

            // 승인이 필요한 경우
            if(Validate.isNotEmpty(cmsVo.getAprvYn()) && cmsVo.getAprvYn().equals("Y")) {
                // 승인 필요 설정인 상태에서는 작성/신청 이외 상태의 컨텐츠는 오류
                if(!cmsCntntsVo.getAprvSttsNo().equals("C1010") && !cmsCntntsVo.getAprvSttsNo().equals("C1020")) {
                    return 0;
                }
            } else {
                // 자동승인 인 경우
                if(cmsCntntsVo.getAprvSttsNo().equals("C1050")) {
                    // 승인사유
                    cmsCntntsVo.setAprvRsn("자동승인");
                    // 적용여부
                    cmsCntntsVo.setAplcnYn("Y");
                    // 이전 버전 컨텐츠 모두 미적용 상태로 변경
                    opCmsCntntsDao.updateCmsCntntsApplcAt(cmsCntntsVo);

                    // 관리자URL 설정
                    StringBuilder sb = new StringBuilder();
                    sb.append(CmsSupport.ADMIN_URL);
                    sb.append("?q_siteSn=");
                    sb.append(cmsCntntsVo.getSiteSn());
                    sb.append("&amp;q_userMenuEngNm=");
                    sb.append(cmsCntntsVo.getUserMenuEngNm());
                    sb.append("&amp;q_contsSn=");
                    sb.append(cmsCntntsVo.getContsSn());
                    cmsCntntsVo.setMngrMenuUrlAddr(sb.toString());

                    // 사용자URL 설정
                    cmsCntntsVo.setUserMenuUrlAddr(cmsVo.getBscPathNm() + cmsCntntsVo.getUserMenuEngNm() + ".jsp");

                    // 신규 등록 컨텐츠 정보로 메뉴관리 정보 수정
                    opCmsCntntsDao.updateCmsCntntsInfo(cmsCntntsVo);
                }
            }
            // 발행자ID
            cmsCntntsVo.setIsrId(cmsCntntsVo.getMdfrId());
            updateCnt = opCmsCntntsDao.updateCmsCntnts(cmsCntntsVo);
        }
        return updateCnt;
    }

    @Override
    public CmsCntntsCompareVO compareScreenCntnts(CmsCntntsCompareVO compareVo) {

        serDiffContents(compareVo);

        return compareVo;
    }

    @Override
    public List<CompareViewVO> compareSourceCntnts(CmsCntntsCompareVO compareVo) {

        serDiffContents(compareVo);

        StringBuilder leftCont = new StringBuilder();
        StringBuilder rightCont = new StringBuilder();

        // 좌측 컨텐츠
        if(Validate.isNotEmpty(compareVo.getLeftCntnts())) {
            if(Validate.isNotEmpty(compareVo.getLeftCntnts().getStrtContsCn())) {
                leftCont.append(compareVo.getLeftCntnts().getStrtContsCn()).append(Config.getString("line.separator"));
            }
            leftCont.append(compareVo.getLeftCntnts().getMainContsCn());
        }

        // 우측 컨텐츠
        if(Validate.isNotEmpty(compareVo.getRightCntnts())) {
            if(Validate.isNotEmpty(compareVo.getRightCntnts().getStrtContsCn())) {
                rightCont.append(compareVo.getRightCntnts().getStrtContsCn()).append(Config.getString("line.separator"));
            }
            rightCont.append(compareVo.getRightCntnts().getMainContsCn());
        }

        CompareView view = new CompareViewImpl().setLeftContent(leftCont).setRightContent(rightCont);

        return view.getCompareVoList();
    }

    /**
     * 비교를 위한 두개 버전의 컨텐츠 정보를 담는다.
     * 
     * @param compareVo
     */
    private void serDiffContents(CmsCntntsCompareVO compareVo) {

        CmsCntntsVO paramVo = new CmsCntntsVO();
        paramVo.setParamMap(compareVo.getParamMap());

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

        // 좌측
        paramVo.addParam("q_contsSn", leftCntntsSn);

        CmsCntntsVO leftCntntsVo = selectCmsCntnts(paramVo);
        compareVo.setLeftCntntsSn(leftCntntsSn);
        compareVo.setLeftCntnts(leftCntntsVo);

        // 우측
        paramVo.addParam("q_contsSn", rightCntntsSn);

        CmsCntntsVO rightCntntsVo = selectCmsCntnts(paramVo);
        compareVo.setRightCntntsSn(rightCntntsSn);
        compareVo.setRightCntnts(rightCntntsVo);

        compareVo.setSiteSn(compareVo.getInteger("q_siteSn"));
        compareVo.setUserMenuEngNm(compareVo.getString("q_userMenuEngNm"));
        compareVo.setContsSns(opCmsCntntsDao.selectCmsCntntsSns(paramVo));
    }

    @Override
    public Integer deleteCmsCntnts(CmsCntntsVO cmsCntntsVo) {
        Integer delCnt = 0;

        // 원본 삭제
        CmsCntntsVO dataVo = selectCmsCntnts(cmsCntntsVo);
        if(Validate.isNotEmpty(dataVo) && dataVo.getAprvSttsNo().equals(ConfmSttus.C1010.toString())) {
            dataVo.setParamMap(cmsCntntsVo.getParamMap());
            delCnt = opCmsCntntsDao.deleteCmsCntnts(dataVo);
        }
        return delCnt;
    }

    @Override
    public Integer deleteCmsAllCntnts(CmsCntntsVO cmsCntntsVo) {
        Integer delCnt = 0;
        CmsCntntsVO paramVo = new CmsCntntsVO();
        paramVo.setParamMap(cmsCntntsVo.getParamMap());

        // 원본 삭제
        List<CmsCntntsVO> dataList = opCmsCntntsDao.selectCmsAllCntnts(cmsCntntsVo);
        for(CmsCntntsVO cntntsVo : dataList) {
            paramVo.addParam("q_contsSn", cntntsVo.getContsSn());

            delCnt += opCmsCntntsDao.deleteCmsCntnts(paramVo);
        }

        return delCnt;
    }

    @Override
    public Boolean publishCmsCntnts(CmsCntntsVO cmsCntntsVo) {
        boolean isPublished = false;

        // 원본
        CmsCntntsVO dataVo = selectCmsCntnts(cmsCntntsVo);
        if(Validate.isNotEmpty(dataVo)) {
            // 메타
            CmsMetaVO metaVo = new CmsMetaVO();
            metaVo.setParamMap(cmsCntntsVo.getParamMap());
            metaVo = opCmsMetaDao.selectCmsMeta(metaVo);

            // 최종 발행상태 변경
            isPublished = PublishUtil.publishContents(dataVo, metaVo);
            if(isPublished) {
                opCmsCntntsDao.updateCmsCntntsPblicte(cmsCntntsVo);
            }
        }

        return isPublished;
    }

}
