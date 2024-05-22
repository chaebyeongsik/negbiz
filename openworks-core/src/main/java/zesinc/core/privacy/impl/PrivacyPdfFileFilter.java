/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.core.privacy.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import zesinc.core.privacy.AbstractPrivacyFilter;
import zesinc.core.privacy.PrivacyFilter;
import zesinc.core.privacy.PrivacyResultVO;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.parser.PdfTextExtractor;

/**
 * PDF 파일(pdf 확장자)에 대한 개인정보 포함여부를 확인한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일               수정자                  수정내용
 * --------------  --------  -------------------------------
 *  
 * 
 *  2013. 06. 04.       (주)제스아이앤씨         개인정보 필터링
 * </pre>
 */
public class PrivacyPdfFileFilter extends AbstractPrivacyFilter implements PrivacyFilter {

    public PrivacyPdfFileFilter(File file) {
        this.file = file;
    }

    /*
     * PDF 파일내에서 개인정보를 포함한 값이 있는지 여부를 확인
     * @see zesinc.privacy.PrivacyFilter#doFilter()
     */
    @Override
    public PrivacyResultVO doFilter() {

        PrivacyResultVO result;

        PdfReader reader = null;
        FileInputStream fileInput = null;

        try {
            StringBuilder sb = new StringBuilder();

            fileInput = new FileInputStream(this.file);
            reader = new PdfReader(fileInput);

            PdfTextExtractor pte = new PdfTextExtractor(reader);

            int pageNum = reader.getNumberOfPages();
            for(int i = 1 ; i <= pageNum ; i++) {
                sb.append(pte.getTextFromPage(i));
            }

            result = doPrivacyCheck(sb.toString());
        } catch (Exception e) {
            result = new PrivacyResultVO();
            logger.error(this.file.getAbsolutePath() + File.separator + this.file.getName() + " PDF(.pdf) File Read Failed", e);
        } finally {
            if(reader != null) {
                reader.close();
            }
            if(fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException e1) {}
            }
        }
        return result;
    }
}
