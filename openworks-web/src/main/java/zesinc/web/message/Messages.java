/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import zesinc.core.cache.CacheService;
import zesinc.core.lang.Validate;
import zesinc.web.support.BaseConfig;

/**
 * 메시지 헨들링 인터페이스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 16.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class Messages {

    /** 메시지가 없는 경우 */
    private static final String NOT_FOUND = "등록된 메시지가 없습니다.";

    /**
     * 모든 메시지 키 목록을 반환한다.
     * 
     * @return
     */
    public static Iterator<String> getKeys() {
        @SuppressWarnings("unchecked")
        MultilangMap<String, String> msg = (MultilangMap<String, String>) CacheService.get(BaseConfig.MULTILANG_CACHE_KEY);

        return msg.keySet().iterator();
    }

    /**
     * 키에 해당하는 메시지 목록을 Map으로 반환한다.
     * 
     * @return
     */
    public static Map<String, Map<String, String>> getMessages() {

        Map<String, String> keyMap = new HashMap<String, String>();

        Iterator<String> keys = getKeys();
        while(keys.hasNext()) {
            String key = keys.next();
            String val = getMessage(key);
            key = key.substring(key.lastIndexOf(".") + 1);
            keyMap.put(key, val);
        }

        Map<String, Map<String, String>> msgMap = new HashMap<String, Map<String, String>>();
        msgMap.put("msg", keyMap);

        return msgMap;
    }

    /**
     * 포멧팅이 필요 없는 기본 메시지 반환
     * 
     * @param key 메시지 키
     * @return
     */
    public static String getMessage(String key) {

        return getMessage(key, null, "");
    }

    /**
     * <pre>
     * 메시지 포멧팅을 수행하여 문자열을 반환한다.
     * key에 해당하는 기본 메시지를 설정에서 가져온 후 messages 에 해당하는 문자열을 포멧팅한다.
     * 단! messages가 null 인 경우 포멧팅 없이 환경설정의 기본값을 그대로 출력한다.
     *     이것은 config.getString() 와 동일하다. 따라서 포멧팅을 사용하지 않는 경우에는 getString()를 사용한다.
     * 예 1)
     * 환경설정 기본메시지 : 입력 가능한 값은 {0}부터 {1}까지이며 {2}은 생략가능합니다.
     * massages : String 배열에 {"2014-01-01","2014-12-31","종료일"} 
     * 포멧팅 결과 : 입력 가능한 값은 2014-01-01부터 2014-12-31까지이며 종료일은 생략가능합니다.
     * 예 2)
     * 환경설정 기본메시지 : {0}--{1}
     * massages : String 배열에 {"훌륭한", "개발자입니다."} 
     * 포멧팅 결과 : 훌륭한--개발자입니다.
     * </pre>
     * 
     * @param key 메시지 키
     * @param messages 메시지 포멧팅에 해당하는 값
     * @return
     * @see java.text.MessageFormat
     */
    public static String getMessage(String key, Object[] messages, String defaultMessage) {

        if(Validate.isEmpty(key)) {
            return "";
        }

        @SuppressWarnings("unchecked")
        MultilangMap<String, String> msg = (MultilangMap<String, String>) CacheService.get(BaseConfig.MULTILANG_CACHE_KEY);
        String returnVal = msg.getString(key);

        if(Validate.isEmpty(returnVal)) {
            returnVal = Validate.isNotEmpty(defaultMessage) ? defaultMessage : NOT_FOUND;
        }

        return Validate.isEmpty(messages) ? returnVal.trim() : new MessageFormat(returnVal).format(messages).trim();
    }
}
