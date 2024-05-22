/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.util.List;

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
public class CmsCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 8739733023053895294L;

    /*
     * 기본정보
     */
    /** 도메인코드 */
    private Integer siteSn;
    /** 도메인명 */
    private String siteNm;
    /** 도메인설명(한글명) */
    private String siteExpln;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 메뉴명 */
    private String menuNm;
    /** 제목 */
    private String ttl;
    /** 기본경로 */
    private String bscPathNm;
    /** 메뉴경로 */
    private String menuPathNm;
    /** 최상위메뉴코드 */
    private String hghrkMenuEngNm;
    /** 최상위메뉴명 */
    private String topMenuNm;
    /** 부모메뉴코드 */
    private String upMenuEngNm;
    /** 부모메뉴명 */
    private String parntsMenuNm;
    /** 메뉴깊이 */
    private Integer menuLvlSn;
    /** 최소메뉴깊이 */
    private Integer minMenuDp;
    /** 최대메뉴깊이 */
    private Integer maxMenuDp;
    /** 정렬순서 */
    private Integer sortSn;
    /** 사용자메뉴URL */
    private String userMenuUrlAddr;
    /** 레이아웃코드 */
    private String lytCdNo;
    /** 권한관리용 레이아웃 */
    private String gradLytCdNo;
    /** 메뉴유형 */
    private String menuType;
    /** 링크유형 */
    private String linkTypeNm;
    /** 컨텐츠유형 */
    private String contsTypeNm;
    /** 컨트롤러명 */
    private String cntrlNm;
    /** 담당자표시여부 */
    private String picIndctYn;
    /** 담당부서코드 */
    private String tkcgDeptCdId;
    /** 담당부서명 */
    private String chrgDeptNm;
    /** 담당자ID */
    private String picId;
    /** 담당자명 */
    private String picNm;
    /** 상단컨텐츠 */
    private String strtContsCn;
    /** 하단컨텐츠 */
    private String endContsCn;
    /** 제목이미지 */
    private String userMenuImgNm;
    /** 메뉴제목이미지 */
    private String menuTtlImgNm;
    /** 메뉴온이미지 */
    private String menuOnImgNm;
    /** 메뉴오프이미지 */
    private String menuOffImgNm;
    /** 만족도표시여부 */
    private String dgstfnIndctYn;
    /** 표시여부 */
    private String indctYn;
    /** 사용여부 */
    private String useYn;
    /** SNS사용여부 */
    private String snsUseYn;
    /** SNS 등록용 URL */
    private String snsUrl;
    /** SNS파라미터 */
    private String snsPrmttNm;

    /** 표시담당자 */
    private CmsMngrCacheVO indictMngr;
    /** 사용자등급목록 */
    private List<CmsUserGradCacheVO> userGradList;
    /** 자식메뉴 목록 */
    private List<CmsCacheVO> childList;
    /** 하위URL 목록 */
    private List<String> subUrlList;

    /*
     * 메타정보
     */
    /** 키워드 */
    private String userMenuSrchNm;
    /** 설명 */
    private String userMenuExpln;
    /** 사용자메뉴로봇 */
    private String siteSrchNm;
    /** 메타태그 */
    private String menuTagCn;
    /** 저작권사용여부 */
    private String cprgtUseYn;
    /** 저작권유형 */
    private String cprgtTypeNm;
    /** 저작권내용 */
    private String cprgtCn;

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
     * Integer minMenuDp을 반환
     * 
     * @return Integer minMenuDp
     */
    public Integer getMinMenuDp() {
        return minMenuDp;
    }

    /**
     * minMenuDp을 설정
     * 
     * @param minMenuDp 을(를) Integer minMenuDp로 설정
     */
    public void setMinMenuDp(Integer minMenuDp) {
        this.minMenuDp = minMenuDp;
    }

    /**
     * Integer maxMenuDp을 반환
     * 
     * @return Integer maxMenuDp
     */
    public Integer getMaxMenuDp() {
        return maxMenuDp;
    }

    /**
     * maxMenuDp을 설정
     * 
     * @param msxMenuDp 을(를) Integer maxMenuDp로 설정
     */
    public void setMaxMenuDp(Integer maxMenuDp) {
        this.maxMenuDp = maxMenuDp;
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
     * String gradLytCdNo을 반환
     * 
     * @return String gradLytCdNo
     */
    public String getGradLytCdNo() {
        return gradLytCdNo;
    }

    /**
     * gradLytCdNo을 설정
     * 
     * @param gradLytCdNo 을(를) String gradLytCdNo로 설정
     */
    public void setGradLytCdNo(String gradLytCdNo) {
        this.gradLytCdNo = gradLytCdNo;
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
     * String snsUrl을 반환
     * 
     * @return String snsUrl
     */
    public String getSnsUrl() {
        return snsUrl;
    }

    /**
     * snsUrl을 설정
     * 
     * @param snsUrl 을(를) String snsUrl로 설정
     */
    public void setSnsUrl(String snsUrl) {
        this.snsUrl = snsUrl;
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
     * 표시담당자 반환
     * 
     * @return CmsMngrCacheVO indictMngr
     */
    public CmsMngrCacheVO getIndictMngr() {
        return indictMngr;
    }

    /**
     * 표시담당자 설정
     * 
     * @param indictMngr 을(를) CmsMngrCacheVO indictMngr로 설정
     */
    public void setIndictMngr(CmsMngrCacheVO indictMngr) {
        this.indictMngr = indictMngr;
    }

    /**
     * 사용자등급목록 반환
     * 
     * @return List<CmsUserGradCacheVO> userGradList
     */
    public List<CmsUserGradCacheVO> getUserGradList() {
        return userGradList;
    }

    /**
     * 사용자등급목록 설정
     * 
     * @param userGradList 을(를) List<CmsUserGradCacheVO> userGradList로 설정
     */
    public void setUserGradList(List<CmsUserGradCacheVO> userGradList) {
        this.userGradList = userGradList;
    }

    /**
     * 자식메뉴목록 반환
     * 
     * @return List<CmsCacheVO> childList
     */
    public List<CmsCacheVO> getChildList() {
        return childList;
    }

    /**
     * 자식메뉴목록 설정
     * 
     * @param childList 을(를) List<CmsCacheVO> childList로 설정
     */
    public void setChildList(List<CmsCacheVO> childList) {
        this.childList = childList;
    }

    /**
     * 하위URL목록 반환
     * 
     * @return List<String> subUrlList
     */
    public List<String> getSubUrlList() {
        return subUrlList;
    }

    /**
     * 하위URL목록 설정
     * 
     * @param subUrlList 을(를) List<String> subUrlList로 설정
     */
    public void setSubUrlList(List<String> subUrlList) {
        this.subUrlList = subUrlList;
    }

    /**
     * String userMenuSrchNm을 반환
     * 
     * @return String userMenuSrchNm
     */
    public String getUserMenuSrchNm() {
        return userMenuSrchNm;
    }

    /**
     * userMenuSrchNm을 설정
     * 
     * @param userMenuSrchNm 을(를) String userMenuSrchNm로 설정
     */
    public void setUserMenuSrchNm(String userMenuSrchNm) {
        this.userMenuSrchNm = userMenuSrchNm;
    }

    /**
     * String userMenuExpln을 반환
     * 
     * @return String userMenuExpln
     */
    public String getUserMenuExpln() {
        return userMenuExpln;
    }

    /**
     * userMenuExpln을 설정
     * 
     * @param userMenuExpln 을(를) String userMenuExpln로 설정
     */
    public void setUserMenuExpln(String userMenuExpln) {
        this.userMenuExpln = userMenuExpln;
    }

    /**
     * String siteSrchNm을 반환
     * 
     * @return String siteSrchNm
     */
    public String getSiteSrchNm() {
        return siteSrchNm;
    }

    /**
     * siteSrchNm을 설정
     * 
     * @param siteSrchNm 을(를) String siteSrchNm로 설정
     */
    public void setSiteSrchNm(String siteSrchNm) {
        this.siteSrchNm = siteSrchNm;
    }

    /**
     * String menuTagCn을 반환
     * 
     * @return String menuTagCn
     */
    public String getMenuTagCn() {
        return menuTagCn;
    }

    /**
     * menuTagCn을 설정
     * 
     * @param menuTagCn 을(를) String menuTagCn로 설정
     */
    public void setMenuTagCn(String menuTagCn) {
        this.menuTagCn = menuTagCn;
    }

    /**
     * String cprgtUseYn을 반환
     * 
     * @return String cprgtUseYn
     */
    public String getCprgtUseYn() {
        return cprgtUseYn;
    }

    /**
     * cprgtUseYn을 설정
     * 
     * @param cprgtUseYn 을(를) String cprgtUseYn로 설정
     */
    public void setCprgtUseYn(String cprgtUseYn) {
        this.cprgtUseYn = cprgtUseYn;
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

}
