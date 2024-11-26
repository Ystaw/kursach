package GUI;

import messages.Message;
import messages.MessageType;
import tables.Account;
import tables.Clients;
import tables.Role;
import tables.StatusUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FrameNewClient extends JDialog {

    BgPanel panel= new BgPanel();
    private ShowClients parent;
    private  JTextField firstName, lastName,middleName, company, details, address, phone ; //Текстовое поле
    private JLabel title;
    private JLabel ttFirstName,ttLastName,ttMiddleName, ttCompany, ttDetails, ttAddress, ttPhone;
    private JButton addEntry, backTo;
    String msg;
    private JFrame mainFrame;

   // public Frame getParent(){return parent;}

    FrameNewClient(String str, ShowClients parent){

        super(parent, str, true);
        this.parent= parent;
       // parent.setVisible(false);
        setLayout(null);
        setSize(610,650);
        setLocation(650,225);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);
        Font LabelFont = new Font("Arial", Font.BOLD, 14);


        panel.setLayout(null);

        title = new JLabel("Добавление клиента");
        ttFirstName = new JLabel("Имя контактного лица:");
        ttLastName = new JLabel("Фамилия контактного лица:");
        ttMiddleName = new JLabel("Отчество контактного лица:");
        ttCompany = new JLabel("Организация:");
        ttDetails = new JLabel("Реквизиты:");
        ttAddress = new JLabel("Адрес главного офиса:");
        ttPhone = new JLabel("Телефон:");
        firstName = new JTextField();
        lastName = new JTextField();
        middleName = new JTextField();
        company = new JTextField();
        details = new JTextField();
        address =new JTextField();
        phone = new JTextField();
        addEntry = new JButton("Добавить");
        backTo = new JButton("Назад");

        title.setLocation(250,20);
        title.setSize(300,30);
        title.setFont(BigFontTR);
        ttFirstName.setBounds(5,100,195, 35);
        ttFirstName.setFont(BigFontTR);
        firstName.setBounds(200,100, 350,35);
        firstName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttLastName.setBounds(5,170,195, 35);
        ttLastName.setFont(BigFontTR);
        lastName.setBounds(200,170,350,35);
        lastName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttMiddleName.setBounds(5,240, 195, 35);
        ttMiddleName.setFont(BigFontTR);
        middleName.setBounds(200,240,350,35);
        middleName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttCompany.setBounds(5,310,195, 35);
        ttCompany.setFont(BigFontTR);
        company.setBounds(200,310,350,35);
        company.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttDetails.setBounds(5,380,195, 35);
        ttDetails.setFont(BigFontTR);
        details.setBounds(200,380,350,35);
        details.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttAddress.setBounds(5,450,195, 35);
        ttAddress.setFont(BigFontTR);
        address.setBounds(200,450,350,35);
        address.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttPhone.setBounds(5,520,195, 35);
        ttPhone.setFont(BigFontTR);
        phone.setBounds(200,520,350,30);
        phone.setFont(new Font("Dialog", Font.PLAIN, 18));
        addEntry.setSize(120,30);
        addEntry.setLocation(80,560);
        addEntry.setFont(BigFontTR);
        backTo.setSize(120,30);
        backTo.setLocation(230,560);
        backTo.setFont(BigFontTR);

    }

    public void launchPanel(){
        panel.add(title);
        panel.add(ttFirstName);
        panel.add(ttLastName);
        panel.add(ttMiddleName);
        panel.add(ttCompany);
        panel.add(ttDetails);
        panel.add(ttAddress);
        panel.add(ttPhone);
        panel.add(firstName);
        panel.add(lastName);
        panel.add(middleName);
        panel.add(company);
        panel.add(details);
        panel.add(address);
        panel.add(phone);
        panel.add(addEntry);
        panel.add(backTo);
        setContentPane(panel);

        addEntry.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBackTo());
    }

    public class ClickedAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Pattern pattern = Pattern.compile("[^a-zA-Zа-яА-Я]+");
            Matcher matcher = pattern.matcher(firstName.getText());
            Matcher matcher1 = pattern.matcher(lastName.getText());
            Matcher matcher2 = pattern.matcher(middleName.getText());
//  trim позволяет удалить пробелы в начале и конце строки
            for(int i = 0; i < 100; i++) { }

            if (firstName.getText().trim().length() > 0 && lastName.getText().trim().length() > 0 &&
                    middleName.getText().trim().length() > 0 && company.getText().trim().length() > 0 &&
                    details.getText().trim().length() > 0 && address.getText().trim().length() > 0 &&
                    phone.getText().trim().length() > 0) {
                if(matcher.find() || matcher1.find() || matcher2.find()){
                    msg = "Не корректно введены данные";
                    JOptionPane.showMessageDialog(mainFrame, msg);
                    return;
                }

                Clients client = new Clients();
                client.setCompany(company.getText());
                client.setDetails(details.getText());
                client.setAddress(address.getText());
                client.setPhone(phone.getText());
                client.setLast_name(lastName.getText());
                client.setFirst_name(firstName.getText());
                client.setMiddle_name(middleName.getText());
                Message message = null;
                message = new Message(client);
                message.setMessageType(MessageType.CLIENT_INSERT);
                ClientThread.sendMessage(message);

                FrameNewClient.this.dispose();

                //msg = "Регистрация прошла успешно";
                //JOptionPane.showMessageDialog(mainFrame, msg);
            }

            else {
                msg = "Заполните все поля формы";
                JOptionPane.showMessageDialog(mainFrame, msg);
            }
        }
    }

    public class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            //getParent().setVisible(true);
        }
    }

}
