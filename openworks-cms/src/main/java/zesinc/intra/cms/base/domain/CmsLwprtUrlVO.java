/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base.domain;

import zesinc.web.vo.BaseVO;

/**
 * 메뉴의 하위 URL 그룹 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 8. 7.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsLwprtUrlVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -4378576379198959752L;

    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 하위메뉴URL */
    private String lwprtMenuUrlAddr;

    /**
     * Integer siteSn을 반환
     * 
     * @return Integer siteSn
     */
    public Integer getSiteSn() {
        return siteSn;
    }

    /**
     * siteSn을 설정
     * 
     * @param siteSn 을(를) Integer siteSn로 설정
     */
    public void setSiteSn(Integer siteSn) {
        this.siteSn = siteSn;
    }

    /**
     * String userMenuEngNm을 반환
     * 
     * @return String userMenuEngNm
     */
    public String getUserMenuEngNm() {
        return userMenuEngNm;
    }

    /**
     * userMenuEngNm을 설정
     * 
     * @param userMenuEngNm 을(를) String userMenuEngNm로 설정
     */
    public void setUserMenuEngNm(String userMenuEngNm) {
        this.userMenuEngNm = userMenuEngNm;
    }

    /**
     * 하위메뉴URL 반환
     * 
     * @return String lwprtMenuUrlAddr
     */
    public String getLwprtMenuUrlAddr() {
        return lwprtMenuUrlAddr;
    }

    /**
     * 하위메뉴URL 설정
     * 
     * @param lwprtMenuUrlAddr 을(를) String lwprtMenuUrlAddr로 설정
     */
    public void setLwprtMenuUrlAddr(String lwprtMenuUrlAddr) {
        this.lwprtMenuUrlAddr = lwprtMenuUrlAddr;
    }

}
