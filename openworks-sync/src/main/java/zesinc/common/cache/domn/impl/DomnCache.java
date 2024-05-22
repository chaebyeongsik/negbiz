/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.domn.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.common.cache.domn.DomnCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 도메인관리 정보를 서비스하기 위한 캐시 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class DomnCache implements Cache {

    /** 로깅 객체 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "opDomnCacheDao")
    private DomnCacheMapper opDomnCacheDao;

    @Override
    public void createCache() {
        DomnCacheVO domnCacheVo = new DomnCacheVO();
        domnCacheVo.setUseYn("Y");

        Map<String, DomnCacheVO> domnNmMap = new HashMap<String, DomnCacheVO>();
        Map<Integer, DomnCacheVO> siteSnMap = new HashMap<Integer, DomnCacheVO>();

        List<DomnCacheVO> domnList = opDomnCacheDao.selectDomnList(domnCacheVo);
        for(DomnCacheVO domnVo : domnList) {
            String siteNm = domnVo.getSiteNm();
            Integer port = domnVo.getPortSn();
            Integer scrtyPortSn = domnVo.getScrtyPortSn();
            String context = domnVo.getSitePathNm();

            String key = siteNm + "_" + port + "_" + context;
            domnNmMap.put(key, domnVo);

            logger.debug("\nDOMN_NM_MAP_CACHE_KEY Key is {}\n", key);

            // https 보안 프로토콜 운영을 하며 기본 포트와 다른 경우 두개의 키로 케시
            String scrtyCacheKey = null;
            if(domnVo.getHttpsYn().equals("Y") && Validate.isNotEmpty(scrtyPortSn) && !port.equals(scrtyPortSn)) {
                scrtyCacheKey = siteNm + "_" + scrtyPortSn + "_" + context;
                domnNmMap.put(scrtyCacheKey, domnVo);

                logger.debug("\nDOMN_NM_MAP_CACHE_KEY ScrtyCacheKey is {}\n", scrtyCacheKey);
            }

            siteSnMap.put(domnVo.getSiteSn(), domnVo);
            domnVo.setDomnGroupList(opDomnCacheDao.selectDomnGroupList(domnVo));
        }

        CacheService.put(BaseConfig.DOMN_LIST_CACHE_KEY, domnList);
        CacheService.put(BaseConfig.DOMN_NM_MAP_CACHE_KEY, domnNmMap);
        CacheService.put(BaseConfig.SITE_SN_MAP_CACHE_KEY, siteSnMap);
    }

    @Override
    public void reloadCache() {
        reloadCache(null);
    }

    @Override
    public void reloadCache(Integer domainCd) {
        createCache();
    }

    @Override
    public String getName() {

        return "도메인정보";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.BOTH;
    }
}
