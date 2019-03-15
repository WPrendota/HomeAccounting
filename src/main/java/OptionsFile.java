import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class OptionsFile {
    private BufferedWriter bw;
    private BufferedReader br;
    private static String default_categories = "Shopping | Fuel | Bread | Coffee | Meal | Trip | Others\r\n";
    private static String default_file_row_spacer = "-----------------------------------------------------------------\r\n";
    private ArrayList<String> br_data = new ArrayList<String>();

    public Boolean isOptionsFileExist(){
        if(new File("Home_Accounting_V1_0_Options.txt").exists()){
            System.out.println("Options File exist.");
            return true;
        }
        else{
            System.out.println("Options File do not exist.");
            return false;
        }
    }

    public void createOptionsFile(){
        new File("Home_Accounting_V1_0_Options.txt");
        System.out.println("Options File created.");
    }

    public void writeOptionsFile(){
        try{
            this.bw = new BufferedWriter(new FileWriter("Home_Accounting_V1_0_Options.txt"));
            this.bw.write("Categories:\r\n");
            this.bw.write(this.default_categories);
            this.bw.write(this.default_file_row_spacer);
            this.bw.flush();
            System.out.println("Options File: options added.");
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadOptionsFile(){
        try{
            this.br = new BufferedReader(new FileReader("Home_Accounting_V1_0_Options.txt"));
            System.out.println("Options File: file loaded.");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public ArrayList<String> readOptionsFile(){
        try{
            while(this.br.ready()){
                this.br_data.add(this.br.readLine());
            }
            System.out.println("Options File: data loaded.");
        }
        catch(IOException e){
            System.out.println(e);
        }
        return this.br_data;
    }

    public ArrayList<String> getFromOptionsFile(String find_string){
        ArrayList<String> temp = new ArrayList<String>();

        for(int x=0;x<this.br_data.size();x++){
            if(this.br_data.get(x).equals(find_string)){
                System.out.println("Options File: '"+find_string+"' found.");
                temp.addAll(Arrays.asList(this.br_data.get(x+1).split(" | ")));
                for(int y=0;y<temp.size();y++){
                    temp.remove("|");
                }
                return temp;
            }
        }

        System.out.println("Options File: '"+find_string+"' not found.");

        return null;
    }

    public void closeOptionsFile(){
        try{
            this.br.close();
            System.out.println("Options File: file closed.");
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}
