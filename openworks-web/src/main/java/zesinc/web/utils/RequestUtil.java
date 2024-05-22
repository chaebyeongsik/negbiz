/*
 * Copyright (c) 2012 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import zesinc.core.lang.Validate;
import zesinc.web.spring.resolver.ParamArgumentResolver;
import zesinc.web.support.BaseConfig;
import zesinc.web.support.pager.Pager;

/**
 * 실제 값을 가진 객체에서 인자로 전달받은 객체의 자료 구조에 맞도록 값을 설정한다.
 * java.util.Map, JAVA BEANS에 HttpServletRequest의 Parameter 바인딩 지원
 * 검색 조건을 QueryString으로 반환하며 prefix 적용여부, 사용자 prefix를 지원한다.
 * - prefix 적용이 <code>true</code> 인경우 "q_"로 시작되는 파라미터만 QueryString로 생성된다.
 * - HttpServletRequest.getParameter() to Java BEANS
 * - HttpServletRequest.getParameter() to java.util.Map 구현체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 31.    방기배   수정보완
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class RequestUtil {

    public static final String PREFIX_SEARCH_FIELD = BaseConfig.PREFIX_SEARCH_PARAM;

    /* ----------------------------------------------------------- */
    /* ----------------------- QueryString ----------------------- */
    /* ----------------------------------------------------------- */

    /**
     * <code>HttpServletRequest</code>에서 Parameger를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param request 요청객채
     * @return '?'를 제외한 querystring
     * @see {@link #getQueryString(HttpServletRequest, boolean)}
     */
    public static String getSearchQueryString(HttpServletRequest request) {

        return getQueryString(request, true);
    }

    /**
     * <code>HttpServletRequest</code>에서 Parameger를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param request 요청객채
     * @return '?'를 제외한 querystring
     * @see {@link #getQueryString(HttpServletRequest, boolean)}
     */
    public static String getQueryString(HttpServletRequest request) {

        return getQueryString(request, false);
    }

    /**
     * <code>HttpServletRequest</code>에서 Parameger를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param request 요청객채
     * @param allowPrefix <code>q_</code>로 시작되는 검색 파라미터만 추출할지 여부
     * @return '?'를 제외한 querystring
     */
    @SuppressWarnings("unchecked")
    public static String getQueryString(HttpServletRequest request, boolean allowPrefix) {

        String paramName;
        String paramVal;

        StringBuilder searchQueryString = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();

        if(allowPrefix) {
            while(e.hasMoreElements()) {
                paramName = e.nextElement();

                if(paramName.startsWith(PREFIX_SEARCH_FIELD)) {
                    paramVal = request.getParameter(paramName);

                    searchQueryString.append(paramName).append("=");
                    searchQueryString.append(paramVal).append("&");
                }
            }
        } else {
            while(e.hasMoreElements()) {
                paramName = e.nextElement();
                paramVal = request.getParameter(paramName);

                searchQueryString.append(paramName).append("=");
                searchQueryString.append(paramVal).append("&");
            }
        }

        return searchQueryString.toString();
    }

    /**
     * <code>java.util.Map</code>에서 Key를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param targetMap 추출대상 Map
     * @return '?'를 제외한 querystring
     */
    public static String getSearchQueryString(Map<String, String> targetMap) {

        return getQueryString(targetMap, true);
    }

    /**
     * <code>java.util.Map</code>에서 Key를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param targetMap 추출대상 Map
     * @return '?'를 제외한 querystring
     */
    public static String getQueryString(Map<String, String> targetMap) {

        return getQueryString(targetMap, false);
    }

    /**
     * <code>java.util.Map</code>에서 Key를 추출하여 QueryString를 생성하여 반환한다.
     * 
     * @param targetMap 추출대상 Map
     * @param allowPrefix <code>q_</code>로 시작되는 검색 파라미터만 추출할지 여부
     * @return '?'를 제외한 querystring
     */
    public static String getQueryString(Map<String, String> targetMap, boolean allowPrefix) {

        String paramName;
        String paramVal;

        StringBuilder searchQueryString = new StringBuilder();
        Iterator<String> it = targetMap.keySet().iterator();

        if(allowPrefix) {
            while(it.hasNext()) {
                paramName = it.next();
                if(paramName.startsWith(PREFIX_SEARCH_FIELD)) {
                    paramVal = targetMap.get(paramName);

                    searchQueryString.append(paramName).append("=");
                    searchQueryString.append(paramVal).append("&");
                }
            }
        } else {
            while(it.hasNext()) {
                paramName = it.next();
                paramVal = targetMap.get(paramName);

                searchQueryString.append(paramName).append("=");
                searchQueryString.append(paramVal).append("&");
            }
        }

        return searchQueryString.toString();
    }

    /**
     * QueryString 문자열 중 특정 parameter의 값을 변경한다.
     * 
     * @param queryString
     * @param paramKey
     * @param paramValue
     * @return paramKey의 값이 paramValue의 값으로 대체된 결과 문자열 반환
     */
    public static String getReplaceParamValue(String queryString, String paramKey, Object paramValue) {

        int startIdx = queryString.indexOf("&" + paramKey);
        // = 포함한 길이
        int passLength = paramKey.length() + 1;

        if(startIdx < 0) {
            startIdx = queryString.indexOf(paramKey);
            passLength = paramKey.length();
        }

        if(startIdx > -1) {
            startIdx += passLength;

            int endIdx = queryString.indexOf("&", startIdx);
            if(endIdx < 0) {
                endIdx = queryString.length();
            }

            String preString = queryString.substring(0, startIdx);
            String postString = queryString.substring(endIdx);

            return preString + "=" + String.valueOf(paramValue) + postString;
        }

        return queryString;
    }

    /* ----------------------------------------------------------- */
    /* -------------------------- Header ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * <code>RequestHeader</code> 중 지정된 값을 반환
     * 
     * @param request
     * @param headerName
     * @return
     */
    public static String getRequestHeader(HttpServletRequest request, String headerName) {

        return request.getHeader(headerName);
    }

    /**
     * <code>RequestHeader</code> 중 지정된 값을 반환
     * 
     * @param request
     * @param headerName
     * @return HashMap key/value 값
     */
    public static List<Map<String, String>> getRequestHeaderList(HttpServletRequest request) {

        List<Map<String, String>> headerList = new ArrayList<Map<String, String>>();
        Map<String, String> headerMap;

        @SuppressWarnings("unchecked")
        Enumeration<String> headers = request.getHeaderNames();
        while(headers.hasMoreElements()) {
            String key = headers.nextElement();
            String value = request.getHeader(key);

            headerMap = new HashMap<String, String>();
            headerMap.put(key, value);
            headerList.add(headerMap);
        }

        return headerList;
    }

    /* ----------------------------------------------------------- */
    /* -------------------------- SESSION ------------------------ */
    /* ----------------------------------------------------------- */

    /**
     * <code>HttpServletRequest</code>에서 HttpSesstion을 추출하여 담긴 정보를 맵으로 변환
     * 
     * @param request 요청객체
     * @return
     */
    public static Map<String, Object> getSessionMap(HttpServletRequest request) {

        return getSessionMap(request.getSession());
    }

    /**
     * <code>HttpSesstion</code>을 추출하여 담긴 정보를 맵으로 변환
     * 
     * @param session HttpSession 세션
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getSessionMap(HttpSession session) {

        Map<String, Object> dataMap = new HashMap<String, Object>();

        Enumeration<String> _enum = session.getAttributeNames();
        while(_enum.hasMoreElements()) {
            String key = _enum.nextElement();
            Object value = session.getAttribute(key);
            dataMap.put(key, value);
        }
        return dataMap;
    }

    /* ----------------------------------------------------------- */
    /* -------------------------- SEARCH ------------------------- */
    /* ----------------------------------------------------------- */

    /**
     * <code>HttpServletRequest</code>에 담긴 정보를 맵으로 변환
     * 
     * @param request 요청객체
     */
    public static Map<String, Object> getSearchMap(HttpServletRequest request) {
        return getSearchMap(request, false, Pager.DEFAULT_ROW_PER_PAGE, Pager.DEFAULT_ROW_PER_PAGE);
    }

    /**
     * <code>HttpServletRequest</code>에 담긴 검색 정보를 맵으로 반환하며,
     * HttpSession 값을 포함 여부를 지정
     * 검색 정보는 "q_" 로시작되는 파라미터로 고정한다.(자동화를 위하여)
     * 
     * @param request 요청객체
     * @param sessionYn 세션포함여부
     * @return "q_" 파라미터와 세션정보 <code>Map</code>
     */
    public static Map<String, Object> getSearchMap(HttpServletRequest request, boolean sessionYn) {
        return getSearchMap(request, sessionYn, Pager.DEFAULT_ROW_PER_PAGE, Pager.DEFAULT_ROW_PER_PAGE);
    }

    /**
     * <code>HttpServletRequest</code>에 담긴 검색 정보를 맵으로 반환하며,
     * HttpSession 값을 포함 여부를 지정
     * 검색 정보는 "q_" 로시작되는 파라미터로 고정한다.(자동화를 위하여)
     * 
     * @param request 요청객체
     * @param rowPerPage 페이지당 목록 수
     * @return "q_" 파라미터 <code>Map</code>
     */
    public static Map<String, Object> getSearchMap(HttpServletRequest request, int rowPerPage) {
        return getSearchMap(request, false, rowPerPage, Pager.DEFAULT_ROW_PER_PAGE);
    }

    /**
     * <code>HttpServletRequest</code>에 담긴 검색 정보를 맵으로 반환하며,
     * HttpSession 값을 포함 여부를 지정
     * 검색 정보는 "q_" 로시작되는 파라미터로 고정한다.(자동화를 위하여)
     * 
     * @param request 요청객체
     * @param rowPerPage 페이지당 목록 수
     * @param pagePerPage 하단 페이지 목록 갯수
     * @return "q_" 파라미터 <code>Map</code>
     */
    public static Map<String, Object> getSearchMap(HttpServletRequest request, int rowPerPage, int pagePerPage) {
        return getSearchMap(request, false, rowPerPage, pagePerPage);
    }

    /**
     * <code>HttpServletRequest</code>에 담긴 검색 정보를 맵으로 반환하며,
     * HttpSession 값을 포함 여부를 지정
     * 검색 정보는 "q_" 로시작되는 파라미터로 고정한다.(자동화를 위하여)
     * 
     * @param request 요청객체
     * @param sessionYn 세션포함여부
     * @param rowPerPage 페이지당 목록 갯수
     * @param pagePerPage 하단 페이지 목록 갯수
     * @return "q_" 파라미터와 세션정보 <code>Map</code>
     */
    public static Map<String, Object> getSearchMap(HttpServletRequest request, boolean sessionYn, int rowPerPage, int pagePerPage) {
        // q_ 로 시작되는 모든 파라미터
        Map<String, Object> dataMap = getRequestMap(request, PREFIX_SEARCH_FIELD);

        int q_currPage = (Integer) dataMap.get(PREFIX_SEARCH_FIELD + "currPage");
        int q_rowPerPage = (Integer) dataMap.get(PREFIX_SEARCH_FIELD + "rowPerPage");
        int q_pagePerPage = (Integer) dataMap.get(PREFIX_SEARCH_FIELD + "pagePerPage");

        if(q_currPage == 0) {
            q_currPage = 1;
        }
        dataMap.put(PREFIX_SEARCH_FIELD + "currPage", q_currPage);

        if(q_rowPerPage == 0) {
            q_rowPerPage = rowPerPage;
        }
        dataMap.put(PREFIX_SEARCH_FIELD + "rowPerPage", q_rowPerPage);

        if(q_pagePerPage == 0) {
            q_pagePerPage = pagePerPage;
        }
        dataMap.put(PREFIX_SEARCH_FIELD + "pagePerPage", q_pagePerPage);

        int iStartNum = Pager.getStartNum(q_currPage, q_rowPerPage) + ParamArgumentResolver.addStartNum;
        int iEndNum = Pager.getEndNum(iStartNum, q_rowPerPage);

        dataMap.put(PREFIX_SEARCH_FIELD + "pagingStartNum", String.valueOf(iStartNum));
        dataMap.put(PREFIX_SEARCH_FIELD + "pagingEndNum", String.valueOf(iEndNum));

        // 세션 값 포함 여부
        if(sessionYn) {
            dataMap.putAll(getSessionMap(request));
        }

        return dataMap;
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 Map에 key, value로 설정.
     * </pre>
     * 
     * @param request 요청객체
     */
    public static Map<String, Object> getRequestMap(HttpServletRequest request) {

        return getRequestMap(request, null);
    }

    /**
     * <pre>
     * <code>HttpServletRequest</code>에서 Parameter를 추출하여 Map에 key, value로 설정
     * 단 복수 값을 갖는 파라미터의 경우 <code>Map<String, List<String>></code> 형태로 설정된다.
     * 
     * - prefix != null : parameter명이 prefix 문자열로 시작되는 것만 추출
     * - prefix == null : 모든 parameter 추출
     * </pre>
     * 
     * @param request 요청객체
     * @param prefix prefix 문자열
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getRequestMap(HttpServletRequest request, String prefix) {

        Map<String, Object> targetMap = new HashMap<String, Object>();

        String paramName;
        String[] paramArr;

        Enumeration<String> e = request.getParameterNames();

        while(e.hasMoreElements()) {
            paramName = e.nextElement();

            if(Validate.isNotEmpty(prefix) && !paramName.startsWith(prefix)) {
                continue;
            }

            paramArr = request.getParameterValues(paramName);
            if(paramArr.length > 1) {
                int arrCnt = paramArr.length;
                ArrayList<String> al = new ArrayList<String>();
                for(int i = 0 ; i < arrCnt ; i++) {
                    al.add(paramArr[i]);
                }
                targetMap.put(paramName, al);
            } else {
                targetMap.put(paramName, paramArr[0]);
            }
        }

        return targetMap;
    }

    /**
     * request path 추출
     * 
     * @param request
     * @return
     */
    public static String getExtractRequestPath(HttpServletRequest request) {

        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String query = request.getQueryString();

        return (servletPath == null ? "" : servletPath) + (pathInfo == null ? "" : pathInfo)
            + (query == null ? "" : ("?" + query));
    }

    /**
     * url에서 파라미터를 파싱하여 Map에 key와 value로 설정하여 반환한다.
     * value가 없는 빈 파라미터의 경우는 "" 빈문자열로 설정하여 반환한다.
     * 
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParamToMap(String url) {

        return getUrlParamToMap(url, new String[] { "&" });
    }

    /**
     * url에서 파라미터를 파싱하여 Map에 key와 value로 설정하여 반환한다.
     * value가 없는 빈 파라미터의 경우는 "" 빈문자열로 설정하여 반환한다.
     * 
     * @param url
     * @return
     */
    public static Map<String, String> getUrlParamToMap(String url, String[] delimiters) {

        Map<String, String> paramMap = new HashMap<String, String>();

        String queryString = (url.indexOf("?") > -1) ? url.substring(url.indexOf("?") + 1) : url;
        String[] param;

        List<String> paramList = new ArrayList<String>();
        for(String delimiter : delimiters) {
            String[] paramStrs = queryString.split(delimiter);
            for(String paramStr : paramStrs) {
                paramList.add(paramStr);
            }
        }

        for(String paramStr : paramList) {
            if(paramStr.indexOf("=") > -1) {
                param = paramStr.split("=");
                if(param.length > 1) {
                    paramMap.put(param[0], param[1]);
                } else {
                    paramMap.put(param[0], "");
                }
            }
        }

        return paramMap;
    }
}
