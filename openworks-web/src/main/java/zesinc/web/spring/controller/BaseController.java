/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.JsonVO;

/**
 * 요청응답 메소드등 컨트롤러 편의 기능 지원
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 29.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public abstract class BaseController {

    /** Contrroller 로깅 */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 현재 접속자의 IP 주소를 반환한다.
     * 
     * @return
     */
    protected String getIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return request.getRemoteAddr();
    }

    /**
     * <code>Object</code>를 JSON 타입으로 변경하여 응답
     * 인자가 없이 호출되었기 때문에 기본 응답 "true" 문자열이 전송됨.
     * (content type = application/json)
     * 
     * @param model
     * @return "true" 문자열 반환
     */
    protected String responseJson(Model model) {

        JsonVO jsonVo = new JsonVO().setResult(Boolean.TRUE);

        return responseJson(model, jsonVo);
    }

    /**
     * 파라미터로 전달되는 항목을 json VO 객체에 담아서 응답
     * (content type = application/json)
     * 
     * @param model
     * @param result 처리결과
     * @return
     */
    protected String responseJson(Model model, Boolean result) {

        JsonVO jsonVo = new JsonVO().setResult(result);

        return responseJson(model, jsonVo);
    }

    /**
     * 파라미터로 전달되는 항목을 json VO 객체에 담아서 응답
     * (content type = application/json)
     * 
     * @param model
     * @param result 처리결과
     * @param message 응답메시지
     * @return
     */
    protected String responseJson(Model model, Boolean result, String message) {

        JsonVO jsonVo = new JsonVO().setResult(result).setMessage(message);

        return responseJson(model, jsonVo);
    }

    /**
     * 파라미터로 전달되는 항목을 json VO 객체에 담아서 응답
     * (content type = application/json)
     * 
     * @param model
     * @param result 처리결과
     * @param value 결과 값
     * @return
     */
    protected String responseJson(Model model, Boolean result, Object value) {

        JsonVO jsonVo = new JsonVO().setResult(result).setValue(value);

        return responseJson(model, jsonVo);
    }

    /**
     * 파라미터로 전달되는 항목을 json VO 객체에 담아서 응답
     * (content type = application/json)
     * 
     * @param model
     * @param value 결과 값
     * @param message 응답메시지
     * @return
     */
    protected String responseJson(Model model, Object value, String message) {

        JsonVO jsonVo = new JsonVO().setValue(value).setMessage(message);

        return responseJson(model, jsonVo);
    }

    /**
     * 파라미터로 전달되는 항목을 json VO 객체에 담아서 응답
     * (content type = application/json)
     * 
     * @param model
     * @param result 처리결과
     * @param value 결과 값
     * @param message 응답메시지
     * @return
     */
    protected String responseJson(Model model, Boolean result, Object value, String message) {

        JsonVO jsonVo = new JsonVO().setResult(result).setValue(value).setMessage(message);

        return responseJson(model, jsonVo);
    }

    /**
     * <code>Object</code>를 JSON 타입으로 변경하여 응답
     * (content type = application/json)
     * 
     * @param model
     * @param target json 변경 대상 객체
     * @return
     * @see zesinc.web.spring.view.JsonView
     */
    protected String responseJson(Model model, Object target) {

        model.addAttribute(BaseConfig.JSON_DATA_KEY, target);

        return BaseConfig.JSON_VIEW_NAME;
    }

    /**
     * textView를 통한 문자열 응답 (응답 문자열 없음)
     * 인자가 없이 호출되었기 때문에 기본 응답 "true" 문자열이 전송됨.
     * (content type = text/plain)
     * 
     * @param model
     * @return "true" 문자열 반환
     * @see #responseText(Model, Object)
     */
    protected String responseText(Model model) {
        return responseText(model, Boolean.TRUE);
    }

    /**
     * textView를 통한 문자열 응답 (<code>Object.toString()</code> 값이 응답문자열로 생성됨)
     * (content type = text/plain)
     * 
     * @param model
     * @param target 응답 문자열
     * @return
     * @see zesinc.base.spring.view.TextView
     */
    protected String responseText(Model model, Object target) {

        model.addAttribute(BaseConfig.TEXT_DATA_KEY, target);

        return BaseConfig.TEXT_VIEW_NAME;
    }

    /**
     * htmlView를 통한 HTML 응답 (<code>Object.toString()</code> 값이 응답문자열로 생성됨)
     * (content type = text/html)
     * 
     * @param model
     * @param target
     * @return
     * @see zesinc.web.spring.view.HtmlView
     */
    protected String responseHtml(Model model, Object target) {

        model.addAttribute(BaseConfig.HTML_DATA_KEY, target);

        return BaseConfig.HTML_VIEW_NAME;
    }

    /**
     * downloadView를 통한 파일 다운로드
     * (content type = application/octet-stream)
     * 
     * @param model
     * @param target
     * @return
     * @see zesinc.base.spring.view.DownloadView
     */
    protected String responseDownload(Model model, Object target) {

        model.addAttribute(BaseConfig.FILE_DATA_KEY, target);

        return BaseConfig.DOWNLOAD_VIEW_NAME;
    }

    /**
     * rssFeedView를 통한 RSS 서비스
     * (content type = application/rss+xml)
     * 
     * @param model
     * @param target
     * @return
     */
    protected String responseRSS(Model model, Object target) {

        model.addAttribute(BaseConfig.RSS_DATA_KEY, target);

        return BaseConfig.RSS_VIEW_NAME;
    }

    /**
     * atomFeedView를 통한 Atom 서비스
     * (content type = application/atom+xml)
     * 
     * @param model
     * @param target
     * @return
     */
    protected String responseAtom(Model model, Object target) {

        model.addAttribute(BaseConfig.ATOM_DATA_KEY, target);

        return BaseConfig.ATOM_VIEW_NAME;
    }

    /**
     * excelView를 통한 excel 파일 다운로드
     * (content type = application/vnd.ms-excel)
     * 
     * @param model
     * @param target
     * @return
     */
    protected String responseExcel(Model model, Object target) {

        model.addAttribute(BaseConfig.EXCEL_DATA_KEY, target);

        return BaseConfig.EXCEL_VIEW_NAME;
    }

    /**
     * pdfView를 통한 pdf 파일 다운로드
     * (content type = application/pdf)
     * 
     * @param model
     * @param target
     * @return
     */
    protected String responsePDF(Model model, Object target) {

        model.addAttribute(BaseConfig.PDF_DATA_KEY, target);

        return BaseConfig.PDF_VIEW_NAME;
    }

    /**
     * JavaScript 기반 메시지 및 창 닫기
     * <p />
     * alert(BaseConfig.MESSAGE_DATA_KEY); history.back();
     * 
     * @param model
     * @param message
     * @return
     */
    protected String alertAndBack(Model model, String message) {
        if(Validate.isEmpty(message)) {
            message = MessageUtil.getMessage("common.alertAndBack", new String[] { message });
        }

        model.addAttribute(BaseConfig.MESSAGE_DATA_KEY, message);

        return BaseConfig.ALERT_AND_BACK;
    }

    /**
     * JavaScript 기반 메시지(alert) 및 창 닫기
     * <p />
     * alert(BaseConfig.MESSAGE_DATA_KEY); opCloseWin();
     * 
     * @param model
     * @param message
     * @return
     */
    protected String alertAndClose(Model model, String message) {
        if(Validate.isEmpty(message)) {
            message = MessageUtil.getMessage("common.alertAndClose", new String[] { message });
        }

        model.addAttribute(BaseConfig.MESSAGE_DATA_KEY, message);

        return BaseConfig.ALERT_AND_CLOSE;
    }

    /**
     * JavaScript 기반 메시지(alert) 및 페이지 이동
     * <p />
     * alert(BaseConfig.MESSAGE_DATA_KEY); self.location.href("url");
     * 
     * @param model
     * @param message
     * @param url
     * @return
     */
    protected String alertAndRedirect(Model model, String message, String url) {
        if(Validate.isEmpty(message)) {
            message = MessageUtil.getMessage("common.alertAndRedirect", new String[] { message });
        }

        if(Validate.isEmpty(url)) {
            url = "/";
        }

        model.addAttribute(BaseConfig.MESSAGE_DATA_KEY, message);
        model.addAttribute("url", url);

        return BaseConfig.ALERT_AND_REDIRECT;
    }

    /**
     * html form 기반 페이지 이동. post 방식으로 submit 시킨다.
     * 
     * <pre>
     * 아래는 target List의 Map 값 생성 방식이다. 
     * Map&lt;String, Object&gt; target = new HashMap&lt;String, Object&gt;();
     * target.put("name", "input 태그의 name에 해당하는 키");
     * target.put("value", "input 태그의 value에 해당하는 값");
     * 
     * 위의 값으로 아래와 같은 html 태크가 생성되며 이 값을 url로 submit 시킨다.
     * 
     * &lt;input type="hidden" name="${target.name}" value="${target.value}" /&gt;
     * </pre>
     * 
     * @param model
     * @param url form의 action 속성의 url
     * @param target form
     * @return
     */
    protected String postMethodRedirect(Model model, String url, List<Map<String, Object>> target) {

        if(Validate.isEmpty(url)) {
            url = "/";
        }

        model.addAttribute(BaseConfig.KEY_PARAM_LIST, target);
        model.addAttribute("action", url);

        return BaseConfig.POST_METHOD_REDIRECT;
    }

    /**
     * JavaScript 기반 메시지 확인(confirm) 및 페이지 이동
     * 
     * <pre>
     * confirm(BaseConfig.MESSAGE_DATA_KEY); 
     * true : self.location.href("url");
     * false : history.back();
     * </pre>
     * 
     * @param model
     * @param message
     * @param url
     * @return
     */
    protected String confirmAndRedirect(Model model, String message, String url) {
        if(Validate.isEmpty(message)) {
            message = MessageUtil.getMessage("common.confirmAndRedirect", new String[] { message });
        }

        if(Validate.isEmpty(url)) {
            url = "/";
        }

        model.addAttribute(BaseConfig.MESSAGE_DATA_KEY, message);
        model.addAttribute("url", url);

        return BaseConfig.CONFIRM_AND_REDIRECT;
    }

    /**
     * JavaScript 코드를 실행한다.
     * <p />
     * 화면내에 &lt;script /&gt; 태그는 작성되어 있으므로 실 코드만 작성한다.<br />
     * 예: alert("HelloWorld");
     * 
     * @param model
     * @param scriptCode
     * @return
     */
    protected String responseScript(Model model, String scriptCode) {

        model.addAttribute(BaseConfig.SCRIPT_DATA_KEY, scriptCode);

        return BaseConfig.RUN_SCRIPT;
    }

}
