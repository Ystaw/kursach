package GUI;

import messages.Message;
import messages.MessageType;
import tables.Clients;
import tables.Outlets;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShowClients extends JFrame {
    private MenuUser parent;
    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private JButton addBtn,  backTo, delBtn;
    JTextField search;
    private DefaultTableModel dfModel;
    private String[] clmnsName = {"id", "Фамилия", "Имя","Отчество", "Компания", "Реквизиты", "Адрес", "Телефон"};
   private List<Clients> listik;



    public ShowClients  (String str){
        super(str);
        setLayout(null);
        setSize(1200,600);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);

        panel = new BgPanelOriginal();
        panel.setLayout(null);



        dfModel = new DefaultTableModel();
        dfModel.setColumnIdentifiers(clmnsName);


        table1 = new JTable(dfModel);
        RowSorter <TableModel> sorter = new TableRowSorter<TableModel>(dfModel);
        table1.setRowSorter(sorter);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        title = new JLabel("Список клиентов:");
        addBtn = new JButton("Добавить клиента");
        delBtn = new JButton("Искать");
        backTo = new JButton("Назад");


        addBtn.setFont(BigFontTR);
        delBtn.setFont(BigFontTR);
        backTo.setFont(BigFontTR);


        title.setBounds(20,20,300, 30); //устанавливает местоположение окна
        title.setFont(BigFontTR);
        table1.setLocation(20, 80);
        table1.setRowHeight(20);
        addBtn.setBounds(20, 400, 300, 30);
        delBtn.setBounds(400, 400, 250, 30);
        backTo.setBounds(450,450,200, 30);


        JScrollPane jScrollPane=new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160,150));
        jScrollPane.setLocation(20,60);

        panel.add(title);
        panel.add(jScrollPane);
        panel.add(addBtn);

        panel.add(delBtn);
        panel.add(backTo);
        setContentPane(panel);

        addBtn.addActionListener(new ClickedAdd() {});
        delBtn.addActionListener(new ClickedDelete() {});
        backTo.addActionListener(new ClickedBackTo() {});
    }

    public void insertClients(List<Clients> clients){  //показывает клиентов
        listik = clients;
        for(int i = 0; i < 100; i++) { }
        if(clients!=null && clients.size()>0){
            for(Clients client:clients){

                Object[] objects={client.getIdclients(), client.getLast_name(), client.getFirst_name(), client.getMiddle_name(), client.getCompany(), client.getDetails(), client.getAddress(), client.getPhone()};
                dfModel.addRow(objects);
            }
        }
    }

    private class ClickedAdd implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            FrameNewClient frame6 = new FrameNewClient("Добавление клиента", ShowClients.this);
            frame6.launchPanel();
            frame6.setVisible(true);
            frame6.setResizable(false);
            frame6.setLocationRelativeTo(null);
        }
    }

    private class ClickedDelete implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            SearchClients frame16 = new SearchClients("Клиенты", ShowClients.this, listik);
            //frame16.insertClients();
            frame16.setVisible(true);
            frame16.setResizable(false);
            frame16.setLocationRelativeTo(null);
        }

    }
    private class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ShowClients.this.dispose();

            MenuUser frame3 = new MenuUser("Меню пользователя");
            frame3.launchPanel();
            frame3.setVisible(true);
            frame3.setResizable(false);
            frame3.setLocationRelativeTo(null);
           // getParent().setVisible(true);
        }
    }
}
