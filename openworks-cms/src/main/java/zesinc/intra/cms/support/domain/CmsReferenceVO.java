/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support.domain;

import java.util.List;

import zesinc.web.vo.BaseVO;

/**
 * CMS 컨트롤에서 대상 기능에 대한 메뉴설정 정보를 전달 받기 위한 BEAN으로
 * CMS 메뉴설정에서 기능을 호출할 때 정보를 담아서 전달하도록 한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 9. 14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsReferenceVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -2707473875939702929L;
    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자 서비스 한글 명 */
    private String name;
    /** 사용자 서비스 키 */
    private String key;
    /** 관리자 서비스 URL */
    private String mngrMenuUrlAddr;
    /** 사용자 서비스 대표 URL */
    private String userMainMenuUrl;
    /** 사용자 서비스 URL */
    private List<String> lwprtMenuUrlAddr;
    /** Spring BEAN 명 */
    private String beanNm;
    /** 사용자 서비스 정보를 받을 함수 명 */
    private String methodNm;
    /** beanNm + methodNm */
    private String cntrlNm;

    /**
     * CmsReferenceVO.java 생성자
     */
    public CmsReferenceVO() {
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
     * String name을 반환
     * 
     * @return String name
     */
    public String getName() {
        return name;
    }

    /**
     * name을 설정
     * 
     * @param name 을(를) String name로 설정
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * String key을 반환
     * 
     * @return String key
     */
    public String getKey() {
        return key;
    }

    /**
     * key을 설정
     * 
     * @param key 을(를) String key로 설정
     */
    public void setKey(String key) {
        this.key = key;
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
     * String userMainMenuUrl을 반환
     * 
     * @return String userMainMenuUrl
     */
    public String getUserMainMenuUrl() {
        return userMainMenuUrl;
    }

    /**
     * userMainMenuUrl을 설정
     * 
     * @param userMainMenuUrl 을(를) String userMainMenuUrl로 설정
     */
    public void setUserMainMenuUrl(String userMainMenuUrl) {
        this.userMainMenuUrl = userMainMenuUrl;
    }

    /**
     * List<String> lwprtMenuUrlAddr을 반환
     * 
     * @return List<String> lwprtMenuUrlAddr
     */
    public List<String> getLwprtMenuUrlAddr() {
        return lwprtMenuUrlAddr;
    }

    /**
     * lwprtMenuUrlAddr을 설정
     * 
     * @param lwprtMenuUrlAddr 을(를) List<String> lwprtMenuUrlAddr로 설정
     */
    public void setLwprtMenuUrlAddr(List<String> lwprtMenuUrlAddr) {
        this.lwprtMenuUrlAddr = lwprtMenuUrlAddr;
    }

    /**
     * String beanNm을 반환
     * 
     * @return String beanNm
     */
    public String getBeanNm() {
        return beanNm;
    }

    /**
     * beanNm을 설정
     * 
     * @param beanNm 을(를) String beanNm로 설정
     */
    public void setBeanNm(String beanNm) {
        this.beanNm = beanNm;
    }

    /**
     * String methodNm을 반환
     * 
     * @return String methodNm
     */
    public String getMethodNm() {
        return methodNm;
    }

    /**
     * methodNm을 설정
     * 
     * @param methodNm 을(를) String methodNm로 설정
     */
    public void setMethodNm(String methodNm) {
        this.methodNm = methodNm;
    }

    /**
     * String cntrlNm을 반환
     * 
     * @return String cntrlNm
     */
    public String getCntrlNm() {
        return cntrlNm;
    }

    /**
     * cntrlNm을 설정
     * 
     * @param cntrlNm 을(를) String cntrlNm로 설정
     */
    public void setCntrlNm(String cntrlNm) {
        this.cntrlNm = cntrlNm;
    }

}
