/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.layout;

import java.util.List;

import zesinc.intra.cms.layout.domain.CmsLayoutVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴레이아웃이력 정보 DAO 클레스
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
@Mapper("opCmsLayoutHistoryDao")
public interface CmsLayoutHistoryMapper {

    /**
     * 사용자메뉴레이아웃이력 상세 조회
     * 
     * @param cmsLayoutVo
     * @return
     */
    CmsLayoutVO selectCmsLayoutHistory(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃이력 목록 조회
     * 
     * @param cmsLayoutVo
     * @return
     */
    List<CmsLayoutVO> selectCmsLayoutHistoryList(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃이력 목록 건수
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer selectCmsLayoutHistoryListCount(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃이력 등록
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer insertCmsLayoutHistory(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃이력 삭제
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer deleteCmsLayoutHistory(CmsLayoutVO cmsLayoutVo);

}
