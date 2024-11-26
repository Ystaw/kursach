package GUI;

import messages.Message;
import messages.MessageType;
import tables.Account;
import tables.Role;
import tables.StatusUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrameNewAdmin extends JDialog {

    BgPanel panel= new BgPanel();
    private final AdminMenu parent;
    private  JTextField firstName, lastName,middleName, login; //Текстовое поле
    private JPasswordField password, repeatPass;
    private JLabel title;
    private JLabel ttFirstName,ttLastName,ttMiddleName, ttLogin, ttPassword, ttRepeatPass;
    private JButton addEntry, backTo;
    String msg;
    private JFrame mainFrame;

    //public AdminMenu getParent(){return parent;}

    FrameNewAdmin(String str, AdminMenu parent)
    {

        super(parent, str, true);
        this.parent= parent;
        //parent.setVisible(false);
        setLayout(null);
        setSize(610,650);
        setLocation(650,225);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);
        Font LabelFont = new Font("Arial", Font.BOLD, 14);


        panel.setLayout(null);

        title = new JLabel("Регистрация");
        ttFirstName = new JLabel("Ваше имя:");
        ttLastName = new JLabel("Ваша фамилия:");
        ttMiddleName = new JLabel("Ваше отчество:");
        ttLogin = new JLabel("Логин:");
        ttPassword = new JLabel("Пароль:");
        ttRepeatPass = new JLabel("Повторите пароль:");
        firstName = new JTextField();
        lastName = new JTextField();
        middleName = new JTextField();
        login = new JTextField();
        password = new JPasswordField();
        repeatPass =new JPasswordField();
        addEntry = new JButton("Добавить");
        backTo = new JButton("Назад");

        title.setLocation(250,20);
        title.setSize(300,30);
        title.setFont(BigFontTR);
        ttFirstName.setSize(195, 35);
        ttFirstName.setLocation(5,100);
        ttFirstName.setFont(BigFontTR);
        firstName.setSize(350,35);
        firstName.setLocation(200,100);
        firstName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttLastName.setSize(195, 35);
        ttLastName.setLocation(5,170);
        ttLastName.setFont(BigFontTR);
        lastName.setSize(350,35);
        lastName.setLocation(200,170);
        lastName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttMiddleName.setSize(195, 35);
        ttMiddleName.setLocation(5,240);
        ttMiddleName.setFont(BigFontTR);
        middleName.setSize(350,35);
        middleName.setLocation(200,240);
        middleName.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttLogin.setSize(195, 35);
        ttLogin.setLocation(5,310);
        ttLogin.setFont(BigFontTR);
        login.setSize(350,35);
        login.setLocation(200,310);
        login.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttPassword.setSize(195, 35);
        ttPassword.setLocation(5,380);
        ttPassword.setFont(BigFontTR);
        password.setSize(350,35);
        password.setLocation(200,380);
        password.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttRepeatPass.setSize(195, 35);
        ttRepeatPass.setLocation(5,450);
        ttRepeatPass.setFont(BigFontTR);
        repeatPass.setSize(350,35);
        repeatPass.setLocation(200,450);
        repeatPass.setFont(new Font("Dialog", Font.PLAIN, 18));
        addEntry.setSize(120,30);
        addEntry.setLocation(80,530);
        addEntry.setFont(BigFontTR);
        backTo.setSize(120,30);
        backTo.setLocation(230,530);
        backTo.setFont(BigFontTR);
    }

    public void launchPanel(){
        panel.add(title);
        panel.add(ttFirstName);
        panel.add(ttLastName);
        panel.add(ttMiddleName);
        panel.add(ttLogin);
        panel.add(ttPassword);
        panel.add(ttRepeatPass);
        panel.add(firstName);
        panel.add(lastName);
        panel.add(middleName);
        panel.add(login);
        panel.add(password);
        panel.add(repeatPass);
        panel.add(addEntry);
        panel.add(backTo);
        setContentPane(panel);

        addEntry.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBackTo());
    }

    public class ClickedAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //new GUI.ClientThread().start();
            ClientThread clientThread=new ClientThread();
            clientThread.start();  // запустить поток
            try {
                Thread.sleep(1000); // приостановить поток
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            Pattern pattern = Pattern.compile("[^a-zA-Zа-яА-Я]+");
            Matcher matcher = pattern.matcher(firstName.getText());
            Matcher matcher1 = pattern.matcher(lastName.getText());
            Matcher matcher2 = pattern.matcher(middleName.getText());
//  trim позволяет удалить пробелы в начале и конце строки
            for(int i = 0; i < 100; i++) { }
            if (firstName.getText().trim().length() > 0 && lastName.getText().trim().length() > 0 &&
                    middleName.getText().trim().length() > 0 && login.getText().trim().length() > 0 &&
                    String.valueOf(password.getPassword()).trim().length() > 0) {

                if(matcher.find() || matcher1.find() || matcher2.find()){
                    msg = "Не корректно введены данные";
                    JOptionPane.showMessageDialog(mainFrame, msg);
                    return;
                }

                Account account = new Account(firstName.getText(), lastName.getText(), middleName.getText(), login.getText(), String.valueOf(password.getPassword()), Role.ADMIN, StatusUser.NORMAL);
                Message message = null;
                message = new Message(account);
                message.setMessageType(MessageType.ADD_ADMIN);
                ClientThread.sendMessage(message);

                FrameNewAdmin.this.dispose();

                //msg = "Регистрация прошла успешно";
                //JOptionPane.showMessageDialog(mainFrame, msg);
            }

            else {
                msg = "Заполните все поля регистрации";
                JOptionPane.showMessageDialog(mainFrame, msg);
            }
        }
    }

    public class ClickedBackTo implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
           // getParent().setVisible(true);
        }
    }

}

