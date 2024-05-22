/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.support;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import zesinc.core.config.Config;
import zesinc.core.lang.MethodUtil;
import zesinc.core.lang.Validate;
import zesinc.intra.cms.support.domain.CmsReferenceVO;
import zesinc.web.support.BaseConfig;

/**
 * CMS 유틸리티
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 5. 9.    방기배
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CmsUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(CmsUtil.class);

    /** WAS 서버의 Root 경로 */
    public static String WAS_ROOT = BaseConfig.WEBAPP_ROOT;

    /** Spring Dispatcher Servlet 명칭 */
    private static final String DISPATCHER_SERVLET_CONTEXT_ATTR_NAME = Config
        .getString("system-config.springframeworks.dispatcherServletContextAttrName");

    /**
     * 사용자컨텐츠 관리에서 응용컨텐츠에 해당하는 <code>Controller</code> 목록을 반환
     * 
     * @return <code>Controller</code> 목록
     */
    public static List<CmsReferenceVO> getCmsReferenceList(ServletContext context) {

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context, DISPATCHER_SERVLET_CONTEXT_ATTR_NAME);
        Map<String, Object> applMap = ctx.getBeansWithAnnotation(CmsReference.class);

        List<CmsReferenceVO> controlList = new ArrayList<CmsReferenceVO>();

        // 레퍼런스 존재여부
        if(Validate.isNotEmpty(applMap)) {
            CmsReference ano;
            CmsReferenceVO refVo = null;

            Iterator<String> keys = applMap.keySet().iterator();
            while(keys.hasNext()) {
                refVo = new CmsReferenceVO();

                String key = keys.next();
                ano = ctx.findAnnotationOnBean(key, CmsReference.class);

                refVo.setName(ano.name());
                refVo.setMethodNm(ano.method());
                refVo.setBeanNm(key);
                String mm = key + "|" + ano.method();
                refVo.setCntrlNm(mm);

                controlList.add(refVo);
            }
        }
        return controlList;
    }

    /**
     * 사용자컨텐츠 관리에서 선택된 응용컨텐츠에서 사용자URL 정보를 추출한다.
     * 
     * @return 서비스명,사용자,관리자URL 정보 목록
     */
    @SuppressWarnings("unchecked")
    public static List<CmsReferenceVO> getCmsReference(ServletContext context, CmsReferenceVO cmsRVO) {

        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context, DISPATCHER_SERVLET_CONTEXT_ATTR_NAME);
        Map<String, Object> applMap = ctx.getBeansWithAnnotation(CmsReference.class);

        Object bean = applMap.get(cmsRVO.getBeanNm());
        Method m = MethodUtil.getMethod(bean.getClass(), cmsRVO.getMethodNm());

        List<CmsReferenceVO> cmsList = null;
        try {
            if(m != null) {
                cmsList = (List<CmsReferenceVO>) m.invoke(bean, cmsRVO);
            }
        } catch (Exception e) {
            logger.error("CMS 관리 목록 생성 실패 " + cmsRVO.getMethodNm(), e);
        }

        return cmsList;
    }

}
