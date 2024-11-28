package GUI;

import messages.Message;
import messages.MessageType;
import tables.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MakeReview <T extends JFrame> extends JDialog {
    private BgPanel panel;
    private T parent;
    private JLabel title;
    private JLabel labelProduct, labelClient, labelDate, labelQualityScore, labelComments;
    private JButton addAssessment, backTo;
    private JComboBox boxProducts, boxClients;
    private JFormattedTextField assessmentDate;
    private JTextField qualityScore, commentsField;
    String msg;
    private JFrame mainFrame;
    public static String FORMAT_DATE = "yyyy.mm.dd";
    public static String SQL_FORMAT_DATE = "yyyy-mm-dd";
    DateFormat formatter, formatter_sql;
    Date dateAssessment;

    public MakeReview(String str, T parent) throws ParseException {
        super(parent, str, true);
        this.parent = parent;
        setLayout(null);
        setSize(650, 600);
        setLocation(650, 225);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font bigFont = new Font("Arial", Font.PLAIN, 18);

        panel = new BgPanel();
        panel.setLayout(null);

        title = new JLabel("Оценка качества продукции:");
        labelProduct = new JLabel("Выберите продукт:");
        labelClient = new JLabel("Выберите клиента:");
        labelDate = new JLabel("Дата оценки:");
        labelQualityScore = new JLabel("Оценка качества (1-100):");
        labelComments = new JLabel("Комментарии:");

        MaskFormatter mf = new MaskFormatter("####.##.##");
        mf.setPlaceholderCharacter('_');
        formatter = new SimpleDateFormat(FORMAT_DATE);
        formatter_sql = new SimpleDateFormat(SQL_FORMAT_DATE);
        dateAssessment = null;

        title = new JLabel("Оценка качества:");

        boxProducts = new JComboBox();
        boxClients = new JComboBox();
        qualityScore = new JTextField();
        commentsField = new JTextField();
        assessmentDate = new JFormattedTextField(mf);
        addAssessment = new JButton("Добавить оценку");
        backTo = new JButton("Назад");

        title.setFont(bigFont);
        labelProduct.setFont(bigFont);
        labelClient.setFont(bigFont);
        labelDate.setFont(bigFont);
        labelQualityScore.setFont(bigFont);
        labelComments.setFont(bigFont);
        boxProducts.setFont(bigFont);
        boxClients.setFont(bigFont);
        assessmentDate.setFont(bigFont);
        qualityScore.setFont(bigFont);
        commentsField.setFont(bigFont);
        addAssessment.setFont(bigFont);
        backTo.setFont(bigFont);

        title.setBounds(200, 20, 300, 30);
        labelProduct.setBounds(20, 90, 250, 40);
        boxProducts.setBounds(280, 90, 300, 40);
        labelClient.setBounds(20, 150, 250, 40);
        boxClients.setBounds(280, 150, 300, 40);
        labelDate.setBounds(20, 220, 250, 40);
        assessmentDate.setBounds(280, 220, 300, 40);
        labelQualityScore.setBounds(20, 290, 250, 40);
        qualityScore.setBounds(280, 290, 300, 40);
        labelComments.setBounds(20, 360, 250, 40);
        commentsField.setBounds(280, 360, 300, 40);
        addAssessment.setBounds(20, 450, 250, 33);
        backTo.setBounds(300, 450, 100, 33);

        panel.add(title);
        panel.add(labelProduct);
        panel.add(boxProducts);
        panel.add(labelClient);
        panel.add(boxClients);
        panel.add(labelDate);
        panel.add(assessmentDate);
        panel.add(labelQualityScore);
        panel.add(qualityScore);
        panel.add(labelComments);
        panel.add(commentsField);
        panel.add(addAssessment);
        panel.add(backTo);
        setContentPane(panel);

        addAssessment.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBack());
    }

    public void insertIDproducts(List<Products> products) { // Заполнение списка продуктов
        if (products!=null && products.size()>0) {
            for (Products product : products) {
                String str= String.valueOf(product.getId());
                boxProducts.addItem(str);
            }
        } else {
            boxProducts.addItem("Товары для оценки отсутствуют");
        }
    }

    public void insertIDclients(List<Clients> clients){   ////поле с клиентами при аренде
        for(int i = 0; i < 100; i++) { }
        if(clients!=null && clients.size()>0){
            for(Clients client:clients){
                String str= String.valueOf(client.getIdclients());
                // String str= String.valueOf(client.getCompany());
                boxClients.addItem(str);
            }
        }
        else {
            boxClients.addItem("Список клиентов пуст");
        }
    }


    private class ClickedAdd implements ActionListener { // Добавление оценки
        public void actionPerformed(ActionEvent e) {
            if (boxProducts.getSelectedIndex()!=-1 &&
                    boxClients.getSelectedIndex()!=-1 &&
                    String.valueOf(assessmentDate.getText()).trim().length()>0 &&
                    !qualityScore.getText().trim().isEmpty()) {

                    try {
                        dateAssessment=(Date) formatter.parse(assessmentDate.getText().toString());
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    Products productForReview = new Products();
                    productForReview.setIdProduct(Integer.parseInt(boxProducts.getSelectedItem().toString()));

                    QualityAssessments qualityAssessment = new QualityAssessments();
                    qualityAssessment.setIdProduct(Integer.parseInt(boxProducts.getSelectedItem().toString()));
                    qualityAssessment.setIdClient(Integer.parseInt(boxClients.getSelectedItem().toString()));
                    qualityAssessment.setAssessmentDate(new java.sql.Date(dateAssessment.getTime())); // Преобразуем в java.sql.Date
                    qualityAssessment.setQualityScore(Integer.parseInt(qualityScore.getText()));
                    qualityAssessment.setComments(commentsField.getText());

                    Message message = new Message(qualityAssessment);
                    message.setMessageType(MessageType.QUALITY_ASSESSMENT_INSERT); // Изменено на QUALITY_ASSESSMENT_INSERT
                    ClientThread.sendMessage(message);

                    MakeReview.this.dispose();
            } else {
                JOptionPane.showMessageDialog(mainFrame, "Заполните все поля оценки.");
            }
        }
    }

    private class ClickedBack implements ActionListener { // Закрытие диалога
        @Override
        public void actionPerformed(ActionEvent e) {
            MakeReview.this.dispose();
        }
    }
}
