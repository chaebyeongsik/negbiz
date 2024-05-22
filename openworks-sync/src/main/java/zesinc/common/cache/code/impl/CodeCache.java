/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.code.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zesinc.common.cache.code.CodeCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.CodeCacheVO;

/**
 * 코드관리의 코드 캐시 정보
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CodeCache implements Cache {

    @Resource(name = "opCodeCacheDao")
    private CodeCacheMapper opCodeCacheDao;

    /** 코드캐시 키 */
    public static final String CODE_LIST_CACHE_KEY = BaseConfig.CODE_LIST_CACHE_KEY;
    /** 코드캐시 키 */
    public static final String CODE_MAP_CACHE_KEY = BaseConfig.CODE_MAP_CACHE_KEY;
    /** 기본하위코드목록저장키 */
    public static final String DEFAULT_LWPRT_KEY = BaseConfig.DEFAULT_LWPRT_KEY;

    @Override
    public void createCache() {

        // 단일코드 정보
        Map<String, CodeCacheVO> codeMapCache = new HashMap<String, CodeCacheVO>();
        // 하위 목록을 가진 코드 정보
        Map<String, Map<String, List<CodeCacheVO>>> codeListCache = new HashMap<String, Map<String, List<CodeCacheVO>>>();

        CodeCacheVO codeCacheVo = new CodeCacheVO();
        List<CodeCacheVO> codeList = opCodeCacheDao.selectCodeList(codeCacheVo);
        for(CodeCacheVO cacheVo : codeList) {
            String cdId = cacheVo.getCdId();
            String hghrkCdId = cacheVo.getHghrkCdId();
            String key = hghrkCdId + "_" + cdId;

            codeMapCache.put(key, cacheVo);

            // 자식이 있는 경우
            if(cacheVo.getHasChild()) {

                Map<String, List<CodeCacheVO>> choiceMap = new HashMap<String, List<CodeCacheVO>>();

                codeCacheVo.setHghrkCdId(hghrkCdId);
                codeCacheVo.setUpCdId(cdId);
                codeCacheVo.setCdId(cdId);

                // 선택자가 없는 경우에 사용될 기본 하위 코드 목록
                List<CodeCacheVO> lwrkCdIdList = opCodeCacheDao.selectCodeList(codeCacheVo);
                choiceMap.put(DEFAULT_LWPRT_KEY, lwrkCdIdList);

                // 선택자가 등록되어 있는 경우 해당 명칭으로 별도 목록을 저장
                List<CodeCacheVO> choiceCacheList = opCodeCacheDao.selectCodeChoiceList(codeCacheVo);

                for(CodeCacheVO choiceVo : choiceCacheList) {
                    // 이미 있다면 추가하고 없으면 목록객체를 만든후 추가한다.
                    List<CodeCacheVO> choiceList = choiceMap.get(choiceVo.getCdChcId());
                    if(Validate.isNotEmpty(choiceList)) {
                        choiceList.add(choiceVo);
                    } else {
                        choiceList = new ArrayList<CodeCacheVO>();
                        choiceList.add(choiceVo);

                        choiceMap.put(choiceVo.getCdChcId(), choiceList);
                    }
                }

                codeListCache.put(key, choiceMap);
            }

        }
        CacheService.put(CODE_MAP_CACHE_KEY, codeMapCache);
        CacheService.put(CODE_LIST_CACHE_KEY, codeListCache);
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

        return "공통코드";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.BOTH;
    }
}
