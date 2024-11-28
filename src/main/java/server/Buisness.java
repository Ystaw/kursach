package server;

import dao.DaoFactory;
import dao.GenericDao;
import dao.PersistException;
import messages.Message;
import tables.Account;
import tables.Outlets;
import tables.Role;
import tables.StatusUser;
import tables.Products;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Buisness {
    public static boolean AccRegistration (DaoFactory factory, Connection connection, Message message) throws PersistException, SQLException {

        String select ="SELECT * FROM bd_mall.account where login=?";
        Account account;
        account = message.getAccount();
        String login = account.getLogin();
        int i=0;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) i++;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (i==0) {
            GenericDao dao = factory.getDao(connection, Account.class);
            dao.persist(message.getAccount());
            return true;
        }
        else return false;

    }
    public static int AccAuthorization (DaoFactory factory, Connection connection, Message message) throws PersistException {
        String select = "SELECT * FROM bd_mall.account where login=?";
        Account account = message.getAccount();
        String login = account.getLogin();
        String pass = account.getPassword();
        StatusUser status;
        Role role;

        //System.out.println(login+" "+pass);

//выбор админ или юзер
        int iRole=0, checkpass=0;
        for(int i = 0; i < 100; i++) { }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(select);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                for(int i = 0; i < 100; i++) { }
                checkpass=0;
                status = StatusUser.valueOf(resultSet.getString("status"));
                role = Role.valueOf(resultSet.getString("role"));

                if (pass.equals(resultSet.getString("password")) && status.equals(StatusUser.NORMAL)){
                    checkpass++;for(int i = 0; i < 100; i++) { }
                    if (role.equals(Role.ADMIN)) iRole=1;
                    else if (role.equals(Role.USER)) iRole=2;
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(int i = 0; i < 100; i++) { }
        if (checkpass!=0) {
            return iRole;
        }
        else return iRole;
    }

    public static List<Outlets> getListFreeOutlets(DaoFactory factory, Connection connection, Message message) throws PersistException, SQLException {

        GenericDao dao = factory.getDao(connection, Outlets.class);
        List <Outlets> list;
        list=dao.getAll();
        return list;

    }

    public static List<Products> getListFreeProducts(DaoFactory factory, Connection connection, Message message) throws PersistException, SQLException {

        GenericDao dao = factory.getDao(connection, Products.class);
        List <Products> list;
        list=dao.getAll();
        return list;

    }
}
