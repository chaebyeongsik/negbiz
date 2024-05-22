/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.layout.domain;

import java.util.Date;

import zesinc.web.validate.annotation.marker.AlphaNumeric;
import zesinc.web.validate.annotation.marker.Digits;
import zesinc.web.validate.annotation.marker.MaxLength;
import zesinc.web.validate.annotation.marker.Required;
import zesinc.web.vo.PageVO;

/**
 * 사용자메뉴레이아웃 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsLayoutVO extends PageVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 6131496984206186157L;
    /** 도메인코드 */
    @Required
    @Digits
    private Integer siteSn;

    /** 도메인명 */
    private String siteNm;

    /** 도메인설명 */
    private String siteExpln;

    /** 레이아웃코드 */
    @Required
    @AlphaNumeric
    @MaxLength(max = 50)
    private String lytCdNo;

    /** 레이아웃명 */
    @Required
    @MaxLength(max = 100)
    private String lytNm;

    /** 로그일련번호 */
    private Integer logSn;

    /** 로그유형 */
    private String logType;

    /** 로그일시 */
    private Date logDt;

    /** 레이아웃컨텐츠 */
    @Required
    private String lytContsCn;

    /** 파일경로 */
    @MaxLength(max = 300)
    private String filePathNm;

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
     * 로그일련번호 반환
     * 
     * @return Integer logSn
     */
    public Integer getLogSn() {
        return logSn;
    }

    /**
     * 로그일련번호 설정
     * 
     * @param logSn 을(를) Integer logSn로 설정
     */
    public void setLogSn(Integer logSn) {
        this.logSn = logSn;
    }

    /**
     * 로그유형 반환
     * 
     * @return String logType
     */
    public String getLogType() {
        return logType;
    }

    /**
     * 로그유형 설정
     * 
     * @param logType 을(를) String logType로 설정
     */
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /**
     * 로그일시을 설정
     * 
     * @param logDt을(를) Date logDt로 설정
     */
    public void setLogDt(Date logDt) {
        this.logDt = logDt;
    }

    /**
     * 로그일시을 반환
     * 
     * @return Date logDt
     */
    public Date getLogDt() {
        return logDt;
    }

    /**
     * 레이아웃컨텐츠 설정
     * 
     * @param lytContsCn을(를) String lytContsCn로 설정
     */
    public void setLytContsCn(String lytContsCn) {
        this.lytContsCn = lytContsCn;
    }

    /**
     * 레이아웃컨텐츠 반환
     * 
     * @return String lytContsCn
     */
    public String getLytContsCn() {
        return lytContsCn;
    }

    /**
     * 파일경로 설정
     * 
     * @param filePathNm을(를) String filePathNm로 설정
     */
    public void setFilePathNm(String filePathNm) {
        this.filePathNm = filePathNm;
    }

    /**
     * 파일경로 반환
     * 
     * @return String filePathNm
     */
    public String getFilePathNm() {
        return filePathNm;
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
