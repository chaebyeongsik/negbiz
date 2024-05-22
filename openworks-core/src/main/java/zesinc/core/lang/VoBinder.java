/*
 * Copyright (c) 2010 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.lang;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BEAN to Map, Map to BEAN, HttpServletRequest to BEAN Binding Support
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 * 2012. 2. 26.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class VoBinder extends Binder {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(VoBinder.class);

    /**
     * targetObj의 필드 명을 sourceMap의 Key로 사용하여 sourceMap의 값을 추출하고
     * targetObj의 필드 값을 <code>Method</code>를 사용하지 않고 직접 바인딩한다.
     * 
     * @param targetObj Bind 대상 Object
     * @param sourceMap Map interface 구현체 (ex:HashMap)
     */
    public static void methodBind(Object targetObj, Map<String, Object> sourceMap) {

        Method[] methods = MethodUtil.getMethods(targetObj.getClass(), PREFIX_SET);
        int mCnt = methods.length;

        Class<?>[] paramType = null;
        Method method = null;
        Object paramObj = null;

        for(int i = 0 ; i < mCnt ; i++) {
            paramType = methods[i].getParameterTypes();
            paramObj = sourceMap.get(MethodUtil.methodToFieldName(methods[i], PREFIX_SET));
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
     * sourceObj의 필드 값을 추출하여 sourceObj의 필드 명을 targetMap의 Key로 사용하고
     * sourceObj의 필드 값을 targetMap의 값으로 설정한다.
     * 
     * @param targetMap Map interface 구현체 (ex:HashMap)
     * @param sourceObj 키와 값 추출 대상 Object
     */
    public static void methodBind(Map<String, Object> targetMap, Object sourceObj) {

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
                targetMap.put(MethodUtil.methodToFieldName(method[i], PREFIX_GET), invokeValue);
            }
        }
    }

    /**
     * targetObj의 필드 명을 sourceMap의 Key로 사용하여 sourceMap의 값을 추출하고
     * targetObj의 필드 값을 <code>Method</code>를 사용하지 않고 직접 바인딩한다.
     * 
     * @param targetObj Bind 대상 Object
     * @param sourceMap Map interface 구현체 (ex:HashMap)
     */
    public static void fieldBind(Object targetObj, Map<String, Object> sourceMap) {

        Field[] fields = FieldUtil.getFields(targetObj.getClass());

        Class<?> fieldType = null;
        Object setObj;
        int fCnt = fields.length;

        for(int i = 0 ; i < fCnt ; i++) {
            try {
                /* final 지시자 체크 */
                if((fields[i].getModifiers() & 16) == 0) {
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
     * sourceObj의 필드 명을 targetMap의 키로 사용하고 sourceObj의 필드 값을
     * targetMap의 값으로 설정한다.
     * 
     * @param targetMap Map interface 구현체 (ex:HashMap)
     * @param sourceObj 키와 값 추출 대상 Object
     */
    public static void fieldBind(Map<String, Object> targetMap, Object sourceObj) {

        Object mapValue = null;

        Field[] fields = FieldUtil.getFields(sourceObj.getClass());
        int fCnt = fields.length;

        for(int i = 0 ; i < fCnt ; i++) {
            try {
                mapValue = fields[i].get(sourceObj);

                targetMap.put(fields[i].getName(), mapValue);
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("Field.get() failed", e);
                }
            }
        }

    }

}
