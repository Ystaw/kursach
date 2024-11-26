package dao.mysql;

import dao.DaoFactory;
import dao.GenericDao;
import dao.PersistException;
import tables.Account;
import tables.Clients;
import tables.Outlets;
import tables.Rent;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLDaoFactory  implements DaoFactory<Connection> {

    private String user = "root";//Логин пользователя
    private String password = "motherlode60074";//Пароль пользователя
    private String url = "jdbc:mysql://localhost:3306/bd_mall?serverTimezone=Europe/Moscow&useSSL=false&useUnicode=true&characterEncoding=UTF-8";//URL адрес
    private String driver = "com.mysql.cj.jdbc.Driver";//Имя драйвера
    private Map<Class, DaoCreator> creators;
    private Connection connection;

    public Connection getContext() throws PersistException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return  connection;
    }


    @Override
    public GenericDao getDao(Connection connection, Class dtoClass) throws PersistException {
        DaoCreator creator = creators.get(dtoClass);
        if (creator == null) {
            throw new PersistException("Dao object for " + dtoClass + " not found.");
        }
        return creator.create(connection);
    }

    public MySQLDaoFactory() {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();//Регистрируем драйвер
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {  // когда приложение пытается создать экземпляр (отличный от массива), но в настоящее время исполняемый метод не имеет доступа к определению указанного класса
            e.printStackTrace();
        } catch (InstantiationException e) { // когда приложение пытается создать экземпляр, но объект класса представляет абстрактный класс, интерфейс, класс массива, примитивный тип или void
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) { //сключением, которое обертывает исключение, выданное вызванным методом или конструктором
            e.printStackTrace();
        }
///Класс HashMap использует хеш-таблицу для хранения карточки, обеспечивая быстрое время выполнения запросов get() и put() при больших наборах
        creators = new HashMap<Class, DaoCreator>();
        creators.put(Account.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {     //добавляем ключ и его значение.
                return new MySQLAccountDao(connection);
            }
        });
        creators.put(Clients.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySQLClientsDao(connection);
            }
        });
        creators.put(Outlets.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySQLOutletsDao(connection);
            }
        });
        creators.put(Rent.class, new DaoCreator<Connection>() {
            @Override
            public GenericDao create(Connection connection) {
                return new MySQLRentDao(connection);
            }
        });
    }
}