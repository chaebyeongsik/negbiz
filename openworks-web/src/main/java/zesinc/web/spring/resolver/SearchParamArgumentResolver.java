/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.resolver;

import java.util.Map;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

import zesinc.web.vo.BaseVO;
import zesinc.web.vo.ParamMap;

/**
 * <pre>
 * 검색 파라미터를 BaseVO를 상속한 VO 객체의 paramMap에 자동설정한다.
 * 검색파라미터는 기본적으로 "q_"로 시작되며 환결설정 파일에서 변경이 가능하다.
 * 
 *  설정파일 : /resources/config/commons/webapp-commons-config.xml
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 */
public class SearchParamArgumentResolver extends ParamArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {

        return BaseVO.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Object object = null;
        Map<String, Object> paramMap = new ParamMap<String, Object>();
        try {
            // 기본 Bean 바인딩 적용
            object = doBind(parameter, mavContainer, webRequest, binderFactory);

            // 검색 파라미터 설정 값으로 시작하는 파라미터를 설정한다. (기본값은 q_)
            setSearchParam(webRequest, paramMap);

            // VO 클레스에 paramMap를 설정한다.
            setParamMap(webRequest, object, paramMap);

            logger.debug("SearchParamArgumentResolver object {} ", object);
        } catch (Exception e) {
            logger.error("바인딩 오류", e);
            Class<?> clazz = parameter.getParameterType();
            object = clazz.newInstance();
            setParamMap(webRequest, object, paramMap);
        }

        return object;
    }

}
