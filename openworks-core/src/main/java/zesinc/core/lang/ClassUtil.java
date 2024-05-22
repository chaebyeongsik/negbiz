/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.lang;

import java.lang.reflect.Constructor;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>Class</code> 관련 Util Class
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
public class ClassUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /** 기본형 클레스 */
    private static final HashMap<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
    static {
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        // primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
    }

    /** 기본형 렙핑 클레스 */
    private static final HashMap<Class<?>, Constructor<?>> constructorMap = new HashMap<Class<?>, Constructor<?>>();
    static {
        /* 기본 생성자 설정 */
        Class<?>[] args = { String.class };
        for(Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
            Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
            try {
                constructorMap.put(primitiveClass, wrapperClass.getDeclaredConstructor(args));
            } catch (NoSuchMethodException e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("DeclaredConstructor failed", e);
                }
            }
        }
    }

    /**
     * 기본형 데이터 타입 값을 위한 Constructor 생성
     * 
     * @param fieldType <code>Field</code> 유형
     * @param fieldValue <code>Field</code> 값
     * @return fieldType에 따른 Constructor
     */
    public static Object getPrimitiveConstructor(Class<?> fieldType, final Object fieldValue) {

        Object constructorObj = null;

        if(fieldType.isPrimitive() && fieldValue != null) {
            try {
                if(!fieldType.equals(char.class)) {
                    constructorObj = constructorMap.get(fieldType).newInstance(
                        new Object[] { fieldValue });
                } else {
                    constructorObj = new Character(((String) fieldValue).charAt(0));
                }
            } catch (Exception e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("newInstance failed", e);
                }
            }
        }

        return constructorObj;
    }

    /**
     * <code>Object</code> 데이터 타입 값을 위한 Constructor 생성
     * 
     * @param fieldType <code>Field</code> 유형
     * @param fieldValue <code>Field</code> 값
     * @return fieldType에 따른 Constructor
     */
    public static Object getObjectConstructor(Class<?> fieldType, final Object fieldValue) {

        Object ObjectInstanse = null;
        Class<?>[] args = { String.class };

        try {
            Constructor<?> constructor = fieldType.getDeclaredConstructor(args);
            ObjectInstanse = constructor.newInstance(new Object[] { fieldValue });
        } catch (Exception e) {}

        return ObjectInstanse;
    }

    /**
     * 클레스 전체이름으로 인스턴스를 생성한다.
     * 
     * @param className
     * @param callingClass
     * @return
     * @throws ClassNotFoundException
     */
    public static Class<?> loadClass(String className, Class<?> callingClass) throws ClassNotFoundException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    return ClassUtil.class.getClassLoader().loadClass(className);
                } catch (ClassNotFoundException exc) {}
            }
        }
        return callingClass.getClassLoader().loadClass(className);
    }
}
