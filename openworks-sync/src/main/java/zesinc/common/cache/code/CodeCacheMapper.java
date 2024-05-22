/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.code;

import java.util.List;

import zesinc.web.vo.cache.CodeCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 코드관리의 코드목록 캐시를 위한 DAO
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
@Mapper("opCodeCacheDao")
public interface CodeCacheMapper {

    /**
     * 전체 코드 목록을 반환한다.
     * 
     * @param codeCacheVo
     * @return
     */
    List<CodeCacheVO> selectCodeList(CodeCacheVO codeCacheVo);

    /**
     * 코드별 코드선택자 목록
     * 
     * @param codeCacheVo
     * @return
     */
    List<CodeCacheVO> selectCodeChoiceList(CodeCacheVO codeCacheVo);
}
