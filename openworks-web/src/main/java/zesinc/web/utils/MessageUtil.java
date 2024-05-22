/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.i18n.LocaleContextHolder;

import zesinc.core.lang.Validate;
import zesinc.web.message.Messages;

/**
 * 다국어 메시지 관리 기능을 통하여 관리하며 캐시를 사용한다.
 * 이 캐시를 통하여 메시지 정보를 기능단에서 사용할 수 있도록 지원한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 31.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class MessageUtil {

    /** 서버명과 언어 매핑 환경설정 접두어 */
    public static String PREFIX_HOST_CONFIG = "host-config.";

    /** 참 값을 상수화 */
    public static final Boolean TRUE = Boolean.TRUE;
    /** 거짓 값을 상수화 */
    public static final Boolean FALSE = Boolean.FALSE;

    /**
     * <pre>
     * LocaleContextHolder 에서 언어 코드를 추출하여 반환.
     * 해당 언어에 해당되는 메시지 설정이 없다면 ko 한국어를 기본으로 반환한다.
     * </pre>
     * 
     * @return
     */
    public static String getLang() {
        Locale locale = LocaleContextHolder.getLocale();

        if(Validate.isEmpty(locale)) {
            locale = new Locale("ko", "KR");
        }

        String country = locale.getCountry();
        String lang = locale.getLanguage();
        String cdId = lang + "_" + country;

        return cdId;
    }

    /**
     * 최상위 rootKey 키를 LocaleContextHolder로 구하여 하위 메시지 목록을 계층형으로 반환한다.
     * 
     * @return
     */
    public static Map<String, Object> getMessages() {
        String rootKey = getLang();

        return getMessages(rootKey);
    }

    /**
     * rootKey를 최상위 키로하여 하위 메시지 목록을 계층형으로 반환한다.
     * 
     * @param rootKey 반환 메시지의 최상위 키
     * @return
     */
    public static Map<String, Object> getMessages(String rootKey) {

        Map<String, Object> rootMasssge = new HashMap<String, Object>();

        if(Validate.isNotEmpty(rootKey)) {
            String key;
            String jsKey;
            int rootKeyLen = rootKey.length() + 1;

            Iterator<String> keys = Messages.getKeys();
            while(keys.hasNext()) {
                key = keys.next();

                if(key.startsWith(rootKey)) {
                    jsKey = key.substring(rootKeyLen);
                    setMessages(rootMasssge, jsKey, Messages.getMessage(key));
                }
            }
        }

        return rootMasssge;
    }

    @SuppressWarnings("unchecked")
    private static void setMessages(Map<String, Object> rootMasssge, String keyStr, Object value) {

        if(Validate.isNotEmpty(keyStr)) {
            /*
             * .(점) 구분자가 없는 경우는 바로 keyStr을 key로 사용하고 value를 put 시키고,
             * 구분자가 있는 경우 split으로 나눠 각각의 단어를 key로 사용하여,
             * 기 존재시 값을 추가하고 없는 경우 객체를 생성 후 값을 추가한다.
             */
            if(keyStr.indexOf(".") < 0) {
                rootMasssge.put(keyStr, value);
            } else {
                // key 는 . 을 구분자로 사용된다.
                String[] keys = keyStr.split("\\.");
                int keyLen = keys.length;

                // 신규 생성시에 사용될 Map 객체
                Map<String, Object> nowConfig = rootMasssge;
                // 신규 생성시에 사용될 Map 객체
                Map<String, Object> newConfig;

                for(int i = 0 ; i < keyLen - 1 ; i++) {
                    if(nowConfig.containsKey(keys[i])) {
                        nowConfig = (Map<String, Object>) nowConfig.get(keys[i]);
                    } else {
                        newConfig = new HashMap<String, Object>();
                        nowConfig.put(keys[i], newConfig);
                        nowConfig = newConfig;
                    }
                }

                nowConfig.put(keys[keyLen - 1], value);
            }
        }
    }

    /**
     * request 객체에서 자동으로 해당 서버에 해당되는 언어설정을 가져와 메시지를 반환한다.
     * request 객체는 Spring Framework의 RequestContextHolder 클레스를 직접 참조한다.
     * 
     * @param key 메시지 키(국가별 코드를 제외하고 실제 메시지 부분의 코드만 전달)
     * @param messages 포멧팅에 사용될 문자배열
     * @return
     */
    public static String getMessage(String key) {

        return getMessage(getLang(), key, null);
    }

    /**
     * request 객체에서 자동으로 해당 서버에 해당되는 언어설정을 가져와 메시지를 반환한다.
     * request 객체는 Spring Framework의 RequestContextHolder 클레스를 직접 참조한다.
     * 
     * @param key 메시지 키(국가별 코드를 제외하고 실제 메시지 부분의 코드만 전달)
     * @param messages 포멧팅에 사용될 문자배열
     * @return
     */
    public static String getMessage(String key, Object[] messages) {

        return getMessage(getLang(), key, messages);
    }

    /**
     * 언어코드와 메시지키를 받아 해당 언어의 메시지를 반환
     * 
     * @param lang 언어코드(예 : 한국어 : ko, 영어 : en, 일본어 : ja, 중국어 : zh)
     * @param key
     * @param messages
     * @return
     */
    public static String getMessage(String lang, String key, Object[] messages) {

        return Messages.getMessage(lang + "." + key, messages, null);
    }

    /**
     * Locale과 메시지키를 받아 해당 언어의 메시지를 반환
     * 
     * @param locale 지역객체
     * @param key
     * @param messages
     * @return
     */
    public static String getMessage(Locale locale, String key, Object[] messages, String defaultMessage) {
        String country = locale.getCountry();
        String lang = locale.getLanguage();
        String cdId = lang + "_" + country;

        return Messages.getMessage(cdId + "." + key, messages, defaultMessage);
    }

}
