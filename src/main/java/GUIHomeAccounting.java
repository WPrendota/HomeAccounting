import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUIHomeAccounting extends JFrame implements ActionListener, Runnable {
    private JButton b_exit;
    private JButton b_add_value;
    private JTextField t_insert_value;
    private JLabel l_insert_value;
    private JFrame window;
    private ArrayList<Integer> add_receipt_value;
    private JComboBox<String> cb_category_chooser = new JComboBox<>();
    private ExcelFile ef;
    private Time time = new Time();

    public GUIHomeAccounting(ExcelFile ef, ArrayList<String> category_list){
        this.ef = ef;
        this.window = new JFrame();
        this.window.setSize(500,500);
        this.window.setTitle("Home Accounting v1.0");
        this.add_receipt_value = new ArrayList<Integer>();
        this.createGUIElements(category_list);
        this.window.setLayout(new GridLayout(6,8,20,20));
    }

    private void createGUIElements(ArrayList<String> category_list){
        // Label insert value:
        this.l_insert_value = new JLabel("Please insert value (PLN):");
        this.l_insert_value.setBounds(100,100,50,20);
        this.window.add(this.l_insert_value);
        this.l_insert_value.setVisible(true);

        // Text insert value:
        this.t_insert_value = new JTextField();
        this.t_insert_value.setBounds(200,200,50,20);
        this.window.add(this.t_insert_value);
        this.t_insert_value.setVisible(true);

        // Add category chooser
        for(int x = 0; x<category_list.size();x++){
            this.cb_category_chooser.addItem(category_list.get(x));
        }
        this.cb_category_chooser.setVisible(true);
        this.window.add(this.cb_category_chooser);

        // Add value button:
        this.b_add_value = new JButton("Add new receipt");
        this.b_add_value.setBounds(10,10, 50,20);
        this.b_add_value.addActionListener(this);
        this.window.add(this.b_add_value);
        this.b_add_value.setVisible(true);

        // Exit button:
        this.b_exit = new JButton("Exit");
        this.b_exit.setBounds(10,10, 50,20);
        this.b_exit.addActionListener(this);
        this.window.add(this.b_exit);
        this.b_exit.setVisible(true);
    }

    // Buttons events:
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if(src == this.b_exit){
            this.ef.closeExcelFile();
            System.exit(0);
        }
        else if(src == this.b_add_value){
            this.ef.writeData(this.ef.getSheetLastRowNumber(), this.time.getDay(), this.t_insert_value.getText(), this.cb_category_chooser.getSelectedItem().toString());
            for(Integer val : this.add_receipt_value){
                System.out.println(val);
            }
        }
    }

    @Override
    public void run() {
        this.window.setVisible(true);
    }
}
