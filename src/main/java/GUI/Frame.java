package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import messages.Message;
import messages.MessageType;


public class Frame extends JFrame {

    private JButton bttn1, bttn2, bttn3;
    private BgPanel panel = new BgPanel();


    public Frame(String str){
        super(str);
        setLayout(null);
        setSize(400,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);

        panel.setLayout(null);
        //setContentPane(new BgPanel());


        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);


        bttn1 = new JButton("Авторизация");
        bttn2 = new JButton("Регистрация");
        bttn3 = new JButton("Выход");


        bttn1.setSize(200,50);
        bttn1.setLocation(100,150);
        bttn2.setSize(200,50);
        bttn2.setLocation(100,230);
        bttn3.setSize(200,50);
        bttn3.setLocation(100,310);
        bttn1.setFont(BigFontTR);
        bttn2.setFont(BigFontTR);
        bttn3.setFont(BigFontTR);

    }

    public void launchPanel(){
        panel.add(bttn1);
        panel.add(bttn2);
        panel.add(bttn3);
        setContentPane(panel);

        bttn1.addActionListener(new ClickedAuthorization());
        bttn2.addActionListener(new ClickedRegistration());
        bttn3.addActionListener(new ClickedExit());

    }

    public class ClickedAuthorization implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            FrameAuthorization frame2 = new FrameAuthorization("Авторизация",Frame.this);
            frame2.launchPanel();
            frame2.setVisible(true);
            frame2.setResizable(false);  //размер окна нельзя изменить, т.к false
            frame2.setLocationRelativeTo(null); ////расположение окна
        }
    }

    public class ClickedRegistration implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            FrameRegistration frame3 = new FrameRegistration("Регистрация", Frame.this);
            frame3.launchPanel();
            frame3.setVisible(true);
            frame3.setResizable(false);
            frame3.setLocationRelativeTo(null);
        }
    }

    public class ClickedExit implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            Message message=new Message();
            message.setMessageType(MessageType.DISCONNECT);
            ClientThread.sendMessage(message);
            System.exit(0);

        }
    }
}




