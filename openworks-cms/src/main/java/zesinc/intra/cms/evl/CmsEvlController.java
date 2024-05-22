/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.intra.cms.evl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.core.cache.CacheService;
import zesinc.intra.cms.evl.domain.CmsEvlVO;
import zesinc.web.auth.AuthType;
import zesinc.web.auth.anotation.OpenworksAuth;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.support.BaseConfig;
import zesinc.web.vo.cache.DomnCacheVO;

/**
 * 사용자메뉴평가 정보 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-06-09.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller("사용자메뉴평가 관리")
@RequestMapping(value = { "/intra/cms/evl" })
public class CmsEvlController extends IntraController {

    @Resource(name = "opCmsEvlService")
    private CmsEvlService opCmsEvlService;

    /**
     * 사용자메뉴평가 메뉴별 점수 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "INC_selectCmsEvlList.do", "PD_selectCmsEvlList.do" })
    public void selectCmsEvlList(HttpServletRequest request, Model model, CmsEvlVO cmsEvlVo) {

        // 검색일이 없는 경우 당월 조회
//        if(Validate.isEmpty(cmsEvlVo.getParam("q_startDt")) ||
//            Validate.isEmpty(cmsEvlVo.getParam("q_endDt"))) {
//            Locale locale = LocaleContextHolder.getLocale();
//            Calendar cal = Calendar.getInstance(locale);
//
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH) + 1;
//            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(year).append("-");
//            if(month < 10) {
//                sb.append("0");
//            }
//            sb.append(month);
//            sb.append("-");
//            cmsEvlVo.addParam("q_startDt", sb.toString() + "01");
//
//            if(lastDay < 10) {
//                sb.append("0");
//            }
//            sb.append(lastDay);
//            cmsEvlVo.addParam("q_endDt", sb.toString());
//        }

        model.addAttribute(BaseConfig.KEY_DATA_LIST, opCmsEvlService.selectCmsEvlList(cmsEvlVo));
    }

    /**
     * 사용자메뉴평가 메뉴별 그래프 데이터
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "ND_selectCmsEvlChartList.do" })
    public String selectCmsEvlChartList(HttpServletRequest request, Model model, CmsEvlVO cmsEvlVo) {

        return responseJson(model, opCmsEvlService.selectCmsEvlChartList(cmsEvlVo));
    }

    /**
     * 사용자메뉴평가 상세 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = "PD_selectCmsEvlDetailList.do")
    public void selectCmsEvlDetailList(HttpServletRequest request, Model model, CmsEvlVO cmsEvlVo) {

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsEvlService.selectCmsEvlDetailPageList(cmsEvlVo));
    }

    /**
     * 사용자메뉴평가 전체 목록
     */
    @OpenworksAuth(authType = AuthType.READ)
    @RequestMapping(value = { "BD_selectCmsEvlList.do" })
    public void selectCmsEvlBDList(HttpServletRequest request, Model model, CmsEvlVO cmsEvlVo) {

        @SuppressWarnings("unchecked")
        List<DomnCacheVO> domnList = (List<DomnCacheVO>) CacheService.get(BaseConfig.DOMN_LIST_CACHE_KEY);
        model.addAttribute(BaseConfig.KEY_DOMAIN_LIST, domnList);

        model.addAttribute(BaseConfig.KEY_PAGER, opCmsEvlService.selectCmsEvlAllPageList(cmsEvlVo));
    }

}
