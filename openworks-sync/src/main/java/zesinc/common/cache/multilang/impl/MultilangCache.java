/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.multilang.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zesinc.common.cache.multilang.MultilangCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.message.MultilangMap;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.MultilangCacheVO;

/**
 * 다국어 관리에서 메시지를 국가언어별로 캐시에 담는다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MultilangCache implements Cache {

    @Resource(name = "opMultilangCacheDao")
    private MultilangCacheMapper opMultilangCacheDao;

    /** 다국어 캐시 키 */
    public static final String MULTILANG_CACHE_KEY = BaseConfig.MULTILANG_CACHE_KEY;
    /** 최상위 메뉴코드 */
    private static final String HIGH_MULTILANG_CODE = Config.getString("webapp-config.defaultCode.highCommonCd");

    @Override
    public void createCache() {
        MultilangCacheVO multilangCacheVo = new MultilangCacheVO();
        multilangCacheVo.setMtlngCdNm(HIGH_MULTILANG_CODE);
        multilangCacheVo.setUseYn("Y");

        List<MultilangCacheVO> multilangList = opMultilangCacheDao.selectMultilangList(multilangCacheVo);
        Map<String, String> multilangMap = new MultilangMap<String, String>();

        if(Validate.isNotEmpty(multilangList)) {
            for(MultilangCacheVO dataVo : multilangList) {
                String key = dataVo.getMtlngUnqCdNm();
                String value = dataVo.getMultiLangMsgCn();

                multilangMap.put(key, value);
            }
        }

        CacheService.put(MULTILANG_CACHE_KEY, multilangMap);
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

        return "다국어메시지";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.BOTH;
    }

}
