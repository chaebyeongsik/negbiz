/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.popup;

import java.util.List;

import zesinc.component.popup.domain.JsPopupVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 팝업 표시를 위한 Dao
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Mapper("opJsPopupDao")
public interface JsPopupMapper {

    /**
     * 팝업 상세 내용
     * 
     * @param jsPopupVo
     * @return
     */
    JsPopupVO selectPopup(JsPopupVO jsPopupVo);

    /**
     * 금일 기준 팝업대상 목록을 반환
     * 
     * @param jsPopupVo
     * @return
     */
    List<JsPopupVO> selectPopupList(JsPopupVO jsPopupVo);
}
