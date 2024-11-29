package GUI;

import messages.Message;
import messages.MessageType;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import tables.*;


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

                        FrameAuthorization frame2 = new FrameAuthorization("Авторизация",new Frame("Система учета оценки качества"));
                        frame2.launchPanel();
                        frame2.setVisible(true);
                        frame2.setResizable(false);
                        frame2.setLocationRelativeTo(null);
                        break;
                    case GET_FREE_PRODUCTS:
                        AvailableProducts frame20 = new AvailableProducts("Доступные для оценки товары");
                        frame20.insertProducts(enteringMessage.getListProducts());
                        frame20.setVisible(true);
                        frame20.setResizable(false);
                        frame20.setLocationRelativeTo(null);


                        List<Products> listProductsForUpdate;
                        listProductsForUpdate=enteringMessage.getListProducts();
                        PrintWriter pq = null;
                        PrintWriter pz = null;
                        String string3 = "";
                        pq = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet1.txt"), "UTF-8"));
                        pz = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet1.json"), "UTF-8"));

                        for(Products products: listProductsForUpdate) {
                            pq.println(products.toString());
                            pz.println(products.toString());
                        }
                        pq.close();
                        pz.close();
                        System.out.println("Отчёт успешно записан");

                        break;
                    case GET_UNAVAILABLE_PRODUCTS:
                        UnavailableProducts frame5 = new UnavailableProducts("Товары с оценкой качества");
                        frame5.insertProducts(enteringMessage.getListProducts());
                        frame5.insertQualityAssessments(enteringMessage.getListQualityAssessmentss());
                        frame5.setVisible(true);
                        frame5.setResizable(false);
                        frame5.setLocationRelativeTo(null);

                        List<QualityAssessments> listQualityAssessmentsForUpdate;
                        listQualityAssessmentsForUpdate=enteringMessage.getListQualityAssessmentss();
                        PrintWriter pw = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        PrintWriter pp = null;//1) 0-2000 2) 2001-5000 3) 5001-8000 4) 8001+
                        String string = "";
                        pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet.txt"), "UTF-8"));
                        pp = new PrintWriter(new OutputStreamWriter(new FileOutputStream("otchet.json"), "UTF-8"));
                        for(QualityAssessments qualityAssessments: listQualityAssessmentsForUpdate) {
                            pw.println(qualityAssessments.toString());
                            pp.println(qualityAssessments.toString());
                        }
                        pw.close();
                        pp.close();
                        System.out.println("Отчёт успешно записан");

                        break;
                    case GET_CLIENTS:
                        ShowClients frame6 = new ShowClients("Оценщики");
                        frame6.insertClients(enteringMessage.getListClients());
                        frame6.setVisible(true);
                        frame6.setResizable(false);
                        frame6.setLocationRelativeTo(null);
                        break;
                    case GET_ID_FOR_REVIEW:
                        try {
                            MakeReview frame7 = new MakeReview("Оценка качества", new MenuUser("www"));
                            frame7.insertIDproducts(enteringMessage.getListProducts());
                            frame7.insertIDclients(enteringMessage.getListClients());
                            frame7.setVisible(true);
                            frame7.setResizable(false);
                            frame7.setLocationRelativeTo(null);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case QUALITY_ASSESSMENT_INSERT:
                        msg = "Оценка добавлена в базу данных";
                        JOptionPane.showMessageDialog(mainFrame, msg);  //Диалоговое окно вывода сообщения
                        break;
                    case CLIENT_INSERT:
                        msg = "Клиент добавлен в базу данных";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case PRODUCT_INSERT:
                        msg = "Продукт добавлен в базу данных";
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
                        PrintWriter pa = null;
                        PrintWriter pu = null;
                        PrintWriter p1 = null;
                        PrintWriter p2 = null;
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
                        FreeProducts frame13 = new FreeProducts("Доступная продукция:");
                        frame13.insertProducts(enteringMessage.getListProducts());
                        frame13.setVisible(true);
                        frame13.setResizable(false);
                        frame13.setLocationRelativeTo(null);
                        ////
                        List<Products> listProductsForUpdate1;
                        listProductsForUpdate1=enteringMessage.getListProducts();

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
                        for(Products products: listProductsForUpdate1) {
                            p6.println(products.toString());
                            p7.println(products.toString());
                            p8.println(products.toString());
                            p9.println(products.toString());
                        }
                        p6.close();
                        p7.close();
                        p8.close();
                        p9.close();
                        System.out.println("Отчёты успешно записаны");

                        ///
                        break;
                    case DELETE_AVAILABLE_PRODUCT:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case DELETE_UNAVAILABLE_PRODUCTS:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case DELETE_REVIEW:
                        msg="Запись удалена";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;
                    case BANN_USER:
                        msg="Пользователь забанен";
                        JOptionPane.showMessageDialog(mainFrame, msg);
                        break;

                    case GRAPHICS:

                        List<QualityAssessments> listQualityAssessments = enteringMessage.getListQualityAssessmentss();
                        List<Integer> listQualityScores = new LinkedList<>();

// Заполнение списка оценками качества
                        for (QualityAssessments assessment : listQualityAssessments) {
                            listQualityScores.add(assessment.getQualityScore());
                        }
                        int numberOfReviews = listQualityAssessments.size();

// Вычисление общей оценки
                        int totalScore = listQualityScores.stream().mapToInt(Integer::intValue).sum();

// Вычисление среднего значения
                        double averageScore = numberOfReviews > 0 ? (double) totalScore / numberOfReviews : 0;

// Создание набора данных для линейного графика
                        DefaultCategoryDataset data = new DefaultCategoryDataset();

// Заполнение набора данных оценками качества по их соответствующим оценкам
                        for (int i = 0; i < listQualityScores.size(); i++) {
                            data.setValue(listQualityScores.get(i), "Оценки качества", "Оценка " + (i + 1));
                        }

// Создание линейного графика
                        JFreeChart lineChart = ChartFactory.createLineChart(
                                "График оценки качества", // Заголовок графика
                                "Оценки",                // Метка по оси X
                                "Качество оценки",       // Метка по оси Y
                                data,                    // Набор данных
                                PlotOrientation.VERTICAL, // Ориентация графика
                                true,                    // Включить легенду
                                true,                    // Подсказки
                                false                    // URL
                        );

// Сохранение графика как файла изображения
                        int width = 640;   // Ширина изображения
                        int height = 480;  // Высота изображения
                        File lineChartFile = new File("QualityLineChart.jpeg");
                        ChartUtilities.saveChartAsJPEG(lineChartFile, lineChart, width, height);

// Отображение графика в окне
                        ChartFrame frame = new ChartFrame("Отчётный график", lineChart);
                        frame.setSize(950, 400);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);

// Отображение среднего значения оценок качества
                        JOptionPane.showMessageDialog(mainFrame, "Среднее значение оценок качества: " + averageScore);
                        break;

                    case GRAPHICS1:

                        List<QualityAssessments> listQualityAssessments1 = enteringMessage.getListQualityAssessmentss();
                        List<Integer> listQualityScores1 = new LinkedList<>();

// Заполнение списка оценками качества
                        for (QualityAssessments assessment : listQualityAssessments1) {
                            listQualityScores1.add(assessment.getQualityScore());
                        }
                        int numberOfReviews1 = listQualityAssessments1.size();

// Вычисление общей оценки
                        int totalScore1 = listQualityScores1.stream().mapToInt(Integer::intValue).sum();

// Вычисление порога для хорошего индекса удовлетворенности
                        int maxPossibleScore = numberOfReviews1 * 100; // Предполагаем, что каждая оценка может максимум быть 100
                        double threshold = 0.75 * maxPossibleScore;

// Создание набора данных для диаграммы с двумя столбцами
                        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                        dataset.setValue(totalScore1, "Сумма оценок", "Оценки товаров");
                        dataset.setValue(maxPossibleScore, "Максимальная оценка", "Оценки товаров");

// Создание столбчатой диаграммы
                        JFreeChart barChart = ChartFactory.createBarChart(
                                "Диаграмма оценок товаров", // Заголовок графика
                                "Категория",                 // Метка по оси X
                                "Значение",                 // Метка по оси Y
                                dataset,                    // Набор данных
                                PlotOrientation.VERTICAL,    // Ориентация графика
                                true,                       // Включить легенду
                                true,                       // Подсказки
                                false                       // URL
                        );

// Сохранение графика как файла изображения
                        int width2 = 640;   // Ширина изображения
                        int height2 = 480;  // Высота изображения
                        File barChartFile = new File("BarChart.jpeg");
                        ChartUtilities.saveChartAsJPEG(barChartFile, barChart, width2, height2);

// Отображение графика в окне
                        ChartFrame frame8 = new ChartFrame("Отчётный график", barChart);
                        frame8.setSize(950, 400);
                        frame8.setLocationRelativeTo(null);
                        frame8.setVisible(true);

                        if (totalScore1 < threshold) {
                            JOptionPane.showMessageDialog(mainFrame, "Индекс удовлетворенности клиентов плохой.");
                        } else {
                            JOptionPane.showMessageDialog(mainFrame, "Индекс удовлетворенности клиентов хороший.");
                        }

                        break;

                    case GRAPHICS2:

                        List<Products> listProducts = enteringMessage.getListProducts(); // Assuming this gets the list of products
                        int availableCount = 0; // Count for products without a rating (AVAILABLE)
                        int unavailableCount = 0; // Count for products with a rating (UNAVAILABLE)

// Count products based on their status
                        for (Products product : listProducts) {
                            if (product.getStatus() == StatusProducts.AVAILABLE) {
                                availableCount++;
                            } else if (product.getStatus() == StatusProducts.UNAVAILABLE) {
                                unavailableCount++;
                            }
                        }

// Prepare the dataset for the pie chart
                        DefaultPieDataset data3 = new DefaultPieDataset();
                        data3.setValue("Без оценки", availableCount); // Count of products without rating
                        data3.setValue("С оценкой", unavailableCount); // Count of products with rating

// Create the chart
                        JFreeChart chart3 = ChartFactory.createPieChart(
                                "Статус товаров",  // chart title
                                data3,             // data
                                true,              // include legend
                                true,
                                false
                        );

                        //chart3.getPlot().setSectionPaint("Без оценки", Color.RED); // Red for AVAILABLE
                        //chart3.getPlot().setSectionPaint("С оценкой", Color.BLUE); // Blue for UNAVAILABLE

                        int width3 = 640;   /* Width of the image */
                        int height3 = 480;  /* Height of the image */
                        File pieChart = new File("PieChart.jpeg");
                        ChartUtilities.saveChartAsJPEG(pieChart, chart3, width3, height3);

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
