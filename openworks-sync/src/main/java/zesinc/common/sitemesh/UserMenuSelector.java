/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.sitemesh;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.sitemesh.webapp.contentfilter.Selector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;
import zesinc.web.utils.DomnCacheUtil;
import zesinc.web.vo.IUserSessVO;
import zesinc.web.vo.cache.CmsCacheVO;
import zesinc.web.vo.cache.CmsUrlCacheVO;
import zesinc.web.vo.cache.CmsUserGradCacheVO;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자단 메뉴
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 11. 10.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class UserMenuSelector implements Selector {

    /** 이미 검증된 경우 케시를 사용 */
    private static final String ALREADY_APPLIED_KEY = UserMenuSelector.class.getName() + ".APPLIED_ONCE";
    /** 사용자 이용권한이 없는 경우 이동 페이지 */
    private static final String NO_AUTH_PAGE = Config.getString("cms-config.noAuthPage", "NoAuth");
    /** 최상위 기본 메뉴코드 */
    public static String HIGH_CMS_CD = Config.getString("webapp-config.defaultCode.highCmsCd", "web");
    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(UserMenuSelector.class);

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
        } else {
            request.setAttribute(ALREADY_APPLIED_KEY, Boolean.TRUE);

            // 데코레이터 정보 설정
            return !setDecorator(request);
        }
    }

    @Override
    public String excludePatternInUse(HttpServletRequest request) {

        return null;
    }

    /**
     * 데코레이터 분기. 미리보기와 실운영보기
     *
     * @param request
     * @return
     */
    protected boolean setDecorator(HttpServletRequest request) {
        String reqUri = request.getRequestURI();
        if(reqUri.endsWith("PV_LayoutContentPreView.do")) {
            return setPreViewDecorator(request);
        }

        boolean hasDecoratorCode = setMenuDecorator(request, null);
        if(!hasDecoratorCode) {
            /*
             * 설정상의 데코레이터가 없으면서 PD_ prefix 인 경우
             * 자동으로 팝업용으로 데코레이터를 입힘
             */
            String prefix = reqUri.substring(reqUri.lastIndexOf("/") + 1);
            if(prefix.startsWith("PD_")) {
                return Boolean.TRUE;
            }
        }

        return hasDecoratorCode;
    }

    /**
     * 미리보기 데코레이터를 설정
     *
     * @param request
     */
    private Boolean setPreViewDecorator(HttpServletRequest request) {

        return Boolean.TRUE;
    }

    /**
     * 실 사용자 메뉴 데코레이터를 설정
     *
     * @param request
     */
    @SuppressWarnings("unchecked")
    private Boolean setMenuDecorator(HttpServletRequest request, String userMenuUrlAddr) {

        DomnCacheVO domnVo = DomnCacheUtil.getDomnCache(request);
        // 사이트 설정에 없는 도메인은 무조건 패스
        if(Validate.isEmpty(domnVo)) {
            return Boolean.FALSE;
        }

        String cacheKey = domnVo.getSiteNm() + "_" + domnVo.getPortSn() + "_" + domnVo.getSitePathNm();

        logger.debug("USER_MENU_URI_MAP_KEY CacheKey is {}", cacheKey);

        // 케쉬에서 메뉴URL Map을 가져 온다.
        Map<RequestMatcher, CmsUrlCacheVO> allMenuUriMap =
            (HashMap<RequestMatcher, CmsUrlCacheVO>) CacheService.get(cacheKey + BaseConfig.USER_MENU_URI_MAP_KEY);

        if(allMenuUriMap != null) {
            CmsUrlCacheVO resultVo = null;
            for(Map.Entry<RequestMatcher, CmsUrlCacheVO> entry : allMenuUriMap.entrySet()) {
                if(entry.getKey().matches(request)) {
                    resultVo = entry.getValue();
                    break;
                }
            }

            if(Validate.isNotEmpty(resultVo)) {
                // 케쉬에서 메뉴코드 Map을 가져 온다.
                HashMap<String, CmsCacheVO> allMenuCodeMap =
                    (HashMap<String, CmsCacheVO>) CacheService.get(cacheKey + BaseConfig.USER_MENU_ENG_NM_MAP_KEY);

                CmsCacheVO userMenuVo = allMenuCodeMap.get(resultVo.getUserMenuEngNm());
                // SNS 등록용 URL 등록
                userMenuVo.setSnsUrl(resultVo.getFullUrl());

                CmsCacheVO userTopMenuVo = null;
                CmsCacheVO userSubMenuVo = null;
                // 최상위 메뉴인 경우 메인 페이지에 해당하므로 1뎁스 메뉴를 서브 메뉴로 사용할 수 있도록 변경해준다.
                if(HIGH_CMS_CD.equals(resultVo.getHghrkMenuEngNm())) {
                    userTopMenuVo = allMenuCodeMap.get(resultVo.getUserMenuEngNm());
                } else {
                    userSubMenuVo = allMenuCodeMap.get(resultVo.getHghrkMenuEngNm());
                    userTopMenuVo = allMenuCodeMap.get(userSubMenuVo.getUpMenuEngNm());
                }

                // 현재 메뉴의 탑 메뉴 정보
                request.setAttribute(BaseConfig.USER_TOP_MENU_INFO_KEY, userTopMenuVo);
                // 현재 메뉴의 좌측 메뉴 정보
                request.setAttribute(BaseConfig.USER_SUB_MENU_INFO_KEY, userSubMenuVo);
                // 현재 메뉴 정보
                request.setAttribute(BaseConfig.USER_MENU_INFO_KEY, userMenuVo);
                // 컨텍스트 정보
                request.setAttribute("contextPath", request.getContextPath());

                // 레이아웃 정보가 없다면 데코레이팅 패스
                String lytCdNo = userMenuVo.getLytCdNo();

                // 메뉴에 특정 사용자 등급이 설정되어 있다면 확인
                Boolean hasGrad = Boolean.TRUE;
                List<CmsUserGradCacheVO> menuGradList = userMenuVo.getUserGradList();
                if(Validate.isNotEmpty(menuGradList)) {
                    // 사용자 등급에 따른 권한
                    IUserSessVO loginVo = (IUserSessVO) OpHelper.getSession(request, BaseConfig.USER_SESSION_KEY);

                    if(Validate.isNotEmpty(loginVo)) {
                        hasGrad = Boolean.FALSE;

                        String loginGrads = loginVo.getUserGrdCdDsctn();
                        if(Validate.isNotEmpty(loginGrads)) {
                            String[] arrLoginGrad = StringUtil.deleteWhitespace(loginGrads).split(",");
                            List<String> loginGradList = Arrays.asList(arrLoginGrad);

                            StringBuilder userGrdNms = new StringBuilder();
                            for(CmsUserGradCacheVO menuGrad : menuGradList) {
                                if(loginGradList.contains(menuGrad.getUserGrdCdId())) {
                                    hasGrad = Boolean.TRUE;
                                    break;
                                }
                                userGrdNms.append("[").append(menuGrad.getUserGrdNm()).append("] ");
                            }
                            if(!hasGrad) {
                                request.setAttribute("userGradeInfo", userGrdNms.toString());
                            }
                        }
                    } else {
                        hasGrad = Boolean.FALSE;
                    }
                }

                // 권한 체크 후 권한이 없다면
                if(!hasGrad) {
                    userMenuVo.setGradLytCdNo(NO_AUTH_PAGE);
                } else {
                    userMenuVo.setGradLytCdNo("");
                }

                if(Validate.isEmpty(lytCdNo)) {
                    return Boolean.FALSE;
                }

                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }
}
