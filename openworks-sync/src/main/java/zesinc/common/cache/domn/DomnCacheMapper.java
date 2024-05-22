/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.domn;

import java.util.List;

import zesinc.web.vo.cache.DomnCacheVO;
import zesinc.web.vo.cache.DomnGroupCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 도메인(사이트)관리 정보 조회 DAO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opDomnCacheDao")
public interface DomnCacheMapper {

    /**
     * 도메인 정보 목록
     * 
     * @param domnCacheVo
     * @return
     */
    List<DomnCacheVO> selectDomnList(DomnCacheVO domnCacheVo);

    /**
     * 도메인 그룹 정보 목록
     * 
     * @param domnCacheVo
     * @return
     */
    List<DomnGroupCacheVO> selectDomnGroupList(DomnCacheVO domnCacheVo);
}
