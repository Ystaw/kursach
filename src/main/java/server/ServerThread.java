package server;

import dao.DaoFactory;
import dao.GenericDao;
import dao.PersistException;
import dao.mysql.MySQLDaoFactory;
import messages.Message;
import messages.MessageType;
import tables.*;

import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ServerThread extends Thread {
    private BufferedReader br; //поток чтения из сокета
    private PrintStream ps;  //используется для вывода на консоль
    private ObjectOutput oos;//передача
    private ObjectInput ois;//чтение
    private Socket s;
    private Account account;


    public ServerThread(Socket s) throws IOException {  //многопоточный сервер
        this.s = s;
    }

    @Override
    public void run() {
        try {
            DaoFactory factory = new MySQLDaoFactory();
            Connection connection = (Connection) factory.getContext();
            br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ps = new PrintStream(s.getOutputStream());

            oos = new ObjectOutputStream(s.getOutputStream());
            ois = new ObjectInputStream(s.getInputStream());
            for(int i = 0; i < 100; i++) { }
            Message message = (Message) ois.readObject();

            for(int i = 0; i < 100; i++) { }
            System.out.println("Пользователь подключился."+" "+s.getLocalSocketAddress());
            do {
                GenericDao dao = null;
                message = (Message) ois.readObject();
                //  System.out.println(message.getMessageType());
                switch (message.getMessageType()) {
                    case DISCONNECT:
                        System.out.println("stop");
                        oos.writeObject(message);
                        break;
                    case CONNECT:
                        System.out.println("working");
                        break;
                    case ACCOUNT_REGISTRATION: {
                        System.out.println("Регистрация");
                        boolean reply = Buisness.AccRegistration(factory, connection, message);
                        if (reply) {
                            oos.writeObject(message);
                        } else {
                            Message message1 = new Message();
                            message1.setMessageType(MessageType.FAILED_REGISTRATION);
                            oos.writeObject(message1);
                        }
                    }
                    break;
                    case ACCOUNT_AUTHORIZATION: {
                        System.out.println("Авторизация");
                        int reply = Buisness.AccAuthorization(factory, connection, message);
                        switch (reply) {
                            case 1:
                                Message message0 = new Message();
                                message0.setMessageType(MessageType.SUCCESSFUL_ADMIN_AUTHORIZATION);
                                oos.writeObject(message0);
                                break;

                            case 2:
                                Message message1 = new Message();
                                message1.setMessageType(MessageType.SUCCESSFUL_USER_AUTHORIZATION);
                                oos.writeObject(message1);
                                break;
                            case 0:
                                Message message2 = new Message();
                                message2.setMessageType(MessageType.FAILED_AUTHORIZATION);
                                oos.writeObject(message2);
                                break;
                        }
                    }
                    break;
                    case SHOW_ACCOUNTS:
                        System.out.println("Просмотр пользователей");
                        List<Account> listAccounts;
                        dao = factory.getDao(connection, Account.class);
                        listAccounts = dao.getAll();
                        Message message10 = new Message();
                        message10.setAccounts(listAccounts);
                        message10.setMessageType(MessageType.SHOW_ACCOUNTS);
                        oos.writeObject(message10);
                        break;
                    case SHOW_FREEOUT:
                        System.out.println("Доступная продукция");
                        List<Products> listProducts;
                        dao = factory.getDao(connection, Products.class);
                        listProducts = dao.getAll();
                        Message message11 = new Message();
                        message11.setProducts(listProducts);
                        message11.setMessageType(MessageType.SHOW_FREEOUT);
                        oos.writeObject(message11);
                        break;
                    case ADD_ADMIN:
                        System.out.println("Добавление администратора");
                        dao = factory.getDao(connection, Account.class);
                        dao.persist(message.getAccount());
                        oos.writeObject(message);
                        break;
                    case GET_FREE_PRODUCTS:
                        System.out.println("Доступные для оценки товары");
                        List<Products> listFreeProducts;
                        dao = factory.getDao(connection, Products.class);
                        Products producto = new Products();
                        producto.setStatus(StatusProducts.AVAILABLE);
                        listFreeProducts = dao.getByParams(producto);
                        Message message6 = new Message();
                        message6.setFreeProducts(listFreeProducts);
                        message6.setMessageType(MessageType.GET_FREE_PRODUCTS);
                        oos.writeObject(message6);
                        break;
                    case GET_UNAVAILABLE_PRODUCTS: {
                        System.out.println("Просмотр товаров с оценкой качества");
                        List<Products> listUnavailableProducts;
                        List<QualityAssessments> listQualityAssessmentss;
                        dao = factory.getDao(connection, Products.class);
                        Products product = new Products();
                        product.setStatus(StatusProducts.UNAVAILABLE);
                        listUnavailableProducts = dao.getByParams(product);

                        dao = factory.getDao(connection, QualityAssessments.class);
                        listQualityAssessmentss = dao.getAll();

                        Message message3 = new Message();
                        message3.setFreeProducts(listUnavailableProducts);
                        message3.setQualityAssessments(listQualityAssessmentss);
                        message3.setMessageType(MessageType.GET_UNAVAILABLE_PRODUCTS);
                        oos.writeObject(message3);
                    }

                    break;
                    case GET_CLIENTS:
                        System.out.println("Просмотр клиентов");
                        List<Clients> listClients;
                        dao=factory.getDao(connection, Clients.class);
                        listClients = dao.getAll();

                        Message message4 = new Message();
                        message4.setClients(listClients);
                        message4.setMessageType(MessageType.GET_CLIENTS);
                        oos.writeObject(message4);
                        break;
                    case GET_ID_FOR_REVIEW:
                        System.out.println("Просмотр оценки качества");
                        List<Products> listFreeeProducts;
                        List<Clients> listForClients;
                        dao = factory.getDao(connection, Products.class);
                        Products product5 = new Products();
                        product5.setStatus(StatusProducts.AVAILABLE);
                        listFreeeProducts = dao.getByParams(product5);
                        dao = factory.getDao(connection, Clients.class);
                        listForClients = dao.getAll();
                        Message message5 = new Message();
                        message5.setFreeProducts(listFreeeProducts);
                        message5.setClients(listForClients);
                        message5.setMessageType(MessageType.GET_ID_FOR_REVIEW);
                        oos.writeObject(message5);
                        break;
                    case QUALITY_ASSESSMENT_INSERT:
                        System.out.println("Добавление оценки");
                        dao = factory.getDao(connection, QualityAssessments.class);
                        dao.persist(message.getQualityAssessments());
                        List<QualityAssessments> listQualityAssessmentsForUpdate;
                        List<Products> listProductsUpdate = new LinkedList<Products>();
                        listQualityAssessmentsForUpdate=dao.getAll();

                        List<Integer> idProductsForUpdate = new LinkedList<Integer>();
                        for(QualityAssessments qualityAssessment: listQualityAssessmentsForUpdate){
                            idProductsForUpdate.add(qualityAssessment.getIdProduct());
                            Products out = new Products();
                            out.setIdProduct(qualityAssessment.getIdProduct());
                            listProductsUpdate.add(out);
                        }
                        dao = factory.getDao(connection, Products.class);

                        for(Products product: listProductsUpdate){
                            // dao.getByParams(outlet);
                            dao.update(product);
                        }
                        oos.writeObject(message);
                        break;
                    case CLIENT_INSERT:
                        System.out.println("Добавление клиента");
                        dao = factory.getDao(connection, Clients.class);
                        dao.persist(message.getClients());
                        oos.writeObject(message);
                        break;
                    case PRODUCT_INSERT:
                        System.out.println("Добавление продукции");
                        dao = factory.getDao(connection, Products.class);
                        dao.persist(message.getProducts());
                        oos.writeObject(message); //сохраняет объект
                        break;
                    case DELETE_AVAILABLE_PRODUCT:
                        System.out.println("Удаление товара");
                        dao = factory.getDao(connection, Products.class);
                        dao.delete(message.getProducts());
                        oos.writeObject(message);
                        break;
                    case DELETE_UNAVAILABLE_PRODUCTS:
                        System.out.println("Удаление уже оцененного товара");
                        dao = factory.getDao(connection, Products.class);
                        dao.delete(message.getProducts());
                        oos.writeObject(message);
                        break;
                    case DELETE_REVIEW:
                        System.out.println("Удаление оценки");
                        dao = factory.getDao(connection, QualityAssessments.class);
                        dao.delete(message.getQualityAssessments());

                        dao=factory.getDao(connection, Products.class);
                        Products products =message.getProducts();
                        String idProductSetFree = String.valueOf(products.getId());
                        String update="UPDATE bd_mall.products SET status = 'AVAILABLE' WHERE id= ?;";


                        try(PreparedStatement statement=connection.prepareStatement(update)) {
                            statement.setString(1, idProductSetFree);
                            int rs=statement.executeUpdate();
                        }catch (Exception e) {
                            throw new PersistException(e);
                        }
                        oos.writeObject(message);
                        break;
                    case BANN_USER:
                        System.out.println("Управление пользователями");
                        Account acc= message.getAccount();
                        dao = factory.getDao(connection, Account.class);
                        dao.update(acc);
                        break;
                    case GRAPHICS:
                        List<QualityAssessments> listQualityAssessmentss;
                        dao = factory.getDao(connection, QualityAssessments.class);
                        listQualityAssessmentss=dao.getAll();
                        message.setQualityAssessments(listQualityAssessmentss);
                        oos.writeObject(message);
                        break;

                        ///////////////////
                    case GRAPHICS1:
                        List<QualityAssessments> listQualityAssessmentss1;
                        dao = factory.getDao(connection, QualityAssessments.class);
                        listQualityAssessmentss1=dao.getAll();
                        message.setQualityAssessments(listQualityAssessmentss1);
                        oos.writeObject(message);
                        break;
                    case GRAPHICS2:
                        List<Products> listProducts2;
                        dao = factory.getDao(connection, Products.class);
                        listProducts2=dao.getAll();
                        message.setProducts(listProducts2);
                        oos.writeObject(message);
                        break;

                }for(int i = 0; i < 100; i++) { }
            } while (message.getMessageType() != MessageType.DISCONNECT);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (PersistException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
