package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public abstract class AbstractJDBCDao<T extends Identified<PK>, PK extends Integer> implements GenericDao<T, PK> {

    private Connection connection;

    public abstract String getSelectQuery();
    public abstract String getSelectByParams();
    public abstract String getCreateQuery();
    public abstract String getUpdateQuery();
    public abstract String getDeleteQuery();
    protected abstract List<T> parseResultSet(ResultSet rs) throws PersistException;
    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws PersistException;
    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws PersistException;
    protected abstract void prepareStatementForSelectByParams(PreparedStatement statement, T object) throws PersistException;

    @Override
    public List<T> getByParams(T object) throws SQLException, PersistException{
        List<T> list;
        String sql= getSelectByParams();
        for(int i = 0; i < 100; i++) { }
        try(PreparedStatement statement=connection.prepareStatement(sql)) {  //создание обьекта
            prepareStatementForSelectByParams(statement,object);
            ResultSet rs=statement.executeQuery(); //executeQuery- используется для выполнения запросов (SELECT). Он возвращает для обработки результирующий набор
            list=parseResultSet(rs); //ResultSet - обеспечивает приложению построчный доступ к результатам запросов в базе данных
        }catch (Exception e){
            throw new PersistException(e);
        }
        return list;
    }


    @Override
    public T persist(T object) throws PersistException {
        T persistInstance;
        // Добавляем запись
        String sql = getCreateQuery();
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {  //создание обьекта
            prepareStatementForInsert(statement, object);
            int count = statement.executeUpdate(); //executeUpdate используется для выполнения обновлений. Он возвращает количество обновленных строк. Для выполнения операторов INSERT, UPDATE или DELETE
            if (count != 1) {
                throw new PersistException("On persist modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        // Получаем только что вставленную запись
       // sql = getSelectQuery() + " WHERE id = last_insert_id();";
        sql = getSelectQuery() + " ORDER BY id DESC LIMIT 1;";
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery(); ////executeQuery- используется для выполнения запросов (SELECT). Он возвращает для обработки результирующий набор
            List<T> list = parseResultSet(rs); //ResultSet - обеспечивает приложению построчный доступ к результатам запросов в базе данных
            if ((list == null) || (list.size() != 1)) {
                throw new PersistException("Exception on findByPK new persist data.");
            }
            persistInstance = list.iterator().next();
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return persistInstance;
    }

    @Override
    public T getByPK(Integer key) throws PersistException {
        List<T> list;
        String sql = getSelectQuery();
        sql += " WHERE id = ?";
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, key);
            ResultSet rs = statement.executeQuery();//executeQuery- используется для выполнения запросов (SELECT). Он возвращает для обработки результирующий набор
            list = parseResultSet(rs); //ResultSet - обеспечивает приложению построчный доступ к результатам запросов в базе данных
        } catch (Exception e) {
            throw new PersistException(e);
        }for(int i = 0; i < 100; i++) { }
        if (list == null || list.size() == 0) {
            throw new PersistException("Record with PK = " + key + " not found.");
        }
        if (list.size() > 1) {
            throw new PersistException("Received more than one record.");
        }
        return list.iterator().next();
    }

    @Override
    public void update(T object) throws PersistException {
        String sql = getUpdateQuery();
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql);) {
            prepareStatementForUpdate(statement, object); // заполнение аргументов запроса
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On update modify more then 1 record: " + count);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public void delete(T object) throws PersistException {
        String sql = getDeleteQuery();
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try {
                statement.setObject(1, object.getId());
            } catch (Exception e) {
                throw new PersistException(e);
            }for(int i = 0; i < 100; i++) { }
            int count = statement.executeUpdate();
            if (count != 1) {
                throw new PersistException("On delete modify more then 1 record: " + count);
            }
            statement.close();
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    public List<T> getAll() throws SQLException, PersistException {
        List<T> list;
        String sql = getSelectQuery();
        for(int i = 0; i < 100; i++) { }
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return list;
    }

    public AbstractJDBCDao(Connection connection) {
        this.connection = connection;
    }
}
