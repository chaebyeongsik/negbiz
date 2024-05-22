/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.util.ArrayList;
import java.util.List;

import zesinc.web.vo.BaseVO;

/**
 * 메뉴 목록을 제공하기 위한 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 8.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MenuCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -6145346463251025011L;

    /** 메뉴코드 */
    private Integer menuSn;
    /** 메뉴명 */
    private String menuNm;
    /** 최상위메뉴코드 */
    private Integer hghrkMenuEngNm;
    /** 상위메뉴코드 */
    private Integer upMenuSn;
    /** 정렬순서 */
    private Integer sortSn;
    /** 도움말 */
    private String menuDtlExpln;
    /** 파라미터1 */
    private String prmttNm1;
    /** 파라미터2 */
    private String prmttNm2;
    /** 파라미터3 */
    private String prmttNm3;
    /** 대표URL(없을 수 있음) */
    private String mainUrl;
    /** 자식메뉴 목록 */
    private List<MenuCacheVO> childList;
    /** 동일메뉴 URL 목록 */
    private List<String> menuUrlList;
    /** 표시여부 */
    private String indctYn;

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
     * Integer sortSn을 반환
     * 
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * sortSn을 설정
     * 
     * @param sortSn 을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * String menuDtlExpln을 반환
     * 
     * @return String menuDtlExpln
     */
    public String getMenuDtlExpln() {
        return menuDtlExpln;
    }

    /**
     * menuDtlExpln을 설정
     * 
     * @param menuDtlExpln 을(를) String menuDtlExpln로 설정
     */
    public void setMenuDtlExpln(String menuDtlExpln) {
        this.menuDtlExpln = menuDtlExpln;
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

    /**
     * String mainUrl을 반환
     * 
     * @return String mainUrl
     */
    public String getMainUrl() {
        return mainUrl;
    }

    /**
     * mainUrl을 설정
     * 
     * @param mainUrl 을(를) String mainUrl로 설정
     */
    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    /**
     * List<MenuCacheVO> childList을 반환
     * 
     * @return List<MenuCacheVO> childList
     */
    public List<MenuCacheVO> getChildList() {
        return childList;
    }

    /**
     * 자식메뉴로 추가
     * 
     * @param childList 을(를) List<MenuCacheVO> childList로 설정
     */
    public void addChildList(MenuCacheVO menuCacheVo) {
        if(this.childList == null) {
            this.childList = new ArrayList<MenuCacheVO>();
        }
        this.childList.add(menuCacheVo);
    }

    /**
     * childList을 설정
     * 
     * @param childList 을(를) List<MenuCacheVO> childList로 설정
     */
    public void setChildList(List<MenuCacheVO> childList) {
        this.childList = childList;
    }

    /**
     * List<String> menuUrlList을 반환
     * 
     * @return List<String> menuUrlList
     */
    public List<String> getMenuUrlList() {
        return menuUrlList;
    }

    /**
     * menuUrlList을 설정
     * 
     * @param menuUrlList 을(를) List<String> menuUrlList로 설정
     */
    public void setMenuUrlList(List<String> menuUrlList) {
        this.menuUrlList = menuUrlList;
    }

    /**
     * String indctYn을 반환
     * 
     * @return String indctYn
     */
    public String getIndctYn() {
        return indctYn;
    }

    /**
     * indctYn을 설정
     * 
     * @param indctYn 을(를) String indctYn로 설정
     */
    public void setIndctYn(String indctYn) {
        this.indctYn = indctYn;
    }

}
