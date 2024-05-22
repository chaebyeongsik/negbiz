/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.message.support;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import zesinc.core.lang.Validate;
import zesinc.web.utils.MessageUtil;

/**
 * <pre>
 *  Spring MessageSource 호환기능으로 properties 파일로 사용되고 있는 메시지 정의를
 *  Openworks의 메시지 관리 기능과 통합하여 사용할 수 있도록 지원하는 기능이다.
 * 
 *  특히 Spring Framework 내부에서 사용되는 메시지 관리 기능까지 통합되어 사용되며
 *  Spring Security에서도 사용할 수 있도록 지원한다.
 * 
 * 또한 Spring Security(spring-security-core-버전.jar) 파일에 내장된 properties에 등록된
 * 각 메시지코드를 다국어관리 기능에 입력하여야 한다.
 * 
 * 또한 Openworks 프레임워크의 언어 설정이 Locale 표준가 상이하기 때문에 Locale 은
 * 무시하고 Openworks 프레임워크의 언어설정으로 대체되도록 한다.
 * 
 * << 개정이력(Modification Information) >>
 * 
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 4. 14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class OpenworksMessageSource implements MessageSource {

    @Override
    public String getMessage(String cdId, Object[] args, String defaultMessage, Locale locale) {

        if(Validate.isEmpty(args)) {
            args = new String[] { defaultMessage };
        }

        return MessageUtil.getMessage(locale, cdId, args, null);
    }

    @Override
    public String getMessage(String cdId, Object[] args, Locale locale) throws NoSuchMessageException {

        return MessageUtil.getMessage(locale, cdId, args, null);
    }

    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {

        String message = resolvable.getDefaultMessage();
        String[] codes = resolvable.getCodes();
        Object[] args = resolvable.getArguments();

        if(Validate.isNotEmpty(codes)) {
            for(String cdId : codes) {
                message = MessageUtil.getMessage(locale, cdId, args, message);
                if(Validate.isNotEmpty(message)) {
                    break;
                }
            }
        }

        return Validate.isNotEmpty(message) ? message : "";
    }

}
