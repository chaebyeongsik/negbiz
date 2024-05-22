/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support;

/**
 * 컨텐츠 승인 절차 코드 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public enum ConfmSttus {

    C1010("작성중"), C1020("요청중"), C1030("반려"), C1040("승인"), C1050("자동승인");

    ConfmSttus(String name) {
        this.name = name;
    }

    /** 한글 명칭 */
    private String name;

    /**
     * 한글명칭 반환
     * 
     * @return
     */
    public String getName() {

        return this.name;
    }
}
