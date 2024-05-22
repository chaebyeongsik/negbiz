/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.cache;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.core.cache.Cache;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.support.helper.SpringHelper;
import zesinc.web.utils.MessageUtil;
import zesinc.web.vo.BaseVO;

/**
 * 케시정보를 새로 갱신시키기 위한 웹 API 컨트롤러
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 19.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = { "/**/component/cache" })
public class CacheReloadController extends BaseController {

    /**
     * 케시 정보를 리로드 하기 위한 웹 API
     */
    @RequestMapping(value = { "ND_reloadCache.do" }, method = RequestMethod.POST)
    public String reloadCache(HttpServletRequest request, Model model, BaseVO baseVo) {

        String cacheName = baseVo.getString("q_cacheName");
        String siteSn = baseVo.getString("q_siteSn");

        Cache cache = (Cache) SpringHelper.findBean(cacheName);
        if(cache != null) {
            cache.reloadCache(new Integer(siteSn));
        }

        return responseJson(model, Boolean.TRUE, MessageUtil.getMessage("common.processOk"));
    }

}
