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

public class AvailableProducts extends JFrame implements TableModelListener  {
    private BgPanelOriginal panel;
    private JLabel title;
    private JTable table1;
    private JButton backTo;
    private DefaultTableModel dfModel;
    private ArrayList<Products> products = new ArrayList<Products>(); // Список продуктов
    private String[] clmnsName = {"id", "Название", "Категория", "Описание", "Цена", "Статус"}; // Названия столбцов для продуктов
    private String msg;

    AvailableProducts(String str) {
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
        backTo = new JButton("Назад");

        backTo.setFont(BigFontTR);

        JScrollPane jScrollPane = new JScrollPane(table1);
        jScrollPane.setSize(new Dimension(1160, 300));
        jScrollPane.setLocation(20, 60);

        title.setBounds(20, 20, 300, 30);
        title.setFont(BigFontTR);

        backTo.setBounds(400, 450, 250, 30);

        panel.add(title);
        panel.add(jScrollPane);
        panel.add(backTo);
        setContentPane(panel);

        backTo.addActionListener(new AvailableProducts.ClickedBackTo());
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


    private class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AvailableProducts.this.dispose();
        }
    }
}
