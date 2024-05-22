/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 캐시 정보를 저장/반환 관리를 담당
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 11. 21.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CacheService {

    /** 케시객체 */
    private static final Map<String, Object> cache = Collections.synchronizedMap(new HashMap<String, Object>());

    /**
     * 지정된 키에 해당하는 오브젝트 반환
     * 
     * @param cacheKey
     * @return 저장된 케시 오브젝트
     */
    public static Object get(String cacheKey) {

        return cache.get(cacheKey);
    }

    /**
     * 케시에 오브젝트를 저장
     * 
     * @param cacheKey 케시 저장키
     * @param cacheValue 케시에 저장할 Object
     */
    public static void put(String cacheKey, Object cacheValue) {

        cache.put(cacheKey, cacheValue);
    }

    /**
     * 지정된 키에 해당하는 케시를 삭제
     * 
     * @param cacheKey 케시 키
     */
    public static void remove(String cacheKey) {

        cache.remove(cacheKey);
    }

}
