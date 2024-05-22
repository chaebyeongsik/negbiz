/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts.domain;

import java.util.Date;

import zesinc.web.validate.annotation.marker.Digits;
import zesinc.web.validate.annotation.marker.MaxLength;
import zesinc.web.validate.annotation.marker.Required;
import zesinc.web.vo.PageVO;

/**
 * 사용자메뉴컨텐츠 정보 VO 클레스
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
public class CmsCntntsVO extends PageVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -4287156136162186632L;

    /** 도메인코드 */
    @Required
    @Digits
    private Integer siteSn;

    /** 도메인명 */
    private String siteNm;

    /** 포트번호 */
    private Integer portSn;

    /** 컨텍스트경로 */
    private String sitePathNm;

    /** 사용자메뉴코드 */
    @Required
    @MaxLength(max = 100)
    private String userMenuEngNm;

    /** 사용자메뉴명 */
    private String menuNm;

    /** 제목 */
    private String ttl;

    /** 컨텐츠 경로 */
    private String bscPathNm;

    /** 관리자메뉴URL */
    private String mngrMenuUrlAddr;

    /** 사용자메뉴URL */
    private String userMenuUrlAddr;

    /** 컨텐츠일련번호 */
    @Digits
    private Integer contsSn;

    /** 레이아웃코드 */
    @MaxLength(max = 20)
    private String lytCdNo;

    /** 레이아웃명 */
    private String lytNm;

    /** 상단컨텐츠 */
    private String strtContsCn;

    /** 본문컨텐츠 */
    @Required
    private String mainContsCn;

    /** 자원일련번호 */
    private Integer dataSn;

    /** 자원버전 */
    private Integer chgCycl;

    /** 적용여부 */
    private String aplcnYn;

    /** 승인상태 */
    @MaxLength(max = 20)
    private String aprvSttsNo;

    /** 승인상태명 */
    private String confmSttusNm;

    /** 승인사유 */
    @MaxLength(max = 4000)
    private String aprvRsn;

    /** 승인자ID */
    @MaxLength(max = 20)
    private String autzrId;

    /** 승인자명 */
    private String confmerNm;

    /** 승인일시 */
    private Date aprvDt;

    /** 발행상태 */
    private String pblcnYn;

    /** 발행사유 */
    @Required
    @MaxLength(max = 4000)
    private String pblcnRsn;

    /** 발행자ID */
    private String isrId;

    /** 발행일시 */
    private Date pblcnDt;

    /** 예약일시 */
    private Date rsvtDt;

    /** 작성방법 */
    @Required
    private String wrtTypeNm;

    /** CSS파일 */
    private String cssFileNm;

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
     * 제목 반환
     * 
     * @return String ttl
     */
    public String getTtl() {
        return ttl;
    }

    /**
     * 제목 설정
     * 
     * @param ttl 을(를) String ttl로 설정
     */
    public void setTtl(String ttl) {
        this.ttl = ttl;
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
     * 기본경로 설정
     * 
     * @param bscPathNm 을(를) String bscPathNm로 설정
     */
    public void setBscPathNm(String bscPathNm) {
        this.bscPathNm = bscPathNm;
    }

    /**
     * String mngrMenuUrlAddr을 반환
     * 
     * @return String mngrMenuUrlAddr
     */
    public String getMngrMenuUrlAddr() {
        return mngrMenuUrlAddr;
    }

    /**
     * mngrMenuUrlAddr을 설정
     * 
     * @param mngrMenuUrlAddr 을(를) String mngrMenuUrlAddr로 설정
     */
    public void setMngrMenuUrlAddr(String mngrMenuUrlAddr) {
        this.mngrMenuUrlAddr = mngrMenuUrlAddr;
    }

    /**
     * String userMenuUrlAddr을 반환
     * 
     * @return String userMenuUrlAddr
     */
    public String getUserMenuUrlAddr() {
        return userMenuUrlAddr;
    }

    /**
     * userMenuUrlAddr을 설정
     * 
     * @param userMenuUrlAddr 을(를) String userMenuUrlAddr로 설정
     */
    public void setUserMenuUrlAddr(String userMenuUrlAddr) {
        this.userMenuUrlAddr = userMenuUrlAddr;
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
     * 본문컨텐츠 설정
     * 
     * @param mainContsCn을(를) String mainContsCn로 설정
     */
    public void setMainContsCn(String mainContsCn) {
        this.mainContsCn = mainContsCn;
    }

    /**
     * 본문컨텐츠 반환
     * 
     * @return String mainContsCn
     */
    public String getMainContsCn() {
        return mainContsCn;
    }

    /**
     * Integer dataSn을 반환
     * 
     * @return Integer dataSn
     */
    public Integer getDataSn() {
        return dataSn;
    }

    /**
     * dataSn을 설정
     * 
     * @param dataSn 을(를) Integer dataSn로 설정
     */
    public void setDataSn(Integer dataSn) {
        this.dataSn = dataSn;
    }

    /**
     * Integer chgCycl을 반환
     * 
     * @return Integer chgCycl
     */
    public Integer getChgCycl() {
        return chgCycl;
    }

    /**
     * chgCycl을 설정
     * 
     * @param chgCycl 을(를) Integer chgCycl로 설정
     */
    public void setChgCycl(Integer chgCycl) {
        this.chgCycl = chgCycl;
    }

    /**
     * 적용여부 설정
     * 
     * @param aplcnYn을(를) String aplcnYn로 설정
     */
    public void setAplcnYn(String aplcnYn) {
        this.aplcnYn = aplcnYn;
    }

    /**
     * 적용여부 반환
     * 
     * @return String aplcnYn
     */
    public String getAplcnYn() {
        return aplcnYn;
    }

    /**
     * 승인상태 설정
     * 
     * @param aprvSttsNo을(를) String aprvSttsNo로 설정
     */
    public void setAprvSttsNo(String aprvSttsNo) {
        this.aprvSttsNo = aprvSttsNo;
    }

    /**
     * 승인상태 반환
     * 
     * @return String aprvSttsNo
     */
    public String getAprvSttsNo() {
        return aprvSttsNo;
    }

    /**
     * 승인상태명 반환
     * 
     * @return String confmSttusNm
     */
    public String getConfmSttusNm() {
        return confmSttusNm;
    }

    /**
     * 승인상태명 설정
     * 
     * @param confmSttusNm 을(를) String confmSttusNm로 설정
     */
    public void setConfmSttusNm(String confmSttusNm) {
        this.confmSttusNm = confmSttusNm;
    }

    /**
     * 발행사유 설정
     * 
     * @param pblcnRsn을(를) String pblcnRsn로 설정
     */
    public void setPblcnRsn(String pblcnRsn) {
        this.pblcnRsn = pblcnRsn;
    }

    /**
     * 발행사유 반환
     * 
     * @return String pblcnRsn
     */
    public String getPblcnRsn() {
        return pblcnRsn;
    }

    /**
     * String isrId을 반환
     * 
     * @return String isrId
     */
    public String getIsrId() {
        return isrId;
    }

    /**
     * isrId을 설정
     * 
     * @param isrId 을(를) String isrId로 설정
     */
    public void setIsrId(String isrId) {
        this.isrId = isrId;
    }

    /**
     * Date pblcnDt을 반환
     * 
     * @return Date pblcnDt
     */
    public Date getPblcnDt() {
        return pblcnDt;
    }

    /**
     * pblcnDt을 설정
     * 
     * @param pblcnDt 을(를) Date pblcnDt로 설정
     */
    public void setPblcnDt(Date pblcnDt) {
        this.pblcnDt = pblcnDt;
    }

    /**
     * 승인사유 설정
     * 
     * @param aprvRsn을(를) String aprvRsn로 설정
     */
    public void setAprvRsn(String aprvRsn) {
        this.aprvRsn = aprvRsn;
    }

    /**
     * 승인사유 반환
     * 
     * @return String aprvRsn
     */
    public String getAprvRsn() {
        return aprvRsn;
    }

    /**
     * 승인자ID 설정
     * 
     * @param autzrId을(를) String autzrId로 설정
     */
    public void setAutzrId(String autzrId) {
        this.autzrId = autzrId;
    }

    /**
     * 승인자ID 반환
     * 
     * @return String autzrId
     */
    public String getAutzrId() {
        return autzrId;
    }

    /**
     * String confmerNm을 반환
     * 
     * @return String confmerNm
     */
    public String getConfmerNm() {
        return confmerNm;
    }

    /**
     * confmerNm을 설정
     * 
     * @param confmerNm 을(를) String confmerNm로 설정
     */
    public void setConfmerNm(String confmerNm) {
        this.confmerNm = confmerNm;
    }

    /**
     * 승인일시 설정
     * 
     * @param aprvDt을(를) Date aprvDt로 설정
     */
    public void setAprvDt(Date aprvDt) {
        this.aprvDt = aprvDt;
    }

    /**
     * 승인일시 반환
     * 
     * @return Date aprvDt
     */
    public Date getAprvDt() {
        return aprvDt;
    }

    /**
     * String pblcnYn을 반환
     * 
     * @return String pblcnYn
     */
    public String getPblicteSttus() {
        return pblcnYn;
    }

    /**
     * pblcnYn을 설정
     * 
     * @param pblicteSttus 을(를) String pblcnYn로 설정
     */
    public void setPblicteSttus(String pblicteSttus) {
        this.pblcnYn = pblicteSttus;
    }

    /**
     * Date rsvtDt을 반환
     * 
     * @return Date rsvtDt
     */
    public Date getRsvtDt() {
        return rsvtDt;
    }

    /**
     * rsvtDt을 설정
     * 
     * @param rsvtDt 을(를) Date rsvtDt로 설정
     */
    public void setRsvtDt(Date rsvtDt) {
        this.rsvtDt = rsvtDt;
    }

    /**
     * 작성방법 설정
     * 
     * @param wrtTypeNm을(를) String wrtTypeNm로 설정
     */
    public void setWrtTypeNm(String wrtTypeNm) {
        this.wrtTypeNm = wrtTypeNm;
    }

    /**
     * 작성방법 반환
     * 
     * @return String wrtTypeNm
     */
    public String getWrtTypeNm() {
        return wrtTypeNm;
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
