/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.compare;

import java.util.List;

/**
 * 두개의 컨텐츠를 비교하여 이전과 신규로 구분하여 변경 사항을 비교하여 결과를 반환한다.
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
 * @see difflib.DiffRow
 * @see difflib.DiffRowGenerator
 */
public interface CompareView {

    /**
     * 비교결과 유형별 보기 설정
     * <p />
     * 전체, 변경 전체, 추가, 삭제, 수정, 동일 건에 대한 유형(기본값은 전체)
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
    enum Type {
        all, change, add, delete, modify, same
    }

    /**
     * 출력 포멧(html)을 설정
     * <p />
     * table, ul, ol, span, div 태그를 지원
     * 
     * <pre>
     * table : &lt;table class="diffContent"&gt;
     *            &lt;tr&gt;
     *               &lt;td class="diffType"&gt;결과유형&lt;/td&gt;
     *               &lt;td class="diffOld"&gt;이전내용&lt;/td&gt;
     *               &lt;td class="diffNew"&gt;신규내용&lt;/td&gt;
     *            &lt;/tr&gt;
     *         &lt;/table&gt;
     * ul : &lt;ul class="diffContent"&gt;
     *          &lt;li class="diffType"&gt;변경유형&lt;/li&gt;
     *          &lt;li class="diffOld"&gt;이전내용&lt;/li&gt;
     *          &lt;li class="diffNew"&gt;신규내용&lt;/li&gt;
     *      &lt;/ul&gt;
     * ol : &lt;ol class="diffContent"&gt;
     *          &lt;li class="diffType"&gt;변경유형&lt;/li&gt;
     *          &lt;li class="diffOld"&gt;이전내용&lt;/li&gt;
     *          &lt;li class="diffNew"&gt;신규내용&lt;/li&gt;
     *      &lt;/ol&gt;
     * span : &lt;span class="diffContent"&gt;
     *          &lt;span class="diffType"&gt;결과유형&lt;/span&gt;
     *          &lt;span class="diffOld"&gt;이전내용&lt;/span&gt;
     *          &lt;span class="diffNew"&gt;신규내용&lt;/span&gt;
     *        &lt;/span&gt;
     * 
     * div : &lt;div class="diffContent"&gt;
     *         &lt;span class="diffType"&gt;결과유형&lt;/span&gt;
     *         &lt;span class="diffOld"&gt;이전내용&lt;/span&gt;
     *         &lt;span class="diffNew"&gt;신규내용&lt;/span&gt;
     *       &lt;/div&gt;
     * </pre>
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
    enum View {
        table, ul, ol, span, div
    }

    /**
     * 비교결과 유형 설정
     * 
     * @param type
     */
    CompareView setType(Type type);

    /**
     * 출력 유형 설정
     * 
     * @param view
     */
    CompareView setView(View view);

    /**
     * 좌측 컨텐츠 설정
     * 
     * @return
     */
    <T> CompareView setLeftContent(T oldContent);

    /**
     * 우측 컨텐츠 설정
     * 
     * @return
     */
    <T> CompareView setRightContent(T newContent);

    /**
     * 신규/이전 내용 비교 결과를 한줄로 합하여 반환한다
     * <p />
     * {@link View} 설정을 참조하여 html 태그로 생성된다.
     * 
     * @return
     */
    List<String> getCompareStringList();

    /**
     * 직접 화면을 만들기 위한 정보만을 반환한다.
     * 
     * @return
     */
    List<CompareViewVO> getCompareVoList();

}
