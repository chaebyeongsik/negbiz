/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.utils;

import zesinc.core.lang.Validate;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.BrowserType;
import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.Manufacturer;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.RenderingEngine;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * 브라우저 Agent 문자열로 사용자 시스템 환경정보를 제공한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2016. 2. 18.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class AgentUtil {

    private UserAgent userAgent;
    private static String UN_KNOW = "UN_KNOW";

    public AgentUtil(String agentString) {
        userAgent = UserAgent.parseUserAgentString(agentString);
    }

    /**
     * 브라우저 명칭을 반환
     * 
     * @return
     */
    public String getBrowserName() {

        userAgent.getBrowser().getBrowserType().getName();

        Browser browser = userAgent.getBrowser();
        String browserName = browser.getName();
        if(Validate.isEmpty(browserName)) {
            browserName = UN_KNOW;
        }

        return browserName;
    }

    /**
     * 브라우저 유형 명칭을 반환
     * 
     * @return
     */
    public String getBrowserTypeName() {
        String browserTypeName = UN_KNOW;

        Browser browser = userAgent.getBrowser();
        BrowserType browserType = browser.getBrowserType();
        if(Validate.isNotEmpty(browserType)) {
            browserTypeName = browserType.getName();
        }

        return browserTypeName;
    }

    /**
     * 브라우저 버전을 반환
     * 
     * @return
     */
    public String getBrowserVersion() {
        String browserVersion = UN_KNOW;
        Version version = userAgent.getBrowserVersion();
        if(Validate.isNotEmpty(version)) {
            browserVersion = version.getVersion();
        }

        return browserVersion;
    }

    /**
     * OS 명을 반환
     * 
     * @return
     */
    public String getOsName() {
        OperatingSystem operatingsystem = userAgent.getOperatingSystem();
        String osName = operatingsystem.getName();
        if(Validate.isEmpty(osName)) {
            osName = UN_KNOW;
        }

        return osName;
    }

    /**
     * 장치명을 반환
     * 
     * @return
     */
    public String getDeviceType() {
        OperatingSystem operatingsystem = userAgent.getOperatingSystem();
        DeviceType deviceType = operatingsystem.getDeviceType();
        String deviceName = UN_KNOW;
        if(deviceType != null) {
            deviceName = deviceType.getName();
        }

        return deviceName;
    }

    /**
     * 휴대용 장치여부
     * 
     * @return
     */
    public Boolean isMobile() {
        Boolean isMobile = Boolean.FALSE;
        OperatingSystem operatingsystem = userAgent.getOperatingSystem();
        DeviceType deviceType = operatingsystem.getDeviceType();
        if(deviceType != null && (deviceType.equals(DeviceType.MOBILE) || deviceType.equals(DeviceType.TABLET))) {
            isMobile = Boolean.TRUE;
        }

        return isMobile;
    }

    /**
     * OS 제조사 정보
     * 
     * @return
     */
    public String getManufacturer() {
        String manufacturer = UN_KNOW;
        OperatingSystem os = userAgent.getOperatingSystem();
        Manufacturer mf = os.getManufacturer();
        if(Validate.isNotEmpty(mf)) {
            manufacturer = mf.getName();
        }

        return manufacturer;
    }

    /**
     * 렌더링 엔진 명칭
     * 
     * @return
     */
    public String getRenderingEngine() {
        String renderingEngine = UN_KNOW;
        Browser browser = userAgent.getBrowser();
        RenderingEngine re = browser.getRenderingEngine();
        if(Validate.isNotEmpty(re)) {
            renderingEngine = re.name();
        }
        return renderingEngine;
    }
}
