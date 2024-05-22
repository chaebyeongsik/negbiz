/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.common.cache.menu;

import java.util.List;

import zesinc.web.vo.cache.MenuCacheVO;
import zesinc.web.vo.cache.MenuUrlCacheVO;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 관리자 메뉴 목록을 캐시에 담는다.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 8.    방기배   최초작성
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 */
@Mapper("opMenuCacheDao")
public interface MenuCacheMapper {

    /**
     * 관리자 메뉴 목록을 반환
     */
    List<MenuCacheVO> selectMenuList(MenuCacheVO menuCacheVo);

    /**
     * 관리자 메뉴 URL 목록을 반환
     */
    List<MenuUrlCacheVO> selectMenuUrlList(MenuUrlCacheVO menuCacheUrlVo);

    /**
     * 관리자 메뉴 URL 목록을 반환
     */
    List<MenuUrlCacheVO> selectMenuActionUrlList(MenuUrlCacheVO menuCacheUrlVo);

    /**
     * 사용자별 메뉴 URL 목록을 반환
     */
    List<MenuCacheVO> selectChargerMenuList(MenuCacheVO menuCacheVo);

    /**
     * 사용자별 MY메뉴 URL 목록을 반환
     */
    List<MenuCacheVO> selectMyMenuList(MenuCacheVO menuCacheVo);

}
