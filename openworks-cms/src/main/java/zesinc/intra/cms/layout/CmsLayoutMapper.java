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
 * 사용자메뉴레이아웃 정보 DAO 클레스
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
@Mapper("opCmsLayoutDao")
public interface CmsLayoutMapper {

    /**
     * 레이아웃코드 중복 체크
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer selectDplctChckCode(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 상세 조회
     * 
     * @param cmsLayoutVo
     * @return
     */
    CmsLayoutVO selectCmsLayout(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 목록 조회
     * 
     * @param cmsLayoutVo
     * @return
     */
    List<CmsLayoutVO> selectCmsLayoutList(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 페이징 목록 조회
     * 
     * @param cmsLayoutVo
     * @return
     */
    List<CmsLayoutVO> selectCmsLayoutPageList(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 페이징 목록 건수
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer selectCmsLayoutPageListCount(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 등록
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer insertCmsLayout(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 수정
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer updateCmsLayout(CmsLayoutVO cmsLayoutVo);

    /**
     * 사용자메뉴레이아웃 삭제
     * 
     * @param cmsLayoutVo
     * @return
     */
    Integer deleteCmsLayout(CmsLayoutVO cmsLayoutVo);

}
