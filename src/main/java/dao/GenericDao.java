package dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface GenericDao<T extends Identified<PK>, PK extends Serializable> {

    /** Создает новую запись и соответствующий ей объект */
    public T create() throws PersistException;

    /** Создает новую запись, соответствующую объекту object */
    public T persist(T object)  throws PersistException;  //создании записи об объекте

    /** Возвращает объект соответствующий записи с первичным ключом key или null */
    public T getByPK(PK key) throws PersistException;

    /** Сохраняет состояние объекта group в базе данных */
    public void update(T object) throws PersistException;

    /** Удаляет запись об объекте из базы данных */
    public void delete(T object) throws PersistException;

    /** Возвращает список объектов соответствующих всем записям в базе данных */
    public List<T> getAll() throws PersistException, SQLException;

    List<T> getByParams(T object) throws SQLException,PersistException;

}
