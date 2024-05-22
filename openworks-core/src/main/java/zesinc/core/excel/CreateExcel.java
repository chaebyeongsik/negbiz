/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.excel;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * HSSFWorkbook 객체 생성
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 2. 13.    황신욱   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CreateExcel {

    /**
     * 넘겨진 리스트를 통하여 workbook객체를 반환한다.
     *
     * @param excelList
     * @return workbook
     */
    public static HSSFWorkbook excelCreate(List<List<String>> excelList) {

        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet sheet = workbook.createSheet();

        // row수 만큼 loop를 돌림
        for(int i = 0 ; i < excelList.size() ; i++) {
            HSSFRow row = sheet.createRow(i);
            List<String> celList = excelList.get(i);
            int colIndex = 0;

            // col수 만큼 loop를 돌면서 각각 col에 데이터 삽입
            for(int j = 0 ; j < celList.size() ; j++) {
                row.createCell(colIndex++).setCellValue(celList.get(j));
            }

        }

        return workbook;
    }

}
