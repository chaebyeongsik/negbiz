/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import zesinc.core.cache.CacheService;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 도메인 객체 지원 유틸 클레스
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 9. 4.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class DomnCacheUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(DomnCacheUtil.class);

    /**
     * 현재 HttpServletRequest 요청정보에 해당하는 도메인코드 정보를 반환한다.
     *
     * @return
     */
    public static Integer getSiteSn() {

        DomnCacheVO domnVo = getDomnCache();
        if(Validate.isNotEmpty(domnVo)) {
            return domnVo.getSiteSn();
        }

        return null;
    }

    /**
     * 현재 HttpServletRequest 요청정보에 해당하는 도메인코드 정보를 반환한다.
     *
     * @param request
     * @return
     */
    public static Integer getSiteSn(HttpServletRequest request) {

        DomnCacheVO domnVo = getDomnCache(request);
        if(Validate.isNotEmpty(domnVo)) {
            return domnVo.getSiteSn();
        }

        return null;
    }

    /**
     * 지정된 도메인키에 해당하는 도메인 케시 VO 객체를 반환한다.
     *
     * @param domnKey domnNm_port_context 조합의 키 예 : www.zesinc.co.kr_80_/
     * @return
     */
    public static DomnCacheVO getDomainCache(String domnKey) {
        @SuppressWarnings("unchecked")
        Map<String, DomnCacheVO> domnNmMap = (Map<String, DomnCacheVO>) CacheService.get(BaseConfig.DOMN_NM_MAP_CACHE_KEY);

        if(Validate.isNotEmpty(domnNmMap)) {
            return domnNmMap.get(domnKey);
        }

        return null;
    }

    /**
     * 지정된 도메인 코드에 해당하는 도메인 케시 VO 객체를 반환한다.
     *
     * @param siteSn
     * @return
     */
    public static DomnCacheVO getDomainCache(Integer siteSn) {
        @SuppressWarnings("unchecked")
        Map<String, DomnCacheVO> siteSnMap = (Map<String, DomnCacheVO>) CacheService.get(BaseConfig.SITE_SN_MAP_CACHE_KEY);

        if(Validate.isNotEmpty(siteSnMap)) {
            return siteSnMap.get(String.valueOf(siteSn));
        }

        return null;
    }

    /**
     * 현재 HttpServletRequest 요청정보에 해당하는 도메인 VO 객체를 반환한다.
     * Controller / Service / DAO 클레스에서만 유효하다.
     * HttpServletRequest request 객체를 SpringFrameworks에서 얻기 때문이다
     *
     * @return
     */
    public static DomnCacheVO getDomnCache() {
        @SuppressWarnings("unchecked")
        Map<String, DomnCacheVO> domnNmMap = (Map<String, DomnCacheVO>) CacheService.get(BaseConfig.DOMN_NM_MAP_CACHE_KEY);

        if(Validate.isNotEmpty(domnNmMap)) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

            String cacheKey = "";
            String server = request.getServerName();
            String port = String.valueOf(request.getServerPort());
            String context = request.getContextPath();

            // 도메인 캐쉬 조회 시 설정된 도메인으로 자동 맵핑함 2018.07 추가
            String[] domains = Config.getStringArray("cms-config.domains.domain");
            String[] names = Config.getStringArray("cms-config.domains.name");
            String[] ports = Config.getStringArray("cms-config.domains.port");

            for (int i = 0; i < names.length; i++) {
                logger.debug("names="+names[i]);

                if (server.equals(names[i])) {
                    logger.debug("domains="+domains[i]);

                    server = domains[i];
                    if (Validate.isNotEmpty(ports) && ports.length >i && Validate.isNotEmpty(ports[i])) {
                        port = ports[i];
                    }
                }
            }

            // 컨텍스트 또는 첫번째 경로를 키로하여 도메인 케시를 1차로 찾는다.
            String uri = "";
            if(Validate.isEmpty(context)) {
                uri = request.getRequestURI();
                int endPos = uri.indexOf("/", 1);
                if(endPos < 0) {
                    endPos = 1;
                }
                context = uri.substring(0, endPos);
            }
            cacheKey = server + "_" + port + "_" + context;

            logger.debug("DomnCacheVO CacheKey is {}, URI is {}", cacheKey, uri);

            DomnCacheVO domnVo = domnNmMap.get(cacheKey);
            if(domnVo != null) {
                return domnVo;
            }

            // 위의 컨텍스트를 포함한 경로로 케시가 존재하지 않는 다면 최상위 경로인 / 로 최종 검색한다.
            cacheKey = server + "_" + port + "_/";

            domnVo = domnNmMap.get(cacheKey);
            if(domnVo != null) {
                return domnVo;
            }
        }

        return null;
    }

    /**
     * 현재 HttpServletRequest 요청정보에 해당하는 도메인 VO 객체를 반환한다.
     *
     * @param request
     * @return
     */
    public static DomnCacheVO getDomnCache(HttpServletRequest request) {
        @SuppressWarnings("unchecked")
        Map<String, DomnCacheVO> domnNmMap = (Map<String, DomnCacheVO>) CacheService.get(BaseConfig.DOMN_NM_MAP_CACHE_KEY);

        if(Validate.isNotEmpty(domnNmMap)) {

            String cacheKey = "";
            String server = request.getServerName();
            String port = String.valueOf(request.getServerPort());
            String context = request.getContextPath();

            // 도메인 캐쉬 조회 시 설정된 도메인으로 자동 맵핑함 2018.07 추가
            String[] domains = Config.getStringArray("cms-config.domains.domain");
            String[] names = Config.getStringArray("cms-config.domains.name");
            String[] ports = Config.getStringArray("cms-config.domains.port");

            for (int i = 0; i < names.length; i++) {
                logger.debug("names="+names[i]);

                if (server.equals(names[i])) {
                    logger.debug("domains="+domains[i]);

                    server = domains[i];
                    if (Validate.isNotEmpty(ports) && ports.length >i && Validate.isNotEmpty(ports[i])) {
                        port = ports[i];
                    }
                }
            }

            // 컨텍스트 또는 첫번째 경로를 키로하여 도메인 케시를 1차로 찾는다.
            String uri = "";
            if(Validate.isEmpty(context)) {
                uri = request.getRequestURI();
                int endPos = uri.indexOf("/", 1);
                if(endPos < 0) {
                    endPos = 1;
                }
                context = uri.substring(0, endPos);
            }
            cacheKey = server + "_" + port + "_" + context;

            logger.debug("DomnCacheVO CacheKey is {}, URI is {}", cacheKey, uri);

            DomnCacheVO domnVo = domnNmMap.get(cacheKey);
            if(domnVo != null) {
                return domnVo;
            }

            // 위의 컨텍스트를 포함한 경로로 케시가 존재하지 않는 다면 최상위 경로인 / 로 최종 검색한다.
            cacheKey = server + "_" + port + "_/";

            domnVo = domnNmMap.get(cacheKey);
            if(domnVo != null) {
                return domnVo;
            }
        }

        return null;
    }
}
