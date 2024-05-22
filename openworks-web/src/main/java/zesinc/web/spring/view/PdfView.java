/*
 * Copyright (c) 2014 ZES Inc. All rights reserved. This software is the
 * confidential and proprietary information of ZES Inc. You shall not disclose
 * such Confidential Information and shall use it only in accordance with the
 * terms of the license agreement you entered into with ZES Inc.
 * (http://www.zesinc.co.kr/)
 */
package zesinc.web.spring.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import zesinc.web.support.BaseConfig;
import zesinc.web.vo.IPdfVO;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;

/**
 * IPdfVO 구현 VO 객체를 전달받아 PDF 파일을 다운로드 한다.
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2012. 3. 4.    방기배   신규 등록
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see zesinc.web.vo.IPdfVO
 */
public class PdfView extends AbstractPdfView {

    @Override
    protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
    }

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
        HttpServletRequest request, HttpServletResponse response) throws Exception {

        IPdfVO basePdfVo = (IPdfVO) model.get(BaseConfig.PDF_DATA_KEY);

        if(basePdfVo != null) {
            basePdfVo.createPdfDocument(document, model);
        }
    }
}
