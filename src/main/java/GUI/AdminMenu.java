package GUI;

import messages.Message;
import messages.MessageType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminMenu extends JFrame {

    private FrameAuthorization parent;
    private JLabel title;  //текст
    private JButton btnShowClients, btnAddAdmin, btnShow, backTo; //кнопки
    private JFrame mainFrame; //окно
    private BgPanel panel;


    AdminMenu(String str) {
        super(str);
        setLayout(null); //к панели добавляем менеджер
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //позволяет указать действие, которое необходимо выполнить, когда пользователь закрывает окно
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);

        panel = new BgPanel();  //создание панели
        panel.setLayout(null); //к панели добавляем менеджер

        title = new JLabel("Меню администратора:");
        btnShowClients = new JButton("Управление пользователями системы");
        btnAddAdmin = new JButton("Добавить нового администратора");
        btnShow = new JButton("Просмотр статуса помещений");
        backTo = new JButton("Назад");

        title.setFont(BigFontTR);  //установка шрифта
        btnShowClients.setFont(BigFontTR);
        btnAddAdmin.setFont(BigFontTR);
        btnShow.setFont(BigFontTR);
        backTo.setFont(BigFontTR);

        title.setBounds(165, 20, 250, 50); //устанавливает местоположение окна
        btnShowClients.setBounds(75, 100, 350, 50);
        btnAddAdmin.setBounds(75, 170, 350, 50);
        btnShow.setBounds(75, 240, 350, 50);
        backTo.setBounds(220, 300, 100, 50);
    }

    public void launchPanel() {
        panel.add(title);  //добавляет на панель элемент управления
        panel.add(btnShowClients);
        panel.add(btnAddAdmin);
        panel.add(btnShow);
        panel.add(backTo);
        setContentPane(panel); //заменяет панель содержимого окна

        btnShowClients.addActionListener(new ClickedShowUsers());  //обработка нажатия кнопки
        btnAddAdmin.addActionListener(new ClickedAddAdmin());
        btnShow.addActionListener(new ClickedShowArended());
        backTo.addActionListener(new ClickedBackTo());

    }



    private class ClickedShowUsers implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.SHOW_ACCOUNTS);
            ClientThread.sendMessage(message);

        }
    }

    private class ClickedAddAdmin implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            FrameNewAdmin frame7 = new FrameNewAdmin("Новый администратор", AdminMenu.this);
            frame7.launchPanel();
            frame7.setVisible(true);
            frame7.setResizable(false);  //размер окна нельзя изменить, т.к false
            frame7.setLocationRelativeTo(null); //расположение окна

        }
    }

    private class ClickedShowArended implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            Message message = null;
            message = new Message();
            message.setMessageType(MessageType.SHOW_FREEOUT);
            ClientThread.sendMessage(message);

        }
    }

    private class ClickedBackTo implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AdminMenu.this.dispose();

            Frame frame = new Frame("Система учета деятельности ТЦ");
            frame.setVisible(true);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.launchPanel();

        }
    }
}


