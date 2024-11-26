package dao;

public interface DaoFactory<Context> {

    public interface DaoCreator<Context> {
        public GenericDao create(Context context);  // создание обьекта
    }

    /** Возвращает подключение к базе данных */
    public Context getContext() throws PersistException;

    /** Возвращает объект для управления персистентным состоянием объекта */
    public GenericDao getDao(Context context, Class dtoClass) throws PersistException;
}
