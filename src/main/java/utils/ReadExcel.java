package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.util.*;

public class ReadExcel {
    static HSSFWorkbook workBook;
    private final static String FILE_NAME = System.getProperty("user.dir") + "\\src\\main\\resources\\testdata.properties";
    String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\testfiles\\";
    private static Properties prop = new PropertiesLoader(FILE_NAME).load();
    public HSSFWorkbook openFileForReading() {

        HSSFWorkbook hssfWorkbook=null;
        try{
            FileInputStream file = new FileInputStream(new File(this.filePath + prop.getProperty("testfilexls")));
            HSSFWorkbook workbook = new HSSFWorkbook(file);
            hssfWorkbook= workbook;
        }catch(Exception e){

        }
        return hssfWorkbook;
    }
    private String getCellValueAsString(HSSFCell cell,FormulaEvaluator formulaEvaluator) {
        if ((cell == null) || (cell.getCellType() == 3)) {
            return "";
        }
        if (formulaEvaluator.evaluate(cell).getCellType() == 5) {
            try {
                throw new Exception(
                        "Error in formula within this cell! Error code: "
                                + cell.getErrorCellValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(formulaEvaluator.evaluateInCell(cell));
    }
    public Map<String, ArrayList<String>> getValueInDiffColumn(List<String> columnHeader){

        Map<String,ArrayList<String>> mapping=new LinkedHashMap<>();

        for(String columnHead:columnHeader){
            try {
                if(workBook==null) {
                    workBook = openFileForReading();

                }
//                HSSFSheet sheet1 = workBook.getSheet("1");
                HSSFSheet sheet1 = workBook.getSheetAt(0);

                FormulaEvaluator formulaEvaluator = workBook.getCreationHelper().createFormulaEvaluator();
                HSSFRow row = sheet1.getRow(0);
                int columnNum=-1;
                int currentColumnNum = 0;
                for (; currentColumnNum < ((HSSFRow) row).getLastCellNum(); ++currentColumnNum) {
                    HSSFCell cell = row.getCell(currentColumnNum);
                    String currentValue = getCellValueAsString(cell, formulaEvaluator);

                    if (currentValue.equals(columnHead)) {
                        columnNum = currentColumnNum;
                        break;
                    }
                }
                if (columnNum == -1) {
                    System.out.println("Column does not exist");
                }else {
                       int rowCount=sheet1.getLastRowNum();
                    mapping.put(columnHead, new ArrayList<String>());
                    for (int i = 1; i <= rowCount;i++) {
                        row = sheet1.getRow(i);
                        HSSFCell cell = row.getCell(columnNum);
                        mapping.get(columnHead).add(getCellValueAsString(cell, formulaEvaluator));
// System.out.println("Mapping value is-"+mapping.get(columnHead));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mapping;
    }

}
