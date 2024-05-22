/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.meta.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.intra.cms.cntnts.CmsCntntsService;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.intra.cms.meta.CmsMetaMapper;
import zesinc.intra.cms.meta.CmsMetaService;
import zesinc.intra.cms.meta.domain.CmsMetaVO;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;

/**
 * 사용자메뉴메타 정보 서비스 구현 클레스
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

@Service("opCmsMetaService")
public class CmsMetaServiceImpl extends EgovAbstractServiceImpl implements CmsMetaService {

    @Resource(name = "opCmsMetaDao")
    private CmsMetaMapper opCmsMetaDao;

    @Resource(name = "opCmsCntntsService")
    private CmsCntntsService opCmsCntntsService;

    @Override
    public CmsMetaVO selectCmsMeta(CmsMetaVO cmsMetaVo) {

        CmsMetaVO dataVo = opCmsMetaDao.selectCmsMeta(cmsMetaVo);

        return dataVo;
    }

    @Override
    public Integer updateCmsMeta(CmsMetaVO cmsMetaVo) {
        Integer cnt = 0;

        Integer count = opCmsMetaDao.selectCmsMetaCount(cmsMetaVo);
        if(count < 1) {
            cnt = opCmsMetaDao.insertCmsMeta(cmsMetaVo);
        } else {
            cnt = opCmsMetaDao.updateCmsMeta(cmsMetaVo);
        }

        return cnt;
    }

    @Override
    public Boolean publishCmsCntnts(CmsMetaVO cmsMetaVo) {
        // 원본
        CmsCntntsVO cmsCntntsVo = new CmsCntntsVO();
        cmsCntntsVo.setParamMap(cmsMetaVo.getParamMap());

        return opCmsCntntsService.publishCmsCntnts(cmsCntntsVo);
    }
}
