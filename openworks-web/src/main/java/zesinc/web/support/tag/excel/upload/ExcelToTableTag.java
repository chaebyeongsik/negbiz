/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag.excel.upload;

import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.tag.OpTagSupport;


/**
 * 
 * 
 * @author (주)제스아이엔씨 기술연구소
 *<pre>
 *<< 개정이력(Modification Information) >>
 *   
 *    수정일       수정자   수정내용
 *--------------  --------  -------------------------------
 * 2015. 2. 11.    박수정   최초작성
 *</pre>
 * @see
 */
public class ExcelToTableTag extends OpTagSupport {

    /** 미지정 시 기본 화면 JSP 템플릿 */
    private static final String DEFAULT_JSP = "excel/excelList.jsp";
    /** 미지정 시 input 사용여부 N */
    private static final String DEFAULT_INPUT_USE_YN = "Y";
    private static final int HEADER_LINE_CNT = 0;

    /** 불러오는 row 수의 제한 */
    private int rowNo;
    /** input 사용여부 기본값 N */
    private String inputUseYn;
    /** 헤더명 */
    private String[] headerNm;
    /** 헤더아이디 */
    private String[] headerId;
    /** input사이즈 */
    private String[] size;
    /** 헤더 줄 수(기본값:0) */
    private int headerLineCnt;

    /**
     * setHeaderNm 설명
     * @param headerNm
     */
    public void setHeaderNm(String headerNm) {
        headerNm = StringUtil.deleteWhitespace(StringUtil.cleanHtml(headerNm));
        this.headerNm = headerNm.split(",");
    }

    /**
     * setHeaderId 설명
     * @param headerId
     */
    public void setHeaderId(String headerId) {
        headerId = StringUtil.deleteWhitespace(StringUtil.cleanHtml(headerId));
        this.headerId = headerId.split(",");
    }

    /**
     * setInputUseYn 설명
     * @param inputUseYn
     */
    public void setInputUseYn(String inputUseYn) {
        this.inputUseYn = inputUseYn;
    }

    /**
     * setRowNo 설명
     * @param rowNo
     */
    public void setRowNo(int rowNo){
        this.rowNo = rowNo;
    }

    @Override
    public String getPage() {
        if(Validate.isEmpty(page)) {
            return DEFAULT_JSP;
        }

        return this.page;
    }

    /**
     * size을 설정
     * @param size 을(를) String[] size로 설정
     */
    public void setSize(String size) {
        size = StringUtil.deleteWhitespace(StringUtil.cleanHtml(size));
        this.size = size.split(",");
    }

    /**
     * int headerLineCnt을 반환
     * @return int headerLineCnt
     */
    public int getHeaderLineCnt() {
        if(Validate.isEmpty(headerLineCnt)) {
            return HEADER_LINE_CNT;
        }
        return headerLineCnt;
    }

    /**
     * headerLineCnt을 설정
     * @param headerLineCnt 을(를) int headerLineCnt로 설정
     */
    public void setHeaderLineCnt(int headerLineCnt) {
        this.headerLineCnt = headerLineCnt;
    }

    @Override
    public void beforeTag() {
        addAttribute("headerNm", headerNm); // 헤더명
        addAttribute("headerId", headerId); // 헤더아이디
        addAttribute("inputSize", size); // 인풋사이즈
        if(Validate.isNotEmpty(rowNo)) {
            addAttribute("rowNo", rowNo);
        }

        if(Validate.isEmpty(inputUseYn)) {
            addAttribute("inputUseYn", DEFAULT_INPUT_USE_YN);
        }else{
            addAttribute("inputUseYn", inputUseYn);
        }
        addAttribute("headerLineCnt", getHeaderLineCnt());
    }
}
