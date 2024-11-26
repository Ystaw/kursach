package dao.mysql;

import dao.AbstractJDBCDao;
import dao.PersistException;
import tables.Outlets;
import tables.StatusOutlets;


import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLOutletsDao extends AbstractJDBCDao<Outlets, Integer>  {

    @Override  //указывает, что далее мы собираемся переопределять метод базового класса
    public String getSelectByParams() {  //выбираем параметры
        return "SELECT * FROM bd_mall.outlets WHERE status=?";
    }

    @Override
    public String getSelectQuery() {  //Возвращает запрос на выборку данных, используя заданное пользовательское подключение
        return "SELECT id, floor, square, conditioner, cost, status FROM bd_mall.outlets";
    }

    @Override
    public String getCreateQuery() {  //делаем запрос
        return "INSERT INTO bd_mall.outlets (floor, square, conditioner, cost, status) \n" +
                "VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {  //обновляем
       // return "UPDATE bd_mall.outlets SET floor= ? square = ? conditioner = ? cost = ? status = ? WHERE id= ?;";
        return "UPDATE bd_mall.outlets SET status = 'ARENDED' WHERE id= ?;";
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM bd_mall.outlets WHERE id= ?;";
    }

    @Override
    public Outlets create() throws PersistException {
        Outlets g = new Outlets();
        return persist(g);
    }

    public MySQLOutletsDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Outlets> parseResultSet(ResultSet rs) throws PersistException {
        List<Outlets> result = new LinkedList<Outlets>();
        try {
            while (rs.next()) {
                Outlets outlet = new Outlets();
                outlet.setIdOutlets(rs.getInt("id"));
                outlet.setFloor(rs.getInt("floor"));
                outlet.setSquare(rs.getFloat("square"));
                outlet.setConditioner(rs.getString("conditioner"));
                outlet.setCost(rs.getFloat("cost"));
                outlet.setStatus(StatusOutlets.valueOf(rs.getString("status")));
                result.add(outlet);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }
    //////////////PreparedStatement. интерфейс может принимать параметры во время работы программы Кроме собственно выполнения запроса этот класс позволяет подготовить запрос, отформатировать его должным образом
    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Outlets object) throws PersistException {
        try {
            statement.setInt(1, object.getFloor());
            statement.setFloat(2, object.getSquare());
            statement.setString(3, object.getConditioner());
            statement.setFloat(4, object.getCost());
            statement.setString(5, String.valueOf(object.getStatus()));
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Outlets object) throws PersistException {
        try {
            statement.setInt(1, object.getIdOutlets());
            /*
            statement.setInt(1, object.getIdOutlets());
            statement.setInt(2, object.getFloor());
            statement.setFloat(3, object.getSquare());
            statement.setString(4, object.getConditioner());
            statement.setFloat(5, object.getCost());
            statement.setString(6, String.valueOf(object.getStatus()));
            */
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, Outlets object) throws PersistException {
        try {
            statement.setString(1, String.valueOf(object.getStatus())); //для связи значения с первым знаком подстановки применяется метод preparedStatement.setString
        } catch (SQLException e) {
            e.printStackTrace(); //Это метод для Exception экземпляров, который печатает трассировку стека экземпляра System.err, очень полезный инструмент для диагностики исключений
        }
    }
}
