/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support;

/**
 * 코드관리에 사용되는 로그유형 상수값
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-03-27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public enum LOG_TYPE {

    INSERT("I"), UPDATE("U"), DELETE("D");

    String cdId;

    LOG_TYPE(String _cdId) {
        this.cdId = _cdId;
    }

    public String getCdId() {
        return this.cdId;
    }
}
