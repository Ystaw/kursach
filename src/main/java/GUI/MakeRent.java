package GUI;

import messages.Message;
import messages.MessageType;
import tables.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MakeRent <T extends JFrame> extends JDialog {
    private BgPanel panel;
    private T parent;
    private JLabel title;
    private JLabel  labelCost, labelOutlets, labelClient, labelStart, labelEnd;
    private JButton addEntry, backTo;
    private JComboBox boxOutlets, boxClients, boxPrimaryCost;
    private JFormattedTextField startDate, endDate;
    String msg;
    private JFrame mainFrame;
    public static String FORMAT_DATE="yyyy.mm.dd";
    public static String SQL_FORMAT_DATE="yyyy-mm-dd";
    DateFormat formatter, formatter_sql;
    Date dateStart, dateEnd;


    //public T getParent() {return parent;}

    public  MakeRent(String str, T parent) throws ParseException {
        super(parent, str, true);
        this.parent=parent;
        //parent.setVisible(false);
        setLayout(null);
        setSize(650,600);
        setLocation(650,225);
        this.setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);



        panel = new BgPanel();
        panel.setLayout(null);



        labelCost = new JLabel("Выберите дневную стоимость аренды:");
        labelOutlets = new JLabel("Выберите помещение:");
        labelClient = new JLabel("Выберите клиента:");
        labelStart = new JLabel("Дата начала аренды:");
        labelEnd = new JLabel("Дата окончания аренды:");

        MaskFormatter mf = new MaskFormatter("####.##.##");
        mf.setPlaceholderCharacter('_');
        formatter = new SimpleDateFormat(FORMAT_DATE);
        formatter_sql = new SimpleDateFormat(SQL_FORMAT_DATE);
        dateEnd =null;
        dateStart=null;

        title = new JLabel("Оформление договора:");

        boxOutlets = new JComboBox();
        boxClients = new JComboBox();
        boxPrimaryCost = new JComboBox();
        startDate = new JFormattedTextField(mf);
        endDate = new JFormattedTextField(mf);
        addEntry = new JButton("Добавить договор");
        backTo = new JButton("Назад");



        labelEnd.setFont(BigFontTR);
        labelStart.setFont(BigFontTR);
        labelClient.setFont(BigFontTR);
        labelOutlets.setFont(BigFontTR);
        labelCost.setFont(BigFontTR);
        title.setFont(BigFontTR);
        boxClients.setFont(BigFontTR);
        boxOutlets.setFont(BigFontTR);
        boxPrimaryCost.setFont(BigFontTR);
        startDate.setFont(BigFontTR);
        endDate.setFont(BigFontTR);
        addEntry.setFont(BigFontTR);
        backTo.setFont(BigFontTR);


        title.setBounds(250,20,300,30);
        labelCost.setBounds(20, 90, 250, 40);
        boxPrimaryCost.setBounds(280, 90, 300,40);
        labelOutlets.setBounds(20, 150, 250, 40);
        boxOutlets.setBounds(280,150, 300,40 );
        labelClient.setBounds(20, 220,250, 40);
        boxClients.setBounds(280,220, 300,40 );
        labelStart.setBounds(20, 290,250, 40);
        startDate.setBounds(280, 290,300,40);
        labelEnd.setBounds(20, 360, 250, 40);
        endDate.setBounds(280, 360,300,40);
        addEntry.setBounds(20, 450, 250,33);
        backTo.setBounds(300, 450, 100,33);


        panel.add(title);
        panel.add(labelCost);
        panel.add(labelOutlets);
        panel.add(boxOutlets);
        panel.add(boxPrimaryCost);
        panel.add(labelClient);
        panel.add(boxClients);
        panel.add(labelStart);
        panel.add(startDate);
        panel.add(labelEnd);
        panel.add(endDate);
        panel.add(addEntry);
        panel.add(backTo);
        setContentPane(panel);

        addEntry.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBack());

    }

    public void insertIDoutlets(List<Outlets> outlets){  //поле со свободными помещениями при аренде
        for(int i = 0; i < 100; i++) { }
        if(outlets!=null && outlets.size()>0){
            for(Outlets outlet:outlets){
                String str= String.valueOf(outlet.getId());
                boxOutlets.addItem(str);
                boxPrimaryCost.addItem(String.valueOf(outlet.getCost()));

            }
        }
        else {
            boxOutlets.addItem("Свободных помещений нет");
            boxPrimaryCost.addItem("Свободных помещений нет");
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

    private class ClickedAdd implements ActionListener{  //составление договора

        public void actionPerformed(ActionEvent e) {
            for(int i = 0; i < 100; i++) { }
            if (boxOutlets.getSelectedIndex()!=-1 &&
                    boxClients.getSelectedIndex()!=-1 &&
                    String.valueOf(startDate.getText()).trim().length()>0 &&
                    String.valueOf(endDate.getText()).trim().length()>0 &&
                    boxPrimaryCost.getSelectedIndex()!=-1) {

                try {
                    dateStart=(Date) formatter.parse(startDate.getText().toString());
                    dateEnd=(Date) formatter.parse(endDate.getText().toString());
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                Outlets outletForRent = new Outlets();
                outletForRent.setIdOutlets(Integer.parseInt(boxOutlets.getSelectedItem().toString()));

                Rent rent = new Rent();
                rent.setIdOutlet(Integer.parseInt(boxOutlets.getSelectedItem().toString()));
                rent.setIdClient(Integer.parseInt(boxClients.getSelectedItem().toString()));
                    rent.setStart_date(formatter_sql.format(dateStart));
                    rent.setFinish_date(formatter_sql.format(dateEnd));
                rent.setPayment(Double.parseDouble(boxPrimaryCost.getSelectedItem().toString()), 1);
                //rent.setPayment(outletForRent);
                Message message = null;
                message = new Message(rent);
                message.setMessageType(MessageType.RENT_INSERT);
                ClientThread.sendMessage(message);

                MakeRent.this.dispose();
                //parent.invalidate();

            }

            else {
                msg = "Заполните все поля договора";
                JOptionPane.showMessageDialog(mainFrame, msg);
            }
        }
    }

    private class ClickedBack implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            MakeRent.this.dispose();
        }
    }

}
