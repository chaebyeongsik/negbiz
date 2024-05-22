/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security.support;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import zesinc.core.lang.Validate;
import zesinc.web.auth.AuthType;
import zesinc.web.support.BaseConfig;

/**
 * 오픈웤스 권한관리 시스템에 적합한 검증을 할 수 있도록하는 Voter
 * 오픈웤스에서는 총괄 : G, 기본 : A, 조회 : R 세가지 타입이 기본이다
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 5. 7.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class OpenworksRoleVoter implements AccessDecisionVoter<Object> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String rolePrefix = "ROLE_";

    // private static String managerCode = AuthType.MANAGER.getCdId();
    // private static String basicCode = AuthType.BASIC.getCdId();
    // private static String readCode = AuthType.READ.getCdId();

    public void setRolePrefix(String rolePrefix) {
        this.rolePrefix = rolePrefix;
    }

    public String getRolePrefix() {
        return rolePrefix;
    }

    /**
     * 자원의 권한정보 존재여부와 권한정보의 접두사 검증
     * 
     * @see org.springframework.security.access.AccessDecisionVoter#supports(org.springframework.security.access.ConfigAttribute)
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        if((attribute.getAttribute() != null) && attribute.getAttribute().startsWith(getRolePrefix())) {
            return true;
        }
        return false;
    }

    /**
     * 사용자 권한정보 존재여부와 권한 접두사 검증
     * 
     * @param authority
     * @return
     */
    public boolean supports(GrantedAuthority authority) {
        if((authority.getAuthority() != null) && authority.getAuthority().startsWith(getRolePrefix())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    /**
     * Openworks의 권한은 메뉴코드_권한코드 형식으로 되어 있다 prefix 사용을 고려하여
     * 루트를 돌며 마지막의 권한코드를 제외한 메뉴 정보가 일치하는지 확인한다.
     * 
     * @param attr 메뉴에 설정된 접근권한
     * @param auth 사용자에 설정된 접근권한
     */
    private boolean matches(String[] attr, String[] auth) {
        int authLen = auth.length;
        int attrLen = attr.length;

        if(attrLen == authLen) {
            for(int i = 0 ; i < authLen - 1 ; i++) {
                if(!auth[i].equals(attr[i])) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        int result = ACCESS_ABSTAIN;

        Collection<? extends GrantedAuthority> authorities = extractAuthorities(authentication);

        // 메뉴별 설정 권한
        for(ConfigAttribute attribute : attributes) {

            // 접두사가 일치하는지 여부
            if(this.supports(attribute)) {
                result = ACCESS_DENIED;

                FilterInvocation invocation = (FilterInvocation) object;
                HttpServletRequest request = invocation.getRequest();

                // prefix 설정유무에 따라 권한코드 위치 지정
                int deliCnt = 3;
                if(Validate.isEmpty(getRolePrefix())) {
                    deliCnt = 2;
                }
                // 메뉴 자체의 권한 정보
                String[] attr = attribute.getAttribute().split("_", deliCnt);

                logger.debug("===========================================");
                logger.debug("메뉴코드 : {}, 필요권한 : {}", attr[deliCnt - 2], attr[deliCnt - 1]);
                logger.debug("===========================================");

                // 메뉴 자체에 설정된 권한이 없는 경우에는 기본권한으로 승인
                if(Validate.isEmpty(attr[deliCnt - 1]) || attr[deliCnt - 1].equals("null")) {
                    request.setAttribute(BaseConfig.AUTH_CODE_KEY, AuthType.BASIC.getCdId());
                    return ACCESS_GRANTED;
                }

                /*
                 * 아래 두 변수는 현재메뉴에 대하여 사용자에게 할당된 모든 권한중에서
                 * 제일 큰 권한을 찾아서 최종 반영하기 위해 사용한다.
                 */
                String authCode = "";
                Integer authVal = new Integer("0");

                // 개인에게 할당된 메뉴별 권한
                for(GrantedAuthority authority : authorities) {
                    // 접두사가 일치하는지 여부(특별 케이스에 Spring에서 정의된 미인증 문자열이 넘어오며 이때는 권한이 없는 것임)
                    if(this.supports(authority)) {
                        // 사용자의 권한 정보
                        String[] auth = authority.getAuthority().split("_", deliCnt);

                        // 메뉴 정보 일치 여부 확인. 메뉴 일치시 권한 코드 비교
                        if(this.matches(attr, auth)) {
                            // 메뉴권한
                            String attrRoleCode = attr[deliCnt - 1];
                            Integer attrRoleVal = new Integer(attrRoleCode);
                            // 사용자권한
                            String authRoleCode = auth[deliCnt - 1];
                            Integer authRolVal = new Integer(authRoleCode);

                            logger.debug("===========================================");
                            logger.debug("메뉴코드 : {}, 필요권한 : {}, 소유권한 : {}", attr[deliCnt - 2], attrRoleCode, authRoleCode);
                            logger.debug("===========================================");

                            /*
                             * 운영자권한을 가진 경우 또는 대상이 최하위 읽기권한을 요구하는 경우 승인
                             * 요구권한이 기본인경우
                             */

                            if(authRoleCode.equals(AuthType.MANAGER.getCdId())) {
                                request.setAttribute(BaseConfig.AUTH_CODE_KEY, AuthType.MANAGER.getCdId());
                                return ACCESS_GRANTED;
                            }

                            // 이전 확인한 권한보다 신규 권한이 큰 경우에만 재확인
                            if(authRolVal > authVal) {
                                // 사용자권한이 메뉴권한보다 같거나 크면 최종 권한 정보를 설정
                                if(authRolVal >= attrRoleVal) {
                                    authVal = authRolVal;
                                    authCode = authRoleCode;
                                }
                            }
                        }
                    }
                }
                // 설정된 권한이 있다면 설정 후 권한인증
                if(Validate.isNotEmpty(authCode)) {
                    request.setAttribute(BaseConfig.AUTH_CODE_KEY, authCode);
                    return ACCESS_GRANTED;
                }
            }
        }

        return result;
    }

    /**
     * 사용자 권한 목록 반환
     * 
     * @param authentication
     * @return
     */
    Collection<? extends GrantedAuthority> extractAuthorities(Authentication authentication) {
        return authentication.getAuthorities();
    }
}
