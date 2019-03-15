import java.lang.Thread;
import java.awt.List;
import java.util.ArrayList;

public class HomeAccounting {
    private static ArrayList<String> header = new ArrayList<String>();
    private static List headers;
    private static ArrayList<String> category_list = new ArrayList<String>();

    public static void main(String[] args){
        ExcelFile ef = new ExcelFile();
        OptionsFile of = new OptionsFile();

        if(of.isOptionsFileExist()) {
            of.loadOptionsFile();
            of.readOptionsFile();
            category_list.addAll(of.getFromOptionsFile("Categories:"));
            of.closeOptionsFile();
        }
        else{
            of.createOptionsFile();
            of.writeOptionsFile();
            of.readOptionsFile();
            category_list.addAll(of.getFromOptionsFile("Categories:"));
            of.closeOptionsFile();
        }

        Thread guiMainWindow = new Thread(new GUIHomeAccounting(ef, category_list));

        if(ef.checkIfFileExist("Home_Accounting_v1.0.xls")){
            ef.loadWorkbook("Home_Accounting_v1.0");
            guiMainWindow.start();
        }
        else{
                headers = new List();
                headers.add("Date:");
                headers.add("Value [PLN]:");
                headers.add("Category:");
                ef.createWorkbook("Home_Accounting_v1.0", headers);
                guiMainWindow.start();
        }
    }
}
