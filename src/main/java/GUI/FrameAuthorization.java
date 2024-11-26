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

public class FrameAuthorization extends JFrame {


    BgPanel panel = new BgPanel();
    String msg;
    private Frame parent;
    private JLabel title;
    private JLabel login, password;
    private JTextField fLogin; //Текстовое поле
    private  JPasswordField fPassword;
    private JButton enter, backTo;
    private JFrame mainFrame;

    public Frame getParent(){
        return parent;
    }

    public FrameAuthorization(String str, Frame parent){

        super(str);
        this.parent= parent;
        parent.setVisible(false);
        setLayout(null);
        setSize(500,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);
        Font LabelFont = new Font("Arial", Font.BOLD, 14);


        panel.setLayout(null);

        title = new JLabel("Авторизация");
        login = new JLabel("Введите логин:");
        password = new JLabel("Введите пароль:");
        fLogin = new JTextField();
        fPassword = new JPasswordField();
        enter = new JButton("Войти");
        backTo = new JButton("Назад");

        title.setLocation(250,20);
        title.setSize(300,30);
        title.setFont(BigFontTR);
        login.setSize(350,50);
        fLogin.setLocation(200,100);
        fLogin.setSize(140,50);
        login.setFont(BigFontTR);
        login.setLocation(20,100);
        password.setSize(350,50);
        fPassword.setLocation(200,170);
        fPassword.setSize(140,50);
        password.setFont(BigFontTR);
        password.setLocation(20,170);
        fPassword.setFont(new Font("Dialog", Font.PLAIN, 18));
        fLogin.setFont(new Font("Dialog", Font.PLAIN, 18));
        enter.setSize(120,30);
        enter.setLocation(20,250);
        enter.setFont(BigFontTR);
        backTo.setSize(120,30);
        backTo.setLocation(160,250);
        backTo.setFont(BigFontTR);
    }



    public void launchPanel(){

        panel.add(title);
        panel.add(login);
        panel.add(password);
        panel.add(fLogin);
        panel.add(fPassword);
        panel.add(enter);
        panel.add(backTo);
        setContentPane(panel);

        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientThread clientThread=new ClientThread();
                clientThread.start();   // запустить поток
                try {
                    Thread.sleep(1000);  //приостановить поток
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }

//  trim позволяет удалить пробелы в начале и конце строки
                for(int i = 0; i < 100; i++) { }
                if (fLogin.getText().trim().length() > 0 && String.valueOf(fPassword.getPassword()).trim().length() > 0) {

                    Account account = new Account(fLogin.getText(), String.valueOf(fPassword.getPassword()));
                    Message message = null;
                    message = new Message(account);
                    message.setMessageType(MessageType.ACCOUNT_AUTHORIZATION);
                    ClientThread.sendMessage(message);

                    FrameAuthorization.this.dispose();

                }
                else {
                    msg = "Поля пусты. Введите логин и пароль!";
                    JOptionPane.showMessageDialog(mainFrame, msg);
                }
            }
        });
        backTo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //setVisible(false);
                FrameAuthorization.this.dispose();  //dispose освобождает ресурсы, занимаемые компонентами окна и внутри окна
                getParent().setVisible(true);

            }
        });

    }

}
