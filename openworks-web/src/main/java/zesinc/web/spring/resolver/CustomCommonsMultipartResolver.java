/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.resolver;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import zesinc.web.support.filter.XSSFilter;
import zesinc.web.support.request.XssMultipartHttpServletRequest;

/**
 * Multipart 데이터의 XSS 필터링을 위한 Resolver
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 10. 30.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CustomCommonsMultipartResolver extends CommonsMultipartResolver {

    private boolean resolveLazily = false;

    /**
     * Set whether to resolve the multipart request lazily at the time of
     * file or parameter access.
     * <p>
     * Default is "false", resolving the multipart elements immediately, throwing corresponding
     * exceptions at the time of the {@link #resolveMultipart} call. Switch this to "true" for lazy
     * multipart parsing, throwing parse exceptions once the application attempts to obtain
     * multipart files or parameters.
     */
    @Override
    public void setResolveLazily(boolean resolveLazily) {
        this.resolveLazily = resolveLazily;
        super.setResolveLazily(resolveLazily);
    }

    @Override
    public MultipartHttpServletRequest resolveMultipart(final HttpServletRequest request) throws MultipartException {
        Assert.notNull(request, "Request must not be null");

        // XSS 필터링 대상 여부
        if(XSSFilter.isTarget(request)) {
            if(this.resolveLazily) {
                return new XssMultipartHttpServletRequest(request) {

                    @Override
                    protected void initializeMultipart() {
                        MultipartParsingResult parsingResult = parseRequest(request);
                        setMultipartFiles(parsingResult.getMultipartFiles());
                        setMultipartParameters(parsingResult.getMultipartParameters());
                        setMultipartParameterContentTypes(parsingResult.getMultipartParameterContentTypes());
                    }
                };
            }
            else {
                MultipartParsingResult parsingResult = parseRequest(request);
                return new XssMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
                    parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());

            }
        }

        // XSS 필터링 대상 이외의 처리
        if(this.resolveLazily) {
            return new DefaultMultipartHttpServletRequest(request) {

                @Override
                protected void initializeMultipart() {
                    MultipartParsingResult parsingResult = parseRequest(request);
                    setMultipartFiles(parsingResult.getMultipartFiles());
                    setMultipartParameters(parsingResult.getMultipartParameters());
                    setMultipartParameterContentTypes(parsingResult.getMultipartParameterContentTypes());
                }
            };
        }
        else {
            MultipartParsingResult parsingResult = parseRequest(request);
            return new DefaultMultipartHttpServletRequest(request, parsingResult.getMultipartFiles(),
                parsingResult.getMultipartParameters(), parsingResult.getMultipartParameterContentTypes());
        }
    }
}
