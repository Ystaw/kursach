package dao.mysql;

import dao.AbstractJDBCDao;
import dao.PersistException;
import tables.Clients;
import tables.Role;
import tables.StatusUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySQLClientsDao extends AbstractJDBCDao<Clients, Integer> {



    @Override //указывает, что далее мы собираемся переопределять метод базового класса
    public String getSelectQuery() {  //Возвращает запрос на выборку данных, используя заданное пользовательское подключение
        return "SELECT id, email, phone, firstName, lastName, middleName FROM bd_mall.clients";
    }

    @Override
    public String getSelectByParams() {
        return "SELECT * FROM bd_mall.clients WHERE email=?";
    } //выбираем параметры

    @Override
    public String getCreateQuery() {  //делаем запрос
        return "INSERT INTO bd_mall.clients (email, phone, firstName, lastName, middleName) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() { //обновляем
        return "UPDATE bd_mall.clients SET email= ? phone = ? firstName = ? lastName = ? middleName = ? WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM bd_mall.clients WHERE id= ?;";  // ? - знаки подстановки, вместо которых будут вставляться реальные значения
    } //удаляем аки

    @Override
    public Clients create() throws PersistException {
        Clients g = new Clients();
        return persist(g);
    }

    public MySQLClientsDao(Connection connection) {
        super(connection);
    }  //super - текущий экземпляр суперкласса

    @Override
    protected List<Clients> parseResultSet(ResultSet rs) throws PersistException {  //в определении метода используется выражение throws Exception(обр икслючения)
        List<Clients> result = new LinkedList<Clients>();
        try {
            while (rs.next()) {
                Clients client = new Clients();
                client.setIdclients(rs.getInt("id"));
                client.setEmail(rs.getString("email"));
                client.setPhone(rs.getString("phone"));
                client.setFirst_name(rs.getString("firstName"));
                client.setLast_name(rs.getString("lastName"));
                client.setMiddle_name(rs.getString("middleName"));
                result.add(client);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    //////////////PreparedStatement. Кроме собственно выполнения запроса этот класс позволяет подготовить запрос, отформатировать его должным образом
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Clients object) throws PersistException {  //jdbc для вставки
        try {
            statement.setString(1, object.getEmail());
            statement.setString(2, object.getPhone());
            statement.setString(3, object.getFirst_name());
            statement.setString(4, object.getLast_name());
            statement.setString(5, object.getMiddle_name());

        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Clients object) throws PersistException {
        try {
            statement.setInt(1, object.getIdclients());
            statement.setString(2, object.getEmail());
            statement.setString(3, object.getPhone());
            statement.setString(4, object.getFirst_name());
            statement.setString(5, object.getLast_name());
            statement.setString(6, object.getMiddle_name());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, Clients object) throws PersistException {

    }
}
