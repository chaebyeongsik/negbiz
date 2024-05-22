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

import zesinc.intra.cms.layout.CmsLayoutHistoryMapper;
import zesinc.intra.cms.layout.CmsLayoutHistoryService;
import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import zesinc.web.support.pager.Pager;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴레이아웃이력 정보 서비스 구현 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-07-03.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */

@Service("opCmsLayoutHistoryService")
public class CmsLayoutHistoryServiceImpl extends EgovAbstractServiceImpl implements CmsLayoutHistoryService {

    @Resource(name = "opCmsLayoutHistoryDao")
    private CmsLayoutHistoryMapper opCmsLayoutHistoryDao;

    @Override
    public CmsLayoutVO selectCmsLayoutHistory(CmsLayoutVO cmsLayoutVo) {

        CmsLayoutVO dataVo = opCmsLayoutHistoryDao.selectCmsLayoutHistory(cmsLayoutVo);

        return dataVo;
    }

    @Override
    public Pager<CmsLayoutVO> selectCmsLayoutHistoryPageList(CmsLayoutVO cmsLayoutVo) {

        List<CmsLayoutVO> dataList = opCmsLayoutHistoryDao.selectCmsLayoutHistoryList(cmsLayoutVo);
        Integer totalNum = opCmsLayoutHistoryDao.selectCmsLayoutHistoryListCount(cmsLayoutVo);

        return new Pager<CmsLayoutVO>(dataList, cmsLayoutVo, totalNum);
    }

    @Override
    public Integer insertCmsLayoutHistory(CmsLayoutVO cmsLayoutVo) {

        return opCmsLayoutHistoryDao.insertCmsLayoutHistory(cmsLayoutVo);
    }

    @Override
    public Integer deleteCmsLayoutHistory(CmsLayoutVO cmsLayoutVo) {

        // 원본 삭제
        CmsLayoutVO dataVo = selectCmsLayoutHistory(cmsLayoutVo);
        dataVo.setParamMap(cmsLayoutVo.getParamMap());

        Integer delCnt = opCmsLayoutHistoryDao.deleteCmsLayoutHistory(dataVo);

        return delCnt;
    }

}
