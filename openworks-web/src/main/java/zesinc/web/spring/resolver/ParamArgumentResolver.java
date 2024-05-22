/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.resolver;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.annotation.ModelFactory;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import zesinc.core.lang.MethodUtil;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.pager.Pager;
import zesinc.web.utils.XssUtil;

/**
 * <pre>
 * 검색 파라미터(기본값은 q_)를 VO 클레스의 paramMap에 주입하며, 일반 파라미터를 Bean 바인딩을 처리한다.
 * 
 *  설정파일 : /resources/config/commons/webapp-commons-config.xml
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 11. 19.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 */
public abstract class ParamArgumentResolver implements HandlerMethodArgumentResolver {

    /** 로깅 */
    protected static Logger logger = LoggerFactory.getLogger(ParamArgumentResolver.class);
    /** 검색 파라미터 접두어 */
    public static final String PREFIX_SEARCH_PARAM = BaseConfig.PREFIX_SEARCH_PARAM;
    /** 파라미터 설정 멥클레스 메소드 */
    protected static final String METHOD_NAME = "setParamMap";
    /** 현재 페이지 번호 기본 값 */
    public static final int DEFAULT_CURR_PAGE = 1;

    /**
     * 페이징 처리 결과의 시작번호(pagingStartNum) 증감 보정 수치
     * 페이징은 1을 기본으로 하나 특정 DB의 경우 0을 기준으로 하는 경우가 있음
     * 이 경우 -1을 설정하면 계산된 값에서 1을 감소시킨다.
     */    
    public static int addStartNum = 0;

    @Value("${page.addstartnum}")
    public void setStartNumStr(int addStartNum) {  
    	this.addStartNum = addStartNum;
    }

    /**
     * 페이징 처리 결과의 종료번호(pagingEndNum) 증감 보정 수치 
     * 페이징은 1을 기본으로 하나 특정 DB의 경우 0을 기준으로 하는 경우가 있음
     * 이 경우 -1을 설정하면 계산된 값에서 1을 감소시킨다.
     */
    public static int addEndNum = 0;

    /**
     * 검색 파라미터 주입.
     * 사용자 지정 접두사 값이 있는 경우에는 해당 값으로 설정한다.
     * <p />
     * 사용자 정의 파라미터 접두사를 담는 input name : opCustomPrefix
     * 
     * @param webRequest
     * @param paramMap
     */
    protected void setSearchParam(NativeWebRequest webRequest, Map<String, Object> paramMap) {

        /*
         * 지정 파라미터(opCustomPrefix) 접두사가 있는 경우 해당 접두사에 해당하는 것만 설정
         * 없는 경우 기본 값의 접두사를 사용
         */
        String paramCustomPrefix = webRequest.getParameter("opCustomPrefix");
        if(Validate.isNotEmpty(paramCustomPrefix)) {
            String[] customPrefixs = paramCustomPrefix.split(",");
            for(String customPrefix : customPrefixs) {
                setPrefixSearchParam(webRequest, customPrefix, paramMap);
            }
        } else {
            // 기본값 q_ 를 설정
            setPrefixSearchParam(webRequest, PREFIX_SEARCH_PARAM, paramMap);
        }
    }

    /**
     * 검색 파라미터 주입
     * 
     * @param webRequest request 객체
     * @param prefixSearchParam 파라미터 접두사(예 : q_)
     * @param paramMap 접두사에 해당하는 파라미터를 담는 Map
     * @see XssUtil#cleanTag(String)
     * @see XssUtil#cleanTag(String, zesinc.web.utils.XssUtil.TYPE)
     */
    protected void setPrefixSearchParam(NativeWebRequest webRequest, String prefixSearchParam, Map<String, Object> paramMap) {
        for(Iterator<String> iterator = webRequest.getParameterNames() ; iterator.hasNext() ;) {
            String key = iterator.next();
            if(key.startsWith(prefixSearchParam)) {
                String[] param = webRequest.getParameterValues(key);
                if(Validate.isNotEmpty(param)) {
                    int paramCnt = param.length;
                    if(paramCnt == 1) {
                        paramMap.put(key, XssUtil.cleanTag(param[0], XssUtil.TYPE.ALL));
                    } else {
                        for(int i = 0 ; i < paramCnt ; i++) {
                            param[i] = XssUtil.cleanTag(param[i], XssUtil.TYPE.ALL);
                        }
                        paramMap.put(key, param);
                    }
                }
            }
        }
    }

    /**
     * 페이징 파라미터 주입.
     * 페이징관련 파라미터로 쿼리 페이징 시작번호와 끝번호를 설정한다.
     * 만일 해당 파라미터가 없는 경우 환경설정의 기본 값을 이용하여 초기화한다.
     * <p />
     * 사용자 정의 파라미터 접두사를 담는 input name : opCustomPrefix
     * 
     * @param paramMap 접두사에 해당하는 파라미터를 담는 Map
     */
    public static void setPageParam(NativeWebRequest webRequest, Map<String, Object> paramMap) {

        /*
         * 지정 파라미터(opCustomPrefix) 접두사가 있는 경우 해당 접두사에 해당하는 것만 설정
         * 없는 경우 기본 값의 접두사를 사용
         */
        String paramCustomPrefix = webRequest.getParameter("opCustomPrefix");
        if(Validate.isNotEmpty(paramCustomPrefix)) {
            String[] customPrefixs = paramCustomPrefix.split(",");
            for(String customPrefix : customPrefixs) {
                setPrefixPageParam(customPrefix, paramMap);
            }
        } else {
            // 기본값 q_ 는 무조건 설정
            setPrefixPageParam(PREFIX_SEARCH_PARAM, paramMap);
        }
    }

    /**
     * 페이징 파라미터 주입.
     * 페이징관련 파라미터로 쿼리 페이징 시작번호와 끝번호를 설정한다.
     * 만일 해당 파라미터가 없는 경우 환경설정의 기본 값을 이용하여 초기화한다.
     * 
     * @param prefixSearchParam 파라미터 접두사(예 : q_)
     * @param paramMap 접두사에 해당하는 파라미터를 담는 Map
     */
    public static void setPrefixPageParam(String prefixSearchParam, Map<String, Object> paramMap) {
        String cPage = (String) paramMap.get(prefixSearchParam + "currPage");
        String rPage = (String) paramMap.get(prefixSearchParam + "rowPerPage");
        String pPage = (String) paramMap.get(prefixSearchParam + "pagePerPage");

        int currPage = (Validate.isNotEmpty(cPage) ? Integer.parseInt(cPage) : DEFAULT_CURR_PAGE);
        int rowPerPage = (Validate.isNotEmpty(rPage) ? Integer.parseInt(rPage) : BaseConfig.DEFAULT_ROW_PER_PAGE);
        int pagePerPage = (Validate.isNotEmpty(pPage) ? Integer.parseInt(pPage) : BaseConfig.DEFAULT_PAGE_PER_PAGE);

    	try {
            currPage = (currPage == 0) ? 1 : currPage;
            rowPerPage = (rowPerPage == 0) ? 1 : rowPerPage;
            pagePerPage = (pagePerPage == 0) ? 1 : pagePerPage;

            paramMap.put(prefixSearchParam + "currPage", currPage);
            paramMap.put(prefixSearchParam + "rowPerPage", rowPerPage);
            paramMap.put(prefixSearchParam + "pagePerPage", pagePerPage);

            
            int iStartNum = Pager.getStartNum(currPage, rowPerPage);
            int iEndNum = Pager.getEndNum(iStartNum, rowPerPage);

            iStartNum += addStartNum;
            iEndNum += addEndNum;

            paramMap.put(prefixSearchParam + "pagingStartNum", Integer.valueOf(iStartNum));
            paramMap.put(prefixSearchParam + "pagingEndNum", Integer.valueOf(iEndNum));
		} catch (NumberFormatException e) {
			e.printStackTrace(); 
		}
    }

    /**
     * VO 클레스에 파라미터 값을 바인딩하며 Spring framework의 기본 바인딩 기술을 이용한다.
     * 
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    protected Object doBind(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String name = ModelFactory.getNameForParameter(parameter);
        Object attribute = (mavContainer.containsAttribute(name)) ?
            mavContainer.getModel().get(name) : BeanUtils.instantiateClass(parameter.getParameterType());

        WebDataBinder binder = binderFactory.createBinder(webRequest, attribute, name);
        if(binder.getTarget() != null) {
            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            ((ExtendedServletRequestDataBinder) binder).bind(request);
        }

        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return binder.getTarget();
    }

    /**
     * VO 클레스에 paramMap를 설정하며 request 객체에 'paramMap'을 키로하여 저장한다.
     * 
     * @param webRequest
     * @param object
     * @throws Exception
     */
    protected void setParamMap(NativeWebRequest webRequest, Object object, Map<String, Object> paramMap) throws Exception {

        @SuppressWarnings("unchecked")
        Map<String, Object> prvParamMap = (Map<String, Object>) webRequest.getAttribute("paramMap", RequestAttributes.SCOPE_REQUEST);
        if(Validate.isEmpty(prvParamMap)) {
            webRequest.setAttribute("paramMap", paramMap, RequestAttributes.SCOPE_REQUEST);
        }

        // 검색 + 페이징 파라미터 주입
        Method m = MethodUtil.getMethod(object.getClass(), METHOD_NAME);
        m.invoke(object, new Object[] { paramMap });
    }

    /**
     * 페이징 처리시 시작번호 증감 보정 수치 반환
     *
     * @return int addStartNum
     */
    public int getAddStartNum() {
        return addStartNum;
    }

    /**
     * 페이징 처리시 시작번호 증감 보정 수치 설정
     * 
     * @param addStartNum 을(를) int addStartNum로 설정
     */
    public void setAddStartNum(int _addStartNum) {
        addStartNum = _addStartNum;
    }

    /**
     * 페이징 처리시 종료번호 증감 보정 수치 반환
     * 
     * @return int addEndNum
     */
    public int getAddEndNum() {
        return addEndNum;
    }

    /**
     * 페이징 처리시 종료번호 증감 보정 수치 설정
     * 페이징시작 번호 기본 값은 1이며 0이 필요한 경우 설정
     * 
     * @param addEndNum 을(를) int addEndNum로 설정
     */
    public void setAddEndNum(int _addEndNum) {
        addEndNum = _addEndNum;
    }

}
