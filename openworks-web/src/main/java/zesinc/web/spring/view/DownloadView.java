/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.view;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.IFileVO;

/**
 * 파일 다운로드 공통 VIEW 구현체
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
 * @see
 */
public class DownloadView extends AbstractView {

    private final int ILLEGAL_ARGUMENT = 1;
    private final int NO_EXIST = 2;
    public static final String DEFAULT_CONTENT_TYPE = "application/x-msdownload";

    private static String DOWN_FILE_NAME_ENCODE_FROM = Config.getString("webapp-config.downFileNameEncodeFrom");
    private static String DOWN_FILE_NAME_ENCODE_TO = Config.getString("webapp-config.downFileNameEncodeTo");
    private static String RESOURCE_ROOT_PATH = "/" + BaseConfig.RESOURCE_FOLDER_PATH;

    public DownloadView() {
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
        HttpServletResponse response) throws Exception {

        String encodedFileNm = null;
        File file = null;

        Object object = model.get(BaseConfig.FILE_DATA_KEY);
        if(Validate.isEmpty(object)) {
            flushError(response, ILLEGAL_ARGUMENT, null);
            return;
        }

        if(object instanceof File) {
            file = (File) object;
            encodedFileNm = file.getName();
        } else if(object instanceof IFileVO) {
            IFileVO fileVo = (IFileVO) object;
            encodedFileNm = fileVo.getOrgnlFileNm();
            if(fileVo.getFileUrlAddr().startsWith(RESOURCE_ROOT_PATH)) {
                file = new File(BaseConfig.RESOURCE_ROOT + fileVo.getFileUrlAddr());
            } else {
                file = new File(BaseConfig.UPLOAD_ROOT + fileVo.getFileUrlAddr());
            }
        } else {
            flushError(response, ILLEGAL_ARGUMENT, null);
            return;
        }
        // 웹 방화벽에서 쉼표를 차단하는 곳이 있어 일괄 치환
        encodedFileNm = encodedFileNm.replaceAll(",", "_");

        if(file == null || !file.exists()) {
            flushError(response, NO_EXIST, encodedFileNm);
            return;
        }

        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setContentLength((int) file.length());

        setContentType(DEFAULT_CONTENT_TYPE);
        setDisposition(encodedFileNm, request, response);

        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

        try {
            FileCopyUtils.copy(in, out);
            out.flush();
        } catch (Exception e) {
            if(logger.isErrorEnabled()) {
                logger.error("Download error : " + e);
            }
        } finally {
            if(in != null) {
                try {
                    in.close();
                } catch (Exception e) {}
            }
            if(out != null) {
                try {
                    out.close();
                } catch (Exception e) {}
            }
        }

        if(file.getName().startsWith("CompressDown_")) {
            try {
                file.delete();
            } catch (Exception e) {}
        }
    }

    /**
     * 오류 발생 시 메시지를 생성하고 alert을 통하여 메시지를 전달 후 history.back()을 호출한다.
     *
     * @param response
     * @param flag
     * @throws Exception
     */
    private void flushError(HttpServletResponse response, int flag, String fileName) throws Exception {

        String message = null;
        switch(flag) {
            case ILLEGAL_ARGUMENT:
                message = "요청 정보가 올바르게 전달되지 않았습니다.";
                break;
            case NO_EXIST:
                message = "[ " + fileName + " ] 파일이 삭제되었거나 존재하지 않습니다.";
                break;
            default:
                message = "";
                break;
        }

        StringBuffer alertScript = new StringBuffer();
        alertScript.append("<!DOCTYPE html>");
        alertScript.append("<html lang=\"ko\">");
        alertScript.append("<head>");
        alertScript.append("<title>");
        alertScript.append(fileName);
        alertScript.append(" 파일을 다운로드합니다.");
        alertScript.append("</title>");
        alertScript.append("<meta charset=\"utf-8\">");

        alertScript.append("<script type=\"text/javascript\">");
        alertScript.append("    alert('" + message + "');");
        alertScript.append("    history.back();");
        alertScript.append("</script>");

        alertScript.append("</head>");
        alertScript.append("<body>");
        alertScript.append("&nbsp;");
        alertScript.append("</body>");
        alertScript.append("</html>");

        response.setContentType("text/html");
        response.setCharacterEncoding(DOWN_FILE_NAME_ENCODE_FROM);
        response.getWriter().write(alertScript.toString());
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
            encodedFilename = "\"" + URLEncoder.encode(filename, DOWN_FILE_NAME_ENCODE_FROM).replaceAll("\\+", "%20") + "\";filename*=\"UTF-8''" + URLEncoder.encode(filename, DOWN_FILE_NAME_ENCODE_FROM).replaceAll("\\+", "%20") + "\";";
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
