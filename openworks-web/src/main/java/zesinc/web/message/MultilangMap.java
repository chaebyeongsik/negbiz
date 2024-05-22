/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 다국어 메시지 관리를 위한 Map으로 HashMap를 상속받고,
 * 기존 프로그램에 영향이 없도록 메소드를 추가 함.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 19.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MultilangMap<K, V> extends HashMap<K, V> implements Map<K, V> {

    /** serialVersionUID */
    private static final long serialVersionUID = -8741492151063054814L;

    /**
     * key에 해당하는 값을 반환한다.
     * 만약 값이 null 이라면 빈 문자열("")을 반환한다.
     * 
     * @param 메시지 키
     * @return
     */
    public String getString(String key) {

        return getString(key, null);
    }

    /**
     * key에 해당하는 값을 반환한다.
     * 만약 값이 null 이라면 val 값을 확인하여 null이 아니면 val 값을 반환하고,
     * null 인 경우 빈 문자열("")을 반환한다.
     * 
     * @param key 메시지 키
     * @param val 값이 없을 경우의 default 값
     * @return
     */
    public String getString(String key, String val) {

        Object value = get(key);
        if(value == null) {
            return (val == null ? "" : val);
        }

        if(String.class.isAssignableFrom(value.getClass())) {
            return (String) get(key);
        }

        return get(key).toString();
    }
}
