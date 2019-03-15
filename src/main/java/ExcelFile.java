import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelFile extends ExcelData {
    private Time time = new Time();
    private String file_name = "Home_Accounting_v1.0";
    private Workbook workbook;
    private FileOutputStream fileOut;
    private Sheet sheet;
    private Integer sheet_last_row_number;

    public ExcelFile(){
    }

    public boolean checkIfFileExist(String name){
        if(new File(name).exists()){
            System.out.println("File exist.");
            return true;
        }
        else{
            System.out.println("File do not exist.");
            return false;
        }
    }

    // Create Workbook with the header row
    public void createWorkbook(String file_name, List headers){

        System.out.println("File creation: "+file_name);
        this.file_name = file_name;

        this.workbook = new HSSFWorkbook(); // Create workbook
        this.sheet = workbook.createSheet(time.getYearMonth());  // Create Sheet

        // Create header in the Excel file
        Row headerRow = sheet.createRow(0);
        for(int x=0;x<headers.getItemCount();x++){
            Cell cell = headerRow.createCell(x);
            cell.setCellValue(headers.getItem(x));
        }
    }

    // Load Excel file existing Workbook and check if have sheet with the same name as the
    // current YYYY_MM - if not create one
    public void loadWorkbook(String file_name){
        try{
            this.workbook = WorkbookFactory.create(new FileInputStream(file_name+".xls"));
            if(this.checkSheetsNames()){
                this.sheet = workbook.getSheetAt(this.getIdenticalSheetNumber());
                System.out.println(this.workbook.getSheetName(this.getIdenticalSheetNumber()));
            }
            else{
                this.sheet = workbook.createSheet(time.getYearMonth());
            }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    // Check if Excel file contains sheet with the same name as the current YYYY_MM
    public Boolean checkSheetsNames(){
        for(int x = 0; x<this.workbook.getNumberOfSheets();x++){
            if(this.workbook.getSheetName(x).equals(time.getYearMonth())){
                return true;
            }
        }
        return false;
    }

    // Get Excel file sheet number with the same name as the current YYYY_MM
    public Integer getIdenticalSheetNumber() {
        for (int x = 0; x < this.workbook.getNumberOfSheets(); x++) {
            if (this.workbook.getSheetName(x).equals(time.getYearMonth())) {
                return x;
            }
        }

        return 0;
    }

    // Get Excel sheet
    public Integer getSheetLastRowNumber(){
        Integer sheet_last_row_number = 0;
        HSSFRow row;
        Iterator< Row > rowIterator = this.sheet.iterator();

        while (rowIterator.hasNext()) {
            row = (HSSFRow) rowIterator.next();
            sheet_last_row_number = row.getRowNum();
        }

        if(sheet_last_row_number == null){
            return 0;
        }

        return sheet_last_row_number;
    }

    // Write data into the Excel file, after last existing row
    public void writeData(Integer sheet_last_row_number, String date, String receipt_value, String receipt_type){
        try{
            // Write data into Excel file
            Row headerRow = sheet.createRow(sheet_last_row_number+1);
            Cell cell = headerRow.createCell(0);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(date);
            cell = headerRow.createCell(1);
            cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(receipt_value);
            cell = headerRow.createCell(2);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(receipt_type);

            this.fileOut = new FileOutputStream(this.file_name+".xls");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    // Close and save data in the Excel file
    public void closeExcelFile(){
        try {
            this.fileOut = new FileOutputStream(this.file_name+".xls");
            this.workbook.write(fileOut);
            this.fileOut.close();
            this.workbook.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
