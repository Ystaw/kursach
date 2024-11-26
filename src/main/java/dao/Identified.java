package dao;

import java.io.Serializable;

/** Возвращает идентификатор объекта */

public interface Identified<PK extends Serializable> { // возвращает идентификатор объекта
    public PK getId();
}
