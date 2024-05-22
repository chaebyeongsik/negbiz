/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.evl;

import java.util.List;
import java.util.Map;

import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.intra.cms.evl.domain.CmsEvlVO;
import zesinc.web.vo.chart.BarChartVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 사용자메뉴평가 정보 DAO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-09.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opCmsEvlDao")
public interface CmsEvlMapper {

    /**
     * 사용자메뉴평가 목록 조회
     * 
     * @param cmsEvlVo
     * @return
     */
    List<CmsEvlVO> selectCmsEvlList(CmsEvlVO cmsEvlVo);

    /**
     * 사용자메뉴평가 그래프 목록 조회
     * 
     * @param cmsEvlVo
     * @return
     */
    List<BarChartVO> selectCmsEvlChartList(CmsEvlVO cmsEvlVo);

    /**
     * 사용자메뉴평가 상세 조회 목록
     * 
     * @param cmsEvlVo
     * @return
     */
    List<CmsEvlVO> selectCmsEvlDetailList(CmsEvlVO cmsEvlVo);

    /**
     * 사용자메뉴평가 상세 조회 목록 카운트
     * 
     * @param cmsEvlVo
     * @return
     */
    Integer selectCmsEvlDetailListCount(CmsEvlVO cmsEvlVo);

    /**
     * 사용자메뉴평가 전체 목록
     * 
     * @param cmsEvlVo
     * @return
     */
    List<CmsEvlVO> selectCmsEvlAllList(CmsEvlVO cmsEvlVo);

    /**
     * 사용자메뉴평가 전체 목록 카운트
     * 
     * @param cmsEvlVo
     * @return
     */
    Integer selectCmsEvlAllListCount(CmsEvlVO cmsEvlVo);

    /**
     * 컨텐츠별 담당자 목록
     * 
     * @param paramMap
     * @return
     */
    List<CmsOrgVO> selectAuthorChargerList(Map<String, Object> paramMap);

}
