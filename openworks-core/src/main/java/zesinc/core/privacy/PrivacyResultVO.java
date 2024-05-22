/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy;

import java.util.ArrayList;
import java.util.List;

/**
 * 개인정보 필터링 후 결과를 저장하는 객체
 * PrivacyResultVO.getResult()
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 6.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see PrivacyResultVO.isResult()
 */
public class PrivacyResultVO {

    /** 필터링대상 검출 여부 */
    private boolean result = false;

    /** 필터링대상 검출 목록 */
    private List<String> filterList = new ArrayList<String>();

    /**
     * 필터링 대상 검출 여부 반환
     * 
     * @return boolean result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * 필터링 대상 검출 여부 설정
     * 
     * @param result 을(를) boolean result로 설정
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * 필터링 된 대상 문자열 목록 반환
     * 
     * @return List<String> filterList
     */
    public List<String> getFilterList() {
        return filterList;
    }

    /**
     * 필터링 된 대상 문자열 목록 설정
     * 
     * @param filterList 을(를) List<String> filterList로 설정
     */
    public void setFilterList(List<String> filterList) {
        this.filterList = filterList;
    }

    /**
     * 필터링된 문자열을 추가
     * 
     * @param addStr
     */
    public void addFileterList(String addStr) {
        this.filterList.add(addStr);
    }

}
