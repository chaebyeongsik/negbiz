/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate.validator;

import zesinc.web.utils.MessageUtil;

/**
 * <code>Validator</code>의 공통 부모 클레스 이며, <code>Validator</code> 인터페이스 구현체
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
public abstract class AbstractValidator implements Validator {

    /** 검증 대상 VO 객체 */
    protected Object currentVo;

    /** <code>Validator</code> 명 */
    protected String validatorName;

    /** 검증 결과 오류 메시지 */
    protected String message;

    /** 포멧팅용 데이터 */
    protected String[] validData = null;

    @Override
    public Validator setObject(Object currentVo) {

        this.currentVo = currentVo;

        return this;
    }

    @Override
    public boolean isValid(Object value) {

        return doValidate(value);
    }

    @Override
    public String getValidatorName() {
        return this.validatorName;
    }

    @Override
    public Validator setValidatorName(String validatorName) {
        this.validatorName = validatorName;
        return this;
    }

    @Override
    public String getMessgse() {

        String returnMsg = "";
        String msgKey = "validator." + this.message;
        String[] messages = getValidData();

        if(null != messages) {
            returnMsg = MessageUtil.getMessage(msgKey, messages);
        } else {
            returnMsg = MessageUtil.getMessage(msgKey);
        }

        return returnMsg;
    }

    @Override
    public String[] getValidData() {
        return this.validData;
    }

    /**
     * 검증 실행
     * 
     * @param value 검증 대상 값
     * @return 검증결과 이상없으면 true, 이외 false
     */
    protected abstract <T> boolean doValidate(T value);

}
