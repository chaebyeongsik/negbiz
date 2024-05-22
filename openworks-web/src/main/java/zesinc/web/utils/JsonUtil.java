/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.Validate;

/**
 * Jackson Java JSON-processor 라이브러리를 이용하여 오브젝트를 JSON화 하여 응답한다.
 * <p />
 * 대상 객체가 <code>null</code>인 경우 그대로 <code>null</code>을 출력한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 10. 15.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class JsonUtil {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    /**
     * 대상 객체를 JSON 객체 문자열로 반환한다.
     * 대상이 없거나, 오류 발생시 빈 배열형식("{}")의 문자열을 반환한다.
     * 
     * @param jsonObj
     * @return
     */
    public static String toJson(Object jsonObj) {

        String json = "{}";
        if(Validate.isNull(jsonObj)) {
            return json;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(jsonObj);
        } catch (IOException e) {}

        return json;
    }

    /**
     * JSON 문자열로 객체를 생성하여 반환한다.
     * JSON과 Class TYPE이 일치 하지 않는 경우 <code>null</code>을 반환한다.
     * 
     * @param jsonStr
     * @param objType
     * @return
     */
    public static <T> T fromJson(String jsonStr, Class<T> objType) {
        T obj = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            obj = mapper.readValue(jsonStr, objType);
        } catch (IOException e) {
            if(logger.isDebugEnabled()) {
                logger.debug("change type failed", e);
            }
        }

        return obj;
    }
}
