/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.prhibt;

import java.util.List;

import zesinc.web.vo.cache.PrhibtWrdCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 금지단어(금칙어) 케시 생성 Dao
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opPrhibtCacheDao")
public interface PrhibtCacheMapper {

    /**
     * 금지단어(금칙어) 목록
     */
    List<PrhibtWrdCacheVO> selectPrhibtWrdList(PrhibtWrdCacheVO prhibtWrdCacheVo);
}
