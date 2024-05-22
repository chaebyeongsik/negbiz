/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * <pre>
 * 메뉴/권한 관리 기능의 데이터를 통하여 검증대상 여부를 확인하고, 검증대상인 경우
 * 해당 대상에 해당하는 권한 정보를 반환한다.(사용자에게 부여된 권한과 비교하기 위한 데이터)
 * 
 * 추가로 reload() 메소드를 통하여 메뉴/권한 정보가 변경된 경우 이를 실기간으로 동기화 할 수 있다.
 * 물론 Spring Framework를 통하여 id 값으로 호충하여 사용한다.
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 30.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see FilterInvocationSecurityMetadataSource
 * @see RequestMatcher
 * @see OpenworksSecuredObjectService
 */
public class OpenworksFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 요청정보와 설정된 대상의 권한을 비교하기 위한
     * 
     * @see RequestMatcher
     */
    private final Map<RequestMatcher, Collection<ConfigAttribute>> matcherMap;

    private OpenworksSecuredObjectService securedObjectService;

    /**
     * Spring bean 설정으로 주입
     * 
     * @param matcherMap
     */
    public OpenworksFilterInvocationSecurityMetadataSource(LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>> matcherMap) {
        this.matcherMap = matcherMap;
    }

    /**
     * 메뉴정보와 권한설정 정보를 조회하는 서비스 객체를 Spring bean 설정으로 주입
     * 
     * @param securedObjectService
     */
    public void setSecuredObjectService(OpenworksSecuredObjectService securedObjectService) {
        this.securedObjectService = securedObjectService;
    }

    /**
     * 현재 요청 정보(HttpServletRequest)와 일치되는 권한 설정 정보를 반환한다.
     * 
     * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Collection<ConfigAttribute> result = null;
        for(Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : matcherMap.entrySet()) {
            if(entry.getKey().matches(request)) {
                result = entry.getValue();
                break;
            }
        }
        return result;
    }

    /**
     * 전체 권한설정 정보를 반환한다.
     * 
     * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {

        Set<ConfigAttribute> allAttributes = new HashSet<ConfigAttribute>();
        for(Map.Entry<RequestMatcher, Collection<ConfigAttribute>> entry : matcherMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }
        return allAttributes;
    }

    /**
     * 정해진 검증 객체인지 여부를 확인
     * 
     * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {

        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    /**
     * 외부에서 변경된 메뉴/권한 정보를 다시 로드하기 위한 메소드
     * 
     * @throws Exception
     */
    public void reload() throws Exception {
        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> reloadedMap = securedObjectService.getRolesAndUrl();
        Iterator<Entry<RequestMatcher, List<ConfigAttribute>>> iterator = reloadedMap.entrySet().iterator();

        Map<RequestMatcher, Collection<ConfigAttribute>> tmpMatcherMap = new LinkedHashMap<RequestMatcher, Collection<ConfigAttribute>>();

        while(iterator.hasNext()) {
            Entry<RequestMatcher, List<ConfigAttribute>> entry = iterator.next();
            tmpMatcherMap.put(entry.getKey(), entry.getValue());
        }

        // 이전 데이터 삭제
        matcherMap.clear();
        matcherMap.putAll(tmpMatcherMap);
        tmpMatcherMap.clear();

        logger.info("Spring Security : Menu and Role mapping loaded");
    }

}
