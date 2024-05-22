/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.cms.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import zesinc.common.cache.cms.CmsCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.spring.security.support.OpenworksRequestMatcher;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.CmsCacheVO;
import zesinc.web.vo.cache.CmsMngrCacheVO;
import zesinc.web.vo.cache.CmsUrlCacheVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자단 메뉴 케쉬 객체
 * 모든 메뉴 케쉬는 동일 메뉴정보의 레퍼런스를 유지 하도록
 * 개별 객체를 생성을 하지 않도록 하여 관리 한다.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 23.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsCache implements Cache {

    @Resource(name = "opCmsCacheDao")
    private CmsCacheMapper opCmsCacheDao;

    /** 로깅 객체 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /*
     * 도메인별 케시명 접미사
     */
    /** 사용자메뉴코드 캐시 키 */
    public static final String USER_MENU_ENG_NM_MAP_KEY = BaseConfig.USER_MENU_ENG_NM_MAP_KEY;
    /** 사용자메뉴 목록 캐시 키 */
    public static final String USER_MENU_LIST_KEY = BaseConfig.USER_MENU_LIST_KEY;
    /** 사용자메뉴 URI 캐시 키 */
    public static final String USER_MENU_URI_MAP_KEY = BaseConfig.USER_MENU_URI_MAP_KEY;

    /** 최상위 기본 메뉴코드 */
    public static String HIGH_CMS_CD = Config.getString("webapp-config.defaultCode.highCmsCd", "web");

    /**
     * 전체 사이트별 케시 리로드
     */
    @Override
    public void createCache() {
        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);

        for(DomnCacheVO domn : domnList) {
            Integer siteSn = domn.getSiteSn();
            createCache(siteSn);
        }
    }

    /**
     * 도메인별 케시 리로드
     */
    public void createCache(Integer domainCd) {

        if(Validate.isEmpty(domainCd)) {
            return;
        }

        @SuppressWarnings("unchecked")
        Map<Integer, DomnCacheVO> siteSnMap = (Map<Integer, DomnCacheVO>) CacheService.get(BaseConfig.SITE_SN_MAP_CACHE_KEY);
        DomnCacheVO domn = siteSnMap.get(domainCd);

        if(Validate.isNotEmpty(domn)) {
            CmsCacheVO paramVo = new CmsCacheVO();

            Integer siteSn = domn.getSiteSn();
            String siteNm = domn.getSiteNm();
            Integer port = domn.getPortSn();
            Integer scrtyPortSn = domn.getScrtyPortSn();
            String context = domn.getSitePathNm();

            String cacheKey = siteNm + "_" + port + "_" + context;
            logger.debug("\nUSER_MENU_URI_MAP_KEY CacheKey is {}\n", cacheKey);

            // https 보안 프로토콜 운영을 하며 기본 포트와 다른 경우 두개의 키로 케시
            String scrtyCacheKey = null;
            if(domn.getHttpsYn().equals("Y") && Validate.isNotEmpty(scrtyPortSn) && !port.equals(scrtyPortSn)) {
                scrtyCacheKey = siteNm + "_" + scrtyPortSn + "_" + context;
                logger.debug("\nUSER_MENU_URI_MAP_KEY ScrtyCacheKey is {}\n", scrtyCacheKey);
            }

            Map<String, CmsCacheVO> menuSnMap = new HashMap<String, CmsCacheVO>();

            paramVo.setSiteSn(siteSn);
            paramVo.setUserMenuEngNm(HIGH_CMS_CD);
            paramVo.setUseYn("Y");
            List<CmsCacheVO> userMenuList = opCmsCacheDao.selectUserMenuList(paramVo);

            CmsCacheVO childVo = new CmsCacheVO();
            setChildList(userMenuList, menuSnMap, childVo);

            CacheService.put(cacheKey + USER_MENU_ENG_NM_MAP_KEY, menuSnMap);
            CacheService.put(cacheKey + USER_MENU_LIST_KEY, userMenuList);
            if(Validate.isNotEmpty(scrtyCacheKey)) {
                CacheService.put(scrtyCacheKey + USER_MENU_ENG_NM_MAP_KEY, menuSnMap);
                CacheService.put(scrtyCacheKey + USER_MENU_LIST_KEY, userMenuList);
            }

            List<String> paramList;

            // 매핑 정보용
            Map<RequestMatcher, CmsUrlCacheVO> menuUriMap = new HashMap<RequestMatcher, CmsUrlCacheVO>();
            List<CmsUrlCacheVO> menuUrlList = opCmsCacheDao.selectLwprtUrlList(paramVo);
            for(CmsUrlCacheVO cacheUrlVo : menuUrlList) {

                paramList = new ArrayList<String>();

                // 메뉴 URL에 잘못된 입력이 있는 경우를 대비하여 URI와 파라미터를 분리 규칙을 적용한다.
                String menuUrlAddr = cacheUrlVo.getUserMenuUrlAddr();
                if(menuUrlAddr.indexOf("?") > -1) {
                    String[] paths = menuUrlAddr.split("\\?", 2);

                    cacheUrlVo.setUserMenuUrlAddr(paths[0]);
                    if(Validate.isNotEmpty(paths[1]) && paths[1].indexOf("=") > -1) {
                        /*
                         * 접근성 관련 &amp;로 입력시에 대한 보강 코딩
                         * <c:url />을 사용하지 않는 경우를 대비함(추후 패턴을 봐서 좀더 합리적으로 처리 할수도..)
                         */
                        String[] querys = null;
                        if(paths[1].indexOf("&amp;") > -1) {
                            querys = paths[1].split("&amp;");
                        } else {
                            querys = paths[1].split("&");
                        }
                        for(String query : querys) {
                            String[] param = query.split("=");
                            if(Validate.isNotEmpty(param) && Validate.isSizeMatch(param, 2, 2)) {
                                paramList.add(query);
                            }
                        }

                    }
                }
                cacheUrlVo.setFullUrl(StringUtil.replace(menuUrlAddr, "&amp;", "&"));
                if (Validate.isNotEmpty(cacheUrlVo.getUserMenuUrlAddr())) {
                    menuUriMap.put(new OpenworksRequestMatcher(cacheUrlVo.getUserMenuUrlAddr(), paramList), cacheUrlVo);
                } else {
                    menuUriMap.put(new OpenworksRequestMatcher(cacheUrlVo.getUserMenuEngNm()), cacheUrlVo);
                }
            }

            CacheService.put(cacheKey + USER_MENU_URI_MAP_KEY, menuUriMap);
            if(Validate.isNotEmpty(scrtyCacheKey)) {
                CacheService.put(scrtyCacheKey + USER_MENU_URI_MAP_KEY, menuUriMap);
            }
        }
    }

    /**
     * 하위 메뉴 계층 구조를 위한 쿼리 설정. 재귀 반복을 통하여 셋팅한다.
     *
     * @param userMenuList
     * @param menuSnMap
     */
    public void setChildList(List<CmsCacheVO> userMenuList, Map<String, CmsCacheVO> menuSnMap, CmsCacheVO childVo) {
        List<CmsCacheVO> childList;

        for(CmsCacheVO menu : userMenuList) {

            childVo.setSiteSn(menu.getSiteSn());
            childVo.setUserMenuEngNm(menu.getUserMenuEngNm());
            childVo.setUseYn("Y");

            // 표시담당자 설정에 따라 담당자 정보를 설정
            if(menu.getPicIndctYn().equals("Y")) {
                CmsMngrCacheVO mngrVo = opCmsCacheDao.selectUserMenuMngr(menu);
                if(Validate.isEmpty(mngrVo)) {
                    mngrVo = new CmsMngrCacheVO();
                }
                menu.setIndictMngr(mngrVo);
            }
            // 회원등급
            menu.setUserGradList(opCmsCacheDao.selectUserGradList(menu));

            childList = opCmsCacheDao.selectUserMenuList(childVo);
            menu.setChildList(childList);

            menuSnMap.put(String.valueOf(menu.getUserMenuEngNm()), menu);
            setChildList(childList, menuSnMap, childVo);
        }
    }

    /**
     * 사이트별 케시 리로드
     *
     * @param domainCd
     */
    @Override
    public void reloadCache(Integer domainCd) {
        createCache(domainCd);
    }

    @Override
    public String getName() {

        return "CMS 메뉴 정보";
    }

    @Override
    public void reloadCache() {
        createCache();
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.USER;
    }
}
