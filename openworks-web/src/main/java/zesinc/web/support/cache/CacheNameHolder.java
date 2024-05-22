/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 캐시 정보를 생성하는 클레스 목록을 저장하고, 리로드 등의 기능에 활용한다.
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
public class CacheNameHolder {

    /** 캐시명 저장 */
    private static Map<String, String> holder = new HashMap<String, String>();

    /**
     * 등록된 모든 캐쉬명을 <code>Map</code> 유형으로 얻는다.
     */
    public static Map<String, String> getMap() {
        return holder;
    }

    /**
     * 등록된 모든 캐쉬명을 <code>List&lt;Map&lt;String, String&gt;&gt;</code> 유형으로 얻는다.
     */
    public static List<Map<String, String>> getList() {
        List<Map<String, String>> cacheList = new ArrayList<Map<String, String>>();
        Map<String, String> cacheMap;

        Iterator<String> keys = holder.keySet().iterator();
        while(keys.hasNext()) {
            String key = keys.next();
            cacheMap = new HashMap<String, String>();
            cacheMap.put("key", key);
            cacheMap.put("value", holder.get(key));

            cacheList.add(cacheMap);
        }

        return cacheList;
    }

    /**
     * 캐쉬명을 등록한다.
     */
    public static void put(String cacheName, String cacheDecs) {

        holder.put(cacheName, cacheDecs);
    }

    /**
     * 캐쉬명을 얻는다.
     */
    public static String get(String cacheName) {

        return holder.get(cacheName);
    }
}
