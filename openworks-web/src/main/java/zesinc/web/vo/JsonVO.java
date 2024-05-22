/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

/**
 * JSON 형식의 단순 결과 메시지를 생성 전달하기 위한 VO
 * <p />
 * Method Chain 지원
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class JsonVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -4606892258945532890L;

    /** 성공여부 결과 */
    private Boolean result;

    /** 프로세스 결과값 */
    private Object value;

    /** 결과 메시지 */
    private String message;

    /**
     * Boolean result을 반환
     * 
     * @return Boolean result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * result을 설정
     * 
     * @param result 을(를) Boolean result로 설정
     */
    public JsonVO setResult(Boolean result) {
        this.result = result;
        return this;
    }

    /**
     * Object value을 반환
     * 
     * @return Object value
     */
    public Object getValue() {
        return value;
    }

    /**
     * value을 설정
     * 
     * @param value 을(를) Object value로 설정
     */
    public JsonVO setValue(Object value) {
        this.value = value;
        return this;
    }

    /**
     * String message을 반환
     * 
     * @return String message
     */
    public String getMessage() {
        return message;
    }

    /**
     * message을 설정
     * 
     * @param message 을(를) String message로 설정
     */
    public JsonVO setMessage(String message) {
        this.message = message;
        return this;
    }
}
