/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.resolver;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.util.WebUtils;

import zesinc.core.lang.Validate;
import zesinc.web.utils.DomnCacheUtil;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * <pre>
 *  Openworks 프레임워크 에서 사용되는 사이트 관리 기능과 연동하여 언어를 적용해야
 *  하기 때문에 개별적으로 구현하여 사용한다.
 * 
 *  최초 접근시 사이트관리의 도메인 정보로 찾아서 세션에 저장하고, 이후 세션에서 찾아서
 *  Locale를 적용한다.
 * 
 *  만약 사이트관리의 도메인 정보에서 일치하는 Locale를 찾지 못한다면.. 
 *  new Locale("KR","ko") 를 기본으로 사용한다.
 * 
 *  request시스템 설정에 따라서 다르게 표현될 수 있다.
 *  
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
public class OpenworksLocaleResolver implements LocaleResolver {

    /** 로깅 */
    private Logger logger = LoggerFactory.getLogger(OpenworksLocaleResolver.class);

    private static String POSTFIX_SESSION_NAME = OpenworksLocaleResolver.class.getName() + ".LOCALE";

    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        Locale locale = getLocaleFromSession(request);

        if(locale == null) {
            locale = determineLocale(request);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        throw new UnsupportedOperationException("Openworks is not supported");
    }

    private Locale getLocaleFromSession(HttpServletRequest request) {

        String serverName = request.getServerName();

        Locale locale = (Locale) WebUtils.getSessionAttribute(request, serverName + "." + POSTFIX_SESSION_NAME);

        return locale;
    }

    private Locale determineLocale(HttpServletRequest request) {

        Locale locale = null;

        DomnCacheVO domnCacheVo = DomnCacheUtil.getDomnCache();

        if(Validate.isNotEmpty(domnCacheVo)) {
            String langCdId = domnCacheVo.getLangCdId();
            String[] langCdIds = langCdId.split("_");

            locale = new Locale(langCdIds[0], langCdIds[1]);
        }

        if(Validate.isEmpty(locale)) {
            locale = new Locale("ko", "KR");
        }

        String serverName = request.getServerName();
        WebUtils.setSessionAttribute(request, serverName + "." + POSTFIX_SESSION_NAME, locale);

        logger.debug("==========================> Set locale is {} {}", locale.toString(), request.getRequestURI());

        return locale;
    }
}
