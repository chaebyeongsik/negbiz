/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import zesinc.core.privacy.impl.PrivacyDocFileFilter;
import zesinc.core.privacy.impl.PrivacyDocxFileFilter;
import zesinc.core.privacy.impl.PrivacyPdfFileFilter;
import zesinc.core.privacy.impl.PrivacyPptFileFilter;
import zesinc.core.privacy.impl.PrivacyPptxFileFilter;
import zesinc.core.privacy.impl.PrivacyStringFilter;
import zesinc.core.privacy.impl.PrivacyTxtFileFilter;
import zesinc.core.privacy.impl.PrivacyXlsFileFilter;
import zesinc.core.privacy.impl.PrivacyXlsxFileFilter;

/**
 * 대상 필터 객체를 반환한다.
 * 현재 xls(x), doc(x), ppt(x), pdf, txt 파일만 인지하고 있으며,
 * 이외 파일이 필요한 경우 개별로 구현하길 바람.
 * 파일이라 하여도 위 확장자가 아닌경우 단순 문자열로 저리됨
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일               수정자                  수정내용
 * --------------  --------  -------------------------------
 *  2013. 06. 04.       (주)제스아이앤씨         개인정보 필터링
 * </pre>
 * 
 * @see PrivacyDocFileFilter
 * @see PrivacyXlsFileFilter
 * @see PrivacyStringFilter
 */
public class PrivacyFilterFactory {

    /**
     * 넘겨진 인자를 통하여 적합한 필터 객체를 반환한다.
     * content는 파일 전체 경로 또는 검증할 문자열을 인자로 해야 한다.
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static PrivacyFilter getInstance(String content) throws Exception {

        // 150자 이상인경우 문자열 컨텐츠로 판단
        if(null == content || content.length() > 150) {
            return new PrivacyStringFilter(content);
        }
        if(isFile(content)) {
            File file = new File(content);
            return getInstance(file);
        }
        return new PrivacyStringFilter(content);
    }

    /**
     * 넘겨진 인자를 통하여 적합한 필터 객체를 반환한다.
     * content는 파일 전체 경로 또는 검증할 문자열을 인자로 해야 한다.
     * 
     * @param content
     * @return
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static PrivacyFilter getInstance(File file) throws Exception {

        PrivacyFilter filter = null;

        if(file.isFile()) {
            String fileName = file.getName();
            int position = fileName.lastIndexOf(".");
            String ext = fileName.substring(position + 1);

            if("xls".equals(ext)) {
                filter = new PrivacyXlsFileFilter(file);
            } else if("xlsx".equals(ext)) {
                filter = new PrivacyXlsxFileFilter(file);
            } else if("doc".equals(ext)) {
                filter = new PrivacyDocFileFilter(file);
            } else if("docx".equals(ext)) {
                filter = new PrivacyDocxFileFilter(file);
            } else if("ppt".equals(ext)) {
                filter = new PrivacyPptFileFilter(file);
            } else if("pptx".equals(ext)) {
                filter = new PrivacyPptxFileFilter(file);
            } else if("txt".equals(ext)) {
                filter = new PrivacyTxtFileFilter(file);
            } else if("pdf".equals(ext)) {
                filter = new PrivacyPdfFileFilter(file);
            } else {
                throw new IllegalArgumentException("지원되지 않는 형식의 파일입니다.");
            }
        } else {
            throw new FileNotFoundException(file.getCanonicalFile() + " 파일을 찾을 수 없습니다.");
        }

        return filter;
    }

    /**
     * 전달된 컨텐츠가 파일인지 문자열인지 구분
     * 
     * @param content 파일경로 또는 문자열 컨텐츠
     * @return
     */
    private static boolean isFile(String content) {

        File file = new File(content);
        if(file.isFile()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

}
