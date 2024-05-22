/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base;

import java.util.List;

import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.web.vo.TreeVO;

/**
 * 사용자메뉴 정보 서비스 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public interface CmsService {

    /**
     * 부서목록 조회(Auto Complete 용)
     * 
     * @param cmsOrgVo
     * @return
     */
    List<CmsOrgVO> selectDeptList(CmsOrgVO cmsOrgVo);

    /**
     * 담당자목록 조회(Auto Complete 용)
     * 
     * @param cmsOrgVo
     * @return
     */
    List<CmsOrgVO> selectMngrList(CmsOrgVO cmsOrgVo);

    /**
     * 메뉴코드 중복 체크
     * 
     * @param cmsVo
     * @return
     */
    Integer selectDplctChckCode(CmsVO cmsVo);

    /**
     * 사용자메뉴 상세 조회(담당자, 하위 URL등 관련정보 전부 설정)
     * 
     * @param cmsVo
     * @return
     */
    CmsVO selectCms(CmsVO cmsVo);

    /**
     * 사용자메뉴 상세 조회(단순 메뉴 정보만)
     * 
     * @param cmsVo
     * @return
     */
    CmsVO selectSimpleCms(CmsVO cmsVo);

    /**
     * 사용자메뉴 트리 목록 조회
     * 
     * @param cmsVo
     * @return
     */
    List<TreeVO> selectCmsTreeList(CmsVO cmsVo);

    /**
     * 사용자메뉴 목록 조회
     * 
     * @param cmsVo
     * @return
     */
    List<CmsVO> selectCmsList(CmsVO cmsVo);

    /**
     * 사용자메뉴 등록
     * 
     * @param cmsVo
     * @return
     */
    Integer insertCms(CmsVO cmsVo);

    /**
     * 사용자메뉴기본정보 수정
     * 
     * @param cmsVo
     * @return
     */
    Integer updateCmsBase(CmsVO cmsVo);

    /**
     * 사용자메뉴추가정보 수정
     * 
     * @param cmsVo
     * @return
     */
    Integer updateCmsAdd(CmsVO cmsVo);

    /**
     * 사용자메뉴 정렬순서 수정
     * 
     * @param cmsVo
     * @return
     */
    Boolean updateCmsSortOrder(CmsVO cmsVo);

    /**
     * 사용자메뉴 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteCms(CmsVO cmsVo);

}
