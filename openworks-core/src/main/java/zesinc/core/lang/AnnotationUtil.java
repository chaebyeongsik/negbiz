/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.lang;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <code>Annotation</code> 관련 Util Class
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
public class AnnotationUtil {

    /**
     * 해당 클레스 메소드 중에서 제시된 <code>Annotation</code>을 포함하고 있는 <code>Method[]</code> 를 반환
     * 
     * @param clazz 대상 클레스
     * @param anno 대상 어노테이션
     * @return anno가 적용된 메소드 목록
     */
    public static Method[] getMethodWidthAnnotations(Class<?> clazz,
        Class<? extends Annotation> anno) {

        List<Method> methodList = new ArrayList<Method>();
        Method[] declaredMethods = MethodUtil.getMethods(clazz);

        for(Method m : declaredMethods) {

            if(m.getAnnotation(anno) != null) {
                methodList.add(m);
            }
        }
        return methodList.toArray(new Method[methodList.size()]);
    }

    /**
     * 해당 클레스 필드멤버 중에서 제시된 <code>Annotation</code>을 포함하고 있는 <code>Field[]</code> 를 반환
     * 
     * @param clazz
     * @param anno
     * @return anno가 적용된 필드멤버 목록
     */
    public static Field[] getFieldWidthAnnotations(Class<?> clazz, Class<? extends Annotation> anno) {

        List<Field> fieldList = new ArrayList<Field>();
        Field[] declaredFields = FieldUtil.getFields(clazz);

        for(Field f : declaredFields) {
            if(f.getAnnotation(anno) != null) {
                fieldList.add(f);
            }
        }
        return fieldList.toArray(new Field[fieldList.size()]);
    }

    /**
     * 대상 <code>Field</code>에 적용된 <code>Annotations</code>를 반환
     * 
     * @param clazz
     * @param fieldName
     * @return
     */
    public static Annotation[] getAnnotationsWidthFieldName(Class<?> clazz, String fieldName) {

        Field field = FieldUtil.getField(clazz, fieldName);
        Annotation[] anotations = null;

        if(field != null) {
            anotations = field.getDeclaredAnnotations();
        }
        return anotations;
    }

    /**
     * 대상 <code>Method</code>에 적용된 <code>Annotations</code>를 반환
     * 
     * @param clazz
     * @param methodName
     * @return
     */
    public static Annotation[] getAnnotationsWidthMethodName(Class<?> clazz, String methodName) {

        Method method = MethodUtil.getMethod(clazz, methodName);
        Annotation[] anotations = null;

        if(method != null) {
            anotations = method.getDeclaredAnnotations();
        }
        return anotations;
    }

}
