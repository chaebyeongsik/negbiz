/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.manage;

import java.util.List;

import zesinc.core.compare.CompareViewVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.manage.domain.CmsManageVO;
import zesinc.web.support.pager.Pager;

/**
 * 사용자메뉴승인 정보 서비스 클레스
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
public interface CmsManageService {

    /**
     * 사용자메뉴승인 상세 조회
     * 
     * @param cmsManageVo
     * @return
     */
    CmsManageVO selectCmsManage(CmsManageVO cmsManageVo);

    /**
     * 화면비교를 위한 컨텐츠 생성
     * 
     * @param compareVo
     * @return
     */
    CmsCntntsCompareVO compareScreenCntnts(CmsCntntsCompareVO compareVo);

    /**
     * 소스비교를 위한 컨텐츠 생성
     * 
     * @param compareVo
     * @return
     */
    List<CompareViewVO> compareSourceCntnts(CmsCntntsCompareVO compareVo);

    /**
     * 사용자메뉴승인 목록 조회
     * 
     * @param cmsManageVo
     * @return
     */
    Pager<CmsManageVO> selectCmsManagePageList(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴승인 업데이트
     * 
     * @param cmsManageVo
     * @return
     */
    Integer updateCmsManage(CmsManageVO cmsManageVo);

    /**
     * 컨텐츠 배포
     * 
     * @param cmsManageVo
     * @return
     */
    Boolean publishCmsCntnts(CmsManageVO cmsManageVo);
}
