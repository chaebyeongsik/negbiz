/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.misc.base64;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

import zesinc.core.config.Config;

/**
 * base64 encoding 클레스
 * Apache commons codec Base64 Delegate
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 9. 26.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class Base64Encoder {

    /** 프레임워크 기본 케릭터 셋 */
    private static String DEFAULT_CHARSET = Config.getString("system-config.defaultCharset");

    /**
     * Base64 encoding한 문자열로 반환한다.
     * 케릭터 셋은 utf-8 이 사용된다.
     * 
     * @param str 인코드대상 문자열
     * @return
     */
    public static String encode(String str) {

        return encode(str, DEFAULT_CHARSET);
    }

    /**
     * 문자열을 Base64 encoding한 문자열로 반환한다.
     * 문자셋
     * 
     * @param str 인코드대상 문자열
     * @param charset 인코드대상 문자열의 케릭터 셋
     * @return
     */
    public static String encode(String str, String charset) {

        byte[] enc;
        try {
            enc = str.getBytes(DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            return str;
        }

        return encode(enc);
    }

    /**
     * byte 문자열을 Base64 encoding한 문자열로 반환한다.
     * 
     * @param str 인코드대상 문자열
     * @return
     */
    public static String encode(byte[] str) {

        byte[] byteStr = Base64.encodeBase64(str);

        return new String(byteStr);
    }

}
