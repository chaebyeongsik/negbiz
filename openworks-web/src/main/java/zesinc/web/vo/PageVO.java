/*
 * Copyright (c) 2015 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.vo;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;

import zesinc.core.lang.Validate;

/**
 * 페이징 VO 객체로 페이징 처리가 필요한 프로그램은 본 VO를 상속받아 사용한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 13.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class PageVO implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 8346094199260151851L;

    private final static String GET_PREFIX = "get";
    private final static String HAS_PREFIX = "has";
    private final static String IS_PREFIX = "is";

    /** IP주소 */
    private String ipAddr;

    /** 검색 확장 값을 저장하기 위한 멥 */
    private Map<String, Object> paramMap = new ParamMap<String, Object>();

    /**
     * 파라미터 맵을 좀더 편히 쓰기 위한 delegate 함수로 파라미터를 추가한다.
     * 
     * @param key
     * @param value
     */
    public void addParam(String key, Object value) {
        paramMap.put(key, value);
    }

    /**
     * 파라미터 맵을 좀더 편히 쓰기 위한 delegate 함수로 키에 해당하는 값을 반환한다.
     * String 타입은 제외하고 이외의 타입 값을 사용한다.( getString(key) 참조 )
     * 
     * @param key
     * @return
     */
    public Object getParam(String key) {

        return paramMap.get(key);
    }

    /**
     * 파라미터 맵을 좀더 편히 쓰기 위한 delegate 함수로 키에 해당하는 문자열 값을 반환한다.
     * 
     * @param key
     * @return
     */
    public String getString(String key) {

        Object value = paramMap.get(key);

        if(value == null) {
            return "";
        }
        if(String.class.isAssignableFrom(value.getClass())) {
            return (String) value;
        }

        return value.toString();
    }

    /**
     * <pre>
     * String[] 문자배열을 반환한다. 
     * 만약 값이 null 이라면 빈문자배열(new String[] {})을 반환하고,
     * String 문자열이라면 문자배열(new String[] {})을 생성하여 반환하고,
     * 
     * 이외의 경우는 String[] 타입으로 케스팅하여 반환한다.
     * 따라서 String 유형이 아닌 경우 {@link ClassCastException}}이 발생된다.
     * </pre>
     * 
     * @param key
     * @return
     */
    public String[] getStrings(String key) {

        Object value = paramMap.get(key);

        // 값이 없는 경우 null 또는 "" 빈 String[] 배열 생성 후 반환
        if(Validate.isEmpty(value)) {
            return new String[] {};
        }

        // String인 경우 빈 String[] 배열 생성 후 반환
        if(String.class.isAssignableFrom(value.getClass())) {
            return new String[] { value.toString() };
        }

        return (String[]) value;
    }

    /**
     * 파라미터 맵을 좀더 편히 쓰기 위한 delegate 함수로 키에 해당하는 값을 Integer 타입으로 반환한다.
     * 단! Integer 타입으로 변환이 불가능한 모든 경우(null 포함)에는 null을 반환한다.
     * 
     * @param key
     * @return
     */
    public Integer getInteger(String key) {

        Object value = paramMap.get(key);
        if(value == null) {
            return null;
        }
        if(Integer.class.isAssignableFrom(value.getClass())) {
            return (Integer) value;
        }

        String strValue = (String) value;
        if(Validate.isDigits(strValue)) {
            return new Integer(strValue);
        }

        return null;
    }

    /**
     * <pre>
     * 값을 Integer[] 숫자배열로 반환한다.
     * 전환시키는 범위는 아래 5개의 경우에 해당되며 이외에는 null이 반환된다.
     * 
     * 1. null 인경우 빈 Integer[] 배열이 반환된다.
     * 2. String[] 문자 배열인 경우 모든 문자열을 Integer로 변환하여 배열로 반환한다.
     *    케스팅을 할 수 없는 경우 {@link ClassCastException}}이 발생된다.
     * 3. String 문자열인 경우 new Integer(문자열) 을 통하여 값을 반환한다.
     *    케스팅을 할 수 없는 경우 {@link ClassCastException}}이 발생된다.
     * 4. 이미 Integer[] 배열 인경우 케스팅 후 반환한다.
     * 5.  Integer 인경우 Integer[] 배열로 생성후 후 반환한다.
     * </pre>
     * 
     * @param key
     * @return
     */
    public Integer[] getIntegers(String key) {

        Object value = paramMap.get(key);
        if(value == null) {
            return new Integer[] {};
        }

        // String[] 문자 배열인 경우
        if(String[].class.isAssignableFrom(value.getClass())) {
            String[] strings = (String[]) value;
            int len = strings.length;

            Integer[] integers = new Integer[len];

            for(int i = 0 ; i < len ; i++) {
                integers[i] = new Integer(strings[i]);
            }

            return integers;
        }

        // String 문자열 처리
        if(String.class.isAssignableFrom(value.getClass())) {
            String strValue = (String) value;
            return new Integer[] { new Integer(strValue) };
        }

        // 이미 Integer[] 배열 인경우 케스팅 후 반환
        if(Integer[].class.isAssignableFrom(value.getClass())) {
            return (Integer[]) value;
        }

        // Integer 인경우 Integer[] 배열로 생성후 후 반환
        if(Integer.class.isAssignableFrom(value.getClass())) {
            return new Integer[] { (Integer) value };
        }

        return null;
    }

    /**
     * String ipAddr을 반환
     * 
     * @return String ipAddr
     */
    public String getIpAddr() {
        return ipAddr;
    }

    /**
     * ipAddr을 설정
     * 
     * @param ipAddr 을(를) String ipAddr로 설정
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    /**
     * 검색 확장 파라미터 값 반환
     * 
     * @return
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    /**
     * 검색 확장 파라미터 값 저장
     * 
     * @param dataMap
     */
    public void setParamMap(Map<String, Object> dataMap) {
        this.paramMap = dataMap;
    }

    /**
     * 맴버 변수 값 전부를 키와 값으로 구분하여 문자열로 반환
     */
    @Override
    public String toString() {

        StringBuilder result = new StringBuilder("VO : ");
        result.append(this.getClass().getName()).append('\n');

        Method[] methods = this.getClass().getMethods();
        for(int i = 0 ; i < methods.length ; i++) {
            Method method = methods[i];
            Class<?>[] params = method.getParameterTypes();
            if(params.length != 0) {
                continue;
            }
            String mname = method.getName();
            if(mname.equals("getClass")) {
                continue;
            }
            if(mname.startsWith(GET_PREFIX) || mname.startsWith(IS_PREFIX)
                || mname.startsWith(HAS_PREFIX)) {

                String paramName = method.getName().substring(3);
                Object mresult = null;
                try {
                    mresult = method.invoke(this);
                } catch (Exception ex) {}

                result.append("  ").append(paramName);
                if(mresult == null) {
                    result.append(":null");
                } else {
                    String val = mresult.toString();
                    if(val.length() > 100) {
                        val = val.substring(0, 100) + "....";
                    }
                    result.append(":[").append(val).append(']');
                }
                result.append('\n');
            }
        }
        return result.toString();
    }

}
