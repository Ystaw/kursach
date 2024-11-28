package GUI;

import messages.Message;
import messages.MessageType;
import tables.Products;
import tables.QualityAssessments;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UnavailableProducts extends JFrame  {

    private BgPanelOriginal panel;
    private JLabel title, title2;  //текст
    private JTable table1, table2;
    private JButton makeReviewBtn, delBtn, backTo, otchet, otchet1,otchet2;
    private DefaultTableModel dfModel, dfModel2;
    private ArrayList<Products> products = new ArrayList<Products>();
    private String[] clmnsName = {"id", "Название", "Категория", "Описание", "Цена", "Статус"};
    private String[] clmnsName2 = {"id отзыва", "id клиента", "id товара","Дата оценки", "Оценка качества", "Комментарий"};
    private String msg;

    UnavailableProducts(String str){
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

        title = new JLabel("Товары с оценкой качества:");
        title2= new JLabel("Оценки качества");
        makeReviewBtn = new JButton("Добавить оценку");
        delBtn = new JButton("Удалить");
        backTo = new JButton("Назад");
        otchet = new JButton("График");
        otchet1 = new JButton("Cтолб. диаграмма");
        otchet2 = new JButton("Круговая диаграмма");

        makeReviewBtn.setFont(BigFontTR);
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
        makeReviewBtn.setBounds(20,450, 250, 30);
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
        panel.add(makeReviewBtn);
        panel.add(delBtn);
        panel.add(backTo);
        panel.add(otchet);
        panel.add(otchet1);
        panel.add(otchet2);
        setContentPane(panel);
        // pack();

        // table1.setVisible(true);
        // table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        delBtn.addActionListener(new UnavailableProducts.ClickedDelete());
        makeReviewBtn.addActionListener(new UnavailableProducts.ClickedMakeReview());
        backTo.addActionListener(new UnavailableProducts.ClickedBackTo());
        otchet.addActionListener(new UnavailableProducts.ClickedGraphics());
        otchet1.addActionListener(new UnavailableProducts.ClickedGraphics1());
        otchet2.addActionListener(new UnavailableProducts.ClickedGraphics2());

    }

    public void insertProducts(List<Products> products){
        if(products!=null && products.size()>0){
            for(Products product:products){
                Object[] objects = {product.getId(), product.getName(), product.getCategory(),
                        product.getDescription(), product.getPrice(), product.getStatus()};
                dfModel.addRow(objects);
            }
        }
    }



    public void insertQualityAssessments(List<QualityAssessments> listQualityAssessments) throws ParseException {
        // Создание рендерера для ячеек таблицы
        DefaultTableCellRenderer jTableRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Если строка выделена, устанавливаем синий фон
                if (isSelected) {
                    cell.setBackground(new Color(173, 216, 230)); // Светло-голубой фон для выделенной строки
                } else {
                    // Установка цвета ячейки в зависимости от столбца с оценкой качества
                    if (column == 4) { // Предполагаем, что оценка качества находится в 5-м столбце (индекс 4)
                        cell.setBackground(new Color(255, 255, 125)); // Подсветка желтым
                    } else {
                        cell.setBackground(new Color(255, 255, 255)); // Белый фон для остальных ячеек
                    }
                }

                return cell;
            }
        };

        // Проверяем, что список не пуст
        if (listQualityAssessments != null && !listQualityAssessments.isEmpty()) {
            for (QualityAssessments qualityAssessment : listQualityAssessments) {
                // Создаем массив объектов для строки таблицы
                Object[] objects = {
                        qualityAssessment.getIdQuality(), // ID оценки
                        qualityAssessment.getIdClient(),   // ID клиента
                        qualityAssessment.getIdProduct(),  // ID продукта
                        qualityAssessment.getAssessmentDate(), // Дата оценки
                        qualityAssessment.getQualityScore(),   // Оценка качества
                        qualityAssessment.getComments()         // Комментарии
                };
                // Добавляем строку в модель таблицы
                dfModel2.addRow(objects);
            }

            // Устанавливаем рендерер для всех столбцов таблицы
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
                Object idProductIndex = new Object();
                idProductIndex = dfModel.getValueAt(rowIndex, 0);
                Products product = new Products();
                product.setIdProduct((Integer) idProductIndex);

                dfModel.removeRow(viewRowIndex);

                Message message = null;
                message = new Message();
                message.setProducts(product);
               // message.setMessageType(MessageType.DELETE_ARENDED_OUTLET);
                message.setMessageType(MessageType.DELETE_UNAVAILABLE_PRODUCTS);
                ClientThread.sendMessage(message);
            }
            else if (table2.getSelectedRow()!=-1) {
                int viewRowIndex = table2.getSelectedRow();
                int rowIndex = table2.convertRowIndexToModel(viewRowIndex);
                Object idProductIndex = new Object();
                Object idReviewIndex;
                idReviewIndex = dfModel2.getValueAt(rowIndex, 0);
                idProductIndex =dfModel2.getValueAt(rowIndex, 2);
                QualityAssessments qualityAssessments = new QualityAssessments();
                Products prodct = new Products();
                qualityAssessments.setIdQuality((Integer) idReviewIndex);
                prodct.setIdProduct((Integer) idProductIndex);


                dfModel2.removeRow(viewRowIndex);

                Message message = null;
                message = new Message();
                message.setQualityAssessments(qualityAssessments);
                message.setProducts(prodct);
                message.setMessageType(MessageType.DELETE_REVIEW);
                ClientThread.sendMessage(message);
            }
            else {
                msg="Выберите строку для удаления";
                JOptionPane.showMessageDialog(UnavailableProducts.this, msg);
            }
        }
    }

    private class ClickedMakeReview implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_ID_FOR_REVIEW);
            ClientThread.sendMessage(message);

        }
    }

    private class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            UnavailableProducts.this.dispose();

            MenuUser frame3 = new MenuUser("Меню пользователя");
            frame3.launchPanel();
            frame3.setVisible(true);
            frame3.setResizable(false);
            frame3.setLocationRelativeTo(null);
        }
    }

}
