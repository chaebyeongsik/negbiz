/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.sitemesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.sitemesh.webapp.contentfilter.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zesinc.common.cache.menu.MenuCacheMapper;
import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.support.helper.SpringHelper;
import zesinc.web.utils.RequestUtil;
import zesinc.web.utils.XssUtil;
import zesinc.web.vo.ISessVO;
import zesinc.web.vo.cache.CmsCacheVO;
import zesinc.web.vo.cache.DomnCacheVO;
import zesinc.web.vo.cache.MenuCacheVO;
import zesinc.web.vo.cache.MenuUrlCacheVO;

/**
 * 메뉴관리에 따른 URL과 메뉴정보를 매핑시켜 주는 역할을 담당함
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 31.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see zesinc.common.cache.menu.impl.MenuCache
 */
public class IntraMenuSelector implements Selector {

    private static final String ALREADY_APPLIED_KEY = IntraMenuSelector.class.getName() + ".APPLIED_ONCE";
    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(IntraMenuSelector.class);
    /** 최상위 메뉴코드 */
    private static final Integer HIGH_MENU_CODE = Config.getInt("webapp-config.defaultCode.highMenuCd");
    /** 사용자별 메뉴 정보 세션 키 */
    private static final String MENU_SESSION_KEY = "__MENU_SESSION_KEY";

    @Override
    public boolean shouldBufferForContentType(String contentType, String mimeType, String encoding) {
        return true;
    }

    @Override
    public boolean shouldAbortBufferingForHttpStatusCode(int statusCode) {
        return !(statusCode == 200);
    }

    @Override
    public boolean shouldBufferForRequest(HttpServletRequest request) {
        return !filterAlreadyAppliedForRequest(request);
    }

    protected boolean filterAlreadyAppliedForRequest(HttpServletRequest request) {
        if(Boolean.TRUE.equals(request.getAttribute(ALREADY_APPLIED_KEY))) {
            return true;
        }
        request.setAttribute(ALREADY_APPLIED_KEY, Boolean.TRUE);

        // 메뉴 정보 설정
        return !setDecorator(request);
    }

    @Override
    public String excludePatternInUse(HttpServletRequest request) {
        return null;
    }

    /**
     * 데코레이터 분기. 사용자단 미리보기와 관리자단 운영
     * 
     * @param request
     * @return
     */
    protected boolean setDecorator(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        if(reqUri.endsWith("PV_LayoutContentPreView.do")) {
            return setUserMenuInfo(request);
        }

        // 메뉴를 포함하지 않는 URI는 제외
        if(reqUri.indexOf("BD_") < 0) {
            return Boolean.TRUE;
        }

        return setMenuInfo(request);
    }

    /**
     * 사용자 레이아웃/컨텐츠 미리보기.
     * 미리보기는 DB 데이터가 없을 수 있기 때문에 파라미터 기반으로 동작된다.
     * 
     * @param request
     * @return
     * @deprecated
     */
    private Boolean setUserMenuInfo(HttpServletRequest request) {

        String siteSnStr = XssUtil.cleanTag(request.getParameter("q_siteSn"), XssUtil.TYPE.ALL);
        String lytCdNo = XssUtil.cleanTag(request.getParameter("q_lytCdNo"), XssUtil.TYPE.ALL);
        String userMenuEngNm = XssUtil.cleanTag(request.getParameter("q_userMenuEngNm"), XssUtil.TYPE.ALL);

        // 도메인 코드와 레이아웃 코드 또는 사용자메뉴 코드는 필수 요소임
        if(Validate.isNotEmpty(siteSnStr) && (Validate.isNotEmpty(lytCdNo) || Validate.isNotEmpty(userMenuEngNm))) {

            Integer siteSn = new Integer(siteSnStr);

            // 케쉬에서 도메인코드 Map을 가져 온다.
            @SuppressWarnings("unchecked")
            Map<Integer, DomnCacheVO> siteSnMap =
                (HashMap<Integer, DomnCacheVO>) CacheService.get(BaseConfig.SITE_SN_MAP_CACHE_KEY);

            DomnCacheVO domnVo = siteSnMap.get(new Integer(siteSn));

            String cacheKey = domnVo.getSiteNm() + "_" + domnVo.getPortSn() + "_" + domnVo.getSitePathNm();

            // 케쉬에서 메뉴코드 Map을 가져 온다.
            @SuppressWarnings("unchecked")
            HashMap<String, CmsCacheVO> allMenuSnMap =
                (HashMap<String, CmsCacheVO>) CacheService.get(cacheKey + BaseConfig.USER_MENU_ENG_NM_MAP_KEY);

            CmsCacheVO userMenuCacheVo = null;
            if(allMenuSnMap != null) {
                userMenuCacheVo = allMenuSnMap.get(userMenuEngNm);
                request.setAttribute(BaseConfig.USER_MENU_INFO_KEY, userMenuCacheVo);

                if(Validate.isNotEmpty(userMenuCacheVo)) {
                    CmsCacheVO userTopMenuCacheVo = allMenuSnMap.get(userMenuCacheVo.getHghrkMenuEngNm());

                    request.setAttribute(BaseConfig.USER_TOP_MENU_INFO_KEY, userTopMenuCacheVo);

                    return Boolean.TRUE;
                }
            }

            // 사용자메뉴가 없고 레이아웃코드가 있다면 레이아웃 코드만 설정
            if(Validate.isNotEmpty(lytCdNo)) {
                userMenuCacheVo = new CmsCacheVO();
                userMenuCacheVo.setSiteSn(siteSn);
                userMenuCacheVo.setLytCdNo(lytCdNo);
                request.setAttribute(BaseConfig.USER_MENU_INFO_KEY, userMenuCacheVo);

                return Boolean.TRUE;
            }

        }
        return Boolean.FALSE;
    }

    /**
     * 데코레이터에서 메뉴 정보를 추가시키기 위한 메소드로
     * 메뉴 정보를 사용하는 BD_ 를 포함하는 URI의 경우에만 메뉴정보를 추가한다.
     * 
     * @param request
     */
    @SuppressWarnings("unchecked")
    private Boolean setMenuInfo(HttpServletRequest request) {

        String reqUri = request.getRequestURI();

        try {
            // 케쉬에서 메뉴URL Map을 가져 온다.
            Map<RequestMatcher, MenuUrlCacheVO> allMenuUriMap =
                (HashMap<RequestMatcher, MenuUrlCacheVO>) CacheService.get(BaseConfig.MENU_URI_MAP_KEY);

            if(allMenuUriMap != null) {
                MenuUrlCacheVO resultVo = null;
                for(Map.Entry<RequestMatcher, MenuUrlCacheVO> entry : allMenuUriMap.entrySet()) {
                    if(entry.getKey().matches(request)) {
                        resultVo = entry.getValue();
                        break;
                    }
                }

                if(Validate.isNotEmpty(resultVo)) {
                    // 케쉬에서 메뉴코드 Map을 가져 온다.
                    HashMap<Integer, MenuCacheVO> allMenuSnMap =
                        (HashMap<Integer, MenuCacheVO>) CacheService.get(BaseConfig.MENU_SN_MAP_KEY);

                    // 현재 메뉴 정보
                    MenuCacheVO menuVo = allMenuSnMap.get(resultVo.getMenuSn());
                    request.setAttribute(BaseConfig.MENU_INFO_KEY, menuVo);

                    /*
                     * 메뉴 경로 생성 (현재 메뉴를 기준으로 상위메뉴를 열기위한 데이터)
                     * 현재 메뉴를 기준 상위 계층형으로 모든 부모 메뉴를 List로설정
                     */
                    List<MenuCacheVO> upperMenuList = new ArrayList<MenuCacheVO>();
                    upperMenuList.add(menuVo);
                    setUpperMenus(upperMenuList, allMenuSnMap, menuVo);
                    Collections.reverse(upperMenuList);
                    request.setAttribute(BaseConfig.MENU_PATH_KEY, upperMenuList);

                    /*
                     * 세션에 메뉴 정보가 있다면 해당 메뉴 목록을 전달
                     * 권한에 따른 메뉴 정보 생성 및 세션에 저장
                     */
                    ISessVO loginVo = (ISessVO) OpHelper.getMgrSession();
                    HttpSession session = request.getSession();
                    String mngrMenuSessionKey = loginVo.getUsername() + "_" + MENU_SESSION_KEY;
                    List<MenuCacheVO> topMenuList = (List<MenuCacheVO>) session.getAttribute(mngrMenuSessionKey);
                    if(topMenuList == null) {
                        topMenuList = setMenuList(request, loginVo);
                        session.setAttribute(mngrMenuSessionKey, topMenuList);
                    }
                    request.setAttribute(BaseConfig.TOP_MENU_INFO_KEY, topMenuList);

                    for(MenuCacheVO cacheVo : topMenuList) {
                    	
                        if(cacheVo.getMenuSn().equals(menuVo.getHghrkMenuEngNm())) {

                            request.setAttribute(BaseConfig.SUB_MENU_INFO_KEY, cacheVo.getChildList());
                            break;
                        }
                    }
                    // MyMenu 목록 생성
                    String myMenuSessionKey = BaseConfig.MY_MENU_KEY;
                    List<MenuCacheVO> myMenuList = (List<MenuCacheVO>) session.getAttribute(myMenuSessionKey);
                    if(Validate.isEmpty(myMenuList)) {
                        myMenuList = setMyMenuList(request, loginVo);
                        session.setAttribute(myMenuSessionKey, myMenuList);
                    }

                }
            }

        } catch (Exception e) {
            logger.error("관리자 메뉴정보 매핑 오류 URL {} {} {}", reqUri, RequestUtil.getQueryString(request, false), e);
        }
        return Boolean.TRUE;
    }

    /**
     * 사용자 세션에 메뉴 정보가 없는 경우 메뉴 정보 세션을 생성한다.
     * 
     * @param request
     */
    private List<MenuCacheVO> setMenuList(HttpServletRequest request, ISessVO loginVo) {
        /*
         * 로그인 정보로 쿼리에서 사용할 파라미터를 생성한다.
         */
        MenuCacheVO paramVo = new MenuCacheVO();
        paramVo.addParam("q_picId", loginVo.getUsername());
        paramVo.addParam("q_upMenuSn", HIGH_MENU_CODE);

        /*
         * DAO Bean 클레스
         */
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx);
        MenuCacheMapper opMenuCacheDao = (MenuCacheMapper) SpringHelper.findBean(ctx, "opMenuCacheDao");

        /*
         * 최상위 메뉴 목록을 추출하고, 자식 목록을 순환하며 채운다
         */
        List<MenuCacheVO> topMenuList = opMenuCacheDao.selectChargerMenuList(paramVo);
        setChildList(opMenuCacheDao, paramVo, topMenuList, Boolean.TRUE);

        // 최상위 메뉴에는 보통 메뉴 링크 정보가 없기 때문에 1대 자식을 통하여 메뉴 링크를 설정한다.
        for(MenuCacheVO topMenuVo : topMenuList) {
            if(Validate.isEmpty(topMenuVo.getMainUrl())) {
                setTopMenuUrl(topMenuVo, topMenuVo.getChildList());
            }
        }

        return topMenuList;
    }

    /**
     * 사용자 세션에 MY메뉴 정보가 없는 경우 메뉴 정보 세션을 생성한다.
     * 
     * @param request
     */
    private List<MenuCacheVO> setMyMenuList(HttpServletRequest request, ISessVO loginVo) {
        /*
         * 로그인 정보로 쿼리에서 사용할 파라미터를 생성한다.
         */
        MenuCacheVO paramVo = new MenuCacheVO();
        paramVo.addParam("q_picId", loginVo.getUsername());

        /*
         * DAO Bean 클레스
         */
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx);
        MenuCacheMapper opMenuCacheDao = (MenuCacheMapper) SpringHelper.findBean(ctx, "opMenuCacheDao");

        /*
         * 최상위 메뉴 목록을 추출하고, 자식 목록을 순환하며 채운다
         */
        List<MenuCacheVO> myMenuList = opMenuCacheDao.selectMyMenuList(paramVo);
        for(MenuCacheVO menu : myMenuList) {
            // URL 생성
            setMainUrl(menu);
        }

        return myMenuList;
    }

    /**
     * 현재 메뉴의 자식메뉴 목록을 설정
     * 
     * @param menuList 자식 메뉴목록
     * @param menuSnMap 케시에 저장할 Map
     */
    private void setChildList(MenuCacheMapper opMenuCacheDao, MenuCacheVO paramVo, List<MenuCacheVO> menuList, Boolean isTop) {

        List<MenuCacheVO> childList;

        for(MenuCacheVO menu : menuList) {
            // URL 생성
            setMainUrl(menu);

            paramVo.addParam("q_upMenuSn", menu.getMenuSn());
            childList = opMenuCacheDao.selectChargerMenuList(paramVo);
            menu.setChildList(childList);

            // 최상위 메뉴 설정
            if(isTop) {
                paramVo.setHghrkMenuEngNm(menu.getMenuSn());
            }
            menu.setHghrkMenuEngNm(paramVo.getHghrkMenuEngNm());

            setChildList(opMenuCacheDao, paramVo, childList, Boolean.FALSE);
        }
    }

    /**
     * 메뉴정보에서 실 URL을 생성
     * 
     * @param menuCacheVo
     */
    private void setMainUrl(MenuCacheVO menuCacheVo) {
        String param1 = menuCacheVo.getPrmttNm1();
        String param2 = menuCacheVo.getPrmttNm2();
        String param3 = menuCacheVo.getPrmttNm3();

        // 파라미터 설정이 있다면 파라미터를 URL에 추가한다.
        if(Validate.isNotEmpty(param1) || Validate.isNotEmpty(param2) || Validate.isNotEmpty(param3)) {
            StringBuilder mainUrl = new StringBuilder();
            mainUrl.append(menuCacheVo.getMainUrl()).append("?");
            if(Validate.isNotEmpty(param1)) {
                mainUrl.append(param1).append("&amp;");
            }
            if(Validate.isNotEmpty(param2)) {
                mainUrl.append(param2).append("&amp;");
            }
            if(Validate.isNotEmpty(param3)) {
                mainUrl.append(param3);
            }
            menuCacheVo.setMainUrl(mainUrl.toString());
            mainUrl.setLength(0);
        }
    }

    /**
     * 최상위 메뉴에 URL이 없는 경우 자식에게서 링크 값을 얻어 설정한다.
     * 
     * @param topMenuVo 최상위 메뉴 정보
     * @param childList 링크 값을 얻을 대상 자식 메뉴정보 목록
     */
    private void setTopMenuUrl(MenuCacheVO topMenuVo, List<MenuCacheVO> childList) {
        if(Validate.isNotEmpty(childList)) {
            MenuCacheVO menuVo = childList.get(0);
            if(Validate.isNotEmpty(menuVo.getMainUrl())) {
                topMenuVo.setMainUrl(menuVo.getMainUrl());
            } else {
                setTopMenuUrl(topMenuVo, menuVo.getChildList());
            }
        }
    }

    /**
     * 부모 메뉴 추적
     * 
     * @param upperMenuList 계층형 부모메뉴 목록
     * @param allMenuSnMap 전체메뉴 케시정보 멥
     * @param menuCacheVo 부모메뉴를 추출하기 위한 현재 메뉴
     */
    private void setUpperMenus(List<MenuCacheVO> upperMenuList, HashMap<Integer, MenuCacheVO> allMenuSnMap,
        MenuCacheVO menuCacheVo) {

        if(Validate.isNotEmpty(menuCacheVo)) {
            Integer upMenuSn = menuCacheVo.getUpMenuSn();
            
            MenuCacheVO upperMenuCacheVo = allMenuSnMap.get(upMenuSn);
            if(Validate.isNotEmpty(upperMenuCacheVo)) {
                upperMenuList.add(upperMenuCacheVo);
                setUpperMenus(upperMenuList, allMenuSnMap, upperMenuCacheVo);
            }
        }
    }

}
