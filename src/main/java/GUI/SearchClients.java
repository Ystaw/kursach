package GUI;

import tables.Clients;
import tables.Products;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SearchClients extends JDialog implements TableModelListener {
    private ShowClients parent;
    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private String strSearch;
    //private JButton addBtn, makeRentBtn, delBtn, backTo;
    private DefaultTableModel dfModel;
    private ArrayList<Products> products = new ArrayList<Products>();
    private String[] clmnsName = {"id", "Фамилия", "Имя","Отчество", "Почта", "Телефон"};
    private List<Clients> listik;
    private JTextField search;
    private JLabel labelSearch= new JLabel("Введите параметр для поиска");


    //DataModel dataModel = new DataModel();

    public ShowClients getParent() {return parent;}

    SearchClients(String str, ShowClients parent, List<Clients> listik) {
        super(parent, str, true);
        this.listik=listik;
        // this.parent = parent;
        // parent.setVisible(false);
        setLayout(null);
        setSize(1200, 600);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);


        panel = new BgPanelOriginal();
        panel.setLayout(null);


        search = new JTextField();
        search.setFont(BigFontTR);
        search.setBounds(20, 220, 200, 20);
        labelSearch.setFont(BigFontTR);
        labelSearch.setBounds(225, 220, 300, 20);
        panel.add(labelSearch);
        panel.add(search);

        dfModel = new DefaultTableModel();
        dfModel.setColumnIdentifiers(clmnsName);


        table1 = new JTable(dfModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dfModel);
        table1.setRowSorter(sorter);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        title = new JLabel("Найденные оценщики:");


        JScrollPane jScrollPane = new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160, 150));
        jScrollPane.setLocation(20, 60);

        title.setBounds(20, 20, 300, 30);  //устанавливает местоположение окна
        title.setFont(BigFontTR);


        panel.add(title);
        panel.add(jScrollPane);
        setContentPane(panel);

        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertClients();
            }
        });



    }

    public void insertClients() {  //воод клиента
        for(int i = 0; i < 100; i++) { }
        if (listik != null && listik.size() > 0) {
            for(int i = 0; i < 100; i++) { }
            for (Clients client : listik) {

                String str1 = client.getEmail();
                String str2 = client.getPhone();
                String strF = client.getLast_name();
                String strN = client.getFirst_name();
                String strO = client.getMiddle_name();


                if (str1.toLowerCase().contains(search.getText())||str2.toLowerCase().contains(search.getText())||
                        strF.toLowerCase().contains(search.getText())||strN.toLowerCase().contains(search.getText())||
                        strO.toLowerCase().contains(search.getText())) {
                    Object[] objects = {client.getIdclients(), client.getLast_name(), client.getFirst_name(), client.getMiddle_name(), client.getEmail(), client.getPhone()};
                    dfModel.addRow(objects);
                }
            }
        }
    }


    @Override
    public void tableChanged(TableModelEvent e) {

    }
}