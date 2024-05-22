/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.utils;

import org.apache.commons.lang3.StringUtils;

import zesinc.core.lang.Validate;

/**
 * Jakarta commons-lang3 패키지의 StringUtils 클레스를 상속받아 활용
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 29.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class StringUtil extends StringUtils {

    /**
     * <pre>
     * 지정한 길이만큼 문자열을 생략한다.
     * StringUtil.omit("aaaa", 2) -> returns "aa.."
     * </pre>
     * 
     * @param String omitStr
     * @param int omitLength
     */
    public static String omit(final String omitStr, int omitLength) {

        if(Validate.isEmpty(omitStr)) {
            return EMPTY;
        }

        if(omitLength <= 0) {
            omitLength = 60;
        }

        String omittee = omitStr;
        if(getUnicodeLength(omittee) > omitLength) {
            omittee = fixUnicodeLength(omittee, omitLength, "..");
        }

        return omittee;
    }

    /**
     * 스트링을 지정한 길이만큼 자른 뒤 지정한 문자열을 추가한다. (한글처리)
     * 
     * @param str 대상 문자열
     * @param limit 자를 길이의 최대값
     * @param postFix 뒤에 추가할 문자열
     * @return 지정한 길이만큼 잘려진 문자열
     */
    public static String fixUnicodeLength(final String str, int limit, String postFix) {

        if(str == null) {
            return EMPTY;
        }

        char[] charArray = str.toCharArray();

        if(limit >= charArray.length) {
            return str;
        }

        if(postFix == null) {
            return new String(charArray, 0, limit);
        } else {
            return new String(charArray, 0, limit).concat(postFix);
        }
    }

    /**
     * 지정 문자열의 길이를 얻는다. (한글처리)
     * 
     * @param str 대상 문자열
     * @return 문자열 길이
     */
    public static int getUnicodeLength(final String str) {

        if(str == null) {
            return 0;
        }

        return str.toCharArray().length;
    }

    /**
     * 문자열을 DB 입력시에 맞도록 byte로 길이를 확인
     * 
     * @param str
     * @return
     */
    public static int getByteLength(final String str) {

        if(Validate.isEmpty(str)) {
            return 0;
        }
        byte[] bt = str.getBytes();

        return bt.length;
    }

    /**
     * DB는 byte를 기준으로 하므로(일반적으로) UTF-8 문자를 3byte로 계산하여 길이를 조절한다.
     * 
     * @param str
     * @param limit 제한길이 수치
     * @param postFix 문자열을 자른 후 붙일 문자 또는 null
     * @return
     */
    public static String fixByteLength(final String str, int limit, String postFix) {

        if(Validate.isEmpty(str)) {
            return "";
        }

        int length = 0;
        StringBuilder sb = new StringBuilder();

        for(int i = 0 ; i < str.length() ; i++) {
            char ch = str.charAt(i);
            if((ch < '0' || ch > '9') && (ch < 'A' || ch > 'Z') && ((ch > 255) || (ch < 0))) {
                length += 3;
            } else {
                length += 1;
            }
            if(length <= limit) {
                sb.append(ch);
            }
        }
        if(postFix != null) {
            sb.append(postFix);
        }
        return sb.toString();
    }

    /**
     * <pre>
     * HTML 태그를 제거한 문자열을 반환한다.
     * StringUtil.cleanHtml("<a href='#'>Hello world</a>") = "Hello world";
     * </pre>
     * 
     * @param src 대상 문자열.
     * @return 변환된 문자열.
     */
    public static String cleanHtml(String src) {

        if(src == null || src.length() == 0) {
            return "";
        }

        return src.replaceAll("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>?", "");
    }

    /**
     * <pre>
     * HTML 태그와 HTML 주석 문장을 제거한 문자열을 반환한다.
     * </pre>
     * 
     * @param src
     * @return
     */
    public static String cleanHtmlAndComment(String src) {

        return cleanHtml(src).replaceAll("(?s)<!--.*?-->", "");
    }

    /**
     * <pre>
     * 캐릭터('\n')을 '&lt;br&gt;'태그로 변환.
     * 
     * StringUtil.translateBR("ABCD\nABCD") = "ABCD&lt;br/&gt;ABCD"
     * StringUtil.translateBR("ABCD\n\nABCD") = "ABCD&lt;br/&gt;&lt;br/&gt;ABCD";
     * </pre>
     * 
     * @param src 대상 문자열.
     * @return 변환된 문자열.
     */
    public static String translateBR(String src) {

        if(src == null || src.length() == 0) {
            return "";
        }

        return StringUtils.replace(src, "\n", "<br />");
    }

    /**
     * XXX_YYY_ZZZ 형식의 DB 컬럼명을 JAVA 맴버변수 형식인 Camel Case 표기법으로 변경한다.
     * 
     * @param field
     * @return
     */
    public static String camelCase(String field) {

        if(Validate.isEmpty(field)) {
            return field;
        }

        int ty = Character.getType(field.charAt(0));
        if((field.indexOf("_", 1) < 0) && Character.UPPERCASE_LETTER != ty) {
            return field;
        }

        String word1, word2;
        String[] words = field.split("_");

        StringBuilder sb = new StringBuilder();
        if(words.length > 1) {
            sb.append(words[0].toLowerCase());
        } else {

            boolean isUpper = true;
            char[] chars = field.toCharArray();
            for(char c : chars) {
                int cTy = Character.getType(c);
                if(Character.UPPERCASE_LETTER != cTy) {
                    isUpper = false;
                    break;
                }
            }

            if(isUpper) {
                word1 = words[0].toLowerCase();
                sb.append(word1);
            } else {
                word1 = words[0].substring(0, 1).toLowerCase();
                word2 = words[0].substring(1);
                sb.append(word1).append(word2);
            }
        }

        for(int i = 1 ; i < words.length ; i++) {
            word1 = words[i].substring(0, 1).toUpperCase();
            word2 = words[i].substring(1).toLowerCase();

            sb.append(word1).append(word2);
        }

        return sb.toString();
    }
}
