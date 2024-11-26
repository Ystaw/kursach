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
                        System.out.println("Просмотр пользователей");
                        List<Outlets> listOutlets;
                        dao = factory.getDao(connection, Outlets.class);
                        listOutlets = dao.getAll();
                        Message message11 = new Message();
                        message11.setOutlets(listOutlets);
                        message11.setMessageType(MessageType.SHOW_FREEOUT);
                        oos.writeObject(message11);
                        break;
                    case ADD_ADMIN:
                        System.out.println("Добавление администратора");
                        dao = factory.getDao(connection, Account.class);
                        dao.persist(message.getAccount());
                        oos.writeObject(message);
                        break;
                    case GET_FREE_OUTLETS:
                        System.out.println("Просмотр свободных помещений");
                        List<Outlets> listFreeOutlets;
                        dao = factory.getDao(connection, Outlets.class);
                        Outlets outleto = new Outlets();
                        outleto.setStatus(StatusOutlets.FREE);
                        // listFreeOutlets=dao.getAll();
                        listFreeOutlets = dao.getByParams(outleto);
                        Message message2 = new Message();
                        message2.setFreeOutlets(listFreeOutlets);
                        message2.setMessageType(MessageType.GET_FREE_OUTLETS);
                        oos.writeObject(message2);
                        break;
                    case GET_ARENDED_OUTLETS: {
                        System.out.println("Просмотр договоров");
                        List<Outlets> listArendedOutlets;
                        List<Rent> listRents;
                        dao = factory.getDao(connection, Outlets.class);
                        Outlets outlet = new Outlets();
                        outlet.setStatus(StatusOutlets.ARENDED);
                        listArendedOutlets = dao.getByParams(outlet);

                        dao = factory.getDao(connection, Rent.class);
                        listRents = dao.getAll();

                        Message message3 = new Message();
                        message3.setFreeOutlets(listArendedOutlets);
                        message3.setRents(listRents);
                        message3.setMessageType(MessageType.GET_ARENDED_OUTLETS);
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
                    case GET_ID_FOR_RENTS:
                        System.out.println("Просмотр договоров");
                        List<Outlets> listFreeeOutlets;
                        List<Clients> listForClients;
                        dao = factory.getDao(connection, Outlets.class);
                        Outlets outlet5 = new Outlets();
                        outlet5.setStatus(StatusOutlets.FREE);
                        listFreeeOutlets = dao.getByParams(outlet5);
                        dao = factory.getDao(connection, Clients.class);
                        listForClients = dao.getAll();
                        Message message5 = new Message();
                        message5.setFreeOutlets(listFreeeOutlets);
                        message5.setClients(listForClients);
                        message5.setMessageType(MessageType.GET_ID_FOR_RENTS);
                        oos.writeObject(message5);
                        break;
                    case RENT_INSERT:
                        System.out.println("Добавление договора");
                        dao = factory.getDao(connection, Rent.class);
                        dao.persist(message.getRent());
                        List<Rent> listRentForUpdate;
                        List<Outlets> listOutletsUpdate = new LinkedList<Outlets>();
                        listRentForUpdate=dao.getAll();

                        List<Integer>  idOutletsForUpdate= new LinkedList<Integer>();
                        for(Rent rent: listRentForUpdate){
                            idOutletsForUpdate.add(rent.getIdOutlet());
                            Outlets out = new Outlets();
                            out.setIdOutlets(rent.getIdOutlet());
                            listOutletsUpdate.add(out);
                        }
                        dao = factory.getDao(connection, Outlets.class);

                        for(Outlets outlet: listOutletsUpdate){
                           // dao.getByParams(outlet);
                            dao.update(outlet);
                        }
                        oos.writeObject(message);
                        break;
                    case CLIENT_INSERT:
                        System.out.println("Добавление клиента");
                        dao = factory.getDao(connection, Clients.class);
                        dao.persist(message.getClients());
                        oos.writeObject(message);
                        break;
                    case OUTLET_INSERT:
                        System.out.println("Добавление помещения");
                        dao = factory.getDao(connection, Outlets.class);
                        dao.persist(message.getOutlets());
                        oos.writeObject(message); //сохраняет объект
                        break;
                    case DELETE_FREE_OUTLET:
                        System.out.println("Удаление помещения");
                        dao = factory.getDao(connection, Outlets.class);
                        dao.delete(message.getOutlets());
                        oos.writeObject(message);
                        break;
                    case DELETE_ARENDED_OUTLET:
                        System.out.println("Удаление арендованного помещения");
                        dao = factory.getDao(connection, Outlets.class);
                        dao.delete(message.getOutlets());
                        oos.writeObject(message);
                        break;
                    case DELETE_RENT:
                        System.out.println("Удаление договора");
                        dao = factory.getDao(connection, Rent.class);
                        dao.delete(message.getRent());

                        dao=factory.getDao(connection, Outlets.class);
                        Outlets outlets =message.getOutlets();
                        String idOutletSetFree = String.valueOf(outlets.getId());
                        String update="UPDATE bd_mall.outlets SET status = 'FREE' WHERE id= ?;";


                        try(PreparedStatement statement=connection.prepareStatement(update)) {
                            statement.setString(1, idOutletSetFree);
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
                        List<Rent> listRents;
                        dao = factory.getDao(connection, Rent.class);
                        listRents=dao.getAll();
                        message.setRents(listRents);
                        oos.writeObject(message);
                        break;

                        ///////////////////
                    case GRAPHICS1:
                        List<Rent> listRents1;
                        dao = factory.getDao(connection, Rent.class);
                        listRents1=dao.getAll();
                        message.setRents(listRents1);
                        oos.writeObject(message);
                        break;
                    case GRAPHICS2:
                        List<Rent> listRents2;
                        dao = factory.getDao(connection, Rent.class);
                        listRents2=dao.getAll();
                        message.setRents(listRents2);
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
