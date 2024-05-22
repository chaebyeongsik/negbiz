package zesinc.core.excel;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.lang.Validate;

public class ExcelParser {

    /** ExcelParser 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ExcelParser.class);

    private static int DEFAULT_ALLOW_ROW_NO = 500;

    public static List<Map<Integer, String>> excelParser(String excelPath) throws Exception {
        return excelParser(DEFAULT_ALLOW_ROW_NO, new File(excelPath));
    }

    public static List<Map<Integer, String>> excelParser(File excelFile) throws Exception {
        return excelParser(DEFAULT_ALLOW_ROW_NO, excelFile);
    }

    public static List<Map<Integer, String>> excelParser(int allowRowNo, String excelPath) throws Exception {
        return excelParser(allowRowNo, new File(excelPath));
    }

    public static List<Map<Integer, String>> excelParser(int allowRowNo, File excelFile) throws Exception {
        return excelParser(allowRowNo, new FileInputStream(excelFile));
    }

    public static List<Map<Integer, String>> excelParser(int allowRowNo, FileInputStream excelFile) throws Exception {
        List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
        if(allowRowNo <= 0)
            allowRowNo = DEFAULT_ALLOW_ROW_NO;

        try {
            Workbook wb = WorkbookFactory.create(excelFile);
            FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            int sheetNum = 0;
            Sheet sheet = wb.getSheetAt(sheetNum);
            int rows = sheet.getPhysicalNumberOfRows();
            if(rows > allowRowNo)
                rows = allowRowNo;

            for(int r = 0 ; r < rows ; r++) {
                Row row = sheet.getRow(r);
                if(row != null) {
                    int cells = row.getLastCellNum();

                    Map<Integer, String> map = new HashMap<Integer, String>();
                    Boolean blankRow = true;
                    for(int c = 0 ; c < cells ; c++) {
                        Cell cell = row.getCell(c);
                        String value = "";
                        if(cell != null) {
                            switch(cell.getCellType()) {
                                case Cell.CELL_TYPE_NUMERIC:
                                    // 날짜 데이터와 순수 숫자 구분
                                    if(HSSFDateUtil.isCellDateFormatted(cell)) {
                                        value = formatter.format(cell.getDateCellValue());
                                    } else {
                                        value = "" + (long) cell.getNumericCellValue();
                                    }
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    value = "" + cell.getRichStringCellValue().getString();
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    value = "";
                                    break;
                                case Cell.CELL_TYPE_FORMULA:
                                    if(!(cell.toString() == "")) {
                                        // 수식 셀인 경우 계산이 된 결과값을 반환
                                        if(evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_NUMERIC) {
                                            value = "" + (long) cell.getNumericCellValue();
                                        } else if(evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_STRING) {
                                            value = cell.getStringCellValue();
                                        } else if(evaluator.evaluateFormulaCell(cell) == Cell.CELL_TYPE_BOOLEAN) {
                                            boolean fbdata = cell.getBooleanCellValue();
                                            value = String.valueOf(fbdata);
                                        }
                                    }
                                    break;
                                case Cell.CELL_TYPE_ERROR:
                                    value = "" + cell.getErrorCellValue();
                                    break;
                            }
                        }
                        map.put(c, value);
                        if(Validate.isNotEmpty(value)) {
                            blankRow = false;
                        }
                    }
                    if(!blankRow) {
                        list.add(map);
                    }
                }
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }

        return list;
    }

}
