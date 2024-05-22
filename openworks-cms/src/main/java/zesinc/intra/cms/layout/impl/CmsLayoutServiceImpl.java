/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.layout.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.intra.cms.layout.CmsLayoutHistoryService;
import zesinc.intra.cms.layout.CmsLayoutMapper;
import zesinc.intra.cms.layout.CmsLayoutService;
import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.intra.cms.support.LOG_TYPE;
import zesinc.intra.cms.support.PublishUtil;
import zesinc.web.support.pager.Pager;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴레이아웃 정보 서비스 구현 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Service("opCmsLayoutService")
public class CmsLayoutServiceImpl extends EgovAbstractServiceImpl implements CmsLayoutService {

    @Resource(name = "opCmsLayoutHistoryService")
    private CmsLayoutHistoryService opCmsLayoutHistoryService;

    @Resource(name = "opCmsLayoutDao")
    private CmsLayoutMapper opCmsLayoutDao;

    @Override
    public Integer selectDplctChckCode(CmsLayoutVO cmsLayoutVo) {

        return opCmsLayoutDao.selectDplctChckCode(cmsLayoutVo);
    }

    @Override
    public CmsLayoutVO selectCmsLayout(CmsLayoutVO cmsLayoutVo) {

        CmsLayoutVO dataVo = opCmsLayoutDao.selectCmsLayout(cmsLayoutVo);

        return dataVo;
    }

    @Override
    public List<CmsLayoutVO> selectCmsLayoutList(CmsLayoutVO cmsLayoutVo) {

        return opCmsLayoutDao.selectCmsLayoutList(cmsLayoutVo);
    }

    @Override
    public Pager<CmsLayoutVO> selectCmsLayoutPageList(CmsLayoutVO cmsLayoutVo) {

        List<CmsLayoutVO> dataList = opCmsLayoutDao.selectCmsLayoutPageList(cmsLayoutVo);
        Integer totalNum = opCmsLayoutDao.selectCmsLayoutPageListCount(cmsLayoutVo);

        return new Pager<CmsLayoutVO>(dataList, cmsLayoutVo, totalNum);
    }

    @Override
    public Integer insertCmsLayout(CmsLayoutVO cmsLayoutVo) {

        PublishUtil.makeLayoutInfo(cmsLayoutVo);

        Integer insertCnt = opCmsLayoutDao.insertCmsLayout(cmsLayoutVo);

        // 등록 이력
        cmsLayoutVo.setLogType(LOG_TYPE.INSERT.getCdId());
        cmsLayoutVo.setMdfrId(cmsLayoutVo.getRgtrId());
        opCmsLayoutHistoryService.insertCmsLayoutHistory(cmsLayoutVo);

        return insertCnt;
    }

    @Override
    public Integer updateCmsLayout(CmsLayoutVO cmsLayoutVo) {

        PublishUtil.makeLayoutInfo(cmsLayoutVo);

        Integer updateCnt = opCmsLayoutDao.updateCmsLayout(cmsLayoutVo);

        // 수정 이력
        cmsLayoutVo.setLogType(LOG_TYPE.UPDATE.getCdId());
        opCmsLayoutHistoryService.insertCmsLayoutHistory(cmsLayoutVo);

        return updateCnt;
    }

    @Override
    public Integer deleteCmsLayout(CmsLayoutVO cmsLayoutVo) {

        // 원본 삭제
        CmsLayoutVO dataVo = selectCmsLayout(cmsLayoutVo);
        dataVo.setParamMap(cmsLayoutVo.getParamMap());

        Integer delCnt = opCmsLayoutDao.deleteCmsLayout(dataVo);

        // 삭제 이력
        dataVo.setLogType(LOG_TYPE.DELETE.getCdId());
        dataVo.setMdfrId(cmsLayoutVo.getMdfrId());
        opCmsLayoutHistoryService.insertCmsLayoutHistory(dataVo);

        // JSP 파일 삭제
        String contentsFilePath = dataVo.getFilePathNm();
        PublishUtil.deleteFile(contentsFilePath);

        return delCnt;
    }

    @Override
    public Boolean publishCmsLayout(CmsLayoutVO cmsLayoutVo) {
        // 원본
        cmsLayoutVo.addParam("q_siteSn", cmsLayoutVo.getSiteSn());
        cmsLayoutVo.addParam("q_lytCdNo", cmsLayoutVo.getLytCdNo());
        CmsLayoutVO dataVo = selectCmsLayout(cmsLayoutVo);

        return PublishUtil.publishLayout(dataVo);
    }

}
