package tables;

import dao.Identified;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class QualityAssessments implements Serializable, Identified<Integer> {

    private int idQuality;
    private int idProduct;
    private int idClient;
    private Date assessmentDate;
    private int qualityScore;
    private String comments;

    // Конструкторы
    public QualityAssessments() {}

    public QualityAssessments(int idQuality, int idProduct, int idClient, Date assessmentDate, int qualityScore, String comments) {
        this.idQuality = idQuality;
        this.idProduct = idProduct;
        this.idClient = idClient;
        this.assessmentDate = assessmentDate;
        this.qualityScore = qualityScore;
        this.comments = comments;
    }

    // Геттеры и Сеттеры
    public int getIdQuality() {
        return idQuality;
    }

    public void setIdQuality(int idQuality) {
        this.idQuality = idQuality;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(Date assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public int getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(int qualityScore) {
        if (qualityScore < 0) {
            this.qualityScore = 0;  // если качество меньше 0, устанавливаем 0
        } else if (qualityScore > 100) {
            this.qualityScore = 100;  // если качество больше 100, устанавливаем 100
        } else {
            this.qualityScore = qualityScore;  // в пределах допустимого диапазона
        }
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public double calculateAverageQualityScore(List<QualityAssessments> assessments) {
        if (assessments == null || assessments.isEmpty()) {
            return 0;  // если нет оценок, возвращаем 0
        }

        double totalScore = 0;
        for (QualityAssessments assessment : assessments) {
            totalScore += assessment.getQualityScore();
        }

        return totalScore / assessments.size();  // вычисляем среднее значение
    }

    // Бизнес-функция 2: рекомендации по улучшению качества
    public String recommendImprovement() {
        // Исправленный код для адекватных рекомендаций
        if (this.qualityScore < 50) {
            return "Продукт нуждается в значительном улучшении. Рекомендуется провести основательную проверку и доработку продукта.";
        } else if (this.qualityScore >= 50 && this.qualityScore < 75) {
            return "Продукт имеет среднее качество. Рекомендуется улучшить несколько ключевых аспектов для повышения конкурентоспособности.";
        } else {
            return "Продукт имеет высокое качество. Рекомендуется поддерживать текущие практики и следить за качеством.";
        }
    }

    @Override
    public Integer getId() {
        return idQuality;
    }

    @Override
    public String toString() {
        return "Оценка качества{" +
                "idКачества=" + idQuality +
                ", idПродукта=" + idProduct +
                ", idОценщика=" + idClient +
                ", Дата оценки=" + assessmentDate +
                ", Оценка качества=" + qualityScore +
                ", Комментарии='" + comments + '\'' +
                '}';
    }
}
