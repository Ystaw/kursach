package tables;

import dao.Identified;

import java.io.Serializable;

public class Products implements Serializable, Identified<Integer> {
    private int idProduct;
    private String name;
    private String category;
    private String description;
    private float price;
    private StatusProducts status;

    // Конструкторы
    public Products() {
    }

    public Products(int idProduct, String name, String category, String description, float price, StatusProducts status) {
        this.idProduct = idProduct;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.status = status;
    }

    // Геттеры и сеттеры
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public StatusProducts getStatus() {
        return status;
    }

    public void setStatus(StatusProducts status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return idProduct;
    }

    @Override
    public String toString() {
        return "Продукт: " +
                "idПродукта=" + idProduct +
                ", Название='" + name + '\'' +
                ", Категория='" + category + '\'' +
                ", Описание='" + description + '\'' +
                ", Цена=" + price +
                ", Статус='" + status + '\'';
    }
}
