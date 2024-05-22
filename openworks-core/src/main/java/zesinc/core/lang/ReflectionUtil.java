/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>Reflection</code> 관련 Util Class
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ReflectionUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /** Java Bean get 메소드 PREFIX */
    public static final String PREFIX_GET = "get";
    /** Java Bean set 메소드 PREFIX */
    public static final String PREFIX_SET = "set";
    /** Java Bean is 메소드 PREFIX */
    public static final String PREFIX_IS = "is";

    /**
     * targetObj의 필드 명을 sourceMap의 Key로 사용하여 sourceMap의 값을 추출하고 targetObj의 필드 값을 <code>Method</code>
     * 를 사용하지 않고 직접 바인딩한다.
     * 
     * @param targetObj Bind 대상 Object
     * @param sourceMap Map interface 구현체 (ex:HashMap)
     */
    public static void methodsBind(Object targetObj, Map<String, Object> sourceMap) {

        Method[] methods = MethodUtil.getMethods(targetObj.getClass(), PREFIX_SET);
        int mCnt = methods.length;

        Class<?>[] paramType = null;
        Method method = null;
        Object paramObj = null;

        for(int i = 0 ; i < mCnt ; i++) {
            paramType = methods[i].getParameterTypes();
            paramObj = sourceMap.get(MethodUtil.methodToFieldName(methods[i]));
            try {
                method = targetObj.getClass().getMethod(methods[i].getName(), paramType);

                method.invoke(targetObj, paramObj);
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("method.invoke failed", e);
                }
            }
        }
    }

    /**
     * <code>Method</code>에 값을 설정한다.
     * 
     * @param method 대상 메소드
     * @param value 설정 값
     */
    public static void methodBind(Object targetObj, Method method, Object value) {

        Object paramObj = value;
        Class<?>[] paramType = method.getParameterTypes();

        try {
            method = targetObj.getClass().getMethod(method.getName(), paramType);

            method.invoke(targetObj, paramObj);
        } catch (Exception e) {
            if(logger.isDebugEnabled()) {
                logger.debug("method.invoke failed", e);
            }
        }

    }

    /**
     * sourceObj의 필드 값을 추출하여 sourceObj의 필드 명을 targetMap의 Key로 사용하고 sourceObj의 필드
     * 값을 targetMap의 값으로 설정한다.
     * 
     * @param targetMap Map interface 구현체 (ex:HashMap)
     * @param sourceObj 키와 값 추출 대상 Object
     */
    public static void methosdBind(Map<String, Object> targetMap, Object sourceObj) {

        Method[] method = MethodUtil.getMethods(sourceObj.getClass(), PREFIX_GET);

        Object invokeValue = null;
        int mCnt = method.length;

        for(int i = 0 ; i < mCnt ; i++) {

            try {
                if(null != method[i]) {
                    invokeValue = method[i].invoke(sourceObj);
                }
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("method.invoke failed", e);
                }
            }

            if(null != invokeValue) {
                targetMap.put(MethodUtil.methodToFieldName(method[i]), invokeValue);
            }
        }
    }

    /**
     * targetObj의 필드 명을 sourceMap의 Key로 사용하여 sourceMap의 값을 추출하고 targetObj의 필드 값을 <code>Method</code>
     * 를 사용하지 않고 직접 바인딩한다.
     * 
     * @param targetObj Bind 대상 Object
     * @param sourceMap Map interface 구현체 (ex:HashMap)
     */
    public static void fieldsBind(Object targetObj, Map<String, Object> sourceMap) {

        Field[] fields = FieldUtil.getFields(targetObj.getClass());

        Class<?> fieldType = null;
        Object setObj;
        int fCnt = fields.length;

        for(int i = 0 ; i < fCnt ; i++) {
            try {
                /* final 지시자 체크 */
                if(!Modifier.isFinal(fields[i].getModifiers())) {
                    fieldType = fields[i].getType();
                    setObj = sourceMap.get(fields[i].getName());

                    if(null != setObj) {
                        if(null != setObj && fieldType.isPrimitive()
                            && !(setObj instanceof String[])) {
                            setObj = String.valueOf(setObj);
                            fields[i].set(targetObj,
                                ClassUtil.getPrimitiveConstructor(fieldType, setObj));
                        } else {
                            fields[i].set(targetObj, setObj);
                        }
                    }
                }
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Field.set() failed", e);
                }
            }
        }
    }

    /**
     * sourceObj의 필드 명을 targetMap의 키로 사용하고 sourceObj의 필드 값을 targetMap의 값으로 설정한다.
     * 
     * @param targetMap Map interface 구현체 (ex:HashMap)
     * @param sourceObj 키와 값 추출 대상 Object
     */
    public static void fieldsBind(Map<String, Object> targetMap, Object sourceObj) {

        Object mapValue = null;

        Field[] fields = FieldUtil.getFields(sourceObj.getClass());
        int fCnt = fields.length;

        for(int i = 0 ; i < fCnt ; i++) {
            try {
                if (!fields[i].isAccessible()) fields[i].setAccessible(true);
                mapValue = fields[i].get(sourceObj);

                targetMap.put(fields[i].getName(), mapValue);
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Field.get() failed", e);
                }
            }
        }

    }

    /**
     * 오브젝트의 메소드를 호출하고 결과 값을 반환한다.
     * 
     * @param obj 대상 오브젝트
     * @param method 메소드 명
     * @param params 메소드 파라메타
     * @param param_types 메소드 파라메타 유형
     * @return 호출 결과
     * @exception IllegalAccessException
     * @exception NoSuchMethodException
     * @exception InvocationTargetException
     */
    /**
     * 오브젝트의 메소드를 호출하고 결과 값을 반환한다.
     * 
     * @param obj 메소드 호출 대상 객체
     * @param method 호출 대상 메소드명
     * @param params 메소드 전달 인자 값
     * @param param_types 메소드 전달 인자 값의 유형(Type)
     * @return 실행 결과 값
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Object invoke(Object obj, String method, Object[] params, Class<?>[] param_types)
        throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Method m = obj.getClass().getMethod(method, param_types);
        return m.invoke(obj, params);
    }

    /**
     * 오브젝트의 메소드를 호출하고 결과 값을 반환한다.
     * 
     * @param obj 메소드 호출 대상 객체
     * @param method 호출 대상 메소드명
     * @param params 메소드 전달 인자 값
     * @return 실행 결과 값
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Object invoke(Object obj, String method, Object[] params) throws IllegalAccessException,
        NoSuchMethodException, InvocationTargetException {

        Method[] all_methods = obj.getClass().getMethods();
        Method method2invoke = null;

        // find method with the same name and matching argument types
        for(int i = 0 ; i < all_methods.length ; i++) {
            Method m = all_methods[i];
            if(m.getName().equals(method)) {
                Class<?>[] pt = m.getParameterTypes();
                boolean match = true;
                int match_loops = pt.length;
                if(match_loops != params.length) {
                    continue;
                }
                for(int j = 0 ; j < match_loops ; j++) {
                    if(pt[i].isInstance(params[i]) == false) {
                        match = false;
                        break;
                    }
                }
                if(match == true) {
                    method2invoke = m;
                }
            }
        }

        // throw an exception if no method to invoke
        if(method2invoke == null) {
            String t = "(";
            for(int i = 0 ; i < params.length ; i++) {
                if(i != 0) {
                    t += ", ";
                }
                t += params[i].getClass().getName();
            }
            t += ")";
            throw new NoSuchMethodException(obj.getClass().getName() + "." + method + t);
        }

        // finally, invoke founded method
        return method2invoke.invoke(obj, params);
    }

    /**
     * 원본 VO 객체의 값을 대상 VO 객체에 바인딩
     * 
     * @param sourceVo 원본 VO
     * @param targetVo 대상 VO
     * @throws Exception
     */
    public static void copyBean(Object sourceVo, Object targetVo) throws Exception {

        Class<? extends Object> sourceCls = sourceVo.getClass();
        Class<? extends Object> targetCls = targetVo.getClass();
        Method[] sourceMethods = sourceCls.getMethods();
        try {
            for(int i = 0 ; sourceMethods.length > i ; i++) {
                // String sourceMethodName = sourceMethods[i].getName();
                if(!sourceMethods[i].getName().startsWith("get")) {
                    continue;
                }
                try {
                    Object returnObj;
                    try {
                        // returnObj = sourceMethods[i].invoke(sourceVo, null)
                        returnObj = sourceMethods[i].invoke(sourceVo);
                    } catch (Exception e1) {
                        continue;
                    }
                    if(returnObj == null) {
                        continue;
                    }
                    String targetMethodName = "set"
                        + sourceMethods[i].getName().substring(3, sourceMethods[i].getName().length());
                    Class<?>[] paramClss = new Class[1];
                    paramClss[0] = sourceMethods[i].getReturnType();
                    Method targetMethod;
                    try {
                        targetMethod = targetCls.getMethod(targetMethodName, paramClss);
                    } catch (Exception e1) {
                        continue;
                    }

                    Object[] paramObjs = new Object[1];
                    paramObjs[0] = returnObj;
                    targetMethod.invoke(targetVo, paramObjs);
                } catch (IllegalArgumentException e2) {
                    throw new Exception(e2);
                } catch (InvocationTargetException e2) {
                    throw new Exception(e2.getTargetException());
                }
            }
        } catch (Exception e) {
            throw new Exception("fail to create for targetVo instance. " + e);
        }
    }
}
