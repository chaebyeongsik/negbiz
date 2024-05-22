/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.io.Serializable;

/**
 * 메뉴관리의 동일 메뉴 URL 목록 정보 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 31.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MenuUrlCacheVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 6994856124770240029L;

    /** 메뉴코드 */
    private Integer menuSn;
    /** 메뉴명 */
    private String menuNm;
    /** 최상위메뉴코드 */
    private Integer hghrkMenuEngNm;
    /** 상위메뉴 코드 */
    private Integer upMenuSn;
    /** 메뉴 URL */
    private String menuUrlAddr;
    /** 메뉴 파라미터 1 */
    private String prmttNm1;
    /** 메뉴 파라미터 1 */
    private String prmttNm2;
    /** 메뉴 파라미터 1 */
    private String prmttNm3;

    /**
     * Integer menuSn을 반환
     * 
     * @return Integer menuSn
     */
    public Integer getMenuSn() {
        return menuSn;
    }

    /**
     * menuSn을 설정
     * 
     * @param menuSn 을(를) Integer menuSn로 설정
     */
    public void setMenuSn(Integer menuSn) {
        this.menuSn = menuSn;
    }

    /**
     * String menuNm을 반환
     * 
     * @return String menuNm
     */
    public String getMenuNm() {
        return menuNm;
    }

    /**
     * menuNm을 설정
     * 
     * @param menuNm 을(를) String menuNm로 설정
     */
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    /**
     * Integer hghrkMenuEngNm을 반환
     * 
     * @return Integer hghrkMenuEngNm
     */
    public Integer getHghrkMenuEngNm() {
        return hghrkMenuEngNm;
    }

    /**
     * hghrkMenuEngNm을 설정
     * 
     * @param hghrkMenuEngNm 을(를) Integer hghrkMenuEngNm로 설정
     */
    public void setHghrkMenuEngNm(Integer hghrkMenuEngNm) {
        this.hghrkMenuEngNm = hghrkMenuEngNm;

    }

    /**
     * Integer upMenuSn을 반환
     * 
     * @return Integer upMenuSn
     */
    public Integer getUpMenuSn() {
        return upMenuSn;
    }

    /**
     * upMenuSn을 설정
     * 
     * @param upMenuSn 을(를) Integer upMenuSn로 설정
     */
    public void setUpMenuSn(Integer upMenuSn) {
        this.upMenuSn = upMenuSn;
    }

    /**
     * String menuUrlAddr을 반환
     * 
     * @return String menuUrlAddr
     */
    public String getMenuUrlAddr() {
        return menuUrlAddr;
    }

    /**
     * menuUrlAddr을 설정
     * 
     * @param menuUrlAddr 을(를) String menuUrlAddr로 설정
     */
    public void setMenuUrlAddr(String menuUrlAddr) {
        this.menuUrlAddr = menuUrlAddr;
    }

    /**
     * String prmttNm1을 반환
     * 
     * @return String prmttNm1
     */
    public String getPrmttNm1() {
        return prmttNm1;
    }

    /**
     * prmttNm1을 설정
     * 
     * @param prmttNm1 을(를) String prmttNm1로 설정
     */
    public void setPrmttNm1(String prmttNm1) {
        this.prmttNm1 = prmttNm1;
    }

    /**
     * String prmttNm2을 반환
     * 
     * @return String prmttNm2
     */
    public String getPrmttNm2() {
        return prmttNm2;
    }

    /**
     * prmttNm2을 설정
     * 
     * @param prmttNm2 을(를) String prmttNm2로 설정
     */
    public void setPrmttNm2(String prmttNm2) {
        this.prmttNm2 = prmttNm2;
    }

    /**
     * String prmttNm3을 반환
     * 
     * @return String prmttNm3
     */
    public String getPrmttNm3() {
        return prmttNm3;
    }

    /**
     * prmttNm3을 설정
     * 
     * @param prmttNm3 을(를) String prmttNm3로 설정
     */
    public void setPrmttNm3(String prmttNm3) {
        this.prmttNm3 = prmttNm3;
    }

}
