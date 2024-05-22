/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.prhibt.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zesinc.common.cache.prhibt.PrhibtCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.PrhibtWrdCacheVO;

/**
 * 금지단어(금칙어) 케시 생성 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PrhibtCache implements Cache {

    @Resource(name = "opPrhibtCacheDao")
    private PrhibtCacheMapper opPrhibtCacheDao;

    public PrhibtCache() {
    }

    @Override
    public void createCache() {
        Map<String, List<String>> prhibtWrdMap = new HashMap<String, List<String>>();

        PrhibtWrdCacheVO prhibtWrdCacheVo = new PrhibtWrdCacheVO();
        prhibtWrdCacheVo.addParam("q_useYn", "Y");

        List<PrhibtWrdCacheVO> prhibtList = opPrhibtCacheDao.selectPrhibtWrdList(prhibtWrdCacheVo);
        for(PrhibtWrdCacheVO prhibtVo : prhibtList) {
            String cdId = prhibtVo.getPhbwdCdId();
            String[] prhibtWrds = StringUtil.split(prhibtVo.getPhbwdCn(), ",");

            List<String> prhibtWrdList = new ArrayList<String>(prhibtWrds.length);
            for(String prhibtWrd : prhibtWrds) {
                prhibtWrdList.add(prhibtWrd);
            }
            prhibtWrdMap.put(cdId, prhibtWrdList);
        }

        CacheService.put(BaseConfig.PRHIBT_CACHE_KEY, prhibtWrdMap);
    }

    @Override
    public void reloadCache() {
        reloadCache(null);
    }

    @Override
    public void reloadCache(Integer siteSn) {
        createCache();
    }

    @Override
    public String getName() {

        return "금지단어";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.BOTH;
    }

}
