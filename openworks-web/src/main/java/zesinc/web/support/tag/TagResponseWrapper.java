/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.web.support.tag;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * tag lib에서 사용하는 include용 wrapper
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015. 11. 2.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
public class TagResponseWrapper extends HttpServletResponseWrapper {

    private final PrintWriter writer;

    public TagResponseWrapper(HttpServletResponse response, PrintWriter writer) {
        super(response);
        this.writer = writer;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return writer;
    }

}
