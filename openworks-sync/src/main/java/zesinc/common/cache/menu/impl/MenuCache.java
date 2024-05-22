/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.menu.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import zesinc.common.cache.menu.MenuCacheMapper;
import zesinc.core.cache.Cache;
import zesinc.core.cache.CacheLocation;
import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.spring.security.OpenworksFilterInvocationSecurityMetadataSource;
import zesinc.web.spring.security.support.OpenworksRequestMatcher;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.SpringHelper;
import zesinc.web.vo.cache.MenuCacheVO;
import zesinc.web.vo.cache.MenuUrlCacheVO;

/**
 * 관리자 메뉴 목록을 제공하기 위한 메뉴목록 캐시 서비스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 8.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see zesinc.common.sitemesh.IntraMenuSelector
 */
public class MenuCache implements Cache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "opMenuCacheDao")
    private MenuCacheMapper opMenuCacheDao;

    /** 메뉴 목록 캐시 키 */
    public static final String MENU_LIST_CACHE_KEY = BaseConfig.MENU_LIST_KEY;
    /** 메뉴 멥 캐시 키 */
    public static final String MENU_SN_CACHE_KEY = BaseConfig.MENU_SN_MAP_KEY;
    /** 메뉴 멥 캐시 키 */
    public static final String MENU_URI_CACHE_KEY = BaseConfig.MENU_URI_MAP_KEY;
    /** 메뉴 멥 캐시 키 */
    public static final String MENU_ACTION_URI_MAP_KEY = BaseConfig.MENU_ACTION_URI_MAP_KEY;
    /** 최상위 메뉴코드 */
    private static final Integer HIGH_MENU_CODE = Config.getInt("webapp-config.defaultCode.highMenuCd");

    private Integer HGHRK_MENU_ENG_NM;

    /**
     * 메뉴 목록 캐시 생성
     *
     * @see zesinc.common.sitemesh.IntraMenuSelector
     */
    @Override
    public void createCache() {
        /*
         * 메뉴코드를 키로하는 모든 메뉴정보.
         * 메뉴 코드로 해당메뉴의 정보를 가져오기 위한 데이터 생성
         */
        Map<Integer, MenuCacheVO> menuSnMap = new HashMap<Integer, MenuCacheVO>();

        MenuCacheVO menuCacheVo = new MenuCacheVO();
        menuCacheVo.setUpMenuSn(HIGH_MENU_CODE);

        List<MenuCacheVO> menuList = opMenuCacheDao.selectMenuList(menuCacheVo);
        MenuCacheVO upperItemVo = new MenuCacheVO();
        setChildList(menuList, menuSnMap, upperItemVo, Boolean.TRUE);

        /*
         * URL과 메뉴를 매핑하기 위한 데이터 생성
         */
        MenuUrlCacheVO menuCacheUrlVo = new MenuUrlCacheVO();
        List<MenuUrlCacheVO> menuUrlList = opMenuCacheDao.selectMenuUrlList(menuCacheUrlVo);

        String param1;
        String param2;
        String param3;
        List<String> paramList;

        Map<RequestMatcher, MenuUrlCacheVO> menuUriMap = new HashMap<RequestMatcher, MenuUrlCacheVO>();

        for(MenuUrlCacheVO cacheUrlVo : menuUrlList) {

            param1 = cacheUrlVo.getPrmttNm1();
            param2 = cacheUrlVo.getPrmttNm2();
            param3 = cacheUrlVo.getPrmttNm3();

            paramList = new ArrayList<String>();
            if(Validate.isNotEmpty(param1)) {
                paramList.add(param1);
            }
            if(Validate.isNotEmpty(param2)) {
                paramList.add(param2);
            }
            if(Validate.isNotEmpty(param3)) {
                paramList.add(param3);
            }

            // 메뉴 URL에 잘못된 입력이 있는 경우를 대비하여 URI와 파라미터를 분리 규칙을 적용한다.
            String menuUrlAddr = cacheUrlVo.getMenuUrlAddr();
            if(menuUrlAddr.indexOf("?") > -1) {
                String[] paths = menuUrlAddr.split("\\?", 2);

                cacheUrlVo.setMenuUrlAddr(paths[0]);

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
            menuUriMap.put(new OpenworksRequestMatcher(cacheUrlVo.getMenuUrlAddr(), paramList), cacheUrlVo);
        }

        CacheService.put(MENU_SN_CACHE_KEY, menuSnMap);
        CacheService.put(MENU_URI_CACHE_KEY, menuUriMap);

        Map<String, MenuUrlCacheVO> menuActionUriMap = new HashMap<String, MenuUrlCacheVO>();
        List<MenuUrlCacheVO> menuActionUrlList = opMenuCacheDao.selectMenuActionUrlList(menuCacheUrlVo);
        for(MenuUrlCacheVO cacheUrlVo : menuActionUrlList) {
            param1 = cacheUrlVo.getPrmttNm1();
            param2 = cacheUrlVo.getPrmttNm2();
            param3 = cacheUrlVo.getPrmttNm3();

            paramList = new ArrayList<String>();
            if(Validate.isNotEmpty(param1)) {
                paramList.add(param1);
            }
            if(Validate.isNotEmpty(param2)) {
                paramList.add(param2);
            }
            if(Validate.isNotEmpty(param3)) {
                paramList.add(param3);
            }

            // 메뉴 URL에 잘못된 입력이 있는 경우를 대비하여 URI와 파라미터를 분리 규칙을 적용한다.
            String menuUrlAddr = cacheUrlVo.getMenuUrlAddr();
            if(menuUrlAddr.indexOf("?") > -1) {
                String[] paths = menuUrlAddr.split("\\?", 2);

                cacheUrlVo.setMenuUrlAddr(paths[0]);

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
            menuActionUriMap.put(cacheUrlVo.getMenuUrlAddr() + "‡" + String.join(",", paramList), cacheUrlVo);
        }
        CacheService.put(MENU_ACTION_URI_MAP_KEY, menuActionUriMap);

        /*
         * DAO Bean 클레스
         */
        try {
            OpenworksFilterInvocationSecurityMetadataSource securityMetadataSource =
                (OpenworksFilterInvocationSecurityMetadataSource) SpringHelper.findBean("openworksFilterInvocationSecurityMetadataSource");
            if(Validate.isNotEmpty(securityMetadataSource)) {
                securityMetadataSource.reload();
            }
        } catch (Exception e) {
            logger.error("메뉴 정보 변경으로 인한 SpringSecurity 권한 로그 오류 : 서버 구동시의 메시지는 무시하세요.");
        }
    }

    /**
     * 현재 메뉴의 자식메뉴 목록을 설정
     *
     * @param menuList 자식 메뉴목록
     * @param menuSnMap 케시에 저장할 Map
     */
    private void setChildList(List<MenuCacheVO> menuList, Map<Integer, MenuCacheVO> menuSnMap, MenuCacheVO upperItemVo, Boolean isTop) {
        List<MenuCacheVO> childList;
        for(MenuCacheVO menu : menuList) {

            if(isTop) {
                HGHRK_MENU_ENG_NM = menu.getMenuSn();
            }
            menu.setHghrkMenuEngNm(HGHRK_MENU_ENG_NM);
            upperItemVo.setUpMenuSn(menu.getMenuSn());

            childList = opMenuCacheDao.selectMenuList(upperItemVo);

            menuSnMap.put(menu.getMenuSn(), menu);
            setChildList(childList, menuSnMap, upperItemVo, Boolean.FALSE);
        }
    }

    /**
     * 메뉴 목록 캐시 리로드
     */
    @Override
    public void reloadCache() {
        reloadCache(null);
    }

    /**
     * 도메인별 메뉴 목록 캐시 리로드(미사용)
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

        return "관리자메뉴";
    }

    @Override
    public CacheLocation getLocation() {

        return CacheLocation.INTRA;
    }
}
