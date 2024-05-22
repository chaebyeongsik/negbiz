/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.request;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.AbstractMultipartHttpServletRequest;

import zesinc.web.utils.XssUtil;

/**
 * XSS 보안 조치를 위한 구현체, 요청 객체에서 값을 요청시 값을 변경하여 반환
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2013. 6. 4.    방기배   신규 생성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class XssMultipartHttpServletRequest extends AbstractMultipartHttpServletRequest {

    private static final String CONTENT_TYPE = "Content-Type";
    private Map<String, String[]> multipartParamMap;
    private Map<String, String> multipartParameterContentTypes;

    private String type = "script";

    public XssMultipartHttpServletRequest(HttpServletRequest request, MultiValueMap<String, MultipartFile> mpFiles,
        Map<String, String[]> mpParams, Map<String, String> mpParamContentTypes) {

        super(request);

        String type = ((HttpServletRequest) request).getSession().getServletContext().getInitParameter("XssType");
        if(type != null) {
            this.type = type;
        }

        setMultipartFiles(mpFiles);
        setMultipartParamMap(mpParams);
        setMultipartParameterContentTypes(mpParamContentTypes);
    }

    public XssMultipartHttpServletRequest(HttpServletRequest request) {

        super(request);
        this.type = request.getSession().getServletContext().getInitParameter("XssType");
    }

    /**
     * Set a Map with parameter names as keys and String array objects as values.
     * To be invoked by subclasses on initialization.
     */
    protected final void setMultipartParameters(Map<String, String[]> multipartParamMap) {
        this.multipartParamMap = multipartParamMap;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Enumeration<String> getParameterNames() {

        Set paramNames = new HashSet();
        Enumeration paramEnum = super.getParameterNames();
        while(paramEnum.hasMoreElements()) {
            paramNames.add(paramEnum.nextElement());
        }

        paramNames.addAll(getMultipartParamMap().keySet());

        return Collections.enumeration(paramNames);
    }

    @Override
    public String getParameter(String name) {

        String[] values = getMultipartParamMap().get(name);
        if(values != null) {

            return ((values.length > 0) ? values[0] : null);
        }

        return alteration(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {

        String[] values = getMultipartParamMap().get(name);
        if(values != null) {
            return values;
        }

        values = super.getParameterValues(name);
        String[] alterationValues = null;
        if(values != null) {

            int count = values.length;
            if(count > 0) {
                alterationValues = new String[count];
                for(int i = 0 ; i < count ; i++) {
                    alterationValues[i] = alteration(values[i]);
                }
            }
        }
        return alterationValues;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, String[]> getParameterMap() {

        Map paramMap = new HashMap();
        paramMap.putAll(super.getParameterMap());
        paramMap.putAll(getMultipartParamMap());

        return paramMap;
    }

    @Override
    public String getHeader(String name) {

        String value = super.getHeader(name);
        if(value == null) {
            return null;
        }

        return value.replaceAll("\r", "").replaceAll("\n", "");
    }

    protected final void setMultipartParamMap(Map<String, String[]> multipartParamMap) {

        if(multipartParamMap != null) {
            Iterator<String> it = multipartParamMap.keySet().iterator();
            while(it.hasNext()) {

                String key = it.next();
                String[] values = multipartParamMap.get(key);
                if(values != null) {

                    int count = values.length;
                    String[] alterationValues = null;
                    if(count > 0) {
                        alterationValues = new String[count];
                        for(int i = 0 ; i < count ; i++) {
                            alterationValues[i] = alteration(values[i]);
                        }
                    }
                    multipartParamMap.put(key, alterationValues);
                }

            }
        }

        this.multipartParamMap = multipartParamMap;
    }

    protected Map<String, String[]> getMultipartParamMap() {

        if(this.multipartParamMap == null) {
            initializeMultipart();
        }

        return this.multipartParamMap;
    }

    protected final void setMultipartParameterContentTypes(Map<String, String> multipartParameterContentTypes) {
        this.multipartParameterContentTypes = multipartParameterContentTypes;
    }

    protected Map<String, String> getMultipartParameterContentTypes() {
        if(this.multipartParameterContentTypes == null) {
            initializeMultipart();
        }
        return this.multipartParameterContentTypes;
    }

    public String getMultipartContentType(String paramOrFileName) {
        MultipartFile file = getFile(paramOrFileName);
        if(file != null) {
            return file.getContentType();
        }
        else {
            return getMultipartParameterContentTypes().get(paramOrFileName);
        }
    }

    public HttpHeaders getMultipartHeaders(String paramOrFileName) {
        String contentType = getMultipartContentType(paramOrFileName);
        if(contentType != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_TYPE, contentType);
            return headers;
        }
        else {
            return null;
        }
    }

    private String alteration(String content) {
        if(content == null) {
            return null;
        }

        return XssUtil.cleanTag(content, XssUtil.TYPE.valueOf(this.type.toUpperCase()));
    }

}
