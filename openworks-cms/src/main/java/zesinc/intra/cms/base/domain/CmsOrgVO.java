/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.base.domain;

import zesinc.web.vo.BaseVO;

/**
 * 부서/담당자 목록 조회(AutoComplete 조회용)
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 6.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsOrgVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -357981622081756417L;

    /** 타이틀명 */
    private String label;
    /** 타이틀 값 */
    private String value;
    /** 담당자ID */
    private String picId;
    /** 담당자명 */
    private String picNm;
    /** 부서코드 */
    private String deptCdId;
    /** 부서명 */
    private String deptNm;
    /** 상위부서명 */
    private String upperDeptNm;
    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 관리담당자ID */
    private String authorPicId;
    /** 관리담당자명 */
    private String authorPicNm;
    /** 등록자ID */
    private String rgtrId;
    /** 등록일 */
    private String regDt;

    /**
     * String label을 반환
     * 
     * @return String label
     */
    public String getLabel() {
        return label;
    }

    /**
     * label을 설정
     * 
     * @param label 을(를) String label로 설정
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * String value을 반환
     * 
     * @return String value
     */
    public String getValue() {
        return value;
    }

    /**
     * value을 설정
     * 
     * @param value 을(를) String value로 설정
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * String picId을 반환
     * 
     * @return String picId
     */
    public String getPicId() {
        return picId;
    }

    /**
     * picId을 설정
     * 
     * @param picId 을(를) String picId로 설정
     */
    public void setPicId(String picId) {
        this.picId = picId;
    }

    /**
     * String picNm을 반환
     * 
     * @return String picNm
     */
    public String getPicNm() {
        return picNm;
    }

    /**
     * picNm을 설정
     * 
     * @param picNm 을(를) String picNm로 설정
     */
    public void setPicNm(String picNm) {
        this.picNm = picNm;
    }

    /**
     * String deptCdId을 반환
     * 
     * @return String deptCdId
     */
    public String getDeptCdId() {
        return deptCdId;
    }

    /**
     * deptCdId을 설정
     * 
     * @param deptCdId 을(를) String deptCdId로 설정
     */
    public void setDeptCdId(String deptCdId) {
        this.deptCdId = deptCdId;
    }

    /**
     * String deptNm을 반환
     * 
     * @return String deptNm
     */
    public String getDeptNm() {
        return deptNm;
    }

    /**
     * deptNm을 설정
     * 
     * @param deptNm 을(를) String deptNm로 설정
     */
    public void setDeptNm(String deptNm) {
        this.deptNm = deptNm;
    }

    /**
     * String upperDeptNm을 반환
     * 
     * @return String upperDeptNm
     */
    public String getUpperDeptNm() {
        return upperDeptNm;
    }

    /**
     * upperDeptNm을 설정
     * 
     * @param upperDeptNm 을(를) String upperDeptNm로 설정
     */
    public void setUpperDeptNm(String upperDeptNm) {
        this.upperDeptNm = upperDeptNm;
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
     * String authorPicId을 반환
     * 
     * @return String authorPicId
     */
    public String getAuthorPicId() {
        return authorPicId;
    }

    /**
     * authorPicId을 설정
     * 
     * @param authorPicId 을(를) String authorPicId로 설정
     */
    public void setAuthorPicId(String authorPicId) {
        this.authorPicId = authorPicId;
    }

    /**
     * String authorPicNm을 반환
     * 
     * @return String authorPicNm
     */
    public String getAuthorPicNm() {
        return authorPicNm;
    }

    /**
     * authorPicNm을 설정
     * 
     * @param authorPicNm 을(를) String authorPicNm로 설정
     */
    public void setAuthorPicNm(String authorPicNm) {
        this.authorPicNm = authorPicNm;
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
