/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.excel.download;

import java.util.ArrayList;
import java.util.List;

import zesinc.component.excel.download.domain.ExcelDownloadVO;

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
public interface ExcelDownloadService {

    /**
     * queryId로 수행한 리스트에 헤더값을 추가한 목록을 반환
     * 
     * @param String queryId, String[] headerId, String[] headerNm,
     *        ExcelDownloadVO excelDownloadVo
     * @return List
     */
    ArrayList<List<String>> selectExcelList(String queryId, String[] headerId, String[] headerNm, ExcelDownloadVO excelDownloadVo);

}
