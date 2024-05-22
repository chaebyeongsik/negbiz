/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.multilang;

import java.util.List;

import zesinc.web.vo.cache.MultilangCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 다국어 관리에서 메시지를 추출할 DAO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opMultilangCacheDao")
public interface MultilangCacheMapper {

    /**
     * 다국어 목록
     */
    List<MultilangCacheVO> selectMultilangList(MultilangCacheVO multilangCacheVo);

}
