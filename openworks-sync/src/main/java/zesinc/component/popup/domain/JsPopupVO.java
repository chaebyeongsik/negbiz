/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.popup.domain;

import java.io.Serializable;
import java.util.List;

import zesinc.component.file.domain.FileVO;

/**
 * 팝업 표시를 위한 정보 VO
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class JsPopupVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 5167557507299110048L;

    /** 쿠키명칭 접두사 */
    private String cookiePrefix = "POPUP_";

    /** 도메인명 */
    private String siteNm;

    /** 도메인코드 */
    private Integer siteSn;

    /**
     * 사용자메뉴코드
     * TODO : 사용자메뉴 설정 및 메뉴별 팝업 사용할 수 있도록...
     * 당장은 시간이 없넹 --
     */
    private String userMenuEngNm;

    /** 팝업일련번호 */
    private Integer regSn;

    /** 제목 */
    private String ttl;

    /** 가로사이즈 */
    private Integer wdthSz;

    /** 세로사이즈 */
    private Integer vrtcSz;

    /** 상단위치 */
    private Integer yAxis;

    /** 좌측위치 */
    private Integer xAxis;

    /** 스크롤유무 */
    private String scrollYn;

    /** 리사이징유무 */
    private String popupSzChgYn;

    /** 팝업유형 */
    private String popupTypeNo;

    /** 팝업주기 */
    private String popupRpttSeNo;

    /** 작성방법 */
    private String wrtTypeNm;
    
    /** 장문내용 */
    private String docContsCn;

    /** 첨부파일일련번호 */
    private Integer fileSn;

    /** 첨부파일 목록 */
    private List<FileVO> fileList;

    /**
     * String cookiePrefix을 반환
     * 
     * @return String cookiePrefix
     */
    public String getCookiePrefix() {
        return cookiePrefix;
    }

    /**
     * String siteNm을 반환
     * 
     * @return String siteNm
     */
    public String getSiteNm() {
        return siteNm;
    }

    /**
     * siteNm을 설정
     * 
     * @param siteNm 을(를) String siteNm로 설정
     */
    public void setSiteNm(String siteNm) {
        this.siteNm = siteNm;
    }

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
     * String ttl을 반환
     * 
     * @return String ttl
     */
    public String getTtl() {
        return ttl;
    }

    /**
     * ttl을 설정
     * 
     * @param ttl 을(를) String ttl로 설정
     */
    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    /**
     * Integer wdthSz을 반환
     * 
     * @return Integer wdthSz
     */
    public Integer getWdthSz() {
        return wdthSz;
    }

    /**
     * wdthSz을 설정
     * 
     * @param wdthSz 을(를) Integer wdthSz로 설정
     */
    public void setWdthSz(Integer wdthSz) {
        this.wdthSz = wdthSz;
    }

    /**
     * Integer vrtcSz을 반환
     * 
     * @return Integer vrtcSz
     */
    public Integer getVrtcSz() {
        return vrtcSz;
    }

    /**
     * vrtcSz을 설정
     * 
     * @param vrtcSz 을(를) Integer vrtcSz로 설정
     */
    public void setVrtcSz(Integer vrtcSz) {
        this.vrtcSz = vrtcSz;
    }

    /**
     * Integer yAxis을 반환
     * 
     * @return Integer yAxis
     */
    public Integer getyAxis() {
        return yAxis;
    }

    /**
     * yAxis을 설정
     * 
     * @param yAxis 을(를) Integer yAxis로 설정
     */
    public void setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
    }

    /**
     * Integer xAxis을 반환
     * 
     * @return Integer xAxis
     */
    public Integer getxAxis() {
        return xAxis;
    }

    /**
     * xAxis을 설정
     * 
     * @param xAxis 을(를) Integer xAxis로 설정
     */
    public void setxAxis(Integer xAxis) {
        this.xAxis = xAxis;
    }

    /**
     * String scrollYn을 반환
     * 
     * @return String scrollYn
     */
    public String getScrollYn() {
        return scrollYn;
    }

    /**
     * scrollYn을 설정
     * 
     * @param scrollYn 을(를) String scrollYn로 설정
     */
    public void setScrollYn(String scrollYn) {
        this.scrollYn = scrollYn;
    }

    /**
     * String popupSzChgYn을 반환
     * 
     * @return String popupSzChgYn
     */
    public String getPopupSzChgYn() {
        return popupSzChgYn;
    }

    /**
     * popupSzChgYn을 설정
     * 
     * @param popupSzChgYn 을(를) String popupSzChgYn로 설정
     */
    public void setPopupSzChgYn(String popupSzChgYn) {
        this.popupSzChgYn = popupSzChgYn;
    }

    /**
     * String popupTypeNo을 반환
     * 
     * @return String popupTypeNo
     */
    public String getPopupTypeNo() {
        return popupTypeNo;
    }

    /**
     * popupTypeNo을 설정
     * 
     * @param popupTypeNo 을(를) String popupTypeNo로 설정
     */
    public void setPopupTypeNo(String popupTypeNo) {
        this.popupTypeNo = popupTypeNo;
    }

    /**
     * String popupRpttSeNo을 반환
     * 
     * @return String popupRpttSeNo
     */
    public String getPopupRpttSeNo() {
        return popupRpttSeNo;
    }

    /**
     * popupRpttSeNo을 설정
     * 
     * @param popupRpttSeNo 을(를) String popupRpttSeNo로 설정
     */
    public void setPopupRpttSeNo(String popupRpttSeNo) {
        this.popupRpttSeNo = popupRpttSeNo;
    }

    
    /**
     * String wrtTypeNm을 반환
     * @return String wrtTypeNm
     */
    public String getWrtTypeNm() {
        return wrtTypeNm;
    }

    
    /**
     * wrtTypeNm을 설정
     * @param wrtTypeNm 을(를) String wrtTypeNm로 설정
     */
    public void setWrtTypeNm(String wrtTypeNm) {
        this.wrtTypeNm = wrtTypeNm;
    }

    /**
     * String docContsCn을 반환
     * 
     * @return String docContsCn
     */
    public String getDocContsCn() {
        return docContsCn;
    }

    /**
     * docContsCn을 설정
     * 
     * @param docContsCn 을(를) String docContsCn로 설정
     */
    public void setDocContsCn(String docContsCn) {
        this.docContsCn = docContsCn;
    }

    /**
     * Integer fileSn을 반환
     * 
     * @return Integer fileSn
     */
    public Integer getFileSn() {
        return fileSn;
    }

    /**
     * fileSn을 설정
     * 
     * @param fileSn 을(를) Integer fileSn로 설정
     */
    public void setFileSn(Integer fileSn) {
        this.fileSn = fileSn;
    }

    /**
     * List<FileVO> fileList을 반환
     * 
     * @return List<FileVO> fileList
     */
    public List<FileVO> getFileList() {
        return fileList;
    }

    /**
     * fileList을 설정
     * 
     * @param fileList 을(를) List<FileVO> fileList로 설정
     */
    public void setFileList(List<FileVO> fileList) {
        this.fileList = fileList;
    }

}
