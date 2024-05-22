/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.cntnts.domain;

import java.util.List;

import zesinc.web.vo.BaseVO;

/**
 * CMS 컨텐츠를 비교하기 위한 정보를 담는 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 4.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsCntntsCompareVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -7235327541826571149L;

    /** 도메인코드 */
    private Integer siteSn;
    /** 사용자메뉴코드 */
    private String userMenuEngNm;
    /** 전체컨텐츠일련번호 */
    private List<Integer> contsSns;
    /** 좌측컨텐츠 일련번호 */
    private Integer leftCntntsSn;
    /** 우측컨텐츠 일련번호 */
    private Integer rightCntntsSn;;
    /** 좌측 컨텐츠 */
    private CmsCntntsVO leftCntnts;
    /** 우측 컨텐츠 */
    private CmsCntntsVO rightCntnts;

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
     * List<Integer> contsSns을 반환
     * 
     * @return List<Integer> contsSns
     */
    public List<Integer> getContsSns() {
        return contsSns;
    }

    /**
     * contsSns을 설정
     * 
     * @param contsSns 을(를) List<Integer> contsSns로 설정
     */
    public void setContsSns(List<Integer> contsSns) {
        this.contsSns = contsSns;
    }

    /**
     * Integer leftCntntsSn을 반환
     * 
     * @return Integer leftCntntsSn
     */
    public Integer getLeftCntntsSn() {
        return leftCntntsSn;
    }

    /**
     * leftCntntsSn을 설정
     * 
     * @param leftCntntsSn 을(를) Integer leftCntntsSn로 설정
     */
    public void setLeftCntntsSn(Integer leftCntntsSn) {
        this.leftCntntsSn = leftCntntsSn;
    }

    /**
     * Integer rightCntntsSn을 반환
     * 
     * @return Integer rightCntntsSn
     */
    public Integer getRightCntntsSn() {
        return rightCntntsSn;
    }

    /**
     * rightCntntsSn을 설정
     * 
     * @param rightCntntsSn 을(를) Integer rightCntntsSn로 설정
     */
    public void setRightCntntsSn(Integer rightCntntsSn) {
        this.rightCntntsSn = rightCntntsSn;
    }

    /**
     * CmsCntntsVO leftCntnts을 반환
     * 
     * @return CmsCntntsVO leftCntnts
     */
    public CmsCntntsVO getLeftCntnts() {
        return leftCntnts;
    }

    /**
     * leftCntnts을 설정
     * 
     * @param leftCntnts 을(를) CmsCntntsVO leftCntnts로 설정
     */
    public void setLeftCntnts(CmsCntntsVO leftCntnts) {
        this.leftCntnts = leftCntnts;
    }

    /**
     * CmsCntntsVO rightCntnts을 반환
     * 
     * @return CmsCntntsVO rightCntnts
     */
    public CmsCntntsVO getRightCntnts() {
        return rightCntnts;
    }

    /**
     * rightCntnts을 설정
     * 
     * @param rightCntnts 을(를) CmsCntntsVO rightCntnts로 설정
     */
    public void setRightCntnts(CmsCntntsVO rightCntnts) {
        this.rightCntnts = rightCntnts;
    }

}
