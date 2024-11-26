package GUI;

import messages.Message;
import messages.MessageType;
import tables.Outlets;
import tables.StatusOutlets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FrameNewOutlet extends JDialog {

    BgPanel panel= new BgPanel();
    private JFrame parent;
    private  JTextField square, cost ;
    private JLabel title;
    private JLabel ttSquare,ttCost, ttFloor, ttCondition;
    private JComboBox floor, conditioner;
    private JButton addEntry, backTo;
    String msg;
    private JFrame mainFrame;



    FrameNewOutlet(String str, FreeOutlets parent){

        super(parent, str, true);
        this.parent= parent;
        // parent.setVisible(false);
        setLayout(null);
        setSize(610,450);
        setLocation(650,225);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(Color.PINK);
        Font BigFontTR = new Font("Arial", Font.PLAIN, 18);
        Font LabelFont = new Font("Arial", Font.BOLD, 14);


        panel.setLayout(null);

        title = new JLabel("Добавление помещения");
        ttFloor = new JLabel("Этаж:");
        ttSquare = new JLabel("Площадь помещения кв.м.:");
        ttCondition = new JLabel("Кондиционер:");
        ttCost = new JLabel("Стоимость аренды в день:");
        floor = new JComboBox(new String[]{"1","2","3","4","5","6"});
        square = new JTextField();
        conditioner = new JComboBox(new String[]{"Есть","Нет"});
        cost = new JTextField();
        addEntry = new JButton("Добавить");
        backTo = new JButton("Назад");

        title.setLocation(250,20);
        title.setSize(300,30);
        title.setFont(BigFontTR);
        ttFloor.setBounds(5,100,250, 35);
        ttFloor.setFont(BigFontTR);
        floor.setBounds(300,100, 150,35);
        floor.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttSquare.setBounds(5,170,250, 35);
        ttSquare.setFont(BigFontTR);
        square.setBounds(300,170,150,35);
        square.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttCondition.setBounds(5,240, 250, 35);
        ttCondition.setFont(BigFontTR);
        conditioner.setBounds(300,240,150,35);
        conditioner.setFont(new Font("Dialog", Font.PLAIN, 18));
        ttCost.setBounds(5,310,250, 35);
        ttCost.setFont(BigFontTR);
        cost.setBounds(300,310,150,35);
        cost.setFont(new Font("Dialog", Font.PLAIN, 18));
        addEntry.setSize(120,30);
        addEntry.setLocation(80,350);
        addEntry.setFont(BigFontTR);
        backTo.setSize(120,30);
        backTo.setLocation(230,350);
        backTo.setFont(BigFontTR);

    }

    public void launchPanel(){
        panel.add(title);
        panel.add(ttCondition);
        panel.add(ttCost);
        panel.add(ttFloor);
        panel.add(ttSquare);
        panel.add(conditioner);
        panel.add(cost);
        panel.add(floor);
        panel.add(square);
        panel.add(addEntry);
        panel.add(backTo);
        setContentPane(panel);

        addEntry.addActionListener(new ClickedAdd());
        backTo.addActionListener(new ClickedBackTo());
    }

    public class ClickedAdd implements ActionListener {
        public void actionPerformed(ActionEvent e) {
//  trim позволяет удалить пробелы в начале и конце строки
            for(int i = 0; i < 100; i++) { }
            if (square.getText().trim().length() > 0 && cost.getText().trim().length() > 0 &&
                    floor.getSelectedIndex()!=-1 && conditioner.getSelectedIndex()!=-1) {

                Outlets outlet = new Outlets();
                outlet.setStatus(StatusOutlets.FREE);
                outlet.setFloor(Integer.parseInt(floor.getSelectedItem().toString()));
                outlet.setSquare(Float.parseFloat(square.getText()));
                outlet.setConditioner(String.valueOf(conditioner.getSelectedItem()));
                outlet.setCost(Float.parseFloat(cost.getText()));
                Message message = null;
                message = new Message(outlet);
                message.setMessageType(MessageType.OUTLET_INSERT);
                ClientThread.sendMessage(message);

                FrameNewOutlet.this.dispose();

                parent.invalidate();
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
