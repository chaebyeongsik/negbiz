/*
 * Copyright (c) 2010 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;

import zesinc.core.lang.Validate;

public class Converter {

    /**
     * Null 방지 (Null일 경우 StringUtil.EMPTY 반환)
     * 
     * @param src
     * @return String
     */
    public static String NVL(Object src) {

        return NVL((String) src);
    }

    public static String NVL(String src) {

        if(src == null || src.length() == 0 || src.equals("null") || " ".equals(src)) {
            return "";
        } else {
            return src.trim();
        }
    }

    /**
     * HTML 태그를 제거한 문자열을 반환한다.
     * 
     * @EXAMPLE Converter.cleanHtml("<a href='#'>Hello world</a>") =
     *          "Hello world";
     * @param src 대상 문자열.
     * @return 변환된 문자열.
     */
    public static String cleanHtml(String src) {

        if(src == null || src.length() == 0) {
            return "";
        }

        return src.replaceAll("</?[a-zA-Z][a-zA-Z0-9]*[^<>]*>?", "");
    }

    public static String cleanHtmlAndComment(String src) {

        return cleanHtml(src).replaceAll("(?s)<!--.*?-->", "");
    }

    /**
     * 캐릭터('\n')을 '&gt;br&lt;'태그로 변환.
     * 
     * @EXAMPLE Converter.translateBR("ABCD\nABCD") = "ABCD<br/>
     *          ABCD"; Converter.translateBR("ABCD\n\nABCD") = "ABCD<br/>
     * <br/>
     *          ABCD";
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
     * 캐릭터('\n')에 대해 대상 문자열의 처음 위치를 '>'태그로 변환.
     * 
     * @EXAMPLE Converter.translateReply("ABCD\nABCD") = ">ABCD>ABCD";
     *          Converter.translateReply("ABCD\n\nABCD") = ">ABCD>>ABCD";
     * @param src 대상 문자열.
     * @return 변환된 문자열.
     */
    public static String translateReply(String src) {

        if(src == null || src.length() == 0) {
            return "";
        }

        return translateInverse(src, ">");
    }

    /**
     * 캐릭터('\n')에 대해 대상 문자열의 처음 위치를 지정한 태그로 변환.
     * 
     * @EXAMPLE Converter.translateInverse("ABCD\nABCD", ">") = ">ABCD>ABCD";
     *          Converter.translateInverse("ABCD\n\nABCD", "-") = "-ABCD--ABCD";
     * @param src 대상 문자열.
     * @param tag 변환할 태그.
     * @return 변환된 문자열.
     */
    public static String translateInverse(String src, String tag) {

        if(src == null || src.length() == 0) {
            return src;
        }

        if(tag == null || tag.length() == 0) {
            return src;
        }

        String result = "";

        String fs[] = StringUtils.split(src, "\n");
        if(fs == null) {
            return src;
        }

        int fsSize = fs.length;
        for(int i = 0 ; i < fsSize ; i++) {
            result += tag + fs[i];
        }

        return result;
    }

    /**
     * 대상 문자열을 지정 문자셋으로 변경.
     * 
     * @param 대상 문자열
     * @param char1 타겟 인코딩
     * @return 지정 문자셋으로 변경된 문자열
     */
    public final static String encode(String src, String char1) {

        return encode(src, "8859_1", char1);
    }

    /**
     * 대상 문자열을 지정 문자셋으로 변경.<br/>
     * <a href='http://www.iana.org/assignments/character-sets'><b>지원 문자셋
     * 보기.</b></a>
     * 
     * @param src 대상 문자열
     * @param char1 대상 인코딩
     * @param char2 타겟 인코딩
     * @return 지정 문자셋으로 변경된 문자열. 예외발생시 원본 문자열 반환.
     */
    public final static String encode(String src, String char1, String char2) {

        if(Validate.isEmpty(src)) {
            return StringUtils.EMPTY;
        }

        String ss = null;

        try {
            ss = new String(src.getBytes(char1), char2);
        } catch (UnsupportedEncodingException uee) {
            return src;
        } catch (NullPointerException nee) {
            return src;
        }

        return ss;
    }

    /**
     * URL encode
     */
    public final static String URLEncode(String src) {
        return URLEncode(src, "UTF-8");
    }

    public final static String URLEncode(String src, String encoding) {

        if(Validate.isEmpty(src)) {
            return StringUtils.EMPTY;
        }

        try {
            return java.net.URLEncoder.encode(src, encoding);
        } catch (UnsupportedEncodingException uee) {
            return src;
        }
    }

    /**
     * NumberFormatException 방지 (변환 실패 시 '0' 반환)
     * 
     * @param src
     * @return String
     */
    public final static int getInt(Object src) {

        if(Validate.isEmpty(src)) {
            return 0;
        }

        if(src instanceof String) {
            return Integer.parseInt((String) src);
        } else if(src instanceof Number) {
            return ((Number) src).intValue();
        }

        try {
            return Integer.parseInt(src.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Null 방지 (Null일 경우 StringUtil.EMPTY 반환)
     * 
     * @param src
     * @return String
     */
    public final static String getString(Object src) {

        return NVL(src);
    }

    /**
     * IP 주소를 IP 시작점으로 변환 A.B.C.D = ((A*256+B)*256+C)*256 + D
     * 
     * @param ipAddr IP ADDRESS
     * @return
     */
    public final static int getStartIp(String ipAddr) {
        if(Validate.isEmpty(ipAddr)) {
            return -1;
        }

        String[] ipAddrs = StringUtils.split(ipAddr, ".");

        if(Validate.isEmpty(ipAddrs)) {
            return -1;
        }

        if(ipAddrs.length != 4) {
            return -1;
        }

        int aClass = Integer.parseInt(ipAddrs[0]);
        int bClass = Integer.parseInt(ipAddrs[1]);
        int cClass = Integer.parseInt(ipAddrs[2]);
        int dClass = Integer.parseInt(ipAddrs[3]);

        return ((aClass * 256 + bClass) * 256 + cClass) * 256 + dClass;
    }

    /* 계층형 표시를 위한 공백 추가하기 */
    public static String appendReplyDepth(Integer depth) {

        if(depth == null) {
            return StringUtils.EMPTY;
        }

        int iReplyLevel = depth.intValue();
        if(iReplyLevel == 0) {
            return StringUtils.EMPTY;
        }

        StringBuffer buff = new StringBuffer();
        for(int i = 0 ; i < iReplyLevel ; i++) {
            buff.append("&nbsp;&nbsp;&nbsp;");
        }

        return buff.toString();
    }

    /* Y, N 명 얻기 */
    public static String getYnNm(String src) {

        if("Y".equals(src)) {
            return "예";
        }
        return "아니오";
    }
}
