/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.bbsconfig.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import zesinc.common.cache.bbsconfig.BbsConfigCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.BbsConfigCacheVO;
import zesinc.web.vo.cache.BbsCtgryCacheVO;
import zesinc.web.vo.cache.BbsDomnCacheVO;
import zesinc.web.vo.cache.BbsItemCacheVO;

/**
 * 게시판 환경설정 캐시 정보
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 8.    woogi   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BbsConfigCache implements Cache {

    @Resource(name = "opBbsConfigCacheDao")
    private BbsConfigCacheMapper opBbsConfigCacheDao;

    public static final String BBS_CACHE_KEY = BaseConfig.BBS_CACHE_KEY;
    public static final String BBS_CONFIG_CACHE_KEY = BaseConfig.BBS_CONFIG_CACHE_KEY;
    public static final String BBS_ITEM_CACHE_KEY = BaseConfig.BBS_ITEM_CACHE_KEY;

    @Override
    public void createCache() {

        Map<String, Object> paramMap = new HashMap<String, Object>();

        List<BbsConfigCacheVO> bbsConfigList = opBbsConfigCacheDao.selectBbsConfigList();

        CacheService.put(BBS_CACHE_KEY, bbsConfigList);

        List<BbsItemCacheVO> listItemList;
        List<BbsItemCacheVO> redngItemList;
        List<BbsItemCacheVO> formItemList;

        for(BbsConfigCacheVO bbsConfigCacheVo : bbsConfigList) {

            listItemList = new ArrayList<BbsItemCacheVO>();
            redngItemList = new ArrayList<BbsItemCacheVO>();
            formItemList = new ArrayList<BbsItemCacheVO>();

            // 도메인 설정별 스킨 설정 설정
            List<BbsDomnCacheVO> domnConfigList = opBbsConfigCacheDao.selectBbsConfigDomnList(bbsConfigCacheVo);
            if(Validate.isNotEmpty(domnConfigList)) {
                Map<Integer, BbsDomnCacheVO> skinMap = new HashMap<Integer, BbsDomnCacheVO>();
                for(BbsDomnCacheVO domnCache : domnConfigList) {
                    skinMap.put(domnCache.getSiteSn(), domnCache);
                }
                bbsConfigCacheVo.setSkins(skinMap);
            }

            // 게시판 분류(카테고리)
            if("Y".equals(bbsConfigCacheVo.getClsfUseYn())) {
                List<BbsCtgryCacheVO> bbsCtgryCacheList = opBbsConfigCacheDao.selectBbsCtgryList(bbsConfigCacheVo.getBbsSn());

                if(Validate.isNotEmpty(bbsCtgryCacheList)) {
                    List<BbsCtgryCacheVO> lwprtCtgryList;
                    for(BbsCtgryCacheVO ctgryVo : bbsCtgryCacheList) {
                        lwprtCtgryList = opBbsConfigCacheDao.selectBbsLwprtCtgryList(ctgryVo);
                        if(Validate.isNotEmpty(lwprtCtgryList)) {
                            ctgryVo.setLwprtCtgryList(lwprtCtgryList);
                        }
                    }
                    bbsConfigCacheVo.setBbsCtgryList(bbsCtgryCacheList);
                }
            }

            List<BbsItemCacheVO> bbsItemList;
            Map<String, BbsItemCacheVO> bbsItemMap = new HashMap<String, BbsItemCacheVO>();
            paramMap.put("bbsSn", bbsConfigCacheVo.getBbsSn());

            // 검색 대상 아이템 목록
            paramMap.put("srchStngYn", "Y");
            bbsItemList = opBbsConfigCacheDao.selectBbsItemList(paramMap);
            for(BbsItemCacheVO bbsItemCacheVo : bbsItemList) {
                bbsItemCacheVo.setFieldNm(bbsItemCacheVo.getColId());
            }
            bbsConfigCacheVo.setSearchColunms(bbsItemList);

            // 전체 아이템 목록
            paramMap.put("srchStngYn", "");
            bbsItemList = opBbsConfigCacheDao.selectBbsItemList(paramMap);

            String fieldNm;
            String colNm;
            for(BbsItemCacheVO bbsItemCacheVo : bbsItemList) {
                fieldNm = StringUtil.camelCase(bbsItemCacheVo.getColId());
                bbsItemCacheVo.setFieldNm(fieldNm);

                colNm = Validate.isEmpty(bbsItemCacheVo.getScrnNm()) ? bbsItemCacheVo.getColNm() : bbsItemCacheVo.getScrnNm();
                bbsItemCacheVo.setColNm(colNm);

                bbsItemMap.put(fieldNm, bbsItemCacheVo);

                if(bbsItemCacheVo.getLstIndctYn().equals("Y")) {
                    listItemList.add(bbsItemCacheVo);
                }
                if(bbsItemCacheVo.getInqIndctYn().equals("Y")) {
                    redngItemList.add(bbsItemCacheVo);
                }
                if(bbsItemCacheVo.getInptIndctYn().equals("Y")) {
                    formItemList.add(bbsItemCacheVo);
                }
            }
            // 목록/상세/폼 화면별 아이템 목록
            bbsConfigCacheVo.setListColunms(listItemList);
            bbsConfigCacheVo.setRedngColunms(redngItemList);
            bbsConfigCacheVo.setFormColunms(formItemList);
            // 전체 아이템 멥
            bbsConfigCacheVo.setBbsItemMap(bbsItemMap);

            CacheService.put(BBS_ITEM_CACHE_KEY + bbsConfigCacheVo.getBbsSn(), bbsItemList);
            CacheService.put(BBS_CONFIG_CACHE_KEY + bbsConfigCacheVo.getBbsSn(), bbsConfigCacheVo);
        }

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

        return "게시판환경설정";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.BOTH;
    }

}
