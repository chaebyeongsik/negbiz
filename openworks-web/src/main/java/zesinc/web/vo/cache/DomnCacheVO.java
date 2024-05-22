/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo.cache;

import java.util.List;

import zesinc.web.validate.annotation.marker.RangeLength;
import zesinc.web.vo.BaseVO;

/**
 * 도메인 정보 캐시 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class DomnCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 3731797221279795234L;

    /** 도메인코드 */
    private Integer siteSn;

    /** 도메인명 */
    private String siteNm;

    /** 도메인설명 */
    private String siteExpln;

    /** 포트번호 */
    private Integer portSn;

    /** 컨텍스트경로 */
    private String sitePathNm;

    /** 언어코드 */
    private String langCdId;

    /** 담당부서코드 */
    private String tkcgDeptCdId;

    /** 담당자ID */
    @RangeLength(max = 30)
    private String picId;

    /** 보안프로토콜여부 */
    private String httpsYn;

    /** 보안포트번호 */
    private Integer scrtyPortSn;

    /** 실컨텍스트여부 */
    private String actlStngYn;

    /** 사용여부 */
    private String useYn;

    /** 도메인 그룹 목록 */
    private List<DomnGroupCacheVO> domnGroupList;

    /**
     * 도메인코드을 설정
     * 
     * @param siteSn을(를) Integer siteSn로 설정
     */
    public void setSiteSn(Integer siteSn) {
        this.siteSn = siteSn;
    }

    /**
     * 도메인코드을 반환
     * 
     * @return Integer siteSn
     */
    public Integer getSiteSn() {
        return siteSn;
    }

    /**
     * 도메인명을 설정
     * 
     * @param siteNm을(를) String siteNm로 설정
     */
    public void setSiteNm(String siteNm) {
        this.siteNm = siteNm;
    }

    /**
     * 도메인명을 반환
     * 
     * @return String siteNm
     */
    public String getSiteNm() {
        return siteNm;
    }

    /**
     * 도메인설명을 설정
     * 
     * @param siteExpln을(를) String siteExpln로 설정
     */
    public void setSiteExpln(String siteExpln) {
        this.siteExpln = siteExpln;
    }

    /**
     * 도메인설명을 반환
     * 
     * @return String siteExpln
     */
    public String getSiteExpln() {
        return siteExpln;
    }

    /**
     * 포트번호을 설정
     * 
     * @param portSn을(를) Integer portSn로 설정
     */
    public void setPortSn(Integer portSn) {
        this.portSn = portSn;
    }

    /**
     * 포트번호을 반환
     * 
     * @return Integer portSn
     */
    public Integer getPortSn() {
        return portSn;
    }

    /**
     * 컨텍스트경로을 설정
     * 
     * @param sitePathNm을(를) String sitePathNm로 설정
     */
    public void setSitePathNm(String sitePathNm) {
        this.sitePathNm = sitePathNm;
    }

    /**
     * 컨텍스트경로을 반환
     * 
     * @return String sitePathNm
     */
    public String getSitePathNm() {
        return sitePathNm;
    }

    /**
     * 언어코드을 설정
     * 
     * @param langCdId을(를) String langCdId로 설정
     */
    public void setLangCdId(String langCdId) {
        this.langCdId = langCdId;
    }

    /**
     * 언어코드을 반환
     * 
     * @return String langCdId
     */
    public String getLangCdId() {
        return langCdId;
    }

    /**
     * 담당부서코드을 설정
     * 
     * @param tkcgDeptCdId을(를) String tkcgDeptCdId로 설정
     */
    public void setTkcgDeptCdId(String tkcgDeptCdId) {
        this.tkcgDeptCdId = tkcgDeptCdId;
    }

    /**
     * 담당부서코드을 반환
     * 
     * @return String tkcgDeptCdId
     */
    public String getTkcgDeptCdId() {
        return tkcgDeptCdId;
    }

    /**
     * 담당자ID을 설정
     * 
     * @param picId을(를) String picId로 설정
     */
    public void setPicId(String picId) {
        this.picId = picId;
    }

    /**
     * 담당자ID을 반환
     * 
     * @return String picId
     */
    public String getPicId() {
        return picId;
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
     * 보안포트번호 반환
     * 
     * @return Integer scrtyPortSn
     */
    public Integer getScrtyPortSn() {
        return scrtyPortSn;
    }

    /**
     * 보안포트번호 설정
     * 
     * @param scrtyPortSn 을(를) Integer scrtyPortSn로 설정
     */
    public void setScrtyPortSn(Integer scrtyPortSn) {
        this.scrtyPortSn = scrtyPortSn;
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
     * 사용여부을 설정
     * 
     * @param useYn을(를) String useYn로 설정
     */
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    /**
     * 사용여부을 반환
     * 
     * @return String useYn
     */
    public String getUseYn() {
        return useYn;
    }

    /**
     * List<DomnGroupVO> domnGroupList을 반환
     * 
     * @return List<DomnGroupVO> domnGroupList
     */
    public List<DomnGroupCacheVO> getDomnGroupList() {
        return domnGroupList;
    }

    /**
     * domnGroupList을 설정
     * 
     * @param domnGroupList 을(를) List<DomnGroupVO> domnGroupList로 설정
     */
    public void setDomnGroupList(List<DomnGroupCacheVO> domnGroupList) {
        this.domnGroupList = domnGroupList;
    }

}
