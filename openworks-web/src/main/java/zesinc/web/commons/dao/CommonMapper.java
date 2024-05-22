/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.commons.dao;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

import zesinc.web.support.pager.Pager;
import zesinc.web.vo.PageVO;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;

/**
 * <pre>
 * 페이징 처리를 유연하게 하기 위한 공통 DAO 클레스
 * 전자정부 프레임워크를 상속받아서 페이징 처리 자동화부분을 추가.
 * 
 * 주의사항은 카운트 쿼리가 반드시 준비되어 있어야 하며,
 * 네이밍 규칙은 목록 쿼리 아이디에 Count 라는 단어가 추가되어 있어야 한다.
 * 
 * 예)
 * - 목록 쿼리 아이디 : xxx.yyy.getList
 * - 목록 카운트 쿼리 : xxx.yyy.getListCount
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CommonMapper extends EgovAbstractMapper {

    /** 카운트 쿼리 접미어 */
    private static final String POSTFIX_COUNT_QUERY = "Count";

    /**
     * <pre>
     * Openworks 페이징 규칙에 일치되는 추가 함수
     * Count 쿼리가 쌍으로 존재해야 한다.
     * 예)
     * - 목록 쿼리 아이디 : xxx.yyy.getList
     * - 목록 카운트 쿼리 : xxx.yyy.getListCount
     * </pre>
     * 
     * @param queryId 쿼리 메핑 ID
     * @param parameterObject BaseVO를 상속받은 VO 클레스
     * @return
     */
    public <T extends PageVO> Pager<T> pagingList(String queryId, T parameterObject) {

        List<T> dataList = selectList(queryId, parameterObject);
        Integer totalNum = selectOne(queryId + POSTFIX_COUNT_QUERY, parameterObject);

        return new Pager<T>(dataList, parameterObject, totalNum);
    }

    /**
     * ResultHandler 를 사용할 수 있도록 기능 추가
     * 
     * @param queryId 쿼리 메핑 ID
     * @param parameterObject 파라미터 객체
     * @param handler ResultHandler
     */
    public void selectWithHandler(String queryId, Object parameterObject, ResultHandler handler) {
        getSqlSession().select(queryId, parameterObject, handler);
    }

}
