/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.meta.domain;

import java.util.Date;

import zesinc.web.validate.annotation.marker.Digits;
import zesinc.web.validate.annotation.marker.MaxLength;
import zesinc.web.validate.annotation.marker.Required;
import zesinc.web.vo.BaseVO;

/**
 * 사용자메뉴메타 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-01.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsMetaVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -6333402428552604197L;
    /** 도메인코드 */
    @Required
    @Digits
    private Integer siteSn;

    /** 도메인명 */
    private String siteNm;

    /** 사용자메뉴코드 */
    @Required
    @MaxLength(max = 100)
    private String userMenuEngNm;

    /** 사용자메뉴명 */
    private String menuNm;

    /** 키워드 */
    @MaxLength(max = 300)
    private String userMenuSrchNm;

    /** 설명 */
    @MaxLength(max = 4000)
    private String userMenuExpln;

    /** 사용자메뉴로봇 */
    @MaxLength(max = 100)
    private String siteSrchNm;

    /** 메타태그 */
    @MaxLength(max = 4000)
    private String menuTagCn;

    /** 저작권사용여부 */
    @Required
    private String cprgtUseYn;

    /** 저작권유형 */
    @MaxLength(max = 40)
    private String cprgtTypeNm;

    /** 저작권내용 */
    @MaxLength(max = 4000)
    private String cprgtCn;

    /** 저작자표시 */
    @MaxLength(max = 5)
    private String authrIndict;

    /** 영리목적허용 */
    @MaxLength(max = 5)
    private String prftmkPurpsPerm;

    /** 컨텐츠변경허용 */
    @MaxLength(max = 5)
    private String cntntsChangePerm;

    /** 등록자ID */
    @MaxLength(max = 20)
    private String rgtrId;

    /** 등록자명 */
    private String rgtrNm;

    /** 등록일시 */
    @Required
    private Date regDt;

    /** 수정자ID */
    @MaxLength(max = 20)
    private String mdfrId;

    /** 수정자명 */
    private String updusrNm;

    /** 수정일시 */
    private Date updtDt;

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
     * 도메인명 설정
     * 
     * @param siteNm을(를) String siteNm로 설정
     */
    public void setSiteNm(String siteNm) {
        this.siteNm = siteNm;
    }

    /**
     * 도메인명 반환
     * 
     * @return String siteNm
     */
    public String getSiteNm() {
        return siteNm;
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
     * 사용자메뉴명 설정
     * 
     * @param menuNm을(를) String menuNm로 설정
     */
    public void setMenuNm(String menuNm) {
        this.menuNm = menuNm;
    }

    /**
     * 사용자메뉴명 반환
     * 
     * @return String menuNm
     */
    public String getMenuNm() {
        return menuNm;
    }

    /**
     * 키워드 설정
     * 
     * @param userMenuSrchNm을(를) String userMenuSrchNm로 설정
     */
    public void setUserMenuSrchNm(String userMenuSrchNm) {
        this.userMenuSrchNm = userMenuSrchNm;
    }

    /**
     * 키워드 반환
     * 
     * @return String userMenuSrchNm
     */
    public String getUserMenuSrchNm() {
        return userMenuSrchNm;
    }

    /**
     * 설명 설정
     * 
     * @param userMenuExpln을(를) String userMenuExpln로 설정
     */
    public void setUserMenuExpln(String userMenuExpln) {
        this.userMenuExpln = userMenuExpln;
    }

    /**
     * 설명 반환
     * 
     * @return String userMenuExpln
     */
    public String getUserMenuExpln() {
        return userMenuExpln;
    }

    /**
     * 사용자메뉴로봇 설정
     * 
     * @param siteSrchNm을(를) String siteSrchNm로 설정
     */
    public void setSiteSrchNm(String siteSrchNm) {
        this.siteSrchNm = siteSrchNm;
    }

    /**
     * 사용자메뉴로봇 반환
     * 
     * @return String siteSrchNm
     */
    public String getSiteSrchNm() {
        return siteSrchNm;
    }

    /**
     * 메타태그 반환
     * 
     * @return String menuTagCn
     */
    public String getMenuTagCn() {
        return menuTagCn;
    }

    /**
     * 메타태그 설정
     * 
     * @param menuTagCn 을(를) String menuTagCn로 설정
     */
    public void setMenuTagCn(String menuTagCn) {
        this.menuTagCn = menuTagCn;
    }

    /**
     * 저작권사용여부 설정
     * 
     * @param cprgtUseYn을(를) String cprgtUseYn로 설정
     */
    public void setCprgtUseYn(String cprgtUseYn) {
        this.cprgtUseYn = cprgtUseYn;
    }

    /**
     * 저작권사용여부 반환
     * 
     * @return String cprgtUseYn
     */
    public String getCprgtUseYn() {
        return cprgtUseYn;
    }

    /**
     * String cprgtTypeNm을 반환
     * 
     * @return String cprgtTypeNm
     */
    public String getCprgtTypeNm() {
        return cprgtTypeNm;
    }

    /**
     * cprgtTypeNm을 설정
     * 
     * @param cprgtTypeNm 을(를) String cprgtTypeNm로 설정
     */
    public void setCprgtTypeNm(String cprgtTypeNm) {
        this.cprgtTypeNm = cprgtTypeNm;
    }

    /**
     * String cprgtCn을 반환
     * 
     * @return String cprgtCn
     */
    public String getCprgtCn() {
        return cprgtCn;
    }

    /**
     * cprgtCn을 설정
     * 
     * @param cprgtCn 을(를) String cprgtCn로 설정
     */
    public void setCprgtCn(String cprgtCn) {
        this.cprgtCn = cprgtCn;
    }

    /**
     * 저작자표시 설정
     * 
     * @param authrIndict을(를) String authrIndict로 설정
     */
    public void setAuthrIndict(String authrIndict) {
        this.authrIndict = authrIndict;
    }

    /**
     * 저작자표시 반환
     * 
     * @return String authrIndict
     */
    public String getAuthrIndict() {
        return authrIndict;
    }

    /**
     * 영리목적허용 설정
     * 
     * @param prftmkPurpsPerm을(를) String prftmkPurpsPerm로 설정
     */
    public void setPrftmkPurpsPerm(String prftmkPurpsPerm) {
        this.prftmkPurpsPerm = prftmkPurpsPerm;
    }

    /**
     * 영리목적허용 반환
     * 
     * @return String prftmkPurpsPerm
     */
    public String getPrftmkPurpsPerm() {
        return prftmkPurpsPerm;
    }

    /**
     * 컨텐츠변경허용 설정
     * 
     * @param cntntsChangePerm을(를) String cntntsChangePerm로 설정
     */
    public void setCntntsChangePerm(String cntntsChangePerm) {
        this.cntntsChangePerm = cntntsChangePerm;
    }

    /**
     * 컨텐츠변경허용 반환
     * 
     * @return String cntntsChangePerm
     */
    public String getCntntsChangePerm() {
        return cntntsChangePerm;
    }

    /**
     * 등록자ID 설정
     * 
     * @param rgtrId을(를) String rgtrId로 설정
     */
    public void setRgtrId(String rgtrId) {
        this.rgtrId = rgtrId;
    }

    /**
     * 등록자ID 반환
     * 
     * @return String rgtrId
     */
    public String getRgtrId() {
        return rgtrId;
    }

    /**
     * 등록자명 설정
     * 
     * @param rgtrNm을(를) String rgtrNm로 설정
     */
    public void setRgtrNm(String rgtrNm) {
        this.rgtrNm = rgtrNm;
    }

    /**
     * 등록자명 반환
     * 
     * @return String rgtrNm
     */
    public String getRgtrNm() {
        return rgtrNm;
    }

    /**
     * 등록일시 설정
     * 
     * @param regDt을(를) Date regDt로 설정
     */
    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

    /**
     * 등록일시 반환
     * 
     * @return Date regDt
     */
    public Date getRegDt() {
        return regDt;
    }

    /**
     * 수정자ID 설정
     * 
     * @param mdfrId을(를) String mdfrId로 설정
     */
    public void setMdfrId(String mdfrId) {
        this.mdfrId = mdfrId;
    }

    /**
     * 수정자ID 반환
     * 
     * @return String mdfrId
     */
    public String getMdfrId() {
        return mdfrId;
    }

    /**
     * 수정자명 설정
     * 
     * @param updusrNm을(를) String updusrNm로 설정
     */
    public void setUpdusrNm(String updusrNm) {
        this.updusrNm = updusrNm;
    }

    /**
     * 수정자명 반환
     * 
     * @return String updusrNm
     */
    public String getUpdusrNm() {
        return updusrNm;
    }

    /**
     * 수정일시 설정
     * 
     * @param updtDt을(를) Date updtDt로 설정
     */
    public void setUpdtDt(Date updtDt) {
        this.updtDt = updtDt;
    }

    /**
     * 수정일시 반환
     * 
     * @return Date updtDt
     */
    public Date getUpdtDt() {
        return updtDt;
    }

}
