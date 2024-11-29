package GUI;

import messages.Message;
import messages.MessageType;
import tables.Products; // Импортируем класс Product

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

public class FreeProducts extends JFrame implements TableModelListener {

    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private JButton addBtn, delBtn, backTo;
    private DefaultTableModel dfModel;
    private ArrayList<Products> products = new ArrayList<Products>(); // Список продуктов
    private String[] clmnsName = {"id", "Название", "Категория", "Описание", "Цена", "Статус"}; // Названия столбцов для продуктов
    private String msg;

    FreeProducts(String str) {
        super(str);
        setLayout(null);
        setSize(1200, 600);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);

        panel = new BgPanelOriginal();
        panel.setLayout(null);

        dfModel = new DefaultTableModel();
        dfModel.setColumnIdentifiers(clmnsName); // задает название(и кол-во) столбцов таблицы

        table1 = new JTable(dfModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dfModel);
        table1.setRowSorter(sorter);
        table1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        title = new JLabel("Список продукции:");
        addBtn = new JButton("Добавить новый товар");
        delBtn = new JButton("Удалить");
        backTo = new JButton("Назад");

        addBtn.setFont(BigFontTR);
        delBtn.setFont(BigFontTR);
        backTo.setFont(BigFontTR);

        JScrollPane jScrollPane = new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160, 300));
        jScrollPane.setLocation(20, 60);

        title.setBounds(20, 20, 300, 30);
        title.setFont(BigFontTR);

        addBtn.setBounds(20, 400, 300, 30);
        delBtn.setBounds(400, 400, 250, 30);
        backTo.setBounds(400, 450, 250, 30);

        panel.add(title);
        panel.add(jScrollPane);
        panel.add(addBtn);
        panel.add(delBtn);
        panel.add(backTo);
        setContentPane(panel);

        addBtn.addActionListener(new ClickedAdd());
        delBtn.addActionListener(new ClickedDelete());
        backTo.addActionListener(new ClickedBackTo());
    }

    public void insertProducts(List<Products> products) {
        if (products != null && products.size() > 0) {
            for (Products product : products) {
                Object[] objects = {product.getId(), product.getName(), product.getCategory(),
                        product.getDescription(), product.getPrice(), product.getStatus()};
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
            // Создание нового окна для добавления продукта
            FrameNewProduct frame6 = new FrameNewProduct("Добавление нового продукта", FreeProducts.this);
            frame6.launchPanel();
            frame6.setVisible(true);
            frame6.setResizable(false);
            frame6.setLocationRelativeTo(null);



        }
    }

    private class ClickedDelete implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (table1.getSelectedRow() != -1) {
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
                message.setMessageType(MessageType.DELETE_AVAILABLE_PRODUCT);
                ClientThread.sendMessage(message);
            } else {
                msg = "Выберите строку для удаления";
                JOptionPane.showMessageDialog(FreeProducts.this, msg);
            }
        }
    }

    private class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FreeProducts.this.dispose();
        }
    }
}
