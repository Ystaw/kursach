package GUI;

import messages.Message;
import messages.MessageType;
import tables.Outlets;
import tables.StatusOutlets;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.*;
import java.util.List;

public class FreeOutlets extends JFrame  implements TableModelListener{
    // private MenuUser parent;
    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private JButton addBtn, makeRentBtn, delBtn, backTo;
    private DefaultTableModel dfModel;
    private ArrayList<Outlets> outlets = new ArrayList<Outlets>();
    private String[] clmnsName = {"id", "Этаж", "Площадь кв.м.", "Кондиционер", "Цена аренды за день", "Статус"};
    private String msg;

    //DataModel dataModel = new DataModel();

    //public MenuUser getParent() {return parent;}

    FreeOutlets(String str) {
        super(str);
        // this.parent = parent;
        // parent.setVisible(false);
        setLayout(null);
        setSize(1200, 600);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);


        panel = new BgPanelOriginal();
        panel.setLayout(null);

        dfModel = new DefaultTableModel();
        dfModel.setColumnIdentifiers(clmnsName);  //задает название(и кол-во) столбцов таблицы


        table1 = new JTable(dfModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dfModel);
        table1.setRowSorter(sorter);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        title = new JLabel("Свободные помещения:");
        addBtn = new JButton("Добавить новое помещение");
        makeRentBtn = new JButton("Оформить договор");
        delBtn = new JButton("Удалить");
        backTo = new JButton("Назад");

        addBtn.setFont(BigFontTR);
        makeRentBtn.setFont(BigFontTR);
        delBtn.setFont(BigFontTR);
        backTo.setFont(BigFontTR);


        JScrollPane jScrollPane = new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160, 300));
        jScrollPane.setLocation(20, 60);

        title.setBounds(20, 20, 300, 30);
        title.setFont(BigFontTR);

        addBtn.setBounds(20, 400, 300, 30);
        delBtn.setBounds(400, 400, 250, 30);
        makeRentBtn.setBounds(750, 400, 250, 30);
        backTo.setBounds(400, 450, 250, 30);

        panel.add(title);
        panel.add(jScrollPane);
        panel.add(addBtn);
        panel.add(makeRentBtn);
        panel.add(delBtn);
        panel.add(backTo);
        setContentPane(panel);


        addBtn.addActionListener(new ClickedAdd());
        delBtn.addActionListener(new ClickedDelete());
        makeRentBtn.addActionListener(new ClickedMakeRent());
        backTo.addActionListener(new ClickedBackTo());

    }

    public void insertOutlets(List<Outlets> outlets) {
        if (outlets != null && outlets.size() > 0) {
            for (Outlets outlet : outlets) {

                Object[] objects = {outlet.getId(), outlet.getFloor(), outlet.getSquare(), outlet.getConditioner(), outlet.getCost(), outlet.getStatus()};
                dfModel.addRow(objects);

                }
        }
    }

    @Override
    public void tableChanged(TableModelEvent e) {
       invalidate();
    }

    private class ClickedAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            FrameNewOutlet frame6 = new FrameNewOutlet("Добавление нового помещения", FreeOutlets.this);
            frame6.launchPanel();
            frame6.setVisible(true);
            frame6.setResizable(false);
            frame6.setLocationRelativeTo(null);

           //dfModel.fireTableDataChanged();
        }
    }

    private class ClickedDelete implements ActionListener {  //удаление свободных помещений по строке
        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < 100; i++) { }
            if (table1.getSelectedRow()!=-1) {
                int viewRowIndex = table1.getSelectedRow();
                int rowIndex = table1.convertRowIndexToModel(viewRowIndex);
                Object idOutletIndex = new Object();
                idOutletIndex = dfModel.getValueAt(rowIndex, 0);
                Outlets outlet = new Outlets();
                outlet.setIdOutlets((Integer) idOutletIndex);

                dfModel.removeRow(viewRowIndex);

                Message message = null;
                message = new Message();
                message.setOutlets(outlet);
                message.setMessageType(MessageType.DELETE_FREE_OUTLET);
                ClientThread.sendMessage(message);
            }
            else {
                msg="Выберите строку для удаления";
                JOptionPane.showMessageDialog(FreeOutlets.this, msg);
            }

        }
    }

    private class ClickedMakeRent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_ID_FOR_RENTS);
            ClientThread.sendMessage(message);

        }
    }


    private class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            FreeOutlets.this.dispose();

        }
    }


}



