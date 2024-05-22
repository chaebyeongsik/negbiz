/*
 * Copyright (c) 2015 ZES Inc. All rights reserved.
 * This software is the confidential and proprietary information of ZES Inc.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with ZES Inc. (http://www.zesinc.co.kr/)
 */
package zesinc.component.resrce.file;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zesinc.component.resrce.file.domain.ResrceFileVO;
import zesinc.web.spring.controller.IntraController;
import zesinc.web.vo.IFileVO;

/**
 * 자원파일 정보 컨트롤러 클레스
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 *    
 *     수정일       수정자   수정내용
 * --------------  --------  -------------------------------
 *  2015-10-14.    방기배   최초작성
 * </pre>
 * 
 * @author (주)제스아이엔씨 기술연구소
 * @see
 */
@Controller
@RequestMapping(value = { "/**/component/resrce/file" })
public class ResrceFileController extends IntraController {

    @Resource(name = "opResrceFileDownService")
    private ResrceFileService opResrceFileDownService;

    /**
     * 자원파일 다운로드
     */
    @RequestMapping(value = "ND_resrceFileDownload.do")
    public String fileDownload(HttpServletRequest request, Model model, ResrceFileVO resrceFileVo) {

        IFileVO dataVo = opResrceFileDownService.selectResrceFile(resrceFileVo);
        return responseDownload(model, dataVo);
    }
}
