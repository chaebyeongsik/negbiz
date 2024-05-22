/*
 * Copyright (c) 2014 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.config;

import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.io.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Apache Jakarta commons-configration을 사용한 환경설정 로드 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2014. 9. 24.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class ConfigLoader {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    /** 환경 설정 파일 패턴 */
    private static String configPattern = "classpath:config/commons/*-commons-config.xml";

    /**
     * 환경설정을 로드한다.
     * 환경설정은 xml 파일 사용을 기본으로 하며, 패키지의 폴더에
     * 위치하는 것을 기본으로 한다.
     */
    public static Configuration getConfig() {

        return getConfig(configPattern);
    }

    /**
     * 환경설정을 로드한다.
     * 환경설정은 xml 파일 사용을 기본으로 하며, 패키지의 폴더에
     * 위치하는 것을 기본으로 한다.
     * 
     * @throws Exception
     */
    public static Configuration getConfig(String _configPattern) {

        CompositeConfiguration config = new CompositeConfiguration();

        config.addConfiguration(new SystemConfiguration());

        PathMatchingResourcePatternResolver prpr = new PathMatchingResourcePatternResolver();

        try {
            Resource[] resources = prpr.getResources(_configPattern);

            for(Resource resource : resources) {

                XMLConfiguration xmlConf = new XMLConfiguration();
                FileHandler handler = new FileHandler(xmlConf);

                //------------ 스트링부트가 아닐 경우---------------------- 일단은 커밋하지 않고 내 컴퓨터에서만 있는걸로...
                handler.load(resource.getFile());

                logger.debug("Config file location : " + resource.getFile().getAbsolutePath());
                //------------ 스트링부트가 아닐 경우----------------------

                //------------ 스트링부트일 경우---------------------- 2018-03-21 황명욱 차장님 요청으로 수정한 부분
                /**
                handler.load(resource.getInputStream()); .. >>> 부트는 이 부분 주석 풀고

                //logger.debug("Config file location : " + resource.getFile().getAbsolutePath());  // 스트링부트는 이 부분을 주석처리해야 함
                */
                //------------ 스트링부트일 경우----------------------

                config.addConfiguration(xmlConf);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return config;
    }

}
