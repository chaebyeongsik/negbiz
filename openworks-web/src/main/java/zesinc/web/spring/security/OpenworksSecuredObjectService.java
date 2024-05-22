/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * 권한 설정에 해당하는 정보를 조회하는 서비스 객체
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
public interface OpenworksSecuredObjectService {

    /**
     * 롤에 대한 URL의 매핑 정보를 얻어온다.
     * 
     * @return
     * @throws Exception
     */
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getRolesAndUrl() throws Exception;

}
