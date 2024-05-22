/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import zesinc.web.vo.BaseVO;


/**
 *<pre>
 *<< 개정이력(Modification Information) >>
 *   
 *    수정일       수정자   수정내용
 *--------------  --------  -------------------------------
 * 2015. 4. 14.    박수정   최초작성
 *</pre>
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class HpcmCacheVO extends BaseVO {

    private static final long serialVersionUID = -5526597533614416098L;

    /** 순번 */
    private Integer regSn;

    /** 메뉴코드 */
    private Integer menuSn;

    /** 도움말내용 */
    private String docCn;

    /**
     * Integer regSn을 반환
     * @return Integer regSn
     */
    public Integer getRegSn() {
        return regSn;
    }

    /**
     * regSn을 설정
     * @param regSn 을(를) Integer regSn로 설정
     */
    public void setRegSn(Integer regSn) {
        this.regSn = regSn;
    }

    /**
     * Integer menuSn을 반환
     * @return Integer menuSn
     */
    public Integer getMenuSn() {
        return menuSn;
    }

    /**
     * menuSn을 설정
     * @param menuSn 을(를) Integer menuSn로 설정
     */
    public void setMenuSn(Integer menuSn) {
        this.menuSn = menuSn;
    }

    /**
     * String docCn을 반환
     * @return String docCn
     */
    public String getDocCn() {
        return docCn;
    }

    /**
     * docCn을 설정
     * @param docCn 을(를) String docCn로 설정
     */
    public void setDocCn(String docCn) {
        this.docCn = docCn;
    }

}
