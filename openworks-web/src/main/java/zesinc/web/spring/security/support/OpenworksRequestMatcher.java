/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.security.support;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * <pre>
 * URI와 Parameter를 개별적으로 검증후 둘다 만족하는지를 확인한다.
 * URI의 경우는 AntPathRequestMatcher 클레스를 통하여 검증하고,
 * 파라미터는 HttpServletRequest의 Parameter 값을 가져와 동일한 값이 존재하는지 확인한다.
 * </pre>
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
public class OpenworksRequestMatcher implements RequestMatcher {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** URI 검증 객체 */
    private AntPathRequestMatcher matcher;
    /** Parameter 검증 데이터 */
    private List<String> paramList;

    /**
     * URI 패턴만 있고, 파라미터가 없는 경우
     * 
     * @param uriPattern
     */
    public OpenworksRequestMatcher(String uriPattern) {
        this.matcher = new AntPathRequestMatcher(uriPattern);
        this.paramList = null;
    }

    /**
     * URI 패턴과 파라미터가 모두 있는 경우
     * 
     * @param uriPattern
     * @param params
     */
    public OpenworksRequestMatcher(String uriPattern, List<String> paramList) {
        this.matcher = new AntPathRequestMatcher(uriPattern);
        this.paramList = paramList;
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        boolean isMatches = matcher.matches(request);

        if(isMatches) {
            /*
             * Spring Security 초기화 시에 임시 클레스가 사용되며,
             * 임시 클레스인 경우 아래 파라미터체크 구간이 실행되지 않도록 한다.
             */
            String name = request.getClass().getName();
            if(name.indexOf("DummyRequest") < 0) {
                if(paramList != null) {
                    for(String param : paramList) {
                        String[] params = param.split("=");
                        String value = request.getParameter(params[0]);

                        if(params.length < 2) {
                            isMatches = false;
                            break;
                        }
                        if(!params[1].equals(value)) {
                            isMatches = false;
                            break;
                        }
                    }
                }
            }
            if(logger.isDebugEnabled()) {
                logger.debug("Matcher URI is {}, Request URI is {}", matcher.getPattern(), request.getRequestURI());
            }
        }

        return isMatches;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((matcher == null) ? 0 : matcher.hashCode());
        result = prime * result + ((paramList == null) ? 0 : paramList.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj == null || getClass() != obj.getClass()) {
            return false;
        }

        OpenworksRequestMatcher other = (OpenworksRequestMatcher) obj;

        if(matcher == null) {
            if(other.matcher != null) {
                return false;
            }
        } else if(!matcher.equals(other.matcher)) {
            return false;
        }
        if(paramList == null) {
            if(other.paramList != null) {
                return false;
            }
        } else if(!paramList.equals(other.paramList)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "OpenworksRequestMatcher [matcher=" + matcher + ", paramList=" + paramList + "]";
    }

}
