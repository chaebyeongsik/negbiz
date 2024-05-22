/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.lang;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.config.Config;

/**
 * HttpServletRequest의 파라미터를 추출하여 대상 VO 객체 또는 Map 객체에
 * 설정한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 2.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class RequestBinder {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(RequestBinder.class);

    /** 검색용 파라이터 prefix */
    public static final String PREFIX_SEARCH_FIELD = Config.getString("webapp-config.prefixSearchParam", "q_");

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 Map에 key, value로 설정.
     * </pre>
     * 
     * @param request 요청객체
     */
    public static Map<String, Object> mapBind(HttpServletRequest request) {

        return mapBind(request, null);
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 Map에 key, value로 설정
     * - prefix != null : parameter명이 prefix 문자열로 시작되는 것만 추출
     * - prefix == null : 모든 parameter 추출
     * 
     * </pre>
     * 
     * @param request 요청객체
     * @param prefix prefix 문자열
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> mapBind(HttpServletRequest request, String prefix) {

        Map<String, Object> targetMap = new HashMap<String, Object>();

        String paramName;
        String[] paramArr;

        Enumeration<String> e = request.getParameterNames();

        while(e.hasMoreElements()) {
            paramName = e.nextElement();
            paramArr = request.getParameterValues(paramName);

            if(Validate.isNotEmpty(prefix)) {
                if(paramName.startsWith(prefix)) {

                    if(paramArr.length > 1) {

                        int arrCnt = paramArr.length;
                        ArrayList<String> al = new ArrayList<String>();
                        for(int i = 0 ; i < arrCnt ; i++) {
                            al.add(paramArr[i]);
                        }
                        targetMap.put(paramName, al);
                    } else {
                        targetMap.put(paramName, paramArr[0]);
                    }
                }
            } else {
                if(paramArr.length > 1) {

                    int arrCnt = paramArr.length;
                    ArrayList<String> al = new ArrayList<String>();
                    for(int i = 0 ; i < arrCnt ; i++) {
                        al.add(paramArr[i]);
                    }
                    targetMap.put(paramName, al);
                } else {
                    targetMap.put(paramName, paramArr[0]);
                }
            }
        }

        return targetMap;
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 targetObj에 key, value로 설정
     * - targetOb는 Java BEANS 표준형식에 맞도록 setter를 가지고 있어야 한다.
     * - prefix != null : parameter명이 prefix 문자열로 시작되는 것만  설정
     * - prefix == null : 모든 parameter 설정
     * </pre>
     * 
     * @param targetMap request에서 parameter를 key와 value로 설정
     * @param request 요청객체
     * @param prefix prefix 문자열 (예 : q_ )
     */
    public static void beanBind(Object targetObj, HttpServletRequest request, String prefix) {

        Method[] methods = MethodUtil.getMethods(targetObj.getClass(), Binder.PREFIX_SET);

        int mCnt = methods.length;

        Class<?>[] paramType = null;
        Method method = null;
        Object paramObj = null;

        String[] paramArr;
        String fieldName = "";
        if(null == prefix) {
            prefix = "";
        }

        for(int i = 0 ; i < mCnt ; i++) {

            fieldName = prefix + MethodUtil.methodToFieldName(methods[i], Binder.PREFIX_SET);
            paramArr = request.getParameterValues(fieldName);
            paramType = methods[i].getParameterTypes();

            if(null != paramArr) {
                if(paramArr.length > 1) {
                    int arrCnt = paramArr.length;
                    ArrayList<String> al = new ArrayList<String>();

                    for(int j = 0 ; j < arrCnt ; j++) {
                        al.add(paramArr[j]);
                    }

                    paramObj = al;
                } else {
                    paramObj = paramArr[0];
                }

                if(null != paramObj) {
                    try {
                        method = targetObj.getClass().getMethod(methods[i].getName(), paramType);

                        // 유형이 다르다면 인스턴스를 생성 후 invoke
                        if(paramType[0] == paramObj.getClass()) {
                            method.invoke(targetObj, paramObj);
                        } else {
                            method.invoke(targetObj, ClassUtil.getObjectConstructor(paramType[0], paramObj));
                        }
                    } catch (Exception e) {
                        if(logger.isDebugEnabled()) {
                            logger.debug("method.invoke failed", e);
                        }
                    }
                }
            }
        }
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 targetOb의 member 변수에 값을 설정한다.
     * </pre>
     * 
     * @param targetMap request에서 parameter를 key와 value로 설정
     * @param request 요청객체
     */
    public static void beanBind(Object targetObj, HttpServletRequest request) {

        beanBind(targetObj, request, PREFIX_SEARCH_FIELD);
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 targetObj에 key, value로 설정
     * - targetOb는 Java BEANS 표준형식에 맞도록 setter를 가지고 있어야 한다.
     * - prefix != null : parameter명이 prefix 문자열로 시작되는 것만  설정(prefix 붙어 있는 형태의 필드명)
     * - prefix == null : 모든 parameter 설정
     * </pre>
     * 
     * @param targetMap request에서 parameter를 key와 value로 설정
     * @param request 요청객체
     * @param prefix prefix 문자열 (예 : q_ )
     */
    public static void beanBindFull(Object targetObj, HttpServletRequest request, String prefix) {

        Method[] methods;
        if(Validate.isNotEmpty(prefix)) {
            methods = MethodUtil.getMethods(targetObj.getClass(), Binder.PREFIX_SET);
        } else {
            methods = MethodUtil.getMethods(targetObj.getClass());
        }

        int mCnt = methods.length;

        Class<?>[] paramType = null;
        Method method = null;
        Object paramObj = null;

        String[] paramArr;
        String fieldName = "";
        if(null == prefix) {
            prefix = "";
        }

        for(int i = 0 ; i < mCnt ; i++) {

            fieldName = MethodUtil.methodToFieldName(methods[i]);
            paramArr = request.getParameterValues(fieldName);
            paramType = methods[i].getParameterTypes();

            if(null != paramArr) {
                if(paramArr.length > 1) {
                    int arrCnt = paramArr.length;
                    ArrayList<String> al = new ArrayList<String>();

                    for(int j = 0 ; j < arrCnt ; j++) {
                        al.add(paramArr[j]);
                    }

                    paramObj = al;
                } else {
                    paramObj = paramArr[0];
                }

                if(null != paramObj) {
                    try {
                        method = targetObj.getClass().getMethod(methods[i].getName(), paramType);

                        // 유형이 다르다면 인스턴스를 생성 후 invoke
                        if(paramType[0] == paramObj.getClass()) {
                            method.invoke(targetObj, paramObj);
                        } else {
                            method.invoke(targetObj,
                                ClassUtil.getObjectConstructor(paramType[0], paramObj));
                        }
                    } catch (Exception e) {
                        if(logger.isDebugEnabled()) {
                            logger.debug("method.invoke failed", e);
                        }
                    }
                }
            }
        }
    }

}
