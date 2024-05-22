package zesinc.excel;

import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zesinc.core.excel.ExcelParser;

public class ExcelUploadTest extends TestCase {
    /** 로깅 */
    private static Logger logger = LoggerFactory.getLogger(ExcelUploadTest.class);
	private String filePath = "src/test/resources/zesinc/excel/Mgr_Excel_Sample.xls";
    @Test
    public void testReadingExcel() throws Exception {
    	List<Map<Integer, String>> list = ExcelParser.excelParser(filePath);
    	int k = 0;
    	for(Map<Integer, String> map : list){
    		k++;
    		logger.debug("<<================ List "+ k +"번째("+map.size()+") ================>>");
    		for(int i = 1; i <= map.size(); i++){
    		    logger.debug("value"+(i+1)+"번째 : " + map.get(i));
    			map.get(i);
    		}
    		
    	}
    }
}
