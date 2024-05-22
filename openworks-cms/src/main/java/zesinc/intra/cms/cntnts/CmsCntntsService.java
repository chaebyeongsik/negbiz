/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts;

import java.util.List;

import zesinc.core.compare.CompareViewVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsCompareVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.web.support.pager.Pager;
import zesinc.web.vo.cache.CmsCacheVO;

/**
 * 사용자메뉴컨텐츠 정보 서비스 클레스
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
public interface CmsCntntsService {

    /**
     * 사용자 레이아웃/컨텐츠 미리보기용 정보
     * 
     * @param cmsCntntsVo
     * @return
     */
    CmsCacheVO selectUserMenuPreview(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 상세 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    CmsCntntsVO selectCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 목록 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    Pager<CmsCntntsVO> selectCmsCntntsPageList(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 등록
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer insertCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 수정
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer updateCmsCntnts(CmsCntntsVO cmsCntntsVo);

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
     * 사용자메뉴컨텐츠 삭제. 삭제는 작성중인 경우에만 삭제가 가능하다.
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer deleteCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * CMS 메뉴 삭제에 따른 컨텐츠 모두 삭제
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer deleteCmsAllCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 컨텐츠 배포
     * 
     * @param cmsCntntsVo
     * @return
     */
    Boolean publishCmsCntnts(CmsCntntsVO cmsCntntsVo);
}
