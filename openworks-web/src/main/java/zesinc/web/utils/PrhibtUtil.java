/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.core.utils.StringUtil;
import zesinc.web.support.BaseConfig;

/**
 * 금지단어 필터링관련 유틸 본
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 6. 19.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PrhibtUtil {

    /** 기본 치완 문자열 */
    private static final char MASK_STR = '*';

    /**
     * <pre>
     * 코드에 해당하는 금지단어 목록을 반환한다.
     * 금지단어 목록은 금지단어 관리에 등록되어 있어야 하며, 서버 기동시에
     * Cache를 통하여 초기화 및 메모리에 적재된다.
     * </pre>
     * 
     * @param prhibtCodes 금지단어 코드 배열
     * @return
     */
    public static List<String> getPrhibtList(String[] prhibtCodes) {

        @SuppressWarnings("unchecked")
        Map<String, List<String>> prhibtWrdMap = (Map<String, List<String>>) CacheService.get(BaseConfig.PRHIBT_CACHE_KEY);

        List<String> prhibtList = new ArrayList<String>();
        List<String> prhibtWrdList = null;

        for(String prhibtCode : prhibtCodes) {
            prhibtWrdList = prhibtWrdMap.get(prhibtCode);
            if(Validate.isNotEmpty(prhibtWrdList)) {
                prhibtList.addAll(prhibtWrdMap.get(prhibtCode));
            }
        }

        return prhibtList;
    }

    /**
     * 금지단어가 포함되어있는지 여부를 확인.
     * 금지단어 관리 기능과 연동된 Cache에서 prhibtCode 코드에 해당하는 금지단어 목록을 가져와서 검증한다.
     * 
     * @param prhibtCode 금지단어 드
     * @param contents 대상 컨텐츠
     * @return 금지단어가 있다면 true 아니면 false
     */
    public static Boolean containsPrhibtWrd(String prhibtCode, String contents) {

        return containsPrhibtWrd(new String[] { prhibtCode }, contents);
    }

    /**
     * 금지단어가 포함되어있는지 여부를 확인.
     * 금지단어 관리 기능과 연동된 Cache에서 prhibtCode 코드에 해당하는 금지단어 목록을 가져와서 검증한다.
     * 
     * @param prhibtCodes 금지단어 코드 배열
     * @param contents 대상 컨텐츠
     * @return 금지단어가 있다면 true 아니면 false
     */
    public static Boolean containsPrhibtWrd(String[] prhibtCodes, String contents) {

        return containsPrhibtWrd(getPrhibtList(prhibtCodes), contents);
    }

    /**
     * 금지단어가 포함되어있는지 여부
     * 
     * @param prhibtWrdList 금지단어 목록
     * @param contents 대상 컨텐츠
     * @return 금지단어가 있다면 true 아니면 false
     */
    public static Boolean containsPrhibtWrd(List<String> prhibtWrdList, String contents) {

        int index = StringUtil.indexOfAny(contents, prhibtWrdList.toArray(new String[prhibtWrdList.size()]));

        return (index < 0 ? Boolean.FALSE : Boolean.TRUE);
    }

    /**
     * 금칙어로 설정된 문자들을 글자길이 만큼 '*' 문자로 마스킹한다.
     * 
     * @param prhibtCode 금지단어 코드
     * @param contents 대상 컨텐츠
     * @return 마스킹된 문자열
     */
    public static String maskPrhibtWrd(String prhibtCode, String contents) {

        return maskPrhibtWrd(prhibtCode, contents, MASK_STR);
    }

    /**
     * 금칙어로 설정된 문자들은 전달된 케릭터로 마스킹한다.
     * 
     * @param prhibtCode 금지단어 코드
     * @param contents 대상 컨텐츠
     * @param mask 마스킹할 케릭터
     * @return 마스킹된 문자열
     */
    public static String maskPrhibtWrd(String prhibtCode, String contents, char mask) {

        return maskPrhibtWrd(new String[] { prhibtCode }, contents, mask);
    }

    /**
     * 금칙어로 설정된 문자들은 전달된 케릭터로 마스킹한다.
     * 
     * @param prhibtCodes 금지단어 코드 배열
     * @param contents 대상 컨텐츠
     * @param mask 마스킹할 케릭터
     * @return 마스킹된 문자열
     */
    public static String maskPrhibtWrd(String[] prhibtCodes, String contents, char mask) {

        List<String> prhibtWrdList = getPrhibtList(prhibtCodes);

        return maskPrhibtWrd(prhibtWrdList, contents, mask);
    }

    /**
     * 전달된 금칙어 목록 문자들은 전달된 케릭터로 마스킹한다.
     * 
     * @param prhibtWrdList 금지단어 목록
     * @param contents 대상 컨텐츠
     * @return 마스킹된 문자열
     */
    public static String maskPrhibtWrd(List<String> prhibtWrdList, String contents) {

        return maskPrhibtWrd(prhibtWrdList, contents, MASK_STR);
    }

    /**
     * 금칙어로 설정된 문자들은 전달된 케릭터로 마스킹한다.
     * 
     * @param prhibtWrdList 금지단어 목록
     * @param contents 대상 컨텐츠
     * @param mask 마스킹할 케릭터
     * @return 마스킹된 문자열
     */
    public static String maskPrhibtWrd(List<String> prhibtWrdList, String contents, char mask) {

        // 금지단어목록이 없는 경우
        if(Validate.isEmpty(prhibtWrdList)) {
            return contents;
        }

        int phbwdCnt = prhibtWrdList.size();
        String[] replacement = new String[phbwdCnt];
        String prhibtWrd = null;
        for(int i = 0 ; i < phbwdCnt ; i++) {
            prhibtWrd = prhibtWrdList.get(i);
            char[] maskStr = new char[prhibtWrd.length()];
            Arrays.fill(maskStr, mask);
            replacement[i] = new String(maskStr);
        }

        return StringUtil.replaceEachRepeatedly(contents, prhibtWrdList.toArray(new String[phbwdCnt]), replacement);
    }

}
