/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.auth;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.auth.anotation.OpenworksAuthWeb;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.helper.OpHelper;

/**
 * TODO 설명을 입력하세요.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 20.    ZES-INC   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AuthorizationAdvice implements MethodBeforeAdvice {

    /** SL4J 로깅 */
    private static Logger logger = LoggerFactory.getLogger(AuthorizationAdvice.class);

    @Override
    public void before(Method method, Object[] arg1, Object arg2) throws Throwable {
        logger.debug("================ CHECK AUTHORIZATION LOGIC START ================");

        OpenworksAuthWeb ano = method.getAnnotation(OpenworksAuthWeb.class);
        AuthWebType authType = ano.authWebType();
        String authTypeCode = authType.getCdId();

        if(Validate.isNotEmpty(authTypeCode)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            Object loginVo = (Object) OpHelper.getSession(request, BaseConfig.USER_SESSION_KEY);

            if(loginVo != null) {

            } else {
                String returnUrl = request.getRequestURL().toString();
                String serverName = request.getServerName();
                if(returnUrl.contains("/PD_")){
                    OpHelper.setSession(request, "__LoginAccessType", "popup");
                } else if (serverName.equals(Config.getString("webapp-config.responsiveForm.useSiteUrl.mobile"))){
                    OpHelper.setSession(request, "__LoginAccessType", "mobile");
                }
                String param = request.getQueryString();
                if(Validate.isNotEmpty(param)){
                    returnUrl += "?" + param;
                }
                OpHelper.setSession(request, "__UserReturnUrl", returnUrl);
                throw new UserNoLoginException("메뉴 사용 권한이 없습니다.");
            }
        }

        logger.debug("================ CHECK AUTHORIZATION LOGIC END ================");
    }

}
