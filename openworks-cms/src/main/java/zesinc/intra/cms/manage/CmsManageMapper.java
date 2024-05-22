/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.manage;

import java.util.List;

import zesinc.intra.cms.manage.domain.CmsManageVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴승인/반려 정보 DAO 클레스
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
@Mapper("opCmsManageDao")
public interface CmsManageMapper {

    /**
     * 사용자메뉴승인 상세 조회
     * 
     * @param cmsManageVo
     * @return
     */
    CmsManageVO selectCmsManage(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴승인 목록 조회
     * 
     * @param cmsManageVo
     * @return
     */
    List<CmsManageVO> selectCmsManageList(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴승인 목록 건수
     * 
     * @param cmsManageVo
     * @return
     */
    Integer selectCmsManageListCount(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴컨텐츠 발행 승인시 이전 컨텐츠 적용상태 변경
     * 
     * @param cmsManageVo
     * @return
     */
    Integer updateCmsCntntsApplcAt(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴의 컨텐츠 정보를 수정한다.
     * 
     * @param cmsManageVo
     * @return
     */
    Integer updateCmsCntntsInfo(CmsManageVO cmsManageVo);

    /**
     * 사용자메뉴승인 업데이트
     * 
     * @param cmsManageVo
     * @return
     */
    Integer updateCmsManage(CmsManageVO cmsManageVo);
}
