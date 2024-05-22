/*
 * Copyright (c) 2012 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.core.compare;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 컨텐츠 변경 사항 비교를 위한 데이터 타입을 생성한다.
 * <p />
 * 반환되는 데이터 타입은 List<String> 하나로 통일한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 22.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class CompareSupport {

    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(CompareSupport.class);

    /** 시스템에 상관없이 줄바꿈에 대한 키는 \n 하나로 잡아서 처리 */
    private static String CR = "\n";

    /**
     * 문자열 관련 데이터를 비교데이터 타입으로 변경
     * <p />
     * 일반 적으로 DB 데이터와의 비교를 위함
     * 
     * @param contents
     * @return
     */
    public static List<String> getContents(CharSequence contents) {

        List<String> contentList = new LinkedList<String>();

        String source = contents.toString();
        contentList = Arrays.asList(source.split(CR));

        return contentList;
    }

    /**
     * 파일을 읽어 라인 단위로 리스트에 담아 비교데이터 타입으로 변경
     * 
     * @param contents
     * @return
     */
    public static List<String> getContents(File contents) {
        List<String> contentList = new LinkedList<String>();

        if(contents == null) {
            return contentList;
        }

        if(contents.exists() && contents.isFile()) {
            String line = "";
            BufferedReader in = null;
            try {
                in = new BufferedReader(new FileReader(contents));
                while((line = in.readLine()) != null) {
                    contentList.add(line);
                }
            } catch (IOException e) {
                if(logger.isDebugEnabled()) {
                    logger.debug("File read failed", e);
                }
            } finally {
                if(in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {}
                }
            }

        }

        return contentList;
    }

    /**
     * 문자열 배열을 비교데이터 타입으로 변경
     * 
     * @param contents
     * @return
     */
    public static List<String> getContents(String[] contents) {

        List<String> contentList = new LinkedList<String>();
        contentList = Arrays.asList(contents);

        return contentList;
    }

    /**
     * html table 태그로 레핑된 결과
     * 
     * @return
     */
    public static List<String> getTableWrap(List<CompareViewVO> rowList) {

        StringBuilder sb = new StringBuilder();
        List<String> wrapList = new LinkedList<String>();

        wrapList.add("<table class=\"diffContent\">");
        for(CompareViewVO row : rowList) {
            sb = new StringBuilder();
            sb.append("<tr>")
                .append("<td class=\"diffType\">")
                .append(row.getTagName())
                .append("</td>")
                .append("<td class=\"diffOld\">")
                .append(row.getLeftLine())
                .append("</td>")
                .append("<td class=\"diffNew\">")
                .append(row.getRightLine())
                .append("</td>")
                .append("</tr>");
            wrapList.add(sb.toString());
        }
        wrapList.add("</table>");

        return wrapList;
    }

    /**
     * html ul 태그로 레핑된 결과
     * 
     * @return
     */
    public static List<String> getUlWrap(List<CompareViewVO> rowList) {

        StringBuilder sb = new StringBuilder();
        List<String> wrapList = new LinkedList<String>();

        wrapList.add("<ul class=\"diffContent\">");
        for(CompareViewVO row : rowList) {
            sb = new StringBuilder();
            sb.append("<li class=\"diffType\">")
                .append(row.getTagName())
                .append("</li>")
                .append("<li class=\"diffOld\">")
                .append(row.getLeftLine())
                .append("</li>")
                .append("<li class=\"diffNew\">")
                .append(row.getRightLine())
                .append("</li>");
            wrapList.add(sb.toString());
        }
        wrapList.add("</ul>");

        return wrapList;
    }

    /**
     * html ol 태그로 레핑된 결과
     * 
     * @return
     */
    public static List<String> getOlWrap(List<CompareViewVO> rowList) {

        StringBuilder sb = new StringBuilder();
        List<String> wrapList = new LinkedList<String>();

        wrapList.add("<ol class=\"diffContent\">");
        for(CompareViewVO row : rowList) {
            sb = new StringBuilder();
            sb.append("<li class=\"diffType\">")
                .append(row.getTagName())
                .append("</li>")
                .append("<li class=\"diffOld\">")
                .append(row.getLeftLine())
                .append("</li>")
                .append("<li class=\"diffNew\">")
                .append(row.getRightLine())
                .append("</li>");
            wrapList.add(sb.toString());
        }
        wrapList.add("</ol>");

        return wrapList;
    }

    /**
     * html span 태그로 레핑된 결과
     * 
     * @return
     */
    public static List<String> getSpanWrap(List<CompareViewVO> rowList) {

        StringBuilder sb = new StringBuilder();
        List<String> wrapList = new LinkedList<String>();

        wrapList.add("<span class=\"diffContent\">");
        for(CompareViewVO row : rowList) {
            sb = new StringBuilder();
            sb.append("<span class=\"diffType\">")
                .append(row.getTagName())
                .append("</span>")
                .append("<span class=\"diffOld\">")
                .append(row.getLeftLine())
                .append("</span>")
                .append("<span class=\"diffNew\">")
                .append(row.getRightLine())
                .append("</span>");
            wrapList.add(sb.toString());
        }
        wrapList.add("</span>");

        return wrapList;
    }

    /**
     * html div 태그로 레핑된 결과
     * 
     * @return
     */
    public static List<String> getDivWrap(List<CompareViewVO> rowList) {

        StringBuilder sb = new StringBuilder();
        List<String> wrapList = new LinkedList<String>();

        wrapList.add("<div class=\"diffContent\">");
        for(CompareViewVO row : rowList) {
            sb = new StringBuilder();
            sb.append("<div class=\"diffType\">")
                .append(row.getTagName())
                .append("</div>")
                .append("<div class=\"diffOld\">")
                .append(row.getLeftLine())
                .append("</div>")
                .append("<div class=\"diffNew\">")
                .append(row.getRightLine())
                .append("</div>");
            wrapList.add(sb.toString());
        }
        wrapList.add("</div>");

        return wrapList;
    }

}
