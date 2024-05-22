/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.request;

import java.util.List;

import zesinc.core.compare.CompareViewVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.request.domain.CmsRequestVO;
import zesinc.web.support.pager.Pager;

/**
 * 컨텐츠발행요청 정보 서비스 클레스
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
public interface CmsRequestService {

    /**
     * 사용자메뉴승인요청 상세 조회
     * 
     * @param cmsRequestVo
     * @return
     */
    CmsRequestVO selectCmsRequest(CmsRequestVO cmsRequestVo);

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
     * 사용자메뉴승인요청 목록 조회
     * 
     * @param cmsRequestVo
     * @return
     */
    Pager<CmsRequestVO> selectCmsRequestPageList(CmsRequestVO cmsRequestVo);

    /**
     * 사용자메뉴승인요청 회수 업데이트
     * 
     * @param cmsRequestVo
     * @return
     */
    Integer updateCmsRequest(CmsRequestVO cmsRequestVo);

}
