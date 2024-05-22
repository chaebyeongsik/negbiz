/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base.domain;

import java.util.Date;
import java.util.List;

import zesinc.component.file.domain.FileVO;
import zesinc.web.validate.annotation.marker.Digits;
import zesinc.web.validate.annotation.marker.MaxLength;
import zesinc.web.validate.annotation.marker.Pattern;
import zesinc.web.validate.annotation.marker.RequireFrom;
import zesinc.web.validate.annotation.marker.Required;
import zesinc.web.vo.BaseVO;

/**
 * 사용자메뉴 정보 VO 클레스
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
public class CmsVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 1096773481855354966L;
    /** 도메인코드 */
    @Required
    @Digits
    private Integer siteSn;

    /** 도메인명 */
    private String siteNm;

    /** 도메인설명 */
    private String siteExpln;

    /** 포트번호 */
    private Integer portSn;

    /** 보안포트번호 */
    private Integer scrtyPortSn;

    /** 컨텍스트경로 */
    private String sitePathNm;

    /** 보안프로토콜여부 */
    private String httpsYn;

    /** 실컨텍스트여부 */
    private String actlStngYn;

    /** 사용자메뉴코드 */
    @Required
    @Pattern(regexp = "[a-zA-Z0-9_]+")
    @MaxLength(max = 100)
    private String userMenuEngNm;

    /** 메뉴명 */
    @Required
    @MaxLength(max = 100)
    private String menuNm;

    /** 제목 */
    @MaxLength(max = 256)
    private String ttl;

    /** 기본경로 */
    @MaxLength(max = 300)
    private String bscPathNm;

    /** 메뉴경로 */
    @MaxLength(max = 300)
    private String menuPathNm;

    /** 최상위메뉴코드 */
    @Pattern(regexp = "[a-zA-Z0-9_]+")
    @MaxLength(max = 100)
    private String hghrkMenuEngNm;

    /** 최상위메뉴명 */
    private String topMenuNm;

    /** 부모메뉴코드 */
    @Pattern(regexp = "[a-zA-Z0-9_]+")
    @MaxLength(max = 100)
    private String upMenuEngNm;

    /** 부모메뉴명 */
    private String parntsMenuNm;

    /** 메뉴깊이 */
    @Digits
    private Integer menuLvlSn;

    /** 정렬순서 */
    @Digits
    private Integer sortSn;

    /** 관리자메뉴URL */
    @MaxLength(max = 2000)
    private String mngrMenuUrlAddr;

    /** 사용자메뉴URL */
    @MaxLength(max = 2000)
    private String userMenuUrlAddr;

    /** 레이아웃코드 */
    @MaxLength(max = 20)
    private String lytCdNo;

    /** 레이아웃명 */
    private String lytNm;

    /** 부모레이아웃사용여부 */
    private String upLytUseYn;

    /** 메뉴유형 */
    @Required
    private String menuType;

    /** 링크유형 */
    @Required
    private String linkTypeNm;

    /** 컨텐츠유형 */
    private String contsTypeNm;

    /** 컨트롤러명 */
    @MaxLength(max = 200)
    private String cntrlNm;

    /** 담당부서코드 */
    @RequireFrom(fieldName = "picIndctYn", fieldValue = "Y", fieldDesc = "담당자표시여부")
    @MaxLength(max = 20)
    private String tkcgDeptCdId;

    /** 담당부서명 */
    private String chrgDeptNm;

    /** 담당자ID */
    @RequireFrom(fieldName = "picIndctYn", fieldValue = "Y", fieldDesc = "담당자표시여부")
    @MaxLength(max = 20)
    private String picId;

    /** 담당자명 */
    private String picNm;

    /** 권한유형 */
    @MaxLength(max = 100)
    private String authrtGroupNm;

    /** 권한부서코드 */
    @MaxLength(max = 20)
    private String authrtDeptCdId;

    /** 권한부서명 */
    private String authorDeptNm;

    /** 권한담당자목록 */
    private List<CmsOrgVO> authorChargerList;

    /** 권한담당자IDs */
    private String[] authorPicIds;

    /** 컨텐츠일련번호 */
    @Digits
    private Integer contsSn;

    /** 상단컨텐츠 */
    private String strtContsCn;

    /** 하단컨텐츠 */
    @MaxLength(max = 4000)
    private String endContsCn;

    /** 제목이미지 */
    @MaxLength(max = 200)
    private String userMenuImgNm;

    /** 메뉴제목이미지 */
    @MaxLength(max = 200)
    private String menuTtlImgNm;

    /** 메뉴온이미지 */
    @MaxLength(max = 200)
    private String menuOnImgNm;

    /** 메뉴오프이미지 */
    @MaxLength(max = 200)
    private String menuOffImgNm;

    /** CSS파일 */
    @MaxLength(max = 300)
    private String cssFileNm;

    /** 파일순번 */
    @Digits
    private Integer fileSn;

    /** 승인여부 */
    private String aprvYn;

    /** 만족도표시여부 */
    private String dgstfnIndctYn;

    /** 담당자표시여부 */
    private String picIndctYn;

    /** 표시여부 */
    @Required
    private String indctYn;

    /** 사용여부 */
    @Required
    private String useYn;

    /** SNS사용여부 */
    private String snsUseYn;

    /** SNS파라미터 */
    private String snsPrmttNm;

    /** 등록자ID */
    @MaxLength(max = 20)
    private String rgtrId;

    /** 등록자명 */
    private String rgtrNm;

    /** 등록일시 */
    private Date regDt;

    /** 수정자ID */
    @MaxLength(max = 20)
    private String mdfrId;

    /** 수정자명 */
    private String updusrNm;

    /** 수정일시 */
    private Date updtDt;

    /** 첨부파일목록 */
    private List<FileVO> fileList;

    /** 사용자등급목록 */
    private List<CmsUserGradVO> userGradList;

    /** 사용자등급코드 */
    private String[] userGrdCdIds;

    /** 하위메뉴URL 목록 */
    private List<String> lwprtMenuUrlAddr;

    /** 자식 존재여부 */
    private Boolean isFolder;

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
     * String siteExpln을 반환
     *
     * @return String siteExpln
     */
    public String getSiteExpln() {
        return siteExpln;
    }

    /**
     * siteExpln을 설정
     *
     * @param siteExpln 을(를) String siteExpln로 설정
     */
    public void setSiteExpln(String siteExpln) {
        this.siteExpln = siteExpln;
    }

    /**
     * Integer portSn을 반환
     *
     * @return Integer portSn
     */
    public Integer getPortSn() {
        return portSn;
    }

    /**
     * portSn을 설정
     *
     * @param portSn 을(를) Integer portSn로 설정
     */
    public void setPortSn(Integer portSn) {
        this.portSn = portSn;
    }

    /**
     * Integer scrtyPortSn을 반환
     *
     * @return Integer scrtyPortSn
     */
	public Integer getScrtyPortSn() {
		return scrtyPortSn;
	}

	/**
	 * scrtyPortSn을 설정
	 *
	 * @param scrtyPortSn 을(를) Integer scrtyPortSn로 설정
	 */
	public void setScrtyPortSn(Integer scrtyPortSn) {
		this.scrtyPortSn = scrtyPortSn;
	}

	/**
     * String sitePathNm을 반환
     *
     * @return String sitePathNm
     */
    public String getSitePathNm() {
        return sitePathNm;
    }

    /**
     * sitePathNm을 설정
     *
     * @param sitePathNm 을(를) String sitePathNm로 설정
     */
    public void setSitePathNm(String sitePathNm) {
        this.sitePathNm = sitePathNm;
    }

    /**
     * String 보안프로토콜여부 반환
     *
     * @return String httpsYn
     */
    public String getHttpsYn() {
        return httpsYn;
    }

    /**
     * 보안프로토콜여부 설정
     *
     * @param httpsYn 을(를) String httpsYn로 설정
     */
    public void setHttpsYn(String httpsYn) {
        this.httpsYn = httpsYn;
    }

    /**
     * 실컨텍스트여부 반환
     *
     * @return String actlStngYn
     */
    public String getActlStngYn() {
        return actlStngYn;
    }

    /**
     * 실컨텍스트여부 설정
     *
     * @param actlStngYn 을(를) String actlStngYn로 설정
     */
    public void setActlStngYn(String actlStngYn) {
        this.actlStngYn = actlStngYn;
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
     * 제목 설정
     *
     * @param ttl을(를) String ttl로 설정
     */
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    /**
     * 제목 반환
     *
     * @return String ttl
     */
    public String getTtl() {
        return ttl;
    }

    /**
     * 기본경로 설정
     *
     * @param bscPathNm을(를) String bscPathNm로 설정
     */
    public void setBscPathNm(String bscPathNm) {
        this.bscPathNm = bscPathNm;
    }

    /**
     * 기본경로 반환
     *
     * @return String bscPathNm
     */
    public String getBscPathNm() {
        return bscPathNm;
    }

    /**
     * 메뉴경로 설정
     *
     * @param menuPathNm을(를) String menuPathNm로 설정
     */
    public void setMenuPathNm(String menuPathNm) {
        this.menuPathNm = menuPathNm;
    }

    /**
     * 메뉴경로 반환
     *
     * @return String menuPathNm
     */
    public String getMenuPathNm() {
        return menuPathNm;
    }

    /**
     * 최상위메뉴코드 설정
     *
     * @param hghrkMenuEngNm을(를) String hghrkMenuEngNm로 설정
     */
    public void setHghrkMenuEngNm(String hghrkMenuEngNm) {
        this.hghrkMenuEngNm = hghrkMenuEngNm;
    }

    /**
     * 최상위메뉴코드 반환
     *
     * @return String hghrkMenuEngNm
     */
    public String getHghrkMenuEngNm() {
        return hghrkMenuEngNm;
    }

    /**
     * 최상위메뉴명 설정
     *
     * @param topMenuNm을(를) String topMenuNm로 설정
     */
    public void setTopMenuNm(String topMenuNm) {
        this.topMenuNm = topMenuNm;
    }

    /**
     * 최상위메뉴명 반환
     *
     * @return String topMenuNm
     */
    public String getTopMenuNm() {
        return topMenuNm;
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
     * 부모메뉴명 설정
     *
     * @param parntsMenuNm을(를) String parntsMenuNm로 설정
     */
    public void setParntsMenuNm(String parntsMenuNm) {
        this.parntsMenuNm = parntsMenuNm;
    }

    /**
     * 부모메뉴명 반환
     *
     * @return String parntsMenuNm
     */
    public String getParntsMenuNm() {
        return parntsMenuNm;
    }

    /**
     * 메뉴깊이 설정
     *
     * @param menuLvlSn을(를) Integer menuLvlSn로 설정
     */
    public void setMenuLvlSn(Integer menuLvlSn) {
        this.menuLvlSn = menuLvlSn;
    }

    /**
     * 메뉴깊이 반환
     *
     * @return Integer menuLvlSn
     */
    public Integer getMenuLvlSn() {
        return menuLvlSn;
    }

    /**
     * 정렬순서 설정
     *
     * @param sortSn을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * 정렬순서 반환
     *
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * 관리자메뉴URL 설정
     *
     * @param mngrMenuUrlAddr을(를) String mngrMenuUrlAddr로 설정
     */
    public void setMngrMenuUrlAddr(String mngrMenuUrlAddr) {
        this.mngrMenuUrlAddr = mngrMenuUrlAddr;
    }

    /**
     * 관리자메뉴URL 반환
     *
     * @return String mngrMenuUrlAddr
     */
    public String getMngrMenuUrlAddr() {
        return mngrMenuUrlAddr;
    }

    /**
     * 사용자메뉴URL 설정
     *
     * @param userMenuUrlAddr을(를) String userMenuUrlAddr로 설정
     */
    public void setuserMenuUrlAddr(String userMenuUrlAddr) {
        this.userMenuUrlAddr = userMenuUrlAddr;
    }

    /**
     * 사용자메뉴URL 반환
     *
     * @return String userMenuUrlAddr
     */
    public String getuserMenuUrlAddr() {
        return userMenuUrlAddr;
    }

    /**
     * 레이아웃코드 설정
     *
     * @param lytCdNo을(를) String lytCdNo로 설정
     */
    public void setLytCdNo(String lytCdNo) {
        this.lytCdNo = lytCdNo;
    }

    /**
     * 레이아웃코드 반환
     *
     * @return String lytCdNo
     */
    public String getLytCdNo() {
        return lytCdNo;
    }

    /**
     * 레이아웃명 설정
     *
     * @param lytNm을(를) String lytNm로 설정
     */
    public void setLytNm(String lytNm) {
        this.lytNm = lytNm;
    }

    /**
     * 레이아웃명 반환
     *
     * @return String lytNm
     */
    public String getLytNm() {
        return lytNm;
    }

    /**
     * 부모레이아웃사용여부 설정
     *
     * @param upLytUseYn을(를) String upLytUseYn로 설정
     */
    public void setUpLytUseYn(String upLytUseYn) {
        this.upLytUseYn = upLytUseYn;
    }

    /**
     * 부모레이아웃사용여부 반환
     *
     * @return String upLytUseYn
     */
    public String getUpLytUseYn() {
        return upLytUseYn;
    }

    /**
     * 메뉴유형 설정
     *
     * @param menuType을(를) String menuType로 설정
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 메뉴유형 반환
     *
     * @return String menuType
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 링크유형 설정
     *
     * @param linkTypeNm을(를) String linkTypeNm로 설정
     */
    public void setLinkTypeNm(String linkTypeNm) {
        this.linkTypeNm = linkTypeNm;
    }

    /**
     * 링크유형 반환
     *
     * @return String linkTypeNm
     */
    public String getLinkTypeNm() {
        return linkTypeNm;
    }

    /**
     * 컨텐츠유형 설정
     *
     * @param contsTypeNm을(를) String contsTypeNm로 설정
     */
    public void setContsTypeNm(String contsTypeNm) {
        this.contsTypeNm = contsTypeNm;
    }

    /**
     * 컨텐츠유형 반환
     *
     * @return String contsTypeNm
     */
    public String getContsTypeNm() {
        return contsTypeNm;
    }

    /**
     * 컨트롤러명 설정
     *
     * @param cntrlNm을(를) String cntrlNm로 설정
     */
    public void setCntrlNm(String cntrlNm) {
        this.cntrlNm = cntrlNm;
    }

    /**
     * 컨트롤러명 반환
     *
     * @return String cntrlNm
     */
    public String getCntrlNm() {
        return cntrlNm;
    }

    /**
     * 담당부서코드 설정
     *
     * @param tkcgDeptCdId을(를) String tkcgDeptCdId로 설정
     */
    public void setTkcgDeptCdId(String tkcgDeptCdId) {
        this.tkcgDeptCdId = tkcgDeptCdId;
    }

    /**
     * 담당부서코드 반환
     *
     * @return String tkcgDeptCdId
     */
    public String getTkcgDeptCdId() {
        return tkcgDeptCdId;
    }

    /**
     * 담당부서명 설정
     *
     * @param chrgDeptNm을(를) String chrgDeptNm로 설정
     */
    public void setChrgDeptNm(String chrgDeptNm) {
        this.chrgDeptNm = chrgDeptNm;
    }

    /**
     * 담당부서명 반환
     *
     * @return String chrgDeptNm
     */
    public String getChrgDeptNm() {
        return chrgDeptNm;
    }

    /**
     * 담당자ID 설정
     *
     * @param picId을(를) String picId로 설정
     */
    public void setPicId(String picId) {
        this.picId = picId;
    }

    /**
     * 담당자ID 반환
     *
     * @return String picId
     */
    public String getPicId() {
        return picId;
    }

    /**
     * 담당자명을 반환
     *
     * @return String picNm
     */
    public String getPicNm() {
        return picNm;
    }

    /**
     * 담당자명을 설정
     *
     * @param picNm 을(를) String picNm로 설정
     */
    public void setPicNm(String picNm) {
        this.picNm = picNm;
    }

    /**
     * 권한유형 설정
     *
     * @param authrtGroupNm을(를) String authrtGroupNm로 설정
     */
    public void setAuthrtGroupNm(String authrtGroupNm) {
        this.authrtGroupNm = authrtGroupNm;
    }

    /**
     * 권한유형 반환
     *
     * @return String authrtGroupNm
     */
    public String getAuthrtGroupNm() {
        return authrtGroupNm;
    }

    /**
     * 권한부서코드 설정
     *
     * @param authrtDeptCdId을(를) String authrtdDeptCdId로 설정
     */
    public void setAuthrtDeptCdId(String authrtdDeptCdId) {
        this.authrtDeptCdId = authrtdDeptCdId;
    }

    /**
     * 권한부서코드 반환
     *
     * @return String authrtdDeptCdId
     */
    public String getAuthrtDeptCdId() {
        return authrtDeptCdId;
    }

    /**
     * 권한부서명 설정
     *
     * @param authorDeptNm을(를) String authorDeptNm로 설정
     */
    public void setAuthorDeptNm(String authorDeptNm) {
        this.authorDeptNm = authorDeptNm;
    }

    /**
     * 권한부서명 반환
     *
     * @return String authorDeptNm
     */
    public String getAuthorDeptNm() {
        return authorDeptNm;
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
     * String[] authorPicIds을 반환
     *
     * @return String[] authorPicIds
     */
    public String[] getAuthorPicIds() {
        return authorPicIds;
    }

    /**
     * authorPicIds을 설정
     *
     * @param authorPicIds 을(를) String[] authorPicIds로 설정
     */
    public void setAuthorPicIds(String[] authorPicIds) {
        this.authorPicIds = authorPicIds;
    }

    /**
     * 컨텐츠일련번호 설정
     *
     * @param contsSn을(를) Integer contsSn로 설정
     */
    public void setContsSn(Integer contsSn) {
        this.contsSn = contsSn;
    }

    /**
     * 컨텐츠일련번호 반환
     *
     * @return Integer contsSn
     */
    public Integer getContsSn() {
        return contsSn;
    }

    /**
     * 상단컨텐츠 설정
     *
     * @param strtContsCn을(를) String strtContsCn로 설정
     */
    public void setStrtContsCn(String strtContsCn) {
        this.strtContsCn = strtContsCn;
    }

    /**
     * 상단컨텐츠 반환
     *
     * @return String strtContsCn
     */
    public String getStrtContsCn() {
        return strtContsCn;
    }

    /**
     * 하단컨텐츠 설정
     *
     * @param endContsCn을(를) String endContsCn로 설정
     */
    public void setEndContsCn(String endContsCn) {
        this.endContsCn = endContsCn;
    }

    /**
     * 하단컨텐츠 반환
     *
     * @return String endContsCn
     */
    public String getEndContsCn() {
        return endContsCn;
    }

    /**
     * 제목이미지 설정
     *
     * @param userMenuImgNm을(를) String userMenuImgNm로 설정
     */
    public void setUserMenuImgNm(String userMenuImgNm) {
        this.userMenuImgNm = userMenuImgNm;
    }

    /**
     * 제목이미지 반환
     *
     * @return String userMenuImgNm
     */
    public String getUserMenuImgNm() {
        return userMenuImgNm;
    }

    /**
     * 메뉴제목이미지 설정
     *
     * @param menuTtlImgNm을(를) String menuTtlImgNm로 설정
     */
    public void setMenuTtlImgNm(String menuTtlImgNm) {
        this.menuTtlImgNm = menuTtlImgNm;
    }

    /**
     * 메뉴제목이미지 반환
     *
     * @return String menuTtlImgNm
     */
    public String getMenuTtlImgNm() {
        return menuTtlImgNm;
    }

    /**
     * 메뉴온이미지 설정
     *
     * @param menuOnImgNm을(를) String menuOnImgNm로 설정
     */
    public void setMenuOnImgNm(String menuOnImgNm) {
        this.menuOnImgNm = menuOnImgNm;
    }

    /**
     * 메뉴온이미지 반환
     *
     * @return String menuOnImgNm
     */
    public String getMenuOnImgNm() {
        return menuOnImgNm;
    }

    /**
     * 메뉴오프이미지 설정
     *
     * @param menuOffImgNm을(를) String menuOffImgNm로 설정
     */
    public void setMenuOffImgNm(String menuOffImgNm) {
        this.menuOffImgNm = menuOffImgNm;
    }

    /**
     * 메뉴오프이미지 반환
     *
     * @return String menuOffImgNm
     */
    public String getMenuOffImgNm() {
        return menuOffImgNm;
    }

    /**
     * String cssFileNm을 반환
     *
     * @return String cssFileNm
     */
    public String getCssFileNm() {
        return cssFileNm;
    }

    /**
     * cssFileNm을 설정
     *
     * @param cssFileNm 을(를) String cssFileNm로 설정
     */
    public void setCssFileNm(String cssFileNm) {
        this.cssFileNm = cssFileNm;
    }

    /**
     * 파일순번 설정
     *
     * @param fileSn을(를) Integer fileSn로 설정
     */
    public void setFileSn(Integer fileSn) {
        this.fileSn = fileSn;
    }

    /**
     * 파일순번 반환
     *
     * @return Integer fileSn
     */
    public Integer getFileSn() {
        return fileSn;
    }

    /**
     * 승인여부 설정
     *
     * @param aprvYn을(를) String aprvYn로 설정
     */
    public void setAprvYn(String aprvYn) {
        this.aprvYn = aprvYn;
    }

    /**
     * 승인여부 반환
     *
     * @return String aprvYn
     */
    public String getAprvYn() {
        return aprvYn;
    }

    /**
     * 만족도표시여부 설정
     *
     * @param dgstfnIndctYn을(를) String dgstfnIndctYn로 설정
     */
    public void setDgstfnIndctYn(String dgstfnIndctYn) {
        this.dgstfnIndctYn = dgstfnIndctYn;
    }

    /**
     * 만족도표시여부 반환
     *
     * @return String dgstfnIndctYn
     */
    public String getDgstfnIndctYn() {
        return dgstfnIndctYn;
    }

    /**
     * 담당자표시여부 설정
     *
     * @param picIndctYn을(를) String picIndctYn로 설정
     */
    public void setPicIndctYn(String picIndctYn) {
        this.picIndctYn = picIndctYn;
    }

    /**
     * 담당자표시여부 반환
     *
     * @return String picIndctYn
     */
    public String getPicIndctYn() {
        return picIndctYn;
    }

    /**
     * 표시여부 설정
     *
     * @param indctYn을(를) String indctYn로 설정
     */
    public void setIndctYn(String indctYn) {
        this.indctYn = indctYn;
    }

    /**
     * 표시여부 반환
     *
     * @return String indctYn
     */
    public String getIndctYn() {
        return indctYn;
    }

    /**
     * 사용여부 설정
     *
     * @param useYn을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * 사용여부 반환
     *
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * SNS사용여부 설정
     *
     * @param snsUseYn을(를) String snsUseYn로 설정
     */
    public void setSnsUseYn(String snsUseYn) {
        this.snsUseYn = snsUseYn;
    }

    /**
     * SNS사용여부 반환
     *
     * @return String snsUseYn
     */
    public String getSnsUseYn() {
        return snsUseYn;
    }

    /**
     * SNS파라미터 반환
     *
     * @return String snsPrmttNm
     */
    public String getSnsPrmttNm() {
        return snsPrmttNm;
    }

    /**
     * SNS파라미터 설정
     *
     * @param snsPrmttNm 을(를) String snsPrmttNm로 설정
     */
    public void setSnsPrmttNm(String snsPrmttNm) {
        this.snsPrmttNm = snsPrmttNm;
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

    /**
     * 파일목록을 설정
     *
     * @param 파일목록을 List&lt;FileVO&gt; fileList로 설정
     */
    public void setFileList(List<FileVO> fileList) {
        this.fileList = fileList;
    }

    /**
     * 파일 목록을 반환
     *
     * @return List&lt;FileVO&gt; fileList
     */
    public List<FileVO> getFileList() {
        return fileList;
    }

    /**
     * 사용자등급목록 반환
     *
     * @return List<UserGradVO> userGradList
     */
    public List<CmsUserGradVO> getUserGradList() {
        return userGradList;
    }

    /**
     * 사용자등급목록 설정
     *
     * @param userGradList 을(를) List<UserGradVO> userGradList로 설정
     */
    public void setUserGradList(List<CmsUserGradVO> userGradList) {
        this.userGradList = userGradList;
    }

    /**
     * 사용자등급코드배열 반환
     *
     * @return String[] userGrdCdIds
     */
    public String[] getUserGrdCdIds() {
        return userGrdCdIds;
    }

    /**
     * 사용자등급코드배열 설정
     *
     * @param userGrdCdIds 을(를) String[] userGrdCdIds로 설정
     */
    public void setUserGrdCdIds(String[] userGrdCdIds) {
        this.userGrdCdIds = userGrdCdIds;
    }

    /**
     * 하위메뉴URL 목록을 반환
     *
     * @return List<String> lwprtMenuUrlAddr
     */
    public List<String> getLwprtMenuUrlAddr() {
        return lwprtMenuUrlAddr;
    }

    /**
     * 하위메뉴URL 목록 설정
     *
     * @param lwprtMenuUrlAddr 을(를) List<String> lwprtMenuUrlAddr로 설정
     */
    public void setLwprtMenuUrlAddr(List<String> lwprtMenuUrlAddr) {
        this.lwprtMenuUrlAddr = lwprtMenuUrlAddr;
    }

    /**
     * Boolean isFolder을 반환
     *
     * @return Boolean isFolder
     */
    public Boolean getIsFolder() {
        return isFolder;
    }

    /**
     * isFolder을 설정
     *
     * @param isFolder 을(를) Boolean isFolder로 설정
     */
    public void setIsFolder(Boolean isFolder) {
        this.isFolder = isFolder;
    }

}
