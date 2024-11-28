package GUI;

import messages.Message;
import messages.MessageType;
import tables.Products;
import tables.StatusProducts;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameNewProduct extends JDialog {

    BgPanel panel = new BgPanel();
    private JFrame parent;
    private JTextField name, category, description, price;
    private JLabel title;
    private JLabel ttName, ttCategory, ttDescription, ttPrice, ttStatus;
    private JComboBox status;
    private JButton addEntry, backTo;
    String msg;
    private JFrame mainFrame;

    FrameNewProduct(String str, FreeProducts parent) {
        super(parent, str, true);
        this.parent = parent;
        setLayout(null);
        setSize(610, 450);
        setLocation(650, 225);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);
        Font LabelFont = new Font("Arial", Font.BOLD, 14);

        panel.setLayout(null);

        title = new JLabel("Добавление нового продукта");
        ttName = new JLabel("Название продукта:");
        ttCategory = new JLabel("Категория:");
        ttDescription = new JLabel("Описание:");
        ttPrice = new JLabel("Цена:");
        //ttStatus = new JLabel("Статус:");

        name = new JTextField();
        category = new JTextField();
        description = new JTextField();
        price = new JTextField();
        //status = new JComboBox(new String[]{"Доступен", "Не доступен"});
        addEntry = new JButton("Добавить");
        backTo = new JButton("Назад");

        title.setLocation(200, 20);
        title.setSize(300, 30);
        title.setFont(BigFontTR);

        ttName.setBounds(5, 80, 250, 35);
        ttName.setFont(BigFontTR);
        name.setBounds(300, 80, 250, 35);
        name.setFont(new Font("Dialog", Font.PLAIN, 18));

        ttCategory.setBounds(5, 130, 250, 35);
        ttCategory.setFont(BigFontTR);
        category.setBounds(300, 130, 250, 35);
        category.setFont(new Font("Dialog", Font.PLAIN, 18));

        ttDescription.setBounds(5, 180, 250, 35);
        ttDescription.setFont(BigFontTR);
        description.setBounds(300, 180, 250, 35);
        description.setFont(new Font("Dialog", Font.PLAIN, 18));

        ttPrice.setBounds(5, 230, 250, 35);
        ttPrice.setFont(BigFontTR);
        price.setBounds(300, 230, 250, 35);
        price.setFont(new Font("Dialog", Font.PLAIN, 18));

        /* ttStatus.setBounds(5, 280, 250, 35);
        ttStatus.setFont(BigFontTR);
        status.setBounds(300, 280, 250, 35);
        status.setFont(new Font("Dialog", Font.PLAIN, 18)); */

        addEntry.setSize(120, 30);
        addEntry.setLocation(80, 310);
        addEntry.setFont(BigFontTR);

        backTo.setSize(120, 30);
        backTo.setLocation(230, 310);
        backTo.setFont(BigFontTR);
    }

    public void launchPanel() {
        panel.add(title);
        panel.add(ttName);
        panel.add(ttCategory);
        panel.add(ttDescription);
        panel.add(ttPrice);
        //panel.add(ttStatus);
        panel.add(name);
        panel.add(category);
        panel.add(description);
        panel.add(price);
        //panel.add(status);
        panel.add(addEntry);
        panel.add(backTo);

        setContentPane(panel);

        addEntry.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBackTo());
    }

    // Обработчик кнопки "Добавить"
    public class ClickedAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (name.getText().trim().length() > 0 && category.getText().trim().length() > 0 &&
                    description.getText().trim().length() > 0 && price.getText().trim().length() > 0) {

                Products product = new Products();
                product.setName(name.getText().trim());
                product.setCategory(category.getText().trim());
                product.setDescription(description.getText().trim());
                product.setPrice(Float.parseFloat(price.getText().trim()));
                product.setStatus(StatusProducts.AVAILABLE);
                Message message = null;
                message = new Message(product);
                message.setMessageType(MessageType.PRODUCT_INSERT);
                ClientThread.sendMessage(message);

                FrameNewProduct.this.dispose(); // Закрыть окно

                parent.invalidate(); // Обновить родительский экран
            } else {
                msg = "Заполните все поля формы";
                JOptionPane.showMessageDialog(mainFrame, msg);
            }
        }
    }

    // Обработчик кнопки "Назад"
    public class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setVisible(false); // Скрыть окно
        }
    }
}
