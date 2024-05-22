/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.util.HashMap;

/**
 * 파라미터에 있는 html 태그 치환해 주는 Map 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ParamMap<K, V> extends HashMap<K, V> {

    /** serialVersionUID */
    private static final long serialVersionUID = 1014215365218784272L;

    /**
     * 파라미터에 있는 html 태그 치환
     */
    public V put(K key, V value) {

        return super.put(key, value);
    }
}
