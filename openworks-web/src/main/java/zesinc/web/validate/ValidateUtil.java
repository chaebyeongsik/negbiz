/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.AnnotationUtil;
import zesinc.core.lang.FieldUtil;
import zesinc.core.lang.Validate;
import zesinc.web.validate.annotation.ValidatorInfo;
import zesinc.web.validate.validator.ValidatorFactory;

/**
 * 검증 툴을 이용하여 검증을 수행하고 결과를 반환한다.
 * <p />
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 2.    방기배   신규 추가
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ValidateUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ValidateUtil.class);

    /**
     * 검증 수행 및 결과 반환
     * 
     * @param vo
     * @return
     */
    public static ValidateResultHolder validate(Object vo) {

        return doValidate(vo);
    }

    /**
     * 개별 필드 검증 수행 및 결과 반환
     * 
     * @param vo
     * @return
     */
    public static ValidateResultHolder validateField(Object vo, String fieldName) {

        return doFiledValidate(vo, fieldName);
    }

    /**
     * 유연성 확보를 위한 메소드
     * <p />
     * VO 객체 전체 검증 수행 및 결과 반환
     * 
     * @param vo
     * @return
     */
    private static ValidateResultHolder doValidate(Object vo) {

        // 전체 검증 결과 설정
        ValidateResultHolder validateHolder = new ValidateResultHolder();

        Field[] fields = FieldUtil.getFields(vo.getClass());

        if(Validate.isNotEmpty(fields)) {
            for(Field field : fields) {
                if(field.isAccessible()) {
                    doFiledValidate(validateHolder, vo, field);
                }
            }
        }
        return validateHolder;
    }

    /**
     * 개별 필드(파라미터) 검증결과 반환
     * 
     * @param vo
     * @param fieldName
     * @return
     */
    public static ValidateResultHolder doFiledValidate(Object vo, String fieldName) {

        // 전체 검증 결과 설정
        ValidateResultHolder validateHolder = new ValidateResultHolder();
        Field field = FieldUtil.getField(vo.getClass(), fieldName);

        if(field != null) {
            if(field.isAccessible()) {
                doFiledValidate(validateHolder, vo, field);
            }
        }

        return validateHolder;
    }

    /**
     * 개별 필드(파라미터) 검증결과 반환
     * 
     * @param vo
     * @param fieldName
     * @param annotations 검증유형의 Annotation
     * @return
     */
    public static ValidateResultHolder doFiledValidate(Object vo, String fieldName, Annotation[] annotations) {

        // 전체 검증 결과 설정
        ValidateResultHolder validateHolder = new ValidateResultHolder();
        Field field = FieldUtil.getField(vo.getClass(), fieldName);

        if(field != null) {
            if(field.isAccessible()) {
                setValidateResults(validateHolder, vo, field, annotations);
            }
        }

        return validateHolder;
    }

    /**
     * 대상을 검증 후 결과를 담는다
     * 
     * @param validateHolder 결과 객체
     * @param vo 대상 Object
     * @param field 대상 field
     */
    public static void doFiledValidate(ValidateResultHolder validateHolder, Object vo, Field field) {

        Annotation[] annotations = AnnotationUtil.getAnnotationsWidthFieldName(vo.getClass(), field.getName());

        setValidateResults(validateHolder, vo, field, annotations);
    }

    /**
     * 대상을 검증 후 결과를 담는다
     * 
     * @param validateHolder 결과 객체
     * @param vo 대상 Object
     * @param field 대상 field
     * @param annotations 검증유형의 Annotation
     */
    public static void doFiledValidate(ValidateResultHolder validateHolder, Object vo, String fieldName, Annotation[] annotations) {

        Field field = FieldUtil.getField(vo.getClass(), fieldName);

        if(field != null) {
            if(field.isAccessible()) {
                setValidateResults(validateHolder, vo, field, annotations);
            }
        }
    }

    /**
     * 대상을 검증 후 결과를 담는다
     * 
     * @param validateHolder 결과 객체
     * @param vo 대상 Object
     * @param field 대상 field
     * @param annotations 검증유형의 Annotation
     */
    private static void setValidateResults(ValidateResultHolder validateHolder, Object vo, Field field, Annotation[] annotations) {

        if(annotations != null) {
            ValidateGroup group = new ValidateGroup();
            Object fieldValue = null;

            try {
                fieldValue = field.get(vo);
            } catch (Exception e) {
                fieldValue = "";
                logger.debug("{} 필드의 값을 읽을 수 없습니다. {}", field.getName(), e.getStackTrace());
            }
            for(Annotation anno : annotations) {
                if(isValidatorInfo(anno)) {
                    group.add(ValidatorFactory.getInstance(anno, vo));
                }
            }
            if(group.get().size() > 0) {
                boolean isValid = group.doValidte(field.getName(), fieldValue).isValid();
                if(!isValid) {
                    validateHolder.setValid(isValid);
                    logger.debug("{} 검증실패 상세내용 : {}", field.getName(), group.getResult());
                }

                validateHolder.addResult(field.getName(), group.getResult());
            }
        }
    }

    /**
     * 검증용 Field 인지 확인
     * 
     * @param anno
     * @return
     */
    public static boolean isValidatorInfo(Annotation anno) {

        return (anno.annotationType().getAnnotation(ValidatorInfo.class) != null);
    }
}
