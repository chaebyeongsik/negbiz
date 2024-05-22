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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.excel.download.domain.ExcelDownloadVO;
import zesinc.core.config.Config;
import zesinc.core.excel.CreateExcel;
import zesinc.core.lang.Validate;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.support.BaseConfig;

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
@Controller
@RequestMapping(value = "/**/component/excel/download")
public class ExcelDownloadController extends BaseController {

    @Resource(name = "opExcelDownloadService")
    private ExcelDownloadService service;

    /**
     * 엑셀 다운로드
     * 
     * @param String excelKey (excel-commons-config.xml에 등록한 key값)
     * @param Map paramMap (검색조건)
     *        ExcelDownloadVO가 BaseVO를 상속받고 있어 q_시작되는 검색조건은 바인딩처리됨
     * @return excel
     */
    @RequestMapping(value = "ND_excelDownload.do")
    public String excelDownload(HttpServletRequest request, Model model, ExcelDownloadVO excelDownloadVo) {

        // queryId는 해당메뉴의 sql.xml에 쿼리 생성 resultType은 hmap으로 리턴
        String queryId = Config.getString("excel-config." + excelDownloadVo.getExcelKey() + ".queryId");
        String[] headerId = Config.getString("excel-config." + excelDownloadVo.getExcelKey() + ".headerId").split(",");
        String[] headerNm = Config.getString("excel-config." + excelDownloadVo.getExcelKey() + ".headerNm").split(",");
        
        String excelFileName = "excel";
        // Taglib 파일명을 입력시 excel-commons0config.xml의 fileNm보다 우선시 됨
        if (Validate.isEmpty(excelDownloadVo.getExcelFileNm())) {
            excelFileName = Config.getString("excel-config." + excelDownloadVo.getExcelKey() + ".fileNm");
        } else {
            excelFileName = excelDownloadVo.getExcelFileNm();
        }
        

        model.addAttribute(BaseConfig.EXCEL_FILE_NAME, excelFileName);

        ArrayList<List<String>> excelList = service.selectExcelList(queryId, headerId, headerNm, excelDownloadVo);

        HSSFWorkbook workbook = CreateExcel.excelCreate(excelList);

        return responseExcel(model, workbook);
    }

}
