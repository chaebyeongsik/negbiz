/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.hpcm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zesinc.common.cache.hpcm.HpcmCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.HpcmCacheVO;

/**
 * 도움말 정보 캐시 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 14.    박수정   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class HpcmCache implements Cache {

    @Resource(name = "opHpcmCacheDao")
    private HpcmCacheMapper opHpcmCacheDao;

    /** 도움말 캐시 키 */
    public static final String HPCM_CACHE_KEY = BaseConfig.HPCM_CACHE_KEY;

    @Override
    public void createCache() {

        Map<Integer, HpcmCacheVO> hpcmMap = new HashMap<Integer, HpcmCacheVO>();
        List<HpcmCacheVO> hpcmList = opHpcmCacheDao.selectHpcmList();
        for(HpcmCacheVO cacheVo : hpcmList) {
            hpcmMap.put(cacheVo.getMenuSn(), cacheVo);
        }
        CacheService.put(HPCM_CACHE_KEY, hpcmMap);
    }

    /**
     * 도움말 목록 캐시 리로드
     */
    @Override
    public void reloadCache() {
        reloadCache(null);
    }

    /**
     * 도메인별 도움말 목록 캐시 리로드(미사용)
     */
    @Override
    public void reloadCache(Integer domainCd) {
        createCache();
    }

    /**
     * 캐시 명칭
     */
    @Override
    public String getName() {
        return "도움말";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.INTRA;
    }
}
