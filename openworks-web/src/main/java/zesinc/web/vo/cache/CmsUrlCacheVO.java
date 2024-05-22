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
 * 사용자메뉴 URL 메핑 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsUrlCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1605431386762795225L;

    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 메뉴명 */
    private String menuNm;
    /** 최상위메뉴코드 */
    private String hghrkMenuEngNm;
    /** 부모메뉴코드 */
    private String upMenuEngNm;
    /** 사용자메뉴URL */
    private String userMenuUrlAddr;

    /** 전체URL */
    private String fullUrl;

    /**
     * 도메인코드 설정
     * 
     * @param siteSn을(를) Integer siteSn로 설정
     */
    public void setSiteSn(Integer siteSn) {
        this.siteSn = siteSn;
    }

    /**
     * 도메인코드 반환
     * 
     * @return Integer siteSn
     */
    public Integer getSiteSn() {
        return siteSn;
    }

    /**
     * 사용자메뉴코드 설정
     * 
     * @param userMenuEngNm을(를) String userMenuEngNm로 설정
     */
    public void setUserMenuEngNm(String userMenuEngNm) {
        this.userMenuEngNm = userMenuEngNm;
    }

    /**
     * 사용자메뉴코드 반환
     * 
     * @return String userMenuEngNm
     */
    public String getUserMenuEngNm() {
        return userMenuEngNm;
    }

    /**
     * 메뉴명 설정
     * 
     * @param menuNm을(를) String menuNm로 설정
     */
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    /**
     * 메뉴명 반환
     * 
     * @return String menuNm
     */
    public String getMenuNm() {
        return menuNm;
    }

    /**
     * 최상위메뉴 반환
     * 
     * @return String hghrkMenuEngNm
     */
    public String getHghrkMenuEngNm() {
        return hghrkMenuEngNm;
    }

    /**
     * 최상위메뉴 설정
     * 
     * @param hghrkMenuEngNm 을(를) String hghrkMenuEngNm로 설정
     */
    public void setHghrkMenuEngNm(String hghrkMenuEngNm) {
        this.hghrkMenuEngNm = hghrkMenuEngNm;
    }

    /**
     * 부모메뉴코드 설정
     * 
     * @param upMenuEngNm을(를) String upMenuEngNm로 설정
     */
    public void setUpMenuEngNm(String upMenuEngNm) {
        this.upMenuEngNm = upMenuEngNm;
    }

    /**
     * 부모메뉴코드 반환
     * 
     * @return String upMenuEngNm
     */
    public String getUpMenuEngNm() {
        return upMenuEngNm;
    }

    /**
     * 사용자메뉴URL 설정
     * 
     * @param userMenuUrlAddr을(를) String userMenuUrlAddr로 설정
     */
    public void setUserMenuUrlAddr(String userMenuUrlAddr) {
        this.userMenuUrlAddr = userMenuUrlAddr;
    }

    /**
     * 사용자메뉴URL 반환
     * 
     * @return String userMenuUrlAddr
     */
    public String getUserMenuUrlAddr() {
        return userMenuUrlAddr;
    }

    /**
     * 전체URL 반환
     * 
     * @return String fullUrl
     */
    public String getFullUrl() {
        return fullUrl;
    }

    /**
     * 전체URL 설정
     * 
     * @param fullUrl 을(를) String fullUrl로 설정
     */
    public void setFullUrl(String fullUrl) {
        this.fullUrl = fullUrl;
    }

}
