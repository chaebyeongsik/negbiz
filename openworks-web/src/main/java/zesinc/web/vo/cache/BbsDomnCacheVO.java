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
 * 도메인별 게시판 스킨 설정
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 9. 17.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BbsDomnCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -9129614935584655267L;

    /** 게시판코드 */
    private Integer bbsSn;
    /** 게시판코드명 */
    private String bbsSnNm;
    /** 목록스킨 */
    private Integer siteSn;
    /** 목록스킨 */
    private String lstTmpltNm;
    /** 읽기스킨 */
    private String pstTmpltNm;
    /** 폼스킨 */
    private String inptTmpltNm;

    /**
     * Integer bbsSn을 반환
     * 
     * @return Integer bbsSn
     */
    public Integer getBbsSn() {
        return bbsSn;
    }

    /**
     * bbsSn을 설정
     * 
     * @param bbsSn 을(를) Integer bbsSn로 설정
     */
    public void setBbsSn(Integer bbsSn) {
        this.bbsSn = bbsSn;
    }

    /**
     * String bbsSnNm을 반환
     * 
     * @return String bbsSnNm
     */
    public String getBbsSnNm() {
        return bbsSnNm;
    }

    /**
     * bbsSnNm을 설정
     * 
     * @param bbsSnNm 을(를) String bbsSnNm로 설정
     */
    public void setBbsSnNm(String bbsSnNm) {
        this.bbsSnNm = bbsSnNm;
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
     * String lstTmpltNm을 반환
     * 
     * @return String lstTmpltNm
     */
    public String getLstTmpltNm() {
        return lstTmpltNm;
    }

    /**
     * lstTmpltNm을 설정
     * 
     * @param lstTmpltNm 을(를) String lstTmpltNm로 설정
     */
    public void setLstTmpltNm(String lstTmpltNm) {
        this.lstTmpltNm = lstTmpltNm;
    }

    /**
     * String pstTmpltNm을 반환
     * 
     * @return String pstTmpltNm
     */
    public String getPstTmpltNm() {
        return pstTmpltNm;
    }

    /**
     * pstTmpltNm을 설정
     * 
     * @param pstTmpltNm 을(를) String pstTmpltNm로 설정
     */
    public void setPstTmpltNm(String pstTmpltNm) {
        this.pstTmpltNm = pstTmpltNm;
    }

    /**
     * String inptTmpltNm을 반환
     * 
     * @return String inptTmpltNm
     */
    public String getInptTmpltNm() {
        return inptTmpltNm;
    }

    /**
     * inptTmpltNm을 설정
     * 
     * @param inptTmpltNm 을(를) String inptTmpltNm로 설정
     */
    public void setInptTmpltNm(String inptTmpltNm) {
        this.inptTmpltNm = inptTmpltNm;
    }

}
