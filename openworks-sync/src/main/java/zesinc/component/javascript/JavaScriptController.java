/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.javascript;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.javascript.domain.JavaScriptConfigVO;
import zesinc.core.config.Config;
import zesinc.core.lang.Validate;
import zesinc.web.spring.controller.BaseController;
import zesinc.web.support.BaseConfig;
import zesinc.web.utils.JsonUtil;
import zesinc.web.utils.MessageUtil;

/**
 * Java와 JavaScript 단과 설정 등을 공유하기 위한 컨트롤러
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 1. 11.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = { "/**/component/js" })
public class JavaScriptController extends BaseController {

    /** 최상위 키 */
    private static String[] rootKeys = { "webapp-config" };
    /** 공통 JavaScript 변수명 */
    private static final String GLOBAL_NAME = "var OpConfig = ";
    /** 메시지 JavaScript 변수명 */
    private static final String MSG_NAME = "var OpMessage = ";
    /** 사용자 정의 JavaScript 변수명 */
    private static final String CUS_NAME = "var OpCustom = ";

    /**
     * 화면단에 JAVA와 동일한 환경 설정 내용을 전달한다.(데코레이터에 정의됨)
     * <p />
     * 프레임워크 기본 설정을 JSON 객체로 반환
     * <p />
     * <code>
     * &lt;script type="text/javascript" src="/component/js/ND_globalConfig.do"&gt;&lt;/script&gt;
     * </code>
     * 
     * @param model 반환값을 담을 리턴 객체
     * @return
     */
    @RequestMapping(value = { "ND_globalConfig.do" })
    public String getGlobalConfig(HttpServletResponse response, Model model) {

        response.setContentType("text/javascript;charset="+BaseConfig.CHAR_SET);

        Map<String, Object> rootConfig = new HashMap<String, Object>();
        for(String rootKey : rootKeys) {
            setGlobalConfig(rootConfig, rootKey);
        }

        String json = GLOBAL_NAME + JsonUtil.toJson(rootConfig);

        return responseText(model, json);
    }

    /**
     * 화면단에 JAVA와 동일한 환경 설정 내용을 전달한다.
     * <p />
     * 기능별 환경 설정 코드를 JSON 객체로 반환
     * <p />
     * <code>
     * &lt;script type="text/javascript" src="/component/js/ND_customConfig.do?rootKey=설정키1&rootKey=설정키2&..."&gt;&lt;/script&gt;
     * </code>
     * 
     * @param request 요청객체
     * @param model 반환값을 담을 리턴 객체
     * @return
     */
    @RequestMapping(value = { "ND_customConfig.do" })
    public String getCustomConfig(HttpServletResponse response, Model model, JavaScriptConfigVO confVo) {

        response.setContentType("text/javascript;charset="+BaseConfig.CHAR_SET);

        Map<String, Object> customConfig = new HashMap<String, Object>();
        if(Validate.isNotEmpty(confVo.getRootKey())) {
            for(String rootKey : confVo.getRootKey()) {
                setGlobalConfig(customConfig, rootKey);
            }
        }

        String json = CUS_NAME + JsonUtil.toJson(customConfig);

        return responseText(model, json);
    }

    /**
     * 화면단에 JAVA와 동일한 환경 설정 내용을 전달한다.(데코레이터에 정의됨)
     * <p />
     * 프레임워크 기본 설정을 JSON 객체로 반환
     * <p />
     * <code>
     * &lt;script type="text/javascript" src="/component/js/ND_massageConfig.do"&gt;&lt;/script&gt;
     * </code>
     * 
     * @param model 반환값을 담을 리턴 객체
     * @return
     */
    @RequestMapping(value = { "ND_massageConfig.do" })
    public String getMassageConfig(HttpServletResponse response, Model model, JavaScriptConfigVO confVo) {

        response.setContentType("text/javascript;charset="+BaseConfig.CHAR_SET);

        Map<String, Object> messageConfig = new HashMap<String, Object>();
        if(Validate.isNotEmpty(confVo.getRootKey())) {
            for(String rootKey : confVo.getRootKey()) {
                messageConfig.putAll(MessageUtil.getMessages(rootKey));
            }
        } else {
            messageConfig.putAll(MessageUtil.getMessages());
        }

        String json = MSG_NAME + JsonUtil.toJson(messageConfig);

        return responseText(model, json);
    }

    /**
     * ROOT Key 를 받아 하위 키와 값을 <code>Map</code>에 설정
     * 
     * @param rootConfig
     * @param rootKey
     */
    private void setGlobalConfig(Map<String, Object> rootConfig, String rootKey) {

        if(Validate.isNotEmpty(rootKey)) {
            String key;
            String jsKey;
            int rootKeyLen = rootKey.length() + 1;

            Iterator<String> keys = Config.getKeys(rootKey);
            while(keys.hasNext()) {
                key = keys.next();
                jsKey = key.substring(rootKeyLen);
                setConfig(rootConfig, jsKey, Config.getString(key, ""));
            }
        }
    }

    /**
     * keyStr을 .(콤마)로 분할하여 Map으로 계층형 구조로 설정한다.
     * 
     * @param rootConfig
     * @param keyStr
     * @param value
     */
    @SuppressWarnings("unchecked")
    private void setConfig(Map<String, Object> rootConfig, String keyStr, Object value) {

        if(Validate.isNotEmpty(keyStr)) {
            /*
             * .(점) 구분자가 없는 경우는 바로 keyStr을 key로 사용하고 value를 put 시키고,
             * 구분자가 있는 경우 split으로 나눠 각각의 단어를 key로 사용하여,
             * 기 존재시 값을 추가하고 없는 경우 객체를 생성 후 값을 추가한다.
             */
            if(keyStr.indexOf(".") < 0) {
                rootConfig.put(keyStr, value);
            } else {
                // key 는 . 을 구분자로 사용된다.
                String[] keys = keyStr.split("\\.");
                int keyLen = keys.length;

                // 신규 생성시에 사용될 Map 객체
                Map<String, Object> nowConfig = rootConfig;
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
}
