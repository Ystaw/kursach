package GUI;


import messages.Message;
import messages.MessageType;
import tables.Account;
import tables.Outlets;
import tables.Products;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ShowAccounts extends JFrame {
    // private MenuUser parent;
    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private JButton addBtn, backTo;
    private DefaultTableModel dfModel;
    private ArrayList<Products> products = new ArrayList<Products>();
    private String[] clmnsName = {"id", "Логин", "Пароль","Роль", "Фамилия", "Имя", "Отчество", "Статус"};


    //DataModel dataModel = new DataModel();

    //public MenuUser getParent() {return parent;}

    ShowAccounts(String str) {
        super(str);

        setLayout(null);
        setSize(1200, 600);
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


        title = new JLabel("Аккаунты системы:");
        addBtn = new JButton("Забанить пользователя");
        backTo = new JButton("Назад");

        addBtn.setFont(BigFontTR);
        backTo.setFont(BigFontTR);


        JScrollPane jScrollPane=new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160,300));
        jScrollPane.setLocation(20,60);

        title.setBounds(20, 20, 300, 30);//устанавливает местоположение окна
        title.setFont(BigFontTR);

        addBtn.setBounds(20, 400, 300, 30);  //устанавливает местоположение окна
        backTo.setBounds(450, 450, 200, 30);//устанавливает местоположение окна

        panel.add(title);
        panel.add(jScrollPane);
        panel.add(addBtn);
        panel.add(backTo);
        setContentPane(panel);


        addBtn.addActionListener(new ClickedBann());
        backTo.addActionListener(new ClickedBackTo());

    }

    public void insertAccounts(List<Account> accounts){
        for(int i = 0; i < 100; i++) { }
        if(accounts!=null && accounts.size()>0){
            for(Account account:accounts){

                Object[] objects={account.getId(),account.getLogin(), account.getPassword(),account.getRole(), account.getLast_name(), account.getFirst_name(), account.getMiddle_name(), account.getStatus()};
                dfModel.addRow(objects);
            }
        }
    }

    private class ClickedBann implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String msg;
            for(int i = 0; i < 100; i++) { }
            if (table1.getSelectedRow()!=-1) {
                int viewRowIndex = table1.getSelectedRow();
                int rowIndex = table1.convertRowIndexToModel(viewRowIndex);
                Object idAccountIndex = new Object();
                idAccountIndex = dfModel.getValueAt(rowIndex, 0);
                Account acc = new Account();
                acc.setIdusers((Integer) idAccountIndex);

                dfModel.removeRow(viewRowIndex);

                Message message = null;
                message = new Message();
                message.setAccounts(acc);
                message.setMessageType(MessageType.BANN_USER);
                ClientThread.sendMessage(message);
            }
            else {
                msg="Выберите строку для удаления";
                JOptionPane.showMessageDialog(ShowAccounts.this, msg);
            }
        }
    }


    private class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            ShowAccounts.this.dispose();

        }
    }


}
