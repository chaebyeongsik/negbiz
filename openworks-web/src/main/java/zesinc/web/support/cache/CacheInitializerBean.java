/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.cache;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.SpringHelper;

/**
 * 스프링 기동시 등록된 캐시 클레스들을 초기화하여 캐시에 담는다.
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
public class CacheInitializerBean implements ApplicationContextAware, InitializingBean {

    private ApplicationContext applicationContext;

    /** 로깅 객체 */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    /** 캐시 클레스 목록 */
    private Map<String, String> cacheMap;

    /**
     * cacheMap을 반환
     * 
     * @return List<String> cacheMap
     */
    public Map<String, String> getCacheMap() {
        return cacheMap;
    }

    /**
     * cacheMap을 설정
     * 
     * @param cacheMap 을(를) cacheMap로 설정
     */
    public void setCacheMap(Map<String, String> cacheMap) {
        this.cacheMap = cacheMap;
    }

    /**
     * 등록된 캐시 클레스를 모두 초기화하며 캐시서비스 객체에 등록한다.
     */
    public void initialize() {
        for(Entry<String, String> entry : cacheMap.entrySet()) {
            try {
                Cache cache = (Cache) SpringHelper.findBean(applicationContext, entry.getKey());
                // 케시가 존재할 경우만 생성한다.
                if(cache != null) {
                    CacheLocation loc = cache.getLocation();
                    if(CacheLocation.BOTH.equals(loc)) {
                        cache.createCache();
                    } else {
                        if(BaseConfig.SYSTEM_KIND.equals("intra") && CacheLocation.INTRA.equals(loc)) {
                            cache.createCache();
                        } else if(BaseConfig.SYSTEM_KIND.equals("user") && CacheLocation.USER.equals(loc)) {
                            cache.createCache();
                        }
                    }
                }
                // 케시 클레스 존재에 상관없이 이름을 담는다. (사용자단만 케시를 사용하는 경우등)
                CacheNameHolder.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error("Exception : ", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // do nothing
    }

    public void destroy() throws Exception {
        // do nothing
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
