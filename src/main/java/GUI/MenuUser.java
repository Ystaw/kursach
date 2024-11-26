package GUI;

import messages.Message;
import messages.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class MenuUser extends JFrame {

    private FrameAuthorization parent;
    private JLabel title;
    private JButton bttn1, bttn2, bttn3, bttn4, backTo;
    private JFrame mainFrame;
    private BgPanel panel;

    //public FrameAuthorization getParent() { return parent; }

    MenuUser(String str) {
        super(str);
        //this.parent = parent;
        //parent.setVisible(false);
        setLayout(null);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);

        panel = new BgPanel();
        panel.setLayout(null);

        title = new JLabel("Меню пользователя:");
        bttn1 = new JButton("Просмотр свободных помещений");
        bttn2 = new JButton("Просмотр арендованных помещений");
        bttn3 = new JButton("Просмотр клиентов");
        bttn4 = new JButton("Оформить договор аренды");
        backTo = new JButton("Назад");

        title.setFont(BigFontTR);
        bttn1.setFont(BigFontTR);
        bttn2.setFont(BigFontTR);
        bttn3.setFont(BigFontTR);
        bttn4.setFont(BigFontTR);
        backTo.setFont(BigFontTR);

        title.setBounds(165, 20, 250, 50);  //устанавливает местоположение окна
        bttn1.setBounds(75, 100, 350, 50);
        bttn2.setBounds(75, 170, 350, 50);
        bttn3.setBounds(75, 240, 350, 50);
        bttn4.setBounds(75, 310, 350, 50);
        backTo.setBounds(220, 380, 100, 50);
    }

    public void launchPanel() {
        panel.add(title);
        panel.add(bttn1);
        panel.add(bttn2);
        panel.add(bttn3);
        panel.add(bttn4);
        panel.add(backTo);
        setContentPane(panel);

        bttn1.addActionListener(new ClickedFreeOutlets());
        bttn2.addActionListener(new ClickedArendedOutlets());
        bttn3.addActionListener(new ClickedShowClients());
        bttn4.addActionListener(new ClickedMakeRent());
        backTo.addActionListener(new ClickedBackTo());

    }

    private class ClickedFreeOutlets implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_FREE_OUTLETS);
            ClientThread.sendMessage(message);



        }
    }

    private class ClickedArendedOutlets implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_ARENDED_OUTLETS);
            ClientThread.sendMessage(message);

            MenuUser.this.dispose();

        }
    }

    private class ClickedShowClients implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_CLIENTS);
            ClientThread.sendMessage(message);

            MenuUser.this.dispose();

        }
    }

    private class ClickedMakeRent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.GET_ID_FOR_RENTS);
            ClientThread.sendMessage(message);

        }
    }

    private class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuUser.this.dispose();

            Frame frame = new Frame("Система учета деятельности ТЦ");
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.launchPanel();

        }
    }
}


