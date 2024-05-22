/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.pager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.vo.PageVO;

/**
 * 데이타 페이징 처리
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 5.    방기배   신규 등록
 * </pre>
 *
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public final class Pager<T extends PageVO> {

    /** 페이지당 목록 수 기본 값 */
    public static final int DEFAULT_ROW_PER_PAGE = Config.getInt("system-config.paging.rowPerPage");
    /** 페이지 하단 표시 페이징 수 기본 값 */
    public static final int DEFAULT_PAGE_PER_PAGE = Config.getInt("system-config.paging.pagePerPage");
    /** 현재 페이지 번호 기본 값 */
    public static final int DEFAULT_CURR_PAGE = 1;

    /** 데이타 시작 인덱스 값 */
    transient private int indexNo;

    /** 총 데이터 갯수 */
    transient private int totalNum;

    /** 총 페이지 갯수 */
    transient private int totalPage;

    /** 화면에 보여줄 페이지 갯수 */
    transient private int pagePerPage;

    /** 현재 페이지 */
    transient private int currPage;

    /** 화면에 출력될 시작 페이지 */
    transient private int startPage;

    /** 화면에 출력될 마지막 페이지 */
    transient private int endPage;

    /** 페이지 목록 */
    transient private int pageList;

    /** 데이터 목록 */
    private List<T> list = new ArrayList<T>();

    /**
     * 페이지 분할 데이터 셋팅
     *
     * @param list 데이터 목록
     * @param pageVo PageVO 상속 VO
     * @param totalNum 조회조건에 해당하는 전체 데이터 컨수
     */
    public Pager(List<T> list, T pageVo, int totalNum) {

        Map<String, Object> paramMap = pageVo.getParamMap();

        if(Validate.isEmpty(pageVo.getParamMap())) {
            paramMap = new HashMap<String, Object>();
        }

        int currPage = (Validate.isNotNull(paramMap.get("q_currPage"))
            ? (Integer) paramMap.get("q_currPage") : DEFAULT_CURR_PAGE);

        int rowPerPage = (Validate.isNotNull(paramMap.get("q_rowPerPage"))
            ? (Integer) paramMap.get("q_rowPerPage") : DEFAULT_ROW_PER_PAGE);

        int pagePerPage = (Validate.isNotNull(paramMap.get("q_pagePerPage"))
            ? (Integer) paramMap.get("q_pagePerPage") : DEFAULT_PAGE_PER_PAGE);

        this.totalNum = totalNum;
        this.pagePerPage = pagePerPage;
        this.currPage = currPage;

        this.totalPage = (int) (Math.ceil((double) totalNum / (double) rowPerPage));
        this.pageList = (int) (Math.ceil((currPage - 1) / pagePerPage)) - 1;

        this.indexNo = getTotalNum() - ((getCurrPage() - 1) * rowPerPage);

        if(list != null) {
            this.list.addAll(list);
        }
    }

    /**
     * 초기화
     *
     * @param list 데이터 목록
     * @param totalNum 데이터 전체 갯수
     */
    public Pager(List<T> list, int totalNum) {
        this(list, totalNum, DEFAULT_CURR_PAGE);
    }

    /**
     * 초기화
     *
     * @param list 데이터 목록
     * @param totalNum 데이터 전체 갯수
     * @param currPage 현재 페이지
     */
    public Pager(List<T> list, int totalNum, int currPage) {
        this(list, totalNum, currPage, DEFAULT_ROW_PER_PAGE);
    }

    /**
     * 초기화
     *
     * @param list 데이터 목록
     * @param totalNum 데이터 전체 갯수
     * @param currPage 현재 페이지
     * @param rowPerPage 한페이지에 출력될 데이터 갯수
     */
    public Pager(List<T> list, int totalNum, int currPage, int rowPerPage) {
        this(list, totalNum, currPage, rowPerPage, DEFAULT_PAGE_PER_PAGE);
    }

    /**
     * 초기화
     *
     * @param list 데이터 목록
     * @param totalNum 데이터 전체 갯수
     * @param currPage 현재 페이지
     * @param rowPerPage 한페이지에 출력될 데이터 갯수
     * @param pagePerPage 화면에 보여줄 페이지 갯수
     */
    public Pager(List<T> list, int totalNum, int currPage, int rowPerPage, int pagePerPage) {

        this.totalNum = totalNum;
        this.pagePerPage = pagePerPage;
        this.currPage = currPage;

        this.totalPage = (int) (Math.ceil((double) totalNum / (double) rowPerPage));
        this.pageList = (int) (Math.ceil((currPage - 1) / pagePerPage)) - 1;

        this.indexNo = getTotalNum() - ((getCurrPage() - 1) * rowPerPage);

        if(list != null) {
            this.list.addAll(list);
        }
    }

    /**
     * 데이터 목록
     *
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * 데이타 시작 인덱스 값을 얻는다.
     *
     * @return int
     */
    public int getIndexNo() {
        return indexNo;
    }

    /**
     * 현재 클릭한 페이지 번호를 얻는다.
     *
     * @return int
     */
    public int getCurrPage() {
        return this.currPage;
    }

    /**
     * 총 페이지 수를 얻는다.
     *
     * @return int
     */
    public int getTotalPage() {
        int pageValue = totalPage;

        if(pageValue == 0) {
            pageValue = 1;
        }

        return pageValue;
    }

    /**
     * 총 게시물 수를 얻는다.
     *
     * @return int
     */
    public int getTotalNum() {
        return this.totalNum;
    }

    /**
     * 출력할 페이지 값을 얻는다.
     *
     * @return int[]
     */
    public int[] getPages() {

        // 화면에 나타날 시작 페이지
        startPage = (pageList + 1) * pagePerPage;

        // 화면에 나타날 마지막 페이지
        endPage = startPage + pagePerPage;

        if(endPage > totalPage) {
            endPage = totalPage;
        }

        int[] page = null;

        if(endPage > startPage) {
            page = new int[endPage - startPage];
        } else {
            page = new int[0];
        }

        int length = page.length;

        for(int i = 0 ; i < length ; i++) {
            page[i] = startPage + i + 1;
        }

        return page;
    }

    /**
     * 이전 부분의 마지막 페이지 값을 얻는다.
     *
     * @return int
     */
    public int getPreviousPartLastPage() {
        return (pageList + 1) * pagePerPage;
    }

    /**
     * 다음 부분의 첫페이지 값을 얻는다.
     *
     * @return int
     */
    public int getNextPartFirstPage() {
        return ((pageList + 2) * pagePerPage) + 1;
    }

    /**
     * 이전 페이지를 얻는다.
     *
     * @return int
     */
    public int getPreviousPage() {
        int previousPage = 0;

        if(currPage != 1) {
            previousPage = currPage - 1;
        } else {
            previousPage = 1;
        }

        return previousPage;
    }

    /**
     * 다음 페이지를 얻는다.
     *
     * @return int
     */
    public int getNextPage() {
        int nextPage = 0;

        if(currPage == totalPage) {
            nextPage = totalPage;
        } else {
            nextPage = currPage + 1;
        }

        return nextPage;
    }

    /**
     * 마지막 페이지 값을 얻는다.
     *
     * @return int
     */
    public int getLastPage() {
        return totalPage;
    }

    /**
     * 현재 클릭한 페이지 부분이 마지막 부분인지 체크
     *
     * @return boolean
     */
    public boolean isLastPagePart() {
        boolean isLastPagePart = false;

        if(endPage >= totalPage) {
            isLastPagePart = true;
        }

        return isLastPagePart;
    }

    /**
     * 현재 클릭한 페이지 부분이 시작 부분인지 체크
     *
     * @return boolean
     */
    public boolean isStartPagePart() {
        boolean isStartPagePart = false;

        if(startPage == 0) {
            isStartPagePart = true;
        }

        return isStartPagePart;
    }

    /**
     * 이전 페이지가 존재하는지 검사
     *
     * @return boolean
     */
    public boolean isPreviousPage() {
        boolean isPreviousPage = false;

        if(currPage > 1) {
            isPreviousPage = true;
        }

        return isPreviousPage;
    }

    /**
     * 다음 페이지가 존재하는지 검사
     *
     * @return boolean
     */
    public boolean isNextPage() {
        boolean isNextPage = false;

        if(currPage < totalPage) {
            isNextPage = true;
        }

        return isNextPage;
    }

    /**
     * 페이지 시작 인덱스 번호
     *
     * @param iCurrentPage
     * @param iRowPerPage
     * @return
     */
    public static int getStartNum(int iCurrentPage, int iRowPerPage) {

        return (iCurrentPage * iRowPerPage) - (iRowPerPage - 1);
    }

    /**
     * 페이지 종료 인덱스 번호
     *
     * @param iStartNum
     * @param iRowPerPage
     * @return
     */
    public static int getEndNum(int iStartNum, int iRowPerPage) {

        return iStartNum + (iRowPerPage - 1);
    }
}
