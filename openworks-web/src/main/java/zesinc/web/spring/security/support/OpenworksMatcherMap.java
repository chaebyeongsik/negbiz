/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security.support;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

import zesinc.web.spring.security.OpenworksSecuredObjectService;

/**
 * 메뉴 관리 정보에서 메뉴별 권한 설정 정보 목록을 반환
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
 * @see
 */
public class OpenworksMatcherMap implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private OpenworksSecuredObjectService securedObjectService;

    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> rolesMap;

    public void setSecuredObjectService(OpenworksSecuredObjectService securedObjectService) {
        this.securedObjectService = securedObjectService;
    }

    public void init() throws Exception {
        rolesMap = securedObjectService.getRolesAndUrl();
    }

    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {
        if(rolesMap == null) {
            rolesMap = securedObjectService.getRolesAndUrl();
        }
        return rolesMap;
    }

    @Override
    public Class<?> getObjectType() {

        return LinkedHashMap.class;
    }

    @Override
    public boolean isSingleton() {

        return true;
    }

}
