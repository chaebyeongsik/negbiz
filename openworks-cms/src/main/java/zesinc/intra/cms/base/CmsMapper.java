/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base;

import java.util.List;

import zesinc.intra.cms.base.domain.CmsLwprtUrlVO;
import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.intra.cms.base.domain.CmsUserGradVO;
import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.web.vo.TreeVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴 정보 DAO 클레스
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
@Mapper("opCmsDao")
public interface CmsMapper {

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
     * 사용자메뉴 상세 조회
     * 
     * @param cmsVo
     * @return
     */
    CmsVO selectCms(CmsVO cmsVo);

    /**
     * 부모사용자메뉴 상세 조회
     * 
     * @param cmsVo
     * @return
     */
    CmsVO selectParentCms(CmsVO cmsVo);

    /**
     * 자식 사용자메뉴 목록 조회
     * 
     * @param cmsVo
     * @return
     */
    List<CmsVO> selectChildCmsList(CmsVO cmsVo);

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
     * 사용자메뉴 목록 건수
     * 
     * @param cmsVo
     * @return
     */
    Integer selectCmsListCount(CmsVO cmsVo);

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
     * 레이아웃 코드 수정(부모레이아웃 상속에 따른 자식 수정)
     * 
     * @param cmsVo
     * @return
     */
    Integer updateLytCdNo(CmsVO cmsVo);

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
    Integer updateCmsSortOrder(CmsVO cmsVo);

    /**
     * 사용자메뉴 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteCms(CmsVO cmsVo);

    /**
     * 사용자메뉴 하위 URL 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteMenuLwprtUrl(CmsVO cmsVo);

    /**
     * 하위메뉴URL 조회
     * 
     * @param cmsVo
     * @return
     */
    List<String> selectUserMenuLwprtUrl(CmsVO cmsVo);

    /**
     * 하위메뉴URL 등록
     * 
     * @param cmsLwprtUrlVo
     * @return
     */
    Integer insertUserMenuLwprtUrl(CmsLwprtUrlVO cmsLwprtUrlVo);

    /**
     * 하위메뉴 URL 삭제
     * 
     * @param cmsLwprtUrlVo
     * @return
     */
    Integer deleteUserMenuLwprtUrl(CmsLwprtUrlVO cmsLwprtUrlVo);

    /**
     * 메뉴 관리담당자 목록 반환
     * 
     * @param cmsVo
     * @return
     */
    List<CmsOrgVO> selectAuthorChargerList(CmsVO cmsVo);

    /**
     * 메뉴 관리담당자 등록
     * 
     * @param cmsOrgVo
     * @return
     */
    Integer insertAuthorCharger(CmsOrgVO cmsOrgVo);

    /**
     * 메뉴 관리담당자 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteAuthorCharger(CmsVO cmsVo);

    /**
     * 사용자등급 목록 조회
     * 
     * @param cmsVo
     * @return
     */
    List<CmsUserGradVO> selectUserGradList(CmsVO cmsVo);

    /**
     * 사용자메뉴등급 등록
     * 
     * @param userGradVo
     * @return
     */
    Integer insertUserGrad(CmsUserGradVO userGradVo);

    /**
     * 사용자메뉴등급 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteUserGrad(CmsVO cmsVo);

    /**
     * 사용자메뉴메타 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteUserMeta(CmsVO cmsVo);

    /**
     * 사용자메뉴평가 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteUserEvl(CmsVO cmsVo);

    /**
     * 사용자메뉴평가상세 삭제
     * 
     * @param cmsVo
     * @return
     */
    Integer deleteUserEvlDetail(CmsVO cmsVo);

}
