/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.evl.domain;

import java.util.Date;
import java.util.List;

import zesinc.intra.cms.base.domain.CmsOrgVO;
import zesinc.web.vo.PageVO;

/**
 * 사용자메뉴평가 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-09.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsEvlVO extends PageVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -8746472577377026753L;

    /** 도메인코드 */
    private Integer siteSn;
    /** 도메인명 */
    private String siteNm;
    /** 도메인설명 */
    private String siteExpln;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 메뉴명 */
    private String menuNm;
    /** 메뉴경로 */
    private String menuPathNm;
    /** 권한유형 */
    private String authrtGroupNm;
    /** 권한부서코드 */
    private String authrtdDeptCdId;
    /** 권한부서명 */
    private String authorDeptNm;
    /** 권한담당자목록 */
    private List<CmsOrgVO> authorChargerList;
    /** 항목번호 */
    private Integer artclSn;
    /** 총점 */
    private Integer sumScr;
    /** 총응답수 */
    private Integer wholRspnsCnt;
    /** 총평균 */
    private Float allAvg;
    /** 등록일자 */
    private String regYmd;
    /** 순번 */
    private Integer regSn;
    /** 의견글내용 */
    private String opnnCn;
    /** 등록IP */
    private String rgtrIpAddr;
    /** 등록자명 */
    private String rgtrNm;
    /** 등록자ID */
    private String rgtrId;
    /** 등록일시 */
    private Date regDt;

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
     * 도메인설명 반환
     * 
     * @return String siteExpln
     */
    public String getSiteExpln() {
        return siteExpln;
    }

    /**
     * 도메인설명 설정
     * 
     * @param siteExpln 을(를) String siteExpln로 설정
     */
    public void setSiteExpln(String siteExpln) {
        this.siteExpln = siteExpln;
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
     * String menuPathNm을 반환
     * 
     * @return String menuPathNm
     */
    public String getMenuPathNm() {
        return menuPathNm;
    }

    /**
     * menuPathNm을 설정
     * 
     * @param menuPathNm 을(를) String menuPathNm로 설정
     */
    public void setMenuPathNm(String menuPathNm) {
        this.menuPathNm = menuPathNm;
    }

    /**
     * String authrtGroupNm을 반환
     * 
     * @return String authrtGroupNm
     */
    public String getAuthrtGroupNm() {
        return authrtGroupNm;
    }

    /**
     * authrtGroupNm을 설정
     * 
     * @param authrtGroupNm 을(를) String authrtGroupNm로 설정
     */
    public void setAuthrtGroupNm(String authrtGroupNm) {
        this.authrtGroupNm = authrtGroupNm;
    }

    /**
     * String authrtdDeptCdId을 반환
     * 
     * @return String authrtdDeptCdId
     */
    public String getAuthrtdDeptCdId() {
        return authrtdDeptCdId;
    }

    /**
     * authrtdDeptCdId을 설정
     * 
     * @param authrtdDeptCdId 을(를) String authrtdDeptCdId로 설정
     */
    public void setAuthrtdDeptCdId(String authrtdDeptCdId) {
        this.authrtdDeptCdId = authrtdDeptCdId;
    }

    /**
     * String authorDeptNm을 반환
     * 
     * @return String authorDeptNm
     */
    public String getAuthorDeptNm() {
        return authorDeptNm;
    }

    /**
     * authorDeptNm을 설정
     * 
     * @param authorDeptNm 을(를) String authorDeptNm로 설정
     */
    public void setAuthorDeptNm(String authorDeptNm) {
        this.authorDeptNm = authorDeptNm;
    }

    /**
     * List<CmsOrgVO> authorChargerList을 반환
     * 
     * @return List<CmsOrgVO> authorChargerList
     */
    public List<CmsOrgVO> getAuthorChargerList() {
        return authorChargerList;
    }

    /**
     * authorChargerList을 설정
     * 
     * @param authorChargerList 을(를) List<CmsOrgVO> authorChargerList로 설정
     */
    public void setAuthorChargerList(List<CmsOrgVO> authorChargerList) {
        this.authorChargerList = authorChargerList;
    }

    /**
     * 항목번호 설정
     * 
     * @param artclSn을(를) Integer artclSn로 설정
     */
    public void setArtclSn(Integer artclSn) {
        this.artclSn = artclSn;
    }

    /**
     * 항목번호 반환
     * 
     * @return Integer artclSn
     */
    public Integer getArtclSn() {
        return artclSn;
    }

    /**
     * 총점 설정
     * 
     * @param sumScr을(를) Integer sumScr로 설정
     */
    public void setSumScr(Integer sumScr) {
        this.sumScr = sumScr;
    }

    /**
     * 총점 반환
     * 
     * @return Integer sumScr
     */
    public Integer getSumScr() {
        return sumScr;
    }

    /**
     * 총응답수 설정
     * 
     * @param wholRspnsCnt을(를) Integer wholRspnsCnt로 설정
     */
    public void setWholRspnsCnt(Integer wholRspnsCnt) {
        this.wholRspnsCnt = wholRspnsCnt;
    }

    /**
     * 총응답수 반환
     * 
     * @return Integer wholRspnsCnt
     */
    public Integer getWholRspnsCnt() {
        return wholRspnsCnt;
    }

    /**
     * Float allAvg을 반환
     * 
     * @return Float allAvg
     */
    public Float getAllAvg() {
        return allAvg;
    }

    /**
     * allAvg을 설정
     * 
     * @param allAvg 을(를) Float allAvg로 설정
     */
    public void setAllAvg(Float allAvg) {
        this.allAvg = allAvg;
    }

    /**
     * 등록일자 설정
     * 
     * @param regYmd을(를) String regYmd로 설정
     */
    public void setRegYmd(String regYmd) {
        this.regYmd = regYmd;
    }

    /**
     * 등록일자 반환
     * 
     * @return String regYmd
     */
    public String getRegYmd() {
        return regYmd;
    }

    /**
     * Integer regSn을 반환
     * 
     * @return Integer regSn
     */
    public Integer getRegSn() {
        return regSn;
    }

    /**
     * regSn을 설정
     * 
     * @param regSn 을(를) Integer regSn로 설정
     */
    public void setRegSn(Integer regSn) {
        this.regSn = regSn;
    }

    /**
     * String opnnCn을 반환
     * 
     * @return String opnnCn
     */
    public String getOpnnCn() {
        return opnnCn;
    }

    /**
     * opnnCn을 설정
     * 
     * @param opnnCn 을(를) String opnnCn로 설정
     */
    public void setOpnnCn(String opnnCn) {
        this.opnnCn = opnnCn;
    }

    /**
     * String rgtrIpAddr을 반환
     * 
     * @return String rgtrIpAddr
     */
    public String getRgtrIpAddr() {
        return rgtrIpAddr;
    }

    /**
     * rgtrIpAddr을 설정
     * 
     * @param rgtrIpAddr 을(를) String rgtrIpAddr로 설정
     */
    public void setRgtrIpAddr(String rgtrIpAddr) {
        this.rgtrIpAddr = rgtrIpAddr;
    }

    /**
     * String rgtrNm을 반환
     * 
     * @return String rgtrNm
     */
    public String getRgtrNm() {
        return rgtrNm;
    }

    /**
     * rgtrNm을 설정
     * 
     * @param rgtrNm 을(를) String rgtrNm로 설정
     */
    public void setRgtrNm(String rgtrNm) {
        this.rgtrNm = rgtrNm;
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
     * Date regDt을 반환
     * 
     * @return Date regDt
     */
    public Date getRegDt() {
        return regDt;
    }

    /**
     * regDt을 설정
     * 
     * @param regDt 을(를) Date regDt로 설정
     */
    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }

}
