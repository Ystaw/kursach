package dao.mysql;

import dao.AbstractJDBCDao;
import dao.PersistException;
import tables.Account;
import tables.Role;
import tables.StatusUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLAccountDao extends AbstractJDBCDao<Account, Integer> {



    @Override //указывает, что далее мы собираемся переопределять метод базового класса
    public String getSelectQuery() {   //Возвращает запрос на выборку данных, используя заданное пользовательское подключение
        return "SELECT id, login, password, role, lastName, firstName, middleName, status FROM bd_mall.account";
    }

    @Override
    public String getSelectByParams() {
        return "SELECT * FROM bd_mall.account WHERE role=?";
    }  //выбираем параметры

    @Override
    public String getCreateQuery() {  //делаем запрос
        return "INSERT INTO bd_mall.account (login, password, role, lastName, firstName, middleName, status) VALUES (?, ?, ?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {  //обновляем
        //return "UPDATE bd_mall.account SET login= ? password = ? role = ? lastName = ? firstName = ? middleName = ? status = ? WHERE id= ?;";
        return "UPDATE bd_mall.account SET status = 'BANNED' WHERE id= ?;";

    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM bd_mall.account WHERE id= ?;";  // ? - знаки подстановки, вместо которых будут вставляться реальные значения
    }  //удаляем аки

    @Override
    public Account create() throws PersistException {
        Account g = new Account();
        return persist(g);
    }

    public MySQLAccountDao(Connection connection) {
        super(connection);
    }  //super - текущий экземпляр суперкласса

    @Override
    protected List<Account> parseResultSet(ResultSet rs) throws PersistException { //в определении метода используется выражение throws Exception(обр икслючения)
        LinkedList<Account> result = new LinkedList<Account>();
        try {
            while (rs.next()) {
                Account acc = new Account();
                acc.setIdusers(rs.getInt("id"));
                acc.setLogin(rs.getString("login"));
                acc.setPassword(rs.getString("password"));
                acc.setRole(Role.valueOf(rs.getString("role")));
                acc.setLast_name(rs.getString("lastName"));
                acc.setFirst_name(rs.getString("firstName"));
                acc.setMiddle_name(rs.getString("middleName"));
                acc.setStatus(StatusUser.valueOf(rs.getString("status")));
                result.add(acc);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
//////////////PreparedStatement. Кроме собственно выполнения запроса этот класс позволяет подготовить запрос, отформатировать его должным образом
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Account object) throws PersistException {  //jdbc для вставки
        try {
            statement.setString(1, object.getLogin());
            statement.setString(2, object.getPassword());
            statement.setString(3, String.valueOf(object.getRole()));
            statement.setString(4, object.getLast_name());
            statement.setString(5, object.getFirst_name());
            statement.setString(6, object.getMiddle_name());
            statement.setString(7, String.valueOf(object.getStatus()));

        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Account object) throws PersistException { // для обновления
        try {
            statement.setInt(1, object.getIdusers());
            /*
            statement.setString(2, object.getLogin());
            statement.setString(3, object.getPassword());
            statement.setString(4, String.valueOf(object.getRole()));
            statement.setString(5, object.getLast_name());
            statement.setString(6, object.getFirst_name());
            statement.setString(7, object.getMiddle_name());
            statement.setString(8, String.valueOf(object.getStatus()));
            */
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, Account object) throws PersistException {
        try {
            statement.setString(1, object.getLogin()); //для связи значения с первым знаком подстановки применяется метод preparedStatement.setString
        } catch (SQLException e) {
            e.printStackTrace();  //Это метод для Exception экземпляров, который печатает трассировку стека экземпляра System.err, очень полезный инструмент для диагностики исключений
        }
    }
}
