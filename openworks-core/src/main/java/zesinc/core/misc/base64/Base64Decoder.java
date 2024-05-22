/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.misc.base64;

import org.apache.commons.codec.binary.Base64;

/**
 * base64 decoding 클레스
 * Apache commons codec Base64 Delegate
 * 
 * @since 2014. 9
 * @author (주)제스아이엔씨 기술연구소
 * 
 *         <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 9. 26.    방기배   최초작성
 * </pre>
 * @see
 */
public class Base64Decoder {

    /**
     * Base64 decoding한 문자열로 반환한다.
     * 케릭터 셋은 utf-8 이 사용된다.
     * 
     * @param encStr 디코드대상 문자열
     * @return
     */
    public static String decode(String encStr) {

        return new String(decodeByte(encStr));
    }

    /**
     * 문자열을 Base64 decoding한 byte[] 문자열로 반환한다.
     * 
     * @param encStr 디코드대상 byte[] 문자열
     * @return
     */
    public static byte[] decodeByte(String encStr) {

        return Base64.decodeBase64(encStr);
    }
}
