/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.compare.impl;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import zesinc.core.compare.CompareSupport;
import zesinc.core.compare.CompareView;
import zesinc.core.compare.CompareViewVO;
import difflib.DiffRow;
import difflib.DiffRow.Tag;
import difflib.DiffRowGenerator;

/**
 * 두개의 컨텐츠를 비교하여 이전과 신규로 구분하여 변경 사항을 비교하여 결과를 반환한다.
 * <p />
 * 결과는 VO 객체로 반환하여 직접 관리 할 수 있는 방법과, 설정으로 지원되는 html 코드로 자체 적용되어 지원되는 타입을 지원한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 20.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CompareViewImpl implements CompareView {

    /**
     * 비교결과 유형. 기본값은 전체 유형
     * 
     * @see Type
     */
    protected Type type = Type.all;

    /**
     * 출력 유형. 기본값은 table 태그 사용
     * 
     * @see View
     */
    protected View view = View.table;

    /** 우측 컨텐츠 */
    protected List<String> rightContent;

    /** 좌측 컨텐츠 */
    protected List<String> leftContent;

    @Override
    public CompareView setType(Type type) {
        this.type = type;
        return this;
    }

    @Override
    public CompareView setView(View view) {
        this.view = view;
        return this;
    }

    /**
     * 우측 컨텐츠 설정
     * 
     * @return
     */
    @Override
    public <T> CompareView setRightContent(T content) {
        if(content instanceof CharSequence) {
            this.rightContent = CompareSupport.getContents((CharSequence) content);
        } else if(content instanceof File) {
            this.rightContent = CompareSupport.getContents((File) content);
        } else {
            new Throwable("지원되지 않는 컨텐츠 형식입니다.");
        }
        return this;

    }

    /**
     * 좌측 컨텐츠 설정
     * 
     * @return
     */
    @Override
    public <T> CompareView setLeftContent(T content) {
        if(content instanceof CharSequence) {
            this.leftContent = CompareSupport.getContents((CharSequence) content);
        } else if(content instanceof File) {
            this.leftContent = CompareSupport.getContents((File) content);
        } else {
            new Throwable("지원되지 않는 컨텐츠 형식입니다.");
        }
        return this;
    }

    @Override
    public List<String> getCompareStringList() {

        List<String> wrapList = new LinkedList<String>();
        List<CompareViewVO> rowList = getDiff();

        switch(this.view) {
            case div:
                wrapList = CompareSupport.getDivWrap(rowList);
                break;
            case ul:
                wrapList = CompareSupport.getUlWrap(rowList);
                break;
            case ol:
                wrapList = CompareSupport.getOlWrap(rowList);
                break;
            case span:
                wrapList = CompareSupport.getSpanWrap(rowList);
                break;
            default:
                wrapList = CompareSupport.getTableWrap(rowList);
                break;
        }

        return wrapList;
    }

    @Override
    public List<CompareViewVO> getCompareVoList() {

        return getDiff();
    }

    /**
     * 줄단위 비교 데이터 결과 반환
     * 
     * @return
     */
    private List<CompareViewVO> getDiff() {

        if(this.type.equals(Type.all)) {
            return getAllCompare();
        } else if(this.type.equals(Type.change)) {
            return getAllChangeCompare();
        }
        return getSelectDiff();
    }

    /**
     * 줄단위 비교 데이터 결과 전체 반환
     * 
     * @return
     */
    private List<CompareViewVO> getAllCompare() {

        DiffRowGenerator generator = new DiffRowGenerator.Builder()
            .columnWidth(Integer.MAX_VALUE).build();

        List<DiffRow> rowList = generator.generateDiffRows(this.leftContent, this.rightContent);

        List<CompareViewVO> wrapList = new LinkedList<CompareViewVO>();

        CompareViewVO viewVo;
        Integer lineNum = 1;
        for(DiffRow row : rowList) {
            lineNum++;
            viewVo = new CompareViewVO(row);
            viewVo.setLineNum(lineNum);
            wrapList.add(viewVo);
        }

        return wrapList;
    }

    /**
     * 줄단위 비교 데이터 결과 변경 전체 반환
     * 
     * @return
     */
    private List<CompareViewVO> getAllChangeCompare() {

        DiffRowGenerator generator = new DiffRowGenerator.Builder().columnWidth(Integer.MAX_VALUE).build();

        List<DiffRow> rowList = generator.generateDiffRows(this.leftContent, this.rightContent);

        List<CompareViewVO> wrapList = new LinkedList<CompareViewVO>();

        CompareViewVO viewVo;
        Integer lineNum = 1;
        for(DiffRow row : rowList) {
            lineNum++;

            if(!row.getTag().equals(Tag.EQUAL)) {
                viewVo = new CompareViewVO(row);
                viewVo.setLineNum(lineNum);
                wrapList.add(viewVo);
            }
        }

        return wrapList;
    }

    /**
     * 줄단위 비교 데이터 결과 선택적 반환
     * <p />
     * 추가, 변경, 삭제, 동일 한 결과만 선택적으로 추출
     * 
     * @return
     */
    private List<CompareViewVO> getSelectDiff() {

        DiffRowGenerator generator = new DiffRowGenerator.Builder().columnWidth(Integer.MAX_VALUE).build();

        List<DiffRow> rowList = generator.generateDiffRows(this.leftContent, this.rightContent);
        List<CompareViewVO> wrapList = new LinkedList<CompareViewVO>();

        CompareViewVO viewVo;
        Integer lineNum = 1;

        for(DiffRow row : rowList) {
            lineNum++;
            if(Type.add.equals(this.type) && row.getTag().equals(Tag.INSERT)) {
                viewVo = new CompareViewVO(row);
                viewVo.setLineNum(lineNum);
                wrapList.add(viewVo);
            } else if(Type.delete.equals(this.type) && row.getTag().equals(Tag.DELETE)) {
                viewVo = new CompareViewVO(row);
                viewVo.setLineNum(lineNum);
                wrapList.add(viewVo);
            } else if(Type.modify.equals(this.type) && row.getTag().equals(Tag.CHANGE)) {
                viewVo = new CompareViewVO(row);
                viewVo.setLineNum(lineNum);
                wrapList.add(viewVo);
            } else if(Type.same.equals(this.type) && row.getTag().equals(Tag.EQUAL)) {
                viewVo = new CompareViewVO(row);
                viewVo.setLineNum(lineNum);
                wrapList.add(viewVo);
            }
        }

        return wrapList;
    }
}
