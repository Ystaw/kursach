package dao.mysql;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import dao.AbstractJDBCDao;
import dao.PersistException;
import tables.Rent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class MySQLRentDao extends AbstractJDBCDao<Rent, Integer> {


    @Override //указывает, что далее мы собираемся переопределять метод базового класса
    public String getSelectByParams() {
        return "SELECT * FROM bd_mall.rent WHERE start_date=?";
    }


    @Override
    public String getSelectQuery() {
        return "SELECT id, outlets, client, start_date, finish_date, payment FROM bd_mall.rent";
    }

    @Override
    public String getCreateQuery() {
        return "INSERT INTO bd_mall.rent (outlets, client, start_date, finish_date, payment) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE bd_mall.rent SET outlets= ? client = ? start_date = ? finish_date = ? payment = ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM bd_mall.rent WHERE id= ?;";
    }

    @Override
    public Rent create() throws PersistException {
        Rent g = new Rent();
        return persist(g);
    }

    public MySQLRentDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Rent> parseResultSet(ResultSet rs) throws PersistException {
        //LinkedList<Rent> result = new LinkedList<Rent>();
        List<Rent> result = new LinkedList<Rent>();
        try {
            while (rs.next()) {
                Rent rent = new Rent();
                rent.setIdRent(rs.getInt("id"));
                rent.setIdOutlet(rs.getInt("outlets"));
                rent.setIdClient(rs.getInt("client"));
                rent.setStart_date(rs.getDate("start_date"));
                rent.setFinish_date(rs.getDate("finish_date"));
                rent.setPayment(rs.getDouble("payment"));
                result.add(rent);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    //////////////PreparedStatement. интерфейс может принимать параметры во время работы программы Кроме собственно выполнения запроса этот класс позволяет подготовить запрос, отформатировать его должным образом
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Rent object) throws PersistException { //интерфейс может принимать параметры во время работы программы
        try {
            statement.setInt(1, object.getIdOutlet());
            statement.setInt(2, object.getIdClient());
            statement.setDate(3, object.getStart_date());
            statement.setDate(4, object.getFinish_date());
            statement.setDouble(5, object.getPayment());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Rent object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
            statement.setInt(2, object.getIdOutlet());
            statement.setInt(3, object.getIdClient());
            statement.setDate(4, object.getStart_date());
            statement.setDate(5, object.getFinish_date());
            statement.setDouble(6, object.getPayment());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, Rent object) throws PersistException {

    }
}