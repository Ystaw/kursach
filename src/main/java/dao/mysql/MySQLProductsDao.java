package dao.mysql;

import dao.AbstractJDBCDao;
import dao.PersistException;
import tables.Products;
import tables.StatusProducts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class MySQLProductsDao extends AbstractJDBCDao<Products, Integer> {

    @Override
    public String getSelectByParams() {
        // Возвращает запрос на выборку продуктов по статусу
        return "SELECT * FROM bd_mall.products WHERE status=?";
    }

    @Override
    public String getSelectQuery() {
        // Возвращает запрос на выборку всех продуктов
        return "SELECT id, name, category, description, price, status FROM bd_mall.products";
    }

    @Override
    public String getCreateQuery() {
        // Запрос для вставки нового продукта
        return "INSERT INTO bd_mall.products (name, category, description, price, status) VALUES (?, ?, ?, ?, ?);";
    }

    @Override
    public String getUpdateQuery() {
        // Запрос для обновления статуса продукта
        return "UPDATE bd_mall.products SET status = 'UNAVAILABLE' WHERE id = ?;";
    }

    @Override
    public String getDeleteQuery() {
        // Запрос для удаления продукта
        return "DELETE FROM bd_mall.products WHERE id = ?;";
    }

    @Override
    public Products create() throws PersistException {
        Products g = new Products();
        return persist(g);
    }

    public MySQLProductsDao(Connection connection) {
        super(connection);
    }

    @Override
    protected List<Products> parseResultSet(ResultSet rs) throws PersistException {
        List<Products> result = new LinkedList<Products>();
        try {
            while (rs.next()) {
                Products product = new Products();
                product.setIdProduct(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setCategory(rs.getString("category"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getFloat("price"));
                product.setStatus(StatusProducts.valueOf(rs.getString("status")));
                result.add(product);
            }
        } catch (SQLException e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Products object) throws PersistException {
        try {
            statement.setString(1, object.getName());
            statement.setString(2, object.getCategory());
            statement.setString(3, object.getDescription());
            statement.setFloat(4, object.getPrice());
            statement.setString(5, String.valueOf(object.getStatus()));
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Products object) throws PersistException {
        try {
            statement.setInt(1, object.getId());
        } catch (SQLException e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, Products object) throws PersistException {
        try {
            statement.setString(1, String.valueOf(object.getStatus())); //для связи значения с первым знаком подстановки применяется метод preparedStatement.setString
        } catch (SQLException e) {
            e.printStackTrace(); //Это метод для Exception экземпляров, который печатает трассировку стека экземпляра System.err, очень полезный инструмент для диагностики исключений
        }
    }
}
