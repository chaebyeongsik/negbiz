/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.AntPathMatcher;

import zesinc.core.lang.Validate;

/**
 * SpringFramework의 AntPathMatcher를 이용한 Matcher로
 * RequestUri와 Parameter를 분리하여 비교한다.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 7. 30.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class RequestPathMatcher {

    /** URI path Matcher */
    private AntPathMatcher matcher = new AntPathMatcher();

    // 참조건이 되어야 할 포함 정보
    private List<PathVO> includeList = new ArrayList<PathVO>();
    // 거짓조건이 되어야 할 제외 정보
    private List<PathVO> excludeList = new ArrayList<PathVO>();

    /**
     * 포함되어야 할 패턴
     *
     * <pre>
     * 패턴 예)
     * /path/to/&#042;.do
     * /path/&#042;&#042;/file.do
     * /path/&#042;&#042;/file.do?param1=val1&amp;param2=val2
     * </pre>
     *
     * @param includes
     */
    public void setInclude(String include) {
        setInclude(new String[] { include });
    }

    /**
     * 포함되어야 할 패턴 배열 목록
     *
     * <pre>
     * 패턴 예)
     * /path/to/&#042;.do
     * /path/&#042;&#042;/file.do
     * /path/&#042;&#042;/file.do?param1=val1&amp;param2=val2
     * </pre>
     *
     * @param includes
     */
    public void setInclude(String[] includes) {
        PathVO pathVo;
        for(String include : includes) {
            pathVo = new PathVO();
            if(include.indexOf("?") > -1) {
                String[] paths = include.split("\\?", 2);
                pathVo.setRequestUri(paths[0]);

                List<String> paramList = new ArrayList<String>();
                if(Validate.isNotEmpty(paths[1]) && paths[1].indexOf("=") > -1) {
                    String[] querys = paths[1].split("&");
                    for(String query : querys) {
                        paramList.add(query);
                    }
                }
                pathVo.setParamList(paramList);
            } else {
                pathVo.setRequestUri(include);
                pathVo.setParamList(new ArrayList<String>());
            }
            includeList.add(pathVo);
        }
    }

    /**
     * 제외되어야 할 패턴
     *
     * <pre>
     * 패턴 예)
     * /path/to/&#042;.do
     * /path/&#042;&#042;/file.do
     * /path/&#042;&#042;/file.do?param1=val1&amp;param2=val2
     * </pre>
     *
     * @param excludes
     */
    public void setExclude(String exclude) {
        setExclude(new String[] { exclude });
    }

    /**
     * 제외되어야 할 패턴 배열 목록
     *
     * <pre>
     * 패턴 예)
     * /path/to/&#042;.do
     * /path/&#042;&#042;/file.do
     * /path/&#042;&#042;/file.do?param1=val1&amp;param2=val2
     * </pre>
     *
     * @param excludes
     */
    public void setExclude(String[] excludes) {
        PathVO pathVo;
        for(String exclude : excludes) {
            pathVo = new PathVO();
            if(exclude.indexOf("?") > -1) {
                String[] paths = exclude.split("\\?", 2);
                pathVo.setRequestUri(paths[0]);

                List<String> paramList = new ArrayList<String>();
                if(Validate.isNotEmpty(paths[1]) && paths[1].indexOf("=") > -1) {
                    String[] querys = paths[1].split("&");
                    for(String query : querys) {
                        paramList.add(query);
                    }
                }
                pathVo.setParamList(paramList);
            } else {
                pathVo.setRequestUri(exclude);
                pathVo.setParamList(new ArrayList<String>());
            }
            excludeList.add(pathVo);
        }
    }

    /**
     * match 설명
     *
     * @param request
     * @return
     */
    public PathVO match(HttpServletRequest request) {
        PathVO resultVo = null;

        boolean isMatches = true;
        String requestUri = request.getRequestURI();

        // 제외 설정이 있다면 확인 match 검사이므로 제외 조건이 일치한 경우 false를 리턴한다.
        if(Validate.isNotEmpty(excludeList)) {

            for(PathVO pathVo : excludeList) {
                isMatches = matcher.match(pathVo.getRequestUri(), requestUri);
                if(isMatches) {
                    List<String> paramList = pathVo.getParamList();
                    if(paramList != null) {
                        for(String param : paramList) {
                            String[] params = param.split("=");
                            String value = request.getParameter(params[0]);

                            if(params.length < 2) {
                                isMatches = false;
                                break;
                            }
                            if(!params[1].equals(value)) {
                                isMatches = false;
                                break;
                            }
                        }
                    }
                }
                // 2017-05-25 제외 필터링 수정 기존 for 문 밖에서 하던걸 for 문 안에서 하도록 변경
                // 제외 조건이 일치하므로 결과는 false를 리턴
                if(isMatches) {
                    return resultVo;
                }
            }
        }

        for(PathVO pathVo : includeList) {
            isMatches = matcher.match(pathVo.getRequestUri(), requestUri);
            if(isMatches) {
                List<String> paramList = pathVo.getParamList();
                if(paramList != null) {
                    for(String param : paramList) {
                        String[] params = param.split("=");
                        String value = request.getParameter(params[0]);

                        if(params.length < 2) {
                            isMatches = false;
                        }
                        if(!params[1].equals(value)) {
                            isMatches = false;
                        }
                    }
                }
            }
            // 일치 정보가 있으면 중지, 일치 정보가 없으면 끝까지 반복
            if(isMatches) {
                resultVo = pathVo;
                break;
            }
        }

        return resultVo;
    }

    /**
     * Path 정보인 URI와 Param 정보를 담는 객체
     *
     * <pre>
     * << 개정이력(Modification Information) >>
     *
     *     수정일       수정자   수정내용
     * --------------  --------  -------------------------------
     *  2015. 7. 30.    방기배   최초작성
     * </pre>
     *
     * @author (주)제스아이엔씨 기술연구소
     * @see
     */
    public class PathVO {

        /** requestUri */
        private String requestUri;
        /** parameter */
        private List<String> paramList;

        /**
         * String requestUri을 반환
         *
         * @return String requestUri
         */
        public String getRequestUri() {
            return requestUri;
        }

        /**
         * requestUri을 설정
         *
         * @param requestUri 을(를) String requestUri로 설정
         */
        public void setRequestUri(String requestUri) {
            this.requestUri = requestUri;
        }

        /**
         * List<String> paramList을 반환
         *
         * @return List<String> paramList
         */
        public List<String> getParamList() {
            return paramList;
        }

        /**
         * paramList을 설정
         *
         * @param paramList 을(를) List<String> paramList로 설정
         */
        public void setParamList(List<String> paramList) {
            this.paramList = paramList;
        }
    }
}
