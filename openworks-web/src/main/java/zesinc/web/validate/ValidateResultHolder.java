/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.validate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import zesinc.web.validate.validator.ValidateResult;

/**
 * 전체 검증 결과를 저장한다.
 * <p />
 * 검증 유형(Anotation Type), 검증 대상 필드와 검증 결과 및 오류 메시지를 저장한다.
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
 * @see ValidateResult
 */
public class ValidateResultHolder {

    /** 전체 검증 결과 */
    private Boolean totalValid = true;

    /** 검증결과 전체 저장 */
    private Map<String, ValidateResult> result = new LinkedHashMap<String, ValidateResult>();

    /**
     * 검증 결과 추가
     * 
     * @param result
     */
    public void addResult(String fieldName, ValidateResult result) {

        this.result.put(fieldName, result);
    }

    /**
     * 검증 결과 전체 리턴
     * 
     * @return
     */
    public Map<String, ValidateResult> getResult() {
        return this.result;
    }

    /**
     * 검증 결과 전체 리턴
     * 
     * @return
     */
    public List<ValidateResult> getResults() {
        List<ValidateResult> resultList = new ArrayList<ValidateResult>();

        Iterator<String> it = this.result.keySet().iterator();
        while(it.hasNext()) {
            resultList.add(this.result.get(it.next()));
        }

        return resultList;
    }

    /**
     * 검증 결과 항목별 리턴
     * 
     * @return
     */
    public ValidateResult getResult(String fieldName) {
        return this.result.get(fieldName);
    }

    /**
     * 최종 검증결과
     * 
     * @return 하나라도 오류가 있으면 <code>false</code>, 이외 <code>true</code>
     */
    public boolean isValid() {
        return this.totalValid;
    }

    /**
     * 최종 검증결과 설정. 현재까지의 검증 결과가 false 가 아닌 경우만 설정
     * 
     * @param totalValid
     */
    public void setValid(boolean totalValid) {
        if(this.totalValid == true && totalValid == false) {
            this.totalValid = totalValid;
        }
    }

}
