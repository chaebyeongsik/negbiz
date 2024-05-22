/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.excel.download.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import zesinc.component.excel.download.ExcelDownloadDAO;
import zesinc.component.excel.download.ExcelDownloadService;
import zesinc.component.excel.download.domain.ExcelDownloadVO;

/**
 * 설명을 입력하세요.
 * 
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
@Service("opExcelDownloadService")
public class ExcelDownloadServiceImpl implements ExcelDownloadService {

    @Resource
    private ExcelDownloadDAO opExcelDownloadDao;

    /**
     * queryId로 수행한 리스트에 헤더값을 추가한 목록을 반환
     * 
     * @param String queryId, String[] headerId, String[] headerNm,
     *        ExcelDownloadVO excelDownloadVo
     * @return List
     */
    @Override
    @SuppressWarnings("rawtypes")
    public ArrayList<List<String>> selectExcelList(String queryId, String[] headerId, String[] headerNm, ExcelDownloadVO excelDownloadVo) {

        // 출력할 목록을 불러온다.
        List<Map> dataList = opExcelDownloadDao.dataList(queryId, excelDownloadVo);
        ArrayList<List<String>> excelList = new ArrayList<List<String>>();

        for(int i = 0 ; i < dataList.size() ; i++) {
            if(i == 0) {
                // 리스트의 첫라인에 컬럼명을 추가
                ArrayList<String> headerNmList = new ArrayList<String>(Arrays.asList(headerNm));
                excelList.add(i, headerNmList);
            }

            HashMap dataMap = (HashMap) dataList.get(i);
            List<String> rowData = new ArrayList<String>();
            for(int j = 0 ; j < headerId.length ; j++) {
                // 헤더ID를 기준으로 출력할 목록을 재가공한다.(null인 경우에는 공백처리)
                String colData = dataMap.get(headerId[j]) == null ? "" : String.valueOf(dataMap.get(headerId[j]));
                rowData.add(colData);
            }
            excelList.add(i + 1, rowData);
        }

        return excelList;
    }

}
