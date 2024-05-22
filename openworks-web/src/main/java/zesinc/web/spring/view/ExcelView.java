/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.view;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.AbstractView;

import zesinc.core.config.Config;
import zesinc.web.support.BaseConfig;

/**
 * Apache POI 컴포넌트를 이용한 엑셀 다운로드 VIEW 
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see org.apache.poi.hssf.usermodel.HSSFWorkbook
 * @see AbstractExcelView
 */
public class ExcelView extends AbstractView {

    private static String DOWN_FILE_NAME_ENCODE_FROM = Config.getString("webapp-config.downFileNameEncodeFrom");
    private static String DOWN_FILE_NAME_ENCODE_TO = Config.getString("webapp-config.downFileNameEncodeTo");

    /** The content type for an Excel response */
    public static final String DEFAULT_CONTENT_TYPE = "application/vnd.ms-excel";

    @Override
    protected final void renderMergedOutputModel(
        Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String encodedFileNm = (String) model.get(BaseConfig.EXCEL_FILE_NAME) + ".xls";
        HSSFWorkbook workbook = (HSSFWorkbook) model.get(BaseConfig.EXCEL_DATA_KEY);

        response.setHeader("Content-Transfer-Encoding", "binary");
        setContentType(DEFAULT_CONTENT_TYPE);
        setDisposition(encodedFileNm, request, response);

        ServletOutputStream out = response.getOutputStream();
        try {
            workbook.write(response.getOutputStream());
            out.flush();
        } catch (Exception e) {
            if(logger.isErrorEnabled()) {
                logger.error("Download error : " + e);
            }
        } finally {
            if(out != null) {
                try {
                    out.close();
                } catch (Exception e) {}
            }
        }
    }

    /**
     * Disposition 설정
     * 
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    private void setDisposition(String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String browser = getBrowser(request);

        String dispositionPrefix = "attachment; filename=";
        String encodedFilename = null;

        if(browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, DOWN_FILE_NAME_ENCODE_FROM).replaceAll("\\+", "%20");
        } else if(browser.equals("Trident")) { // IE11 문자열 깨짐 방지
            encodedFilename = URLEncoder.encode(filename, DOWN_FILE_NAME_ENCODE_FROM).replaceAll("\\+", "%20");
        } else if(browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes(DOWN_FILE_NAME_ENCODE_FROM), DOWN_FILE_NAME_ENCODE_TO) + "\"";
        } else if(browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes(DOWN_FILE_NAME_ENCODE_FROM), DOWN_FILE_NAME_ENCODE_TO) + "\"";
        } else if(browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();
            for(int i = 0 ; i < filename.length() ; i++) {
                char c = filename.charAt(i);
                if(c > '~') {
                    sb.append(URLEncoder.encode("" + c, DOWN_FILE_NAME_ENCODE_FROM));
                } else {
                    sb.append(c);
                }
            }
            encodedFilename = sb.toString();
        } else {
            throw new IOException("Not supported browser");
        }

        response.setHeader("Content-Disposition", dispositionPrefix + encodedFilename);

        if("Opera".equals(browser)) {
            response.setContentType("application/octet-stream;charset=UTF-8");
        }
    }

    /**
     * User-Agent 구분
     * 
     * @param request
     * @return
     */
    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        if(header.indexOf("MSIE") > -1) {
            return "MSIE";
        } else if(header.indexOf("Trident") > -1) { // IE11 문자열 깨짐 방지
            return "Trident";
        } else if(header.indexOf("Chrome") > -1) {
            return "Chrome";
        } else if(header.indexOf("Opera") > -1) {
            return "Opera";
        }
        return "Firefox";
    }
}
