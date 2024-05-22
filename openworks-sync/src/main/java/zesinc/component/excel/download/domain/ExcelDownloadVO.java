/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.excel.download.domain;

import zesinc.web.vo.PageVO;

/**
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 5.    황신욱   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ExcelDownloadVO extends PageVO {

    /** serialVersionUID */
    private static final long serialVersionUID = 4694866616790082979L;

    /** 엑셀고유키(xml에 정의해놓은 key) */
    private String excelKey;

    /** 엑셀파일명 */
    private String excelFileNm;

    /**
     * String excelKey을 반환
     * 
     * @return String excelKey
     */
    public String getExcelKey() {
        return excelKey;
    }

    /**
     * excelKey을 설정
     * 
     * @param excelKey 을(를) String excelKey로 설정
     */
    public void setExcelKey(String excelKey) {
        this.excelKey = excelKey;
    }

    /**
     * String excelFileNm을 반환
     * 
     * @return String excelFileNm
     */
    public String getExcelFileNm() {
        return excelFileNm;
    }

    /**
     * excelFileNm을 설정
     * 
     * @param excelFileNm 을(를) String excelFileNm로 설정
     */
    public void setExcelFileNm(String excelFileNm) {
        this.excelFileNm = excelFileNm;
    }

}
