/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.helper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;

/**
 * Spring FrameWork 관련 지원 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 5. 9.    방기배   신규 생성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class SpringHelper {

    private static final String DISPATCHER_SERVLET_CONTEXT_ATTR_NAME = Config
        .getString("system-config.springframeworks.dispatcherServletContextAttrName");

    /**
     * Spring Bean으로 로드된 Controller를 이름으로 찾아서 반환한다.
     * Controller annotation으로 설정된 bean만 대상이된다.
     * 
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Controller Bean 객체
     */
    public static Object findController(String serviceName) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx, DISPATCHER_SERVLET_CONTEXT_ATTR_NAME);

        return findBean(ctx, Controller.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 Controller 객체를 이름으로 찾아서 반환한다.
     * Controller annotation으로 설정된 bean만 대상이된다.
     * 
     * @param ctx ApplicationContext
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Controller Bean 객체
     */
    public static Object findController(ApplicationContext ctx, String serviceName) {

        return findBean(ctx, Controller.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 Service 객체를 이름으로 찾아서 반환한다.
     * Service annotation으로 설정된 bean만 대상이된다.
     * 
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Service Bean 객체
     */
    public static Object findService(String serviceName) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx);

        return findBean(ctx, Service.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 Service 객체를 이름으로 찾아서 반환한다.
     * Service annotation으로 설정된 bean만 대상이된다.
     * 
     * @param ctx ApplicationContext
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Service Bean 객체
     */
    public static Object findService(ApplicationContext ctx, String serviceName) {

        return findBean(ctx, Service.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 DAO 객체를 이름으로 찾아서 반환한다.
     * Repository annotation으로 설정된 bean만 대상이된다.
     * 
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 DAO Bean 객체
     */
    public static Object findDao(String serviceName) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx);

        return findBean(ctx, Repository.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 DAO 객체를 이름으로 찾아서 반환한다.
     * Repository annotation으로 설정된 bean만 대상이된다.
     * 
     * @param ctx ApplicationContext
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 DAO Bean 객체
     */
    public static Object findDao(ApplicationContext ctx, String serviceName) {

        return findBean(ctx, Repository.class, serviceName);
    }

    /**
     * Spring Bean으로 로드된 DAO 객체를 이름으로 찾아서 반환한다.
     * 이름으로 등록된 bean을 검색할 경우에 사용한다.
     * 
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 DAO Bean 객체
     */
    public static Object findBean(String serviceName) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx);

        return findBean(ctx, serviceName);
    }

    /**
     * ApplicationContext에서 해당 Bean이름을 찾아서 반환한다.
     * 이름으로 등록된 bean을 검색할 경우에 사용한다.
     * 
     * @param ctx Spring ApplicationContext
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Bean 객체
     */
    public static Object findBean(ApplicationContext ctx, String serviceName) {

        if(ctx.containsBean(serviceName)) {
            return ctx.getBean(serviceName);
        }
        return null;
    }

    /**
     * ApplicationContext에서 해당 Bean이름을 찾아서 반환한다.
     * annotation으로 설정된 bean만 대상이된다.
     * 
     * @param ctx Spring ApplicationContext
     * @param clazz Spring Annotation
     *        (Controller.class/Service.class/Repository.class)
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return ApplicationContext에 등록된 Bean 객체
     */
    private static Object findBean(ApplicationContext ctx, Class<? extends Annotation> clazz, String serviceName) {

        Map<String, Object> applMap = ctx.getBeansWithAnnotation(clazz);

        return applMap.get(serviceName);
    }

    /**
     * 각각의 컨트롤러명, 컨트롤러 패스 정보를 반환한다.
     * 
     * @return 각각의 컨트롤러명, 컨트롤러 패스 정보 목록
     */
    public static Object getControllerList() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        ServletContext svrCtx = request.getSession().getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(svrCtx, DISPATCHER_SERVLET_CONTEXT_ATTR_NAME);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        // 컨트롤러 클래스 전체
        Map<String, Object> applMap = ctx.getBeansWithAnnotation(Controller.class);

        // 각각의 컨트롤러 클래스들
        for(final Object ctrl : applMap.values()) {

            Map<String, Object> ctrlMap = new HashMap<String, Object>();

            // 클래스 자신
            Class<? extends Object> ctrlClass = ctrl.getClass();

            // 클래스 명
            Controller ctrlVal = ctrlClass.getAnnotation(Controller.class);
            if(Validate.isEmpty(ctrlVal) || Validate.isEmpty(ctrlVal.value())) {
                continue;
            }
            ctrlMap.put("ctrlNm", ctrlVal.value());

            // 클래스 풀패스
            ctrlMap.put("ctrlFullPath", ctrlClass.getName());

            list.add(ctrlMap);
        }
        return list;
    }

    /**
     * 컨트롤러 내부 리퀘스트 매핑 정보를 반환한다.
     * 
     * @param serviceName ApplicationContext에 등록된 Bean 이름
     * @return 컨트롤러 내부 리퀘스트 매핑 정보
     */
    public static List<Map<String, String>> getRequestMappingInController(String serviceName) {

        List<Map<String, String>> uriAndAuthCodeList = new ArrayList<Map<String, String>>();

        Object ctrl = findController(serviceName);
        if(Validate.isNotEmpty(ctrl)) {

            // 클래스 자신
            Class<? extends Object> ctrlClass = ctrl.getClass();
            // 클래스의 리퀘스트매핑 어노테이션 자신
            RequestMapping requestMappingAnnotation = ctrlClass.getAnnotation(RequestMapping.class);

            String[] basePath = null;

            // 리퀘스트매핑 정보가 있다면 basePath에 담는다(클래스-리퀘스트매핑)
            if(Validate.isNotEmpty(requestMappingAnnotation)
                && Validate.isNotEmpty(requestMappingAnnotation.value())) {
                basePath = requestMappingAnnotation.value();
            }

            // 권한코드 추출용 변수들
            OpenworksAuth authAnno;
            AuthType authTypeAnno;
            String authCode;

            // 클래스 내부 메소드들
            Method[] methods = ctrlClass.getMethods();
            Map<String, String> uriAndAuthCode;

            for(Method m : methods) {
                // 리퀘스트매핑 어노테이션
                requestMappingAnnotation = m.getAnnotation(RequestMapping.class);

                if(Validate.isNotEmpty(requestMappingAnnotation)) {
                    // 권한 정보
                    authAnno = m.getAnnotation(OpenworksAuth.class);
                    authCode = "";
                    if(authAnno != null) {
                        authTypeAnno = authAnno.authType();
                        authCode = authTypeAnno.getCdId();
                    }

                    String[] values = requestMappingAnnotation.value();

                    // 리퀘스트매핑 정보 목록
                    for(String pattern : values) {
                        // URI 와 권한코드
                        uriAndAuthCode = new HashMap<String, String>();

                        // basePath가 없거나 메핑주소가 절대경로의 경우는 basePath를 사용하지 않는다.
                        if(pattern.startsWith("/") || Validate.isEmpty(basePath)) {
                            uriAndAuthCode.put("pattern", pattern);
                            uriAndAuthCode.put("authCode", authCode);

                            uriAndAuthCodeList.add(uriAndAuthCode);
                        } else {
                            // 클래스에 리퀘스트매핑이 있었다면 메소드 리퀘스트매핑과 합쳐준다
                            for(String base : basePath) {
                                pattern = base + "/" + pattern;

                                uriAndAuthCode.put("pattern", pattern);
                                uriAndAuthCode.put("authCode", authCode);

                                uriAndAuthCodeList.add(uriAndAuthCode);
                            }
                        }
                    }// end for[requestMappingAnnotation]
                }// end if
            }// end for[methods]
        }

        return uriAndAuthCodeList;
    }
}
