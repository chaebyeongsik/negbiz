/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.bbsconfig;

import java.util.List;
import java.util.Map;

import zesinc.web.vo.cache.BbsConfigCacheVO;
import zesinc.web.vo.cache.BbsCtgryCacheVO;
import zesinc.web.vo.cache.BbsDomnCacheVO;
import zesinc.web.vo.cache.BbsItemCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 게시판 환경설정 캐시를 위한 DAO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 8.    woogi   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opBbsConfigCacheDao")
public interface BbsConfigCacheMapper {

    /**
     * 게시판 환경설정 목록을 반환한다.
     * 
     * @param bbsConfigCacheVO
     * @return
     */
    List<BbsConfigCacheVO> selectBbsConfigList();

    /**
     * 게시판 환경설정 도메인 목록을 반환한다.
     * 
     * @param bbsConfigCacheVO
     * @return
     */
    List<BbsDomnCacheVO> selectBbsConfigDomnList(BbsConfigCacheVO bbsConfigCacheVo);

    /**
     * 게시판 환경설정 정보를 반환한다.
     * 
     * @param bbsConfigCacheVO
     * @return
     */
    BbsConfigCacheVO selectBbsConfig(BbsConfigCacheVO bbsConfigCacheVo);

    /**
     * 게시판 카테고리 목록 조회
     * 
     * @param bbsConfigCacheVO
     * @return
     */
    List<BbsCtgryCacheVO> selectBbsCtgryList(Integer bbsSn);

    /**
     * 게시판 하위카테고리 목록 조회
     * 
     * @param bbsconfigVo
     * @return
     */
    List<BbsCtgryCacheVO> selectBbsLwprtCtgryList(BbsCtgryCacheVO bbsCtgryVo);

    /**
     * 게시판 환경설정 정보를 반환한다.
     * 
     * @param bbsConfigCacheVO
     * @return
     */
    List<BbsItemCacheVO> selectBbsItemList(Map<String, Object> paramMap);

}
