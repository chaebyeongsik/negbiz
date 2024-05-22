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
 * 게시판환경설정 정보 VO 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-05-04.    황신욱   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class BbsCtgryCacheVO extends BaseVO {

    /** serialVersionUID */
    private static final long serialVersionUID = -6148391621625038524L;

    /** 게시판코드 */
    private Integer bbsSn = 0;
    /** 분류코드 */
    private Integer clsfNo = 0;
    /** 분류명 */
    private String clsfNm;
    /** 정렬순서 */
    private Integer sortSn = 0;
    /** 하위분류코드 */
    private Integer lwrkClsfSn;
    /** 하위분류코드명 */
    private String lwrkClsfNm;
    /** 하위카테고리 목록 */
    private List<BbsCtgryCacheVO> lwprtCtgryList;

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
     * Integer clsfNo을 반환
     * 
     * @return Integer clsfNo
     */
    public Integer getClsfNo() {
        return clsfNo;
    }

    /**
     * clsfNo을 설정
     * 
     * @param clsfNo 을(를) Integer clsfNo로 설정
     */
    public void setClsfNo(Integer clsfNo) {
        this.clsfNo = clsfNo;
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
     * Integer sortSn을 반환
     * 
     * @return Integer sortSn
     */
    public Integer getSortSn() {
        return sortSn;
    }

    /**
     * sortSn을 설정
     * 
     * @param sortSn 을(를) Integer sortSn로 설정
     */
    public void setSortSn(Integer sortSn) {
        this.sortSn = sortSn;
    }

    /**
     * Integer lwrkClsfSn을 반환
     * 
     * @return Integer lwrkClsfSn
     */
    public Integer getLwrkClsfSn() {
        return lwrkClsfSn;
    }

    /**
     * lwrkClsfSn을 설정
     * 
     * @param lwrkClsfSn 을(를) Integer lwrkClsfSn로 설정
     */
    public void setLwrkClsfSn(Integer lwrkClsfSn) {
        this.lwrkClsfSn = lwrkClsfSn;
    }

    /**
     * String lwrkClsfNm을 반환
     * 
     * @return String lwrkClsfNm
     */
    public String getLwrkClsfNm() {
        return lwrkClsfNm;
    }

    /**
     * lwrkClsfNm을 설정
     * 
     * @param lwrkClsfNm 을(를) String lwrkClsfNm로 설정
     */
    public void setLwrkClsfNm(String lwrkClsfNm) {
        this.lwrkClsfNm = lwrkClsfNm;
    }

    /**
     * List<BbsCtgryCacheVO> lwprtCtgryList을 반환
     * 
     * @return List<BbsCtgryCacheVO> lwprtCtgryList
     */
    public List<BbsCtgryCacheVO> getLwprtCtgryList() {
        return lwprtCtgryList;
    }

    /**
     * lwprtCtgryList을 설정
     * 
     * @param lwprtCtgryList 을(를) List<BbsCtgryCacheVO> lwprtCtgryList로 설정
     */
    public void setLwprtCtgryList(List<BbsCtgryCacheVO> lwprtCtgryList) {
        this.lwprtCtgryList = lwprtCtgryList;
    }

}
