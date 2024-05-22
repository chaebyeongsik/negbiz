package zesinc.component.excel.upload;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import zesinc.component.excel.upload.domain.ExcelToTableVO;
import zesinc.component.file.FileService;
import zesinc.component.file.domain.FileVO;
import zesinc.component.file.support.UploadHelper;
import zesinc.core.excel.ExcelParser;
import zesinc.core.lang.Validate;
import zesinc.web.spring.controller.BaseController;

@Controller
@RequestMapping(value = "/**/component/excel/upload")
public class ExcelToTableController extends BaseController {

    @Resource(name = "opFileService")
    private FileService opFileService;

    @RequestMapping(value = "ND_excelToTable.do", method = RequestMethod.POST)
    public String excelToTable(HttpServletRequest request, Model model, ExcelToTableVO excelToTableVo) throws Exception {

        String[] headerNms = excelToTableVo.getHeaderNm();
        String[] headerIds = excelToTableVo.getHeaderId();
        String[] sizes = excelToTableVo.getSize();
        List<FileVO> fileList = UploadHelper.upload(request, "excelData");
        List<List<ExcelToTableVO>> resultList = new ArrayList<List<ExcelToTableVO>>();
        try {
            for(FileVO file : fileList) {
                File f = file.getFile();
                List<Map<Integer, String>> excelData = null;
                if(Validate.isNotEmpty(excelToTableVo.getRowNo()) && excelToTableVo.getRowNo() > 0) {
                    // 헤더가 포함되어 조회(안그러면 헤더가 포함된 rowNo이기 때문에..)
                    excelData = ExcelParser.excelParser(excelToTableVo.getRowNo() + excelToTableVo.getHeaderLineCnt(), f);
                } else {
                    excelData = ExcelParser.excelParser(f);
                }
                // 모든 행을 읽어온다.
                int step = 0;
                int cellSize = headerNms.length;
                for(Map<Integer, String> map : excelData) {
                    if(step < excelToTableVo.getHeaderLineCnt()) {
                        if(step < excelToTableVo.getHeaderLineCnt()) {
                            step++;
                            continue; // 헤더 부분 건너뛰기 (ex: 아이디, 성명...)
                        }
                    }
                    List<ExcelToTableVO> columnSet = new ArrayList<ExcelToTableVO>();
                    // 행안의 컬럼을 읽어온다.
                    for(int i = 0 ; i < cellSize ; i++) {
                        ExcelToTableVO resultVo = new ExcelToTableVO();
                        // input한글, input영문, 값
                        resultVo.setInputId(headerIds[i]);
                        resultVo.setInputNm(headerNms[i]);
                        if(sizes != null) {
                            resultVo.setInputSize(sizes[i]);
                        }
                        resultVo.setValue(map.get(i));
                        columnSet.add(resultVo);
                    }
                    resultList.add(columnSet);
                }
                model.addAttribute("excelData", resultList);
                model.addAttribute("headerNms", headerNms);
                model.addAttribute("headerLineCnt", excelToTableVo.getHeaderLineCnt());
                model.addAttribute("inputUseYn", excelToTableVo.getInputUseYn());
                if(sizes != null) {
                    model.addAttribute("sizes", sizes);
                }
                f.delete();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return "common/taglib/excel/ND_excelToTable";
    }
}
