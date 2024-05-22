/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator;

import java.lang.annotation.Annotation;

/**
 * 검증을 수행하기 위한 메소드를 정의한다.
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
public interface Validator {

    /**
     * 검증을 수행
     * 
     * @return
     */
    boolean isValid(Object value);

    /**
     * <code>Validator</code> 명을 반환
     * 
     * @return
     */
    String getValidatorName();

    /**
     * <code>Validator</code> 명을 설정
     * 
     * @return
     */
    Validator setValidatorName(String validatorName);

    /**
     * 오류 메시지 반환
     * 
     * @return
     */
    String getMessgse();

    /**
     * 메시지 포멧용 데이터
     * 
     * @return
     */
    String[] getValidData();

    /**
     * 검증 대상 VO 객체
     * 
     * @param obj
     */
    Validator setObject(Object currentVo);

    /**
     * 검증 데이터를 가진 Annotatin을 설정
     * 
     * @param anno
     * @return
     */
    Validator setAnnotation(Annotation annotation);
}
