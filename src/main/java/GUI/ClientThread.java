package GUI;

import messages.Message;
import messages.MessageType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import tables.Account;
import tables.Outlets;
import tables.Rent;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

//
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.ChartUtilities;
import org.jfree.ui.RefineryUtilities;

//
public class ClientThread extends Thread {
    private static ObjectInput ois;
    private static ObjectOutput oos;
    private BufferedReader br;
    private PrintStream ps;
    private JFrame mainFrame;


    public ClientThread() {
    }

    public void run() {
        try {
            Socket s = new Socket(InetAddress.getLocalHost(), 8030);
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());  //чтение ранее сериализованных данных из потока(приним. ссылку на поток ввода)
            oos = new ObjectOutputStream(s.getOutputStream()); //записывает данные в поток
            String msg;
            Message message = new Message();
            message.setMessageType(MessageType.CONNECT);
            oos.writeObject(message);
            for(int i = 0; i < 100; i++) { }
            do {
                //Scanner scn = new Scanner(System.in);
                Message enteringMessage = (Message) ois.readObject();
                switch (enteringMessage.getMessageType()) {
                    case CONNECT:
                        message = new Message();
                        message.setMessageType(MessageType.CONNECT);
                        oos.writeObject(message);
                        break;
                    case DISCONNECT:
                        message = new Message();
                        message.setMessageType(MessageType.DISCONNECT);
                        System.out.println(message.getMessageType());
                        oos.writeObject(message);
                        break;
                    case ACCOUNT_REGISTRATION:
                        //message.getAccount();
                        msg = "Регистрация прошла успешно";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        //System.out.println("sos");
                        break;
                    case FAILED_REGISTRATION:
                        msg = "Пользователь с таким логином уже существует. \nИзмените логин.";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case SUCCESSFUL_USER_AUTHORIZATION:
                        MenuUser frame3 = new MenuUser("Меню пользователя");
                        frame3.launchPanel();
                        frame3.setVisible(true);
                        frame3.setResizable(false);
                        frame3.setLocationRelativeTo(null);
                        //FrameAuthorization.this.dispose();
                        break;
                    case SUCCESSFUL_ADMIN_AUTHORIZATION:
                        AdminMenu frame10 = new AdminMenu("Меню администратора");
                        frame10.launchPanel();
                        frame10.setVisible(true);
                        frame10.setResizable(false);
                        frame10.setLocationRelativeTo(null);
                        break;
                    case FAILED_AUTHORIZATION:
                        msg = "Вы ввели неверный логин или пароль!";
                        JOptionPane.showMessageDialog(mainFrame, msg);

                        FrameAuthorization frame2 = new FrameAuthorization("Авторизация",new Frame("www"));
                        frame2.launchPanel();
                        frame2.setVisible(true);
                        frame2.setResizable(false);
                        frame2.setLocationRelativeTo(null);
                        break;
                    case GET_FREE_OUTLETS:
                        FreeOutlets frame4 = new FreeOutlets("Свободные помещения");
                        frame4.insertOutlets(enteringMessage.getListOutlets());
                        frame4.setVisible(true);
                        frame4.setResizable(false);
                        frame4.setLocationRelativeTo(null);


                        List<Outlets> listOutletsForUpdate;
                        listOutletsForUpdate=enteringMessage.getListOutlets();
                        PrintWriter pq = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter pz = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        String string1 = "";
                        pq = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet1.txt"), "UTF-8"));
                        pz = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet1.json"), "UTF-8"));

                        for(Outlets outlets: listOutletsForUpdate) {
                            pq.println(outlets.toString());
                            pz.println(outlets.toString());
                        }
                        pq.close();
                        pz.close();
                        System.out.println("Отчёт успешно записан");

                        break;
                    case GET_ARENDED_OUTLETS:
                        ArendedOutlets frame5 = new ArendedOutlets("Арендованные помещения");
                        frame5.insertOutlets(enteringMessage.getListOutlets());
                        frame5.insertRents(enteringMessage.getListRents());
                        frame5.setVisible(true);
                        frame5.setResizable(false);
                        frame5.setLocationRelativeTo(null);

                        List<Rent> listRentForUpdate;
                        listRentForUpdate=enteringMessage.getListRents();
                        PrintWriter pw = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter pp = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        String string = "";
                        pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet.txt"), "UTF-8"));
                        pp = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet.json"), "UTF-8"));
                        for(Rent rent: listRentForUpdate) {
                            pw.println(rent.toString());
                            pp.println(rent.toString());
                        }
                        pw.close();
                        pp.close();
                        System.out.println("Отчёт успешно записан");

                        break;
                    case GET_CLIENTS:
                        ShowClients frame6 = new ShowClients("Клиенты");
                        frame6.insertClients(enteringMessage.getListClients());
                        frame6.setVisible(true);
                        frame6.setResizable(false);
                        frame6.setLocationRelativeTo(null);
                        break;
                    case GET_ID_FOR_RENTS:

                        try {
                            MakeRent frame7 = new MakeRent("Оформление договора", new MenuUser("www"));
                            frame7.insertIDoutlets(enteringMessage.getListOutlets());
                            frame7.insertIDclients(enteringMessage.getListClients());
                            frame7.setVisible(true);
                            frame7.setResizable(false);
                            frame7.setLocationRelativeTo(null);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case RENT_INSERT:
                        msg = "Договор добавлен в базу данных";
                        JOptionPane.showMessageDialog(mainFrame, msg);  //Диалоговое окно вывода сообщения
                        break;
                    case CLIENT_INSERT:
                        msg = "Клиент добавлен в базу данных";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case OUTLET_INSERT:
                        msg = "Помещение добавлено в базу данных";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case ADD_ADMIN:
                        msg = "Новый администратор добавлен";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case SHOW_ACCOUNTS:
                        ShowAccounts frame12 = new ShowAccounts("Аккаунты системы:");
                        frame12.insertAccounts(enteringMessage.getListAccounts());
                        frame12.setVisible(true);
                        frame12.setResizable(false);
                        frame12.setLocationRelativeTo(null);
                        ////
                        List<Account> listAccountForUpdate;
                        listAccountForUpdate=enteringMessage.getListAccounts();
                        PrintWriter pa = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter pu = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter p1 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter p2 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        String string2 = "";
                        pa = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.txt"), "UTF-8"));
                        pu = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.json"), "UTF-8"));
                        p1 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.pdf"), "UTF-8"));
                        p2 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.docx"), "UTF-8"));
                        for(Account account: listAccountForUpdate) {
                            pa.println(account.toString());
                            pu.println(account.toString());
                            p1.println(account.toString());
                            p2.println(account.toString());
                        }
                        pa.close();
                        pu.close();
                        p1.close();
                        p2.close();
                        System.out.println("Отчёты успешно записаны");
                        ///
                        break;
                    case SHOW_FREEOUT:
                        FreeOutlets frame13 = new FreeOutlets("Свободные помещения:");
                        frame13.insertOutlets(enteringMessage.getListOutlets());
                        frame13.setVisible(true);
                        frame13.setResizable(false);
                        frame13.setLocationRelativeTo(null);
                        ////
                        List<Outlets> listOutletsForUpdate1;
                        listOutletsForUpdate1=enteringMessage.getListOutlets();

                        ///

                        PrintWriter p6 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter p7 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter p8 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter p9 = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        String string7 = "";
                        p6 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.txt"), "UTF-8"));
                        p7 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.json"), "UTF-8"));
                        p8 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.pdf"), "UTF-8"));
                        p9 = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet2.docx"), "UTF-8"));
                        for(Outlets outlets: listOutletsForUpdate1) {
                            p6.println(outlets.toString());
                            p7.println(outlets.toString());
                            p8.println(outlets.toString());
                            p9.println(outlets.toString());
                        }
                        p6.close();
                        p7.close();
                        p8.close();
                        p9.close();
                        System.out.println("Отчёты успешно записаны");

                        ///
                        break;
                    case DELETE_FREE_OUTLET:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case DELETE_ARENDED_OUTLET:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case DELETE_RENT:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case BANN_USER:
                        msg="Пользователь забанен";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;

                    case GRAPHICS:

                        List<Rent> listRents=enteringMessage.getListRents();
                        List<Double> listPayments = new LinkedList<Double>();

                        for(Rent rent:listRents){
                            listPayments.add(
                                    rent.getPayment());
                        }

                        int[] mas = new int[5];
                        for (int i = 0; i < 5; i++) {
                            mas[i] = 0;
                        }
                        int summ = 0;

                        for (Double i:listPayments) {

                                if (i > 0 && i < 2000) {
                                    summ += i;
                                    mas[0]++;
                                }
                                if (i > 2001 && i < 5000) {
                                    summ += i;
                                    mas[1]++;
                                }
                                if (i > 5001 && i < 8000) {
                                    summ += i;
                                    mas[2]++;
                                }
                                if (i > 8001) {
                                    summ += i;
                                    mas[3]++;
                                }
                            mas[4] = summ;
                        }


                        DefaultCategoryDataset data = new DefaultCategoryDataset();
                        data.setValue(mas[0], "Прибыль", "до 2000$");
                        data.setValue(mas[1], "Прибыль", "до 5000$");
                        data.setValue(mas[2], "Прибыль", "до 8000$");
                        data.setValue(mas[3], "Прибыль", "от 8001$");
                        //Создает гистограмму
                        JFreeChart chart = ChartFactory.createBarChart("График ", "денежные диапазоны", "количество заключенных договоров", data, PlotOrientation.VERTICAL, true, true, false);

                        int wid = 640;   /* Width of the image */
                        int heig = 480;  /* Height of the image */
                        File BarChart1 = new File( "BarChart1.jpeg" );
                        ChartUtilities.saveChartAsJPEG( BarChart1 , chart , wid , heig );

                        ChartFrame frame = new ChartFrame("Отчётный график", chart);
                        frame.setSize(950, 400);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        JOptionPane.showMessageDialog(mainFrame, "Сумма прибыли с аренды: " + mas[4]+"$");

                        break;

                    case GRAPHICS1:


                        List<Rent> listRents1=enteringMessage.getListRents();
                        List<Double> listPayments1 = new LinkedList<Double>();

                        for(Rent rent:listRents1){
                            listPayments1.add(
                                    rent.getPayment());
                        }

                        int[] mas1 = new int[11];
                        for (int i = 0; i < 11; i++) {
                            mas1[i] = 0;
                        }


                        for (Double i:listPayments1) {

                            if (i > 0 && i < 1000) {

                                mas1[0]++;
                            }
                            if (i > 1001 && i < 2000) {

                                mas1[1]++;
                            }
                            if (i > 2001 && i < 3000) {

                                mas1[2]++;
                            }
                            if (i > 3001 && i < 4000) {

                                mas1[3]++;
                            }
                            if (i > 4001 && i < 5000) {

                                mas1[4]++;
                            }
                            if (i > 5001 && i < 6000) {

                                mas1[5]++;
                            }
                            if (i > 6001 && i < 7000) {

                                mas1[6]++;
                            }
                            if (i > 7001 && i < 8000) {

                                mas1[7]++;
                            }
                            if (i > 8001 && i < 9000) {

                                mas1[8]++;
                            }
                            if (i > 9001 && i < 10000) {

                                mas1[9]++;
                            }
                            if (i > 10001) {

                                mas1[10]++;
                            }

                        }


                        DefaultCategoryDataset data1 = new DefaultCategoryDataset();
                        data1.setValue(mas1[0], "Прибыль", "до 1000$");
                        data1.setValue(mas1[1], "Прибыль", "до 2000$");
                        data1.setValue(mas1[2], "Прибыль", "до 3000$");
                        data1.setValue(mas1[3], "Прибыль", "до 4000$");
                        data1.setValue(mas1[4], "Прибыль", "до 5000$");
                        data1.setValue(mas1[5], "Прибыль", "до 6000$");
                        data1.setValue(mas1[6], "Прибыль", "до 7000$");
                        data1.setValue(mas1[7], "Прибыль", "до 8000$");
                        data1.setValue(mas1[8], "Прибыль", "до 9000$");
                        data1.setValue(mas1[9], "Прибыль", "до 10000$");
                        data1.setValue(mas1[10], "Прибыль", "от 10001$");
                        //Создает гистограмму
                        JFreeChart chart1 = ChartFactory.createBarChart("График c меньшей шкалой ", "денежные диапазоны", "количество заключенных договоров", data1, PlotOrientation.VERTICAL, true, true, false);
                        int widt = 1100;   /* Width of the image */
                        int heigh = 480;  /* Height of the image */
                        File BarChart = new File( "BarChart.jpeg" );
                        ChartUtilities.saveChartAsJPEG( BarChart , chart1 , widt , heigh );

                        ChartFrame frame1 = new ChartFrame("Отчётный график", chart1);
                        frame1.setSize(1100, 400);
                        frame1.setLocationRelativeTo(null);
                        frame1.setVisible(true);

                        break;

                    case GRAPHICS2:


                        List<Rent> listRents2=enteringMessage.getListRents();
                        List<Double> listPayments2 = new LinkedList<Double>();

                        for(Rent rent:listRents2){
                            listPayments2.add(
                                    rent.getPayment());
                        }

                        int[] mas2 = new int[11];
                        for (int i = 0; i < 11; i++) {
                            mas2[i] = 0;
                        }


                        for (Double i:listPayments2) {

                            if (i > 0 && i < 1000) {

                                mas2[0]++;
                            }
                            if (i > 1001 && i < 2000) {

                                mas2[1]++;
                            }
                            if (i > 2001 && i < 3000) {

                                mas2[2]++;
                            }
                            if (i > 3001 && i < 4000) {

                                mas2[3]++;
                            }
                            if (i > 4001 && i < 5000) {

                                mas2[4]++;
                            }
                            if (i > 5001 && i < 6000) {

                                mas2[5]++;
                            }
                            if (i > 6001 && i < 7000) {

                                mas2[6]++;
                            }
                            if (i > 7001 && i < 8000) {

                                mas2[7]++;
                            }
                            if (i > 8001 && i < 9000) {

                                mas2[8]++;
                            }
                            if (i > 9001 && i < 10000) {

                                mas2[9]++;
                            }
                            if (i > 10001) {

                                mas2[10]++;
                            }

                        }

                        DefaultPieDataset data3 = new DefaultPieDataset( );
                        data3.setValue("до 1000$", mas2[0] );
                        data3.setValue("до 2000$", mas2[1] );
                        data3.setValue("до 3000$", mas2[2] );
                        data3.setValue("до 4000$", mas2[3] );
                        data3.setValue("до 5000$", mas2[4] );
                        data3.setValue("до 6000$", mas2[5] );
                        data3.setValue("до 7000$", mas2[6] );
                        data3.setValue("до 8000$", mas2[7] );
                        data3.setValue("до 9000$", mas2[8] );
                        data3.setValue("до 10000$", mas2[9] );
                        data3.setValue("от 10001$", mas2[10] );

                        JFreeChart chart3 = ChartFactory.createPieChart(
                                "Плата за аренду",   // chart title
                                data3,          // data
                                true,             // include legend
                                true,
                                false);

                        int width = 640;   /* Width of the image */
                        int height = 480;  /* Height of the image */
                        File pieChart = new File( "PieChart.jpeg" );
                        ChartUtilities.saveChartAsJPEG( pieChart , chart3 , width , height );

                        ChartFrame demo = new ChartFrame("Круговая диаграмма", chart3);
                        demo.setSize(960, 400);
                        demo.setLocationRelativeTo(null);
                        demo.setVisible(true);

                        break;
                }
            } while (message.getMessageType() != MessageType.DISCONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public BufferedReader getBr() {
        return br;
    }

    public PrintStream getPs() {
        return ps;
    }

    public static void sendMessage(Message message) {
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
