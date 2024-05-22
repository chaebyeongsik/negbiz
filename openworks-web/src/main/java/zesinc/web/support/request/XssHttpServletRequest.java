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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import zesinc.web.utils.XssUtil;

/**
 * XSS 보안 조치를 위한 구현체, 요청 객체에서 값을 요청시 값을 변경하여 반환
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2013. 6. 2.    방기배   신규 생성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class XssHttpServletRequest extends HttpServletRequestWrapper {

    private Map<String, String[]> paramMap = new HashMap<String, String[]>();

    private String type = "script";

    /**
     * 초기화와 함께 web.xml 파일의 설정을 확인하여 적용 유형을 설정한다.
     *
     * @param request
     */
    public XssHttpServletRequest(ServletRequest request) {

        super((HttpServletRequest) request);

        String type = ((HttpServletRequest) request).getSession().getServletContext().getInitParameter("XssType");
        if(type != null) {
            this.type = type;
        }

        setParamMap((HttpServletRequest) request);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Enumeration<String> getParameterNames() {

        Set paramNames = new HashSet();
        Enumeration paramEnum = super.getParameterNames();
        while(paramEnum.hasMoreElements()) {
            paramNames.add(paramEnum.nextElement());
        }

        paramNames.addAll(getParamMap().keySet());

        return Collections.enumeration(paramNames);
    }

    @Override
    public String getParameter(String name) {

        String[] values = getParamMap().get(name);
        if(values != null) {

            return ((values.length > 0) ? values[0] : null);
        }

        return alteration(super.getParameter(name));
    }

    @Override
    public String[] getParameterValues(String name) {

        String[] values = getParamMap().get(name);
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
    @SuppressWarnings("unchecked")
    public Map<String, String[]> getParameterMap() {

        Map<String, String[]> paramMap = new HashMap<String, String[]>();
        paramMap.putAll(super.getParameterMap());
        paramMap.putAll(getParamMap());

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

    /**
     * XSS 필터링이된 값을 자체 Map에 저장한다.
     *
     * @param params
     */
    @SuppressWarnings("unchecked")
    protected final void setParamMap(HttpServletRequest request) {

        Map<String, String[]> params = request.getParameterMap();
        if(params != null) {
            Iterator<String> it = params.keySet().iterator();
            while(it.hasNext()) {

                String key = it.next();
                String[] values = params.get(key);
                if(values != null) {

                    int count = values.length;
                    String[] alterationValues = null;
                    if(count > 0) {
                        alterationValues = new String[count];
                        for(int i = 0 ; i < count ; i++) {
                            alterationValues[i] = alteration(values[i]);
                        }
                    }
                    this.paramMap.put(key, alterationValues);
                }
            }
        }
    }

    /**
     * 자체 저장된 파라미터 Map를 반환한다.
     *
     * @return
     */
    protected Map<String, String[]> getParamMap() {

        return this.paramMap;
    }

    private String alteration(String content) {
        if(content == null) {
            return null;
        }

        return XssUtil.cleanTag(content, XssUtil.TYPE.valueOf(this.type.toUpperCase()));
    }
}
