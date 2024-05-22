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
 * 사용자메뉴 화면에 표시할 담당자 정보 VO 클레스
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
public class CmsMngrCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -1574805987073747319L;

    /** 담당자ID */
    private String picId;
    /** 부서코드 */
    private String deptCdId;
    /** 부서명 */
    private String deptNm;
    /** 담당자명 */
    private String picNm;
    /** 직급코드 */
    private String jbgdCdId;
    /** 직급명 */
    private String clsfNm;
    /** 담당업무 */
    private String taskCn;
    /** 전화번호1 */
    private String rgnTelno;
    /** 전화번호2 */
    private String telofcTelno;
    /** 전화번호3 */
    private String indivTelno;
    /** 팩스번호1 */
    private String rgnFxno;
    /** 팩스번호2 */
    private String telofcFxno;
    /** 팩스번호3 */
    private String indivFxno;
    /** 휴대전화번호1 */
    private String mblRgnTelno;
    /** 휴대전화번호2 */
    private String mblTelofcTelno;
    /** 휴대전화번호3 */
    private String mblIndivTelno;
    /** 이메일1 */
    private String emlId;
    /** 이메일2 */
    private String emlSiteNm;
    /** 상태코드 */
    private Integer sttsSn;

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
     * String jbgdCdId을 반환
     * 
     * @return String jbgdCdId
     */
    public String getJbgdCdId() {
        return jbgdCdId;
    }

    /**
     * jbgdCdId을 설정
     * 
     * @param jbgdCdId 을(를) String jbgdCdId로 설정
     */
    public void setJbgdCdId(String jbgdCdId) {
        this.jbgdCdId = jbgdCdId;
    }

    /**
     * String clsfNm을 반환
     * 
     * @return String clsfNm
     */
    public String getClsfNm() {
        return clsfNm;
    }

    /**
     * clsfNm을 설정
     * 
     * @param clsfNm 을(를) String clsfNm로 설정
     */
    public void setClsfNm(String clsfNm) {
        this.clsfNm = clsfNm;
    }

    /**
     * String taskCn을 반환
     * 
     * @return String taskCn
     */
    public String getTaskCn() {
        return taskCn;
    }

    /**
     * taskCn을 설정
     * 
     * @param taskCn 을(를) String taskCn로 설정
     */
    public void setTaskCn(String taskCn) {
        this.taskCn = taskCn;
    }

    /**
     * String rgnTelno을 반환
     * 
     * @return String rgnTelno
     */
    public String getRgnTelno() {
        return rgnTelno;
    }

    /**
     * rgnTelno을 설정
     * 
     * @param rgnTelno 을(를) String rgnTelno로 설정
     */
    public void setRgnTelno(String rgnTelno) {
        this.rgnTelno = rgnTelno;
    }

    /**
     * String telofcTelno을 반환
     * 
     * @return String telofcTelno
     */
    public String getTelofcTelno() {
        return telofcTelno;
    }

    /**
     * telofcTelno을 설정
     * 
     * @param telofcTelno 을(를) String telofcTelno로 설정
     */
    public void setTelofcTelno(String telofcTelno) {
        this.telofcTelno = telofcTelno;
    }

    /**
     * String indivTelno을 반환
     * 
     * @return String indivTelno
     */
    public String getIndivTelno() {
        return indivTelno;
    }

    /**
     * indivTelno을 설정
     * 
     * @param indivTelno 을(를) String indivTelno로 설정
     */
    public void setIndivTelno(String indivTelno) {
        this.indivTelno = indivTelno;
    }

    /**
     * String rgnFxno을 반환
     * 
     * @return String rgnFxno
     */
    public String getRgnFxno() {
        return rgnFxno;
    }

    /**
     * rgnFxno을 설정
     * 
     * @param rgnFxno 을(를) String rgnFxno로 설정
     */
    public void setRgnFxno(String rgnFxno) {
        this.rgnFxno = rgnFxno;
    }

    /**
     * String telofcFxno을 반환
     * 
     * @return String telofcFxno
     */
    public String getTelofcFxno() {
        return telofcFxno;
    }

    /**
     * telofcFxno을 설정
     * 
     * @param telofcFxno 을(를) String telofcFxno로 설정
     */
    public void setTelofcFxno(String telofcFxno) {
        this.telofcFxno = telofcFxno;
    }

    /**
     * String indivFxno을 반환
     * 
     * @return String indivFxno
     */
    public String getIndivFxno() {
        return indivFxno;
    }

    /**
     * indivFxno을 설정
     * 
     * @param indivFxno 을(를) String indivFxno로 설정
     */
    public void setIndivFxno(String indivFxno) {
        this.indivFxno = indivFxno;
    }

    /**
     * String mblRgnTelno을 반환
     * 
     * @return String mblRgnTelno
     */
    public String getMblRgnTelno() {
        return mblRgnTelno;
    }

    /**
     * mblRgnTelno을 설정
     * 
     * @param mblRgnTelno 을(를) String mblRgnTelno로 설정
     */
    public void setMblRgnTelno(String mblRgnTelno) {
        this.mblRgnTelno = mblRgnTelno;
    }

    /**
     * String mblTelofcTelno을 반환
     * 
     * @return String mblTelofcTelno
     */
    public String getMblTelofcTelno() {
        return mblTelofcTelno;
    }

    /**
     * mblTelofcTelno을 설정
     * 
     * @param mblTelofcTelno 을(를) String mblTelofcTelno로 설정
     */
    public void setMblTelofcTelno(String mblTelofcTelno) {
        this.mblTelofcTelno = mblTelofcTelno;
    }

    /**
     * String mblIndivTelno을 반환
     * 
     * @return String mblIndivTelno
     */
    public String getMblIndivTelno() {
        return mblIndivTelno;
    }

    /**
     * mblIndivTelno을 설정
     * 
     * @param mblIndivTelno 을(를) String mblIndivTelno로 설정
     */
    public void setMblIndivTelno(String mblIndivTelno) {
        this.mblIndivTelno = mblIndivTelno;
    }

    /**
     * String emlId을 반환
     * 
     * @return String emlId
     */
    public String getEmlId() {
        return emlId;
    }

    /**
     * emlId을 설정
     * 
     * @param emlId 을(를) String emlId로 설정
     */
    public void setEmlId(String emlId) {
        this.emlId = emlId;
    }

    /**
     * String emlSiteNm을 반환
     * 
     * @return String emlSiteNm
     */
    public String getEmlSiteNm() {
        return emlSiteNm;
    }

    /**
     * emlSiteNm을 설정
     * 
     * @param emlSiteNm 을(를) String emlSiteNm로 설정
     */
    public void setEmlSiteNm(String emlSiteNm) {
        this.emlSiteNm = emlSiteNm;
    }

    /**
     * Integer sttsSn을 반환
     * 
     * @return Integer sttsSn
     */
    public Integer getSttsSn() {
        return sttsSn;
    }

    /**
     * sttsSn을 설정
     * 
     * @param sttsSn 을(를) Integer sttsSn로 설정
     */
    public void setSttsSn(Integer sttsSn) {
        this.sttsSn = sttsSn;
    }

}
