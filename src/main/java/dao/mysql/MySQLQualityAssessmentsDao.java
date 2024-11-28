package dao.mysql;

import tables.QualityAssessments;
import dao.AbstractJDBCDao;
import dao.PersistException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

public class MySQLQualityAssessmentsDao extends AbstractJDBCDao<QualityAssessments, Integer> {

    public MySQLQualityAssessmentsDao(Connection connection) {
        super(connection);
    }

    @Override
    public String getSelectByParams() {
        // Получаем оценку качества по ID продукта
        return "SELECT * FROM quality_assessments WHERE product_id=?";
    }

    @Override
    public String getSelectQuery() {
        // Получаем все записи о качестве
        return "SELECT * FROM quality_assessments";
    }

    @Override
    public String getCreateQuery() {
        // Вставка новой записи о качестве
        return "INSERT INTO quality_assessments (product_id, client_id, assessment_date, quality_score, comments) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public String getUpdateQuery() {
        // Обновление записи о качестве
        return "UPDATE quality_assessments SET product_id=?, client_id=?, assessment_date=?, quality_score=?, comments=? WHERE id=?";
    }

    @Override
    public String getDeleteQuery() {
        // Удаление записи о качестве
        return "DELETE FROM quality_assessments WHERE id=?";
    }

    @Override
    public QualityAssessments create() throws PersistException {
        QualityAssessments quality = new QualityAssessments();
        return persist(quality);  // Сохранение нового объекта
    }

    @Override
    protected List<QualityAssessments> parseResultSet(ResultSet rs) throws PersistException {
        List<QualityAssessments> result = new LinkedList<>();
        try {
            while (rs.next()) {
                QualityAssessments quality = new QualityAssessments();
                quality.setIdQuality(rs.getInt("id"));
                quality.setIdProduct(rs.getInt("product_id"));
                quality.setIdClient(rs.getInt("client_id"));
                quality.setAssessmentDate(rs.getDate("assessment_date"));
                quality.setQualityScore(rs.getInt("quality_score"));
                quality.setComments(rs.getString("comments"));
                result.add(quality);
            }
        } catch (Exception e) {
            throw new PersistException(e);
        }
        return result;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, QualityAssessments object) throws PersistException {
        try {
            statement.setInt(1, object.getIdProduct());
            statement.setInt(2, object.getIdClient());
            statement.setDate(3, object.getAssessmentDate());
            statement.setInt(4, object.getQualityScore());
            statement.setString(5, object.getComments());
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, QualityAssessments object) throws PersistException {
        try {
            statement.setInt(1, object.getIdProduct());
            statement.setInt(2, object.getIdClient());
            statement.setDate(3, object.getAssessmentDate());
            statement.setInt(4, object.getQualityScore());
            statement.setString(5, object.getComments());
            statement.setInt(6, object.getIdQuality());  // Указываем ID для обновления
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }

    @Override
    protected void prepareStatementForSelectByParams(PreparedStatement statement, QualityAssessments object) throws PersistException {
        try {
            statement.setInt(1, object.getIdProduct());  // Фильтруем по ID продукта
        } catch (Exception e) {
            throw new PersistException(e);
        }
    }
}
