/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts;

import java.util.List;

import zesinc.intra.cms.base.domain.CmsVO;
import zesinc.intra.cms.cntnts.domain.CmsCntntsVO;
import zesinc.web.vo.cache.CmsCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴컨텐츠 정보 DAO 클레스
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
@Mapper("opCmsCntntsDao")
public interface CmsCntntsMapper {

    /**
     * 사용자 레이아웃/컨텐츠 미리보기용 정보
     * 
     * @param cmsCntntsVo
     * @return
     */
    CmsCacheVO selectUserMenuPreview(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴 상세 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    CmsVO selectCms(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 상세 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    CmsCntntsVO selectCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 해당 사용자메뉴의 모든 컨텐츠 일련번호 목록(비교 파라미터용)
     * 
     * @param cmsCntntsVo
     * @return
     */
    List<Integer> selectCmsCntntsSns(CmsCntntsVO cmsCntntsVo);

    /**
     * 특정 CMS 메뉴에 해당하는 모든 컨텐츠 목록 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    List<CmsCntntsVO> selectCmsAllCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 목록 조회
     * 
     * @param cmsCntntsVo
     * @return
     */
    List<CmsCntntsVO> selectCmsCntntsList(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 목록 건수
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer selectCmsCntntsListCount(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 등록
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer insertCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴의 컨텐츠 정보를 수정한다.
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer updateCmsCntntsInfo(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 수정
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer updateCmsCntnts(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 발행정보 수정
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer updateCmsCntntsPblicte(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 발행 승인시 이전 컨텐츠 적용상태 변경
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer updateCmsCntntsApplcAt(CmsCntntsVO cmsCntntsVo);

    /**
     * 사용자메뉴컨텐츠 삭제
     * 
     * @param cmsCntntsVo
     * @return
     */
    Integer deleteCmsCntnts(CmsCntntsVO cmsCntntsVo);

}
