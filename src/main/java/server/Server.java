package server;

import dao.PersistException;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.*;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Server {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, PersistException {


        Properties props=null;
        for(int i = 0; i < 100; i++) { }
        ConfigLoader configLoader=new ConfigLoader();  //ConfigLoader используется для поиска файлов конфигурации внутри пути к классам, если файлы конфигурации найдены, он может обеспечить легкий доступ к информации конфигурации
        try {
            props=configLoader.getProps();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String portValue=props.getProperty("PORT");
        Integer port=Integer.parseInt(portValue);



        try {

            ServerSocket serverSocket = new ServerSocket(port);  //ServerSocket сокет, только для сервера
            System.out.println("initialization");


            while (true) {
                new ServerThread(serverSocket.accept()).start();//запуск потока
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
       // finally { connection.close(); }

    }
}

