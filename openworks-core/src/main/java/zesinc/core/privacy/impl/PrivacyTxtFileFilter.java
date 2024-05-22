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

/**
 * 텍스트(.txt) 파일 내에 개인정보가 포함되어 있는지를 확인
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
public class PrivacyTxtFileFilter extends AbstractPrivacyFilter implements PrivacyFilter {

    public PrivacyTxtFileFilter(File file) {
        this.file = file;
    }

    /*
     * 텍스트 파일내에서 개인정보를 포함한 값이 있는지 여부를 확인
     * @see zesinc.privacy.PrivacyFilter#doFilter()
     */
    @Override
    public PrivacyResultVO doFilter() {

        PrivacyResultVO result;

        FileInputStream fileInput = null;

        try {
            fileInput = new FileInputStream(this.file);

            int size = fileInput.available();
            byte input[] = new byte[size];

            String str = null;
            while(fileInput.read(input) > -1) {
                str = new String(input);
            }

            result = doPrivacyCheck(str);
        } catch (Exception e) {
            result = new PrivacyResultVO();
            logger.error(this.file.getAbsolutePath() + File.separator + this.file.getName() + " Text(.txt) File Read Failed", e);
        } finally {
            if(fileInput != null) {
                try {
                    fileInput.close();
                } catch (IOException e1) {}
            }
        }
        return result;
    }

}
