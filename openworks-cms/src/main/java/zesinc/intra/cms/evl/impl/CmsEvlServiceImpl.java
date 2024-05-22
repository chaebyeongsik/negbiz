/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.evl.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.core.lang.Validate;
import zesinc.intra.cms.evl.CmsEvlMapper;
import zesinc.intra.cms.evl.CmsEvlService;
import zesinc.intra.cms.evl.domain.CmsEvlVO;
import zesinc.web.support.pager.Pager;
import zesinc.web.vo.chart.BarChartVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴평가 정보 서비스 구현 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-09.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Service("opCmsEvlService")
public class CmsEvlServiceImpl extends EgovAbstractServiceImpl implements CmsEvlService {

    @Resource(name = "opCmsEvlDao")
    private CmsEvlMapper opCmsEvlDao;

    @Override
    public List<CmsEvlVO> selectCmsEvlList(CmsEvlVO cmsEvlVo) {

        List<CmsEvlVO> dataList = opCmsEvlDao.selectCmsEvlList(cmsEvlVo);

        return dataList;
    }

    @Override
    public List<BarChartVO> selectCmsEvlChartList(CmsEvlVO cmsEvlVo) {

        List<BarChartVO> dataList = opCmsEvlDao.selectCmsEvlChartList(cmsEvlVo);

        return dataList;
    }

    @Override
    public Pager<CmsEvlVO> selectCmsEvlDetailPageList(CmsEvlVO cmsEvlVo) {

        List<CmsEvlVO> dataList = opCmsEvlDao.selectCmsEvlDetailList(cmsEvlVo);
        Integer totalNum = opCmsEvlDao.selectCmsEvlDetailListCount(cmsEvlVo);

        return new Pager<CmsEvlVO>(dataList, cmsEvlVo, totalNum);
    }

    @Override
    public Pager<CmsEvlVO> selectCmsEvlAllPageList(CmsEvlVO cmsEvlVo) {

        List<CmsEvlVO> dataList = opCmsEvlDao.selectCmsEvlAllList(cmsEvlVo);
        Integer totalNum = opCmsEvlDao.selectCmsEvlAllListCount(cmsEvlVo);

        for(CmsEvlVO dataVo : dataList) {
            // 메뉴권한담당자목록
            if(Validate.isNotEmpty(dataVo.getAuthrtGroupNm()) && dataVo.getAuthrtGroupNm().equals("CHARGER")) {
                cmsEvlVo.addParam("siteSn", dataVo.getSiteSn());
                cmsEvlVo.addParam("userMenuEngNm", dataVo.getUserMenuEngNm());
                dataVo.setAuthorChargerList(opCmsEvlDao.selectAuthorChargerList(cmsEvlVo.getParamMap()));
            }
        }

        return new Pager<CmsEvlVO>(dataList, cmsEvlVo, totalNum);
    }
}
