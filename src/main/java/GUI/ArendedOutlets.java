package GUI;

import messages.Message;
import messages.MessageType;
import tables.Outlets;
import tables.Rent;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ArendedOutlets extends JFrame {
    //private MenuUser parent;
    private BgPanelOriginal panel;
    private JLabel title, title2;  //текст
    private JTable table1, table2;
    private JButton  makeRentBtn, delBtn, backTo, otchet, otchet1,otchet2;
    private DefaultTableModel dfModel, dfModel2;
    private ArrayList<Outlets> outlets = new ArrayList<Outlets>();
    private String[] clmnsName = {"id", "Этаж", "Площадь кв.м.","Кондиционер", "Цена аренды за день", "Статус"};
    private String[] clmnsName2 = {"id договора", "id клиента", "id помещения","Дата начала аренды", "Дата конца аренды", "Прибыль"};
    private String msg;

    ArendedOutlets(String str){
        super(str);
        //this.parent=parent;
       // parent.setVisible(false);
        setLayout(null); //к панели добавляем менеджер
        setSize(1200,600);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);


        panel = new BgPanelOriginal();
        panel.setLayout(null);

        dfModel = new DefaultTableModel();
        dfModel.setColumnIdentifiers(clmnsName);

        dfModel2= new DefaultTableModel();
        dfModel2.setColumnIdentifiers(clmnsName2);

        table1 = new JTable(dfModel);
        RowSorter <TableModel> sorter = new TableRowSorter<TableModel>(dfModel);
        table1.setRowSorter(sorter);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);


        table2 = new JTable(dfModel2);
        RowSorter <TableModel> sorter2 = new TableRowSorter<TableModel>(dfModel2);
        table2.setRowSorter(sorter2);
        table2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        title = new JLabel("Арендованные помещения:");
        title2= new JLabel("Оформленные договоры");
        makeRentBtn = new JButton("Оформить договор");
        delBtn = new JButton("Удалить");
        backTo = new JButton("Назад");
        otchet = new JButton("График");
        otchet1 = new JButton("Cтолб. диаграмма");
        otchet2 = new JButton("Круговая диаграмма");

        makeRentBtn.setFont(BigFontTR);
        delBtn.setFont(BigFontTR);
        backTo.setFont(BigFontTR);
        otchet.setFont(BigFontTR);
        otchet1.setFont(BigFontTR);
        otchet2.setFont(BigFontTR);



        title.setBounds(20,20,500, 30);  //устанавливает местоположение окна
        title.setFont(BigFontTR);
        title2.setBounds(20,220, 500,20);
        table1.setLocation(20, 80);
        table1.setRowHeight(20);
        table1.setLocation(20, 80);
        table2.setRowHeight(20);
        delBtn.setBounds(800, 450, 250, 30);
        makeRentBtn.setBounds(20,450, 250, 30);
        backTo.setBounds(450,450,200, 30);
        otchet.setBounds(20,500,250, 30);
        otchet1.setBounds(450,500,200, 30);
        otchet2.setBounds(800,500,250, 30);

        JScrollPane jScrollPane=new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160,150));
        jScrollPane.setLocation(20,60);

        JScrollPane jScrollPane2=new JScrollPane(table2);
        jScrollPane2.setSize(new Dimension(1160,150));
        jScrollPane2.setLocation(20,250);

        panel.add(title);  //добавляет на панель  элемент управления
        panel.add(title2);
        panel.add(jScrollPane);
        panel.add(jScrollPane2);
        panel.add(makeRentBtn);
        panel.add(delBtn);
        panel.add(backTo);
        panel.add(otchet);
        panel.add(otchet1);
        panel.add(otchet2);
        setContentPane(panel);
        // pack();

        // table1.setVisible(true);
        // table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        delBtn.addActionListener(new ClickedDelete());
        makeRentBtn.addActionListener(new ClickedMakeRent());
        backTo.addActionListener(new ClickedBackTo());
        otchet.addActionListener(new ClickedGraphics());
        otchet1.addActionListener(new ClickedGraphics1());
        otchet2.addActionListener(new ClickedGraphics2());

    }

    public void insertOutlets(List<Outlets> outlets){
        if(outlets!=null && outlets.size()>0){
            for(Outlets outlet:outlets){
                Object[] objects={outlet.getId(),outlet.getFloor(), outlet.getSquare(), outlet.getConditioner(), outlet.getCost(), outlet.getStatus()};
                dfModel.addRow(objects);
            }
        }
    }



    public void insertRents(List<Rent> listRents) throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd");

        DefaultTableCellRenderer jTableRenderer = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    String str = table2.getValueAt (row, 4).toString();

                    for(int i = 0; i < 100; i++) { }
                    //Object data = rent.getFinish_date();
                    Date parsedDate = null;
                    try {
                        parsedDate = format.parse(str);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    for(int i = 0; i < 100; i++) { }
                    long curTime = System.currentTimeMillis();   // Для получения текущего системного времени
                    Date curDate = new Date(curTime);  //значение типа Date, с этим временем

                    if (parsedDate.compareTo(curDate) > 0) {

                        cell.setBackground(new Color(255, 255, 255));
                    } else
                        {
                        cell.setBackground(new Color(255, 125, 125));

                    }

                }
                return cell;
            }
        };
        for(int i = 0; i < 100; i++) { }
        if(listRents!=null && listRents.size()>0){




            for(Rent rent:listRents){
                Object[] objects={rent.getId(),rent.getIdClient(), rent.getIdOutlet(), rent.getStart_date(), rent.getFinish_date(), rent.getPayment()};
                dfModel2.addRow(objects);
            }for(int i = 0; i < 100; i++) { }
            for (int i = 0; i < table2.getColumnModel().getColumnCount(); i++) {
                table2.getColumnModel().getColumn(i).setCellRenderer(jTableRenderer);
            }
        }


    }

    private class ClickedGraphics implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GRAPHICS);
            ClientThread.sendMessage(message);
        }
    }

    private class ClickedGraphics1 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GRAPHICS1);
            ClientThread.sendMessage(message);
        }
    }

    private class ClickedGraphics2 implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GRAPHICS2);
            ClientThread.sendMessage(message);
        }
    }

    private class ClickedDelete implements ActionListener{
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
                message.setMessageType(MessageType.DELETE_ARENDED_OUTLET);
                ClientThread.sendMessage(message);
            }
            else if (table2.getSelectedRow()!=-1) {
                int viewRowIndex = table2.getSelectedRow();
                int rowIndex = table2.convertRowIndexToModel(viewRowIndex);
                Object idOutletIndex = new Object();
                Object idRentIndex;
                idRentIndex = dfModel2.getValueAt(rowIndex, 0);
                idOutletIndex=dfModel2.getValueAt(rowIndex, 2);
                Rent rent = new Rent();
                Outlets outlt = new Outlets();
                rent.setIdRent((Integer) idRentIndex);
                outlt.setIdOutlets((Integer) idOutletIndex);


                dfModel2.removeRow(viewRowIndex);

                Message message = null;
                message = new Message();
                message.setRent(rent);
                message.setOutlets(outlt);
                message.setMessageType(MessageType.DELETE_RENT);
                ClientThread.sendMessage(message);
            }
            else {
                msg="Выберите строку для удаления";
                JOptionPane.showMessageDialog(ArendedOutlets.this, msg);
            }
        }
    }

    private class ClickedMakeRent implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_ID_FOR_RENTS);
            ClientThread.sendMessage(message);

        }
    }

    private class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ArendedOutlets.this.dispose();

            MenuUser frame3 = new MenuUser("Меню пользователя");
            frame3.launchPanel();
            frame3.setVisible(true);
            frame3.setResizable(false);
            frame3.setLocationRelativeTo(null);
        }
    }




}
