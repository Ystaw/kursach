package GUI;

import GUI.Frame;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args)
    {

        Frame frame = new Frame("Система учета деятельности ТЦ");
        frame.setVisible(true);
        frame.setResizable(false);  //размер окна нельзя изменить, т.к false
        frame.setLocationRelativeTo(null); //расположение окна
        frame.launchPanel();
    }


}
