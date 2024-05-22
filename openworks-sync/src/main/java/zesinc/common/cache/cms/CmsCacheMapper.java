/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.cms;

import java.util.List;

import zesinc.web.vo.cache.CmsCacheVO;
import zesinc.web.vo.cache.CmsMngrCacheVO;
import zesinc.web.vo.cache.CmsUrlCacheVO;
import zesinc.web.vo.cache.CmsUserGradCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴관리의 메뉴정보 캐시를 위한 DAO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opCmsCacheDao")
public interface CmsCacheMapper {

    /**
     * 사용자메뉴 목록(상위메뉴 기준의 자식목록 반환)
     * 
     * @param cmsCacheVo
     * @return
     */
    List<CmsCacheVO> selectUserMenuList(CmsCacheVO cmsCacheVo);

    /**
     * 사용자메뉴의 하위메뉴 URL 목록
     * 
     * @param cmsCacheVo
     * @return
     */
    List<CmsUrlCacheVO> selectLwprtUrlList(CmsCacheVO cmsCacheVo);

    /**
     * 사용자메뉴의 이용가능 회원등급 목록
     * 
     * @param cmsCacheVo
     * @return
     */
    List<CmsUserGradCacheVO> selectUserGradList(CmsCacheVO cmsCacheVo);

    /**
     * 사용자메뉴에 표시할 담당자 정보
     * 
     * @param cmsCacheVo
     * @return
     */
    CmsMngrCacheVO selectUserMenuMngr(CmsCacheVO cmsCacheVo);

}
