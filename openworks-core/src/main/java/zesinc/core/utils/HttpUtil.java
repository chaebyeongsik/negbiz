/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import zesinc.core.http.METHOD;
import zesinc.core.http.OpenWorksTrustManager;
import zesinc.core.http.PROTOCOL;
import zesinc.core.lang.Validate;

/**
 * <pre>
 * Http 접속을 지원하기 위한 UTIL 클레스  아래 항목들을 인자로 받아서 처리해준다.
 * 접속 서버에서 오류가 발생하는 경우 오류문자열이 그래도 전달되므로 적절한
 * 예외처리가 필요하다.
 * 
 * method : get/post default get
 * protocol : http/https default http
 * certType : 인증유형 default STRICT_HOSTNAME_VERIFIER
 *               Self-Signed Certificate 인 경우 ALLOW_ALL_HOSTNAME_VERIFIER
 * contentType : default  application/x-www-form-urlencoded;charset=utf-8)
 * host : domain/ip
 * port : default 80
 * parameter : java Map(key/value) api 사용
 * timeout : 연결제한시간 default 3000 (3초)
 * </pre>
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 3. 23.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@SuppressWarnings("deprecation")
public class HttpUtil {

    /** SSL 인스턴스명 */
    private static final String TLS = "TLS";
    /** 연결제한시간 */
    private static final int TIME_OUT = 3000;
    /** 접속서버포트 */
    private static final int PORT = 80;
    /** 기본컨텐츠 타입 */
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded;charset=utf-8";
    /** 인증유형 */
    private static final String CERT_TYPE = "STRICT_HOSTNAME_VERIFIER";

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @return
     */
    public static String get(String host, String uri) {

        return get(host, PORT, uri, null);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @return
     */
    public static String get(String host, String uri, Map<String, String> param) {

        return get(host, PORT, uri, param);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param method METHOD.GET 또는 METHOD.POST
     * @return
     */
    public static String get(String host, String uri, Map<String, String> param, METHOD method) {

        return get(host, PORT, uri, param, TIME_OUT, method);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @return
     */
    public static String get(String host, int port, String uri, Map<String, String> param) {

        return get(host, port, uri, param, TIME_OUT);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @return
     */
    public static String get(String host, int port, String uri, Map<String, String> param, int timeout) {

        return get(host, port, uri, param, timeout, METHOD.GET);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @param method METHOD.GET 또는 METHOD.POST
     * @return
     */
    public static String get(String host, int port, String uri, Map<String, String> param, int timeout,
        METHOD method) {

        return get(host, port, uri, param, timeout, method, CONTENT_TYPE);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @param method METHOD.http 또는 METHOD.https
     * @param contentType 전송데이터의 컨텐츠유형
     * @return
     */
    public static String get(String host, int port, String uri, Map<String, String> param, int timeout,
        METHOD method, String contentType) {

        return get(PROTOCOL.HTTP, host, port, uri, param, timeout, method, contentType, CERT_TYPE);
    }

    /*
     * protocol을 지정하여 접속
     */

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @return
     */
    public static String get(PROTOCOL protocol, String host, String uri) {

        return get(host, PORT, uri, null);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @return
     */
    public static String get(PROTOCOL protocol, String host, String uri, Map<String, String> param) {

        return get(protocol, host, PORT, uri, param);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @return
     */
    public static String get(PROTOCOL protocol, String host, int port, String uri, Map<String, String> param) {

        return get(protocol, host, port, uri, param, TIME_OUT);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @return
     */
    public static String get(PROTOCOL protocol, String host, int port, String uri, Map<String, String> param, int timeout) {

        return get(protocol, host, port, uri, param, timeout, METHOD.GET);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @param method METHOD.http 또는 METHOD.https
     * @return
     */
    public static String get(PROTOCOL protocol, String host, int port, String uri, Map<String, String> param, int timeout,
        METHOD method) {

        return get(protocol, host, port, uri, param, timeout, method, CONTENT_TYPE);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @param method METHOD.http 또는 METHOD.https
     * @param contentType 전송데이터의 컨텐츠유형
     * @return
     */
    public static String get(PROTOCOL protocol, String host, int port, String uri, Map<String, String> param, int timeout,
        METHOD method, String contentType) {

        return get(protocol, host, port, uri, param, timeout, method, contentType, CERT_TYPE);
    }

    /**
     * 주어진 인자를 통하여 서버에 연결한 후 결과를 반환한다.
     * 클레스 설명을 참조하여 기본값을 확인한다.
     * 
     * @param protocol PROTOCOL.HTTP 또는 PROTOCOL.HTTPS
     * @param host 접속대상서버(도메인 또는 ip 주로를 사용)
     * @param port 접속대상서버의 포트번호
     * @param uri 접속주소의 URI, parameter는 param 인자를 사용한다.
     * @param param Map 객체로 key/value 쌍으로 작성
     * @param timeout 연결시도제한시간
     * @param method METHOD.http 또는 METHOD.https
     * @param contentType 전송데이터의 컨텐츠유형
     * @param certType 인증유형
     * @return
     */
    @SuppressWarnings("resource")
    public static String get(PROTOCOL protocol, String host, int port, String uri, Map<String, String> param, int timeout,
        METHOD method, String contentType, String certType) {

        StringBuilder sb = new StringBuilder();
        StringBuilder errorMsg = new StringBuilder();
        Integer statusCode = Integer.valueOf(0);

        HttpHost httpHost = new HttpHost(host, new Integer(port));
        HttpClient httpclient = new DefaultHttpClient();

        try {

            if(PROTOCOL.HTTPS.equals(protocol)) {
                httpHost = new HttpHost(host, new Integer(port), "https");

                TrustManager trustManager = new OpenWorksTrustManager();

                // SSLContext 지정된 시큐어 소켓 프로토콜 구현
                SSLContext sslcontext = SSLContext.getInstance(TLS);
                sslcontext.init(null, new TrustManager[] { trustManager }, null);

                /* 인증 방식에 따라서 분기 */
                SSLSocketFactory socketFactory = null;
                if(certType.equals("STRICT_HOSTNAME_VERIFIER")) {
                    socketFactory = new SSLSocketFactory(sslcontext, SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
                } else {
                    socketFactory = new SSLSocketFactory(sslcontext, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                }

                Scheme sch = new Scheme(protocol.getProtocol(), port, socketFactory);
                httpclient.getConnectionManager().getSchemeRegistry().register(sch);
            }

            HttpParams params = httpclient.getParams();
            HttpConnectionParams.setConnectionTimeout(params, timeout);
            HttpConnectionParams.setSoTimeout(params, timeout);
            HttpResponse response = null;

            if(METHOD.POST.equals(method)) {
                List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();

                Iterator<String> iter = param.keySet().iterator();
                while(iter.hasNext()) {
                    String key = iter.next();
                    String val = (String) param.get(key);
                    if(val == null) {
                        val = "";
                    }
                    nameValuePairs.add(new BasicNameValuePair(key, val));
                }

                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8);

                HttpPost post = new HttpPost(uri);
                post.setEntity(formEntity);
                post.addHeader("Content-Type", contentType);

                response = httpclient.execute(httpHost, post);

                EntityUtils.consume(formEntity);
            } else {

                Iterator<String> iter = param.keySet().iterator();
                while(iter.hasNext()) {
                    String key = iter.next();
                    String val = (String) param.get(key);
                    if(val == null) {
                        val = "";
                    }
                    params.setParameter(key, val);
                }

                HttpGet get = new HttpGet(uri);
                get.setParams(params);

                response = httpclient.execute(httpHost, get);
            }

            statusCode = Integer.valueOf(response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();

            if(statusCode.intValue() == HttpStatus.SC_OK) {
                sb.append(EntityUtils.toString(entity, HTTP.UTF_8));
            } else {
                errorMsg.append("ERROR : ").append(host).append(":").append(port).append(uri).append(" StatusCode : ");
                errorMsg.append(statusCode).append(" Message : ").append(response.getStatusLine().getReasonPhrase()).append("<br/>");
            }

            EntityUtils.consume(entity);
        } catch (Exception e) {
            errorMsg.append("ERROR : ").append(host).append(":").append(port).append(uri).append(" Message : ").append(e.getMessage())
                .append("<br/>");
        } finally {
            httpclient.getConnectionManager().shutdown();
        }

        if(Validate.isNotEmpty(errorMsg)) {
            return errorMsg.toString();
        }

        return sb.toString();
    }
}
