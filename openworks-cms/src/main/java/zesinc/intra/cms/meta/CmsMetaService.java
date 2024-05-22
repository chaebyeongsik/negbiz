/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.meta;

import zesinc.intra.cms.meta.domain.CmsMetaVO;

/**
 * 사용자메뉴메타 정보 서비스 클레스
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
public interface CmsMetaService {

    /**
     * 사용자메뉴메타 상세 조회
     * 
     * @param cmsMetaVo
     * @return
     */
    CmsMetaVO selectCmsMeta(CmsMetaVO cmsMetaVo);

    /**
     * 사용자메뉴메타 수정
     * 
     * @param cmsMetaVo
     * @return
     */
    Integer updateCmsMeta(CmsMetaVO cmsMetaVo);

    /**
     * 컨텐츠 배포
     * 
     * @param cmsManageVo
     * @return
     */
    Boolean publishCmsCntnts(CmsMetaVO cmsMetaVo);
}
