package zesinc.core.config;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration2.Configuration;

/**
 * Apache Jakarta commons-configration을 사용한 환경설정 객체
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일        수정자       수정내용
 * --------------   --------     -------------------------------
 *  2014. 9. 24.    방기배     최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public final class Config {

    /** 환경설정 객체 */
    private static Configuration conf = ConfigLoader.getConfig();

    /**
     * 기본 설정파일 경로를 통하여 환경설정을 로드한다
     */
    public static void reloadConfiguration() {
        conf = ConfigLoader.getConfig();
    }

    /**
     * 환경설정 경로로 환경설정을 로드한다.
     * 
     * @param configPattern 환경설정 파일 경로 패턴<br />
     *        예 : <br />
     *        classpath:config/commons/*-commons-config.xml <br />
     *        config/commons/core-commons-config.xml
     */
    public static void reloadConfiguration(String configPattern) {
        conf = ConfigLoader.getConfig(configPattern);
    }

    public static String getString(String key) {
        return conf.getString(key);
    }

    public static String getString(String key, String defaultValue) {
        return conf.getString(key, defaultValue);
    }

    public static String[] getStringArray(String key) {
        return conf.getStringArray(key);
    }

    public static List<Object> getList(String key) {
        return conf.getList(key);
    }

    public static <T> List<T> getList(Class<T> valueClass, String key) {
        return conf.getList(valueClass, key);
    }

    public static List<Object> getList(String key, List<Object> defaultValue) {
        return conf.getList(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return conf.getBoolean(key);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return conf.getBoolean(key, defaultValue);
    }

    public static double getDouble(String key) {
        return conf.getDouble(key);
    }

    public static double getDouble(String key, double defaultValue) {
        return conf.getDouble(key, defaultValue);
    }

    public static long getLong(String key) {
        return conf.getLong(key);
    }

    public static long getLong(String key, long defaultValue) {
        return conf.getLong(key, defaultValue);
    }

    public static float getFloat(String key) {
        return conf.getFloat(key);
    }

    public static float getFloat(String key, float defaultValue) {
        return conf.getFloat(key, defaultValue);
    }

    public static int getInt(String key) {
        return conf.getInt(key);
    }

    public static int getInt(String key, int defaultValue) {
        return conf.getInt(key, defaultValue);
    }

    public static short getShort(String key) {
        return conf.getShort(key);
    }

    public static short getShort(String key, short defaultValue) {
        return conf.getShort(key, defaultValue);
    }

    public static Iterator<String> getKeys() {
        return conf.getKeys();
    }

    public static Iterator<String> getKeys(String key) {
        return conf.getKeys(key);
    }

    public static Properties getProperties(String key) {
        return conf.getProperties(key);
    }

}
