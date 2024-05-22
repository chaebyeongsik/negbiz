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
 * 사용자등급 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-27.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsUserGradCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -6984863026831825097L;

    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 회원등급코드 */
    private String userGrdCdId;
    /** 회원등급명 */
    private String userGrdNm;
    /** 회원등급설명 */
    private String userGrdExpln;
    /** 등급보유여부 */
    private Boolean hasGrad;
    /** 등록자ID */
    private String rgtrId;
    /** 등록일 */
    private String regDt;

    /**
     * 도메인코드 반환
     * 
     * @return Integer siteSn
     */
    public Integer getSiteSn() {
        return siteSn;
    }

    /**
     * 도메인코드 설정
     * 
     * @param siteSn 을(를) Integer siteSn로 설정
     */
    public void setSiteSn(Integer siteSn) {
        this.siteSn = siteSn;
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
     * 사용자메뉴코드 설정
     * 
     * @param userMenuEngNm 을(를) String userMenuEngNm로 설정
     */
    public void setUserMenuEngNm(String userMenuEngNm) {
        this.userMenuEngNm = userMenuEngNm;
    }

    /**
     * 회원등급코드 설정
     * 
     * @param userGrdCdId을(를) String userGrdCdId로 설정
     */
    public void setUserGrdCdId(String userGrdCdId) {
        this.userGrdCdId = userGrdCdId;
    }

    /**
     * 회원등급코드 반환
     * 
     * @return String userGrdCdId
     */
    public String getUserGrdCdId() {
        return userGrdCdId;
    }

    /**
     * 회원등급명 설정
     * 
     * @param userGrdNm을(를) String userGrdNm로 설정
     */
    public void setUserGrdNm(String userGrdNm) {
        this.userGrdNm = userGrdNm;
    }

    /**
     * 회원등급명 반환
     * 
     * @return String userGrdNm
     */
    public String getUserGrdNm() {
        return userGrdNm;
    }

    /**
     * 회원등급설명 설정
     * 
     * @param userGrdExpln을(를) String userGrdExpln로 설정
     */
    public void setUserGrdExpln(String userGrdExpln) {
        this.userGrdExpln = userGrdExpln;
    }

    /**
     * 회원등급설명 반환
     * 
     * @return String userGrdExpln
     */
    public String getUserGrdExpln() {
        return userGrdExpln;
    }

    /**
     * Boolean hasGrad을 반환
     * 
     * @return Boolean hasGrad
     */
    public Boolean getHasGrad() {
        return hasGrad;
    }

    /**
     * hasGrad을 설정
     * 
     * @param hasGrad 을(를) Boolean hasGrad로 설정
     */
    public void setHasGrad(Boolean hasGrad) {
        this.hasGrad = hasGrad;
    }

    /**
     * String rgtrId을 반환
     * 
     * @return String rgtrId
     */
    public String getRgtrId() {
        return rgtrId;
    }

    /**
     * rgtrId을 설정
     * 
     * @param rgtrId 을(를) String rgtrId로 설정
     */
    public void setRgtrId(String rgtrId) {
        this.rgtrId = rgtrId;
    }

    /**
     * String regDt을 반환
     * 
     * @return String regDt
     */
    public String getRegDt() {
        return regDt;
    }

    /**
     * regDt을 설정
     * 
     * @param regDt 을(를) String regDt로 설정
     */
    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

}
