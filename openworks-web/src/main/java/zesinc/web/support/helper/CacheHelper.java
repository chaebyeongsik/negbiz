/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.helper;

import zesinc.core.cache.Cache;

/**
 * 캐시 관리 지원 클레스
 * 캐시 클레스의 Bean 명칭은 아래 파일을 참조한다.
 * 
 * 설정 파일 /config/spring/context-initialize.xml
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CacheHelper {

    /**
     * 캐시 내역을 갱신한다.
     * 
     * @param clazzName
     * @param sqlSession
     */
    public static void cacheReload(String shortClassName) {
        cacheReload(shortClassName, null);
    }

    /**
     * 캐시 내역을 갱신한다.
     * 
     * @param clazzName
     * @param sqlSession
     */
    public static void cacheReload(String cacheName, Integer domainCd) {
        try {
            // 케쉬 정보 재설정
            Cache cache = (Cache) SpringHelper.findBean(cacheName);
            if(cache != null) {
                cache.reloadCache(domainCd);
            }
        } catch (Exception e) {
            // do nothing
        }
    }
}
