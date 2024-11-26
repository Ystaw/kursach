package tables;

import dao.Identified;

import java.io.Serializable;

public class Outlets implements Serializable,Identified<Integer> {
    private int idOutlets;
    private int floor;
    private float square;
    private String conditioner;
    private float cost;
    private StatusOutlets status;



    public Outlets() {
    }

    public Outlets(int idOutlets, int floor, float square, String conditioner, float cost, StatusOutlets status) {
        this.idOutlets = idOutlets;
        this.floor = floor;
        this.square = square;
        this.conditioner = conditioner;
        this.cost = cost;
        this.status = status;
    }


    public int getIdOutlets() {
        return idOutlets;
    } //получить

    public void setIdOutlets(int idOutlets) {
        this.idOutlets = idOutlets;
    } //установить

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public float getSquare() {
        return square;
    }

    public void setSquare(float square) {
        this.square = square;
    }

    public String getConditioner() {
        return conditioner;
    }

    public void setConditioner(String conditioner) {
        this.conditioner = conditioner;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public StatusOutlets getStatus() {
        return status;
    }

    public void setStatus(StatusOutlets status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return idOutlets;
    }


    @Override
    public String toString() {
        return "Свободные помещения:" +
                "idПомещения=" + idOutlets +
                ", Этаж=" + floor +
                ", Площадь=" + square +
                ", Наличие кондиционера=" + conditioner +
                ", Стоимость=" + cost +
                ", Статус помещения=" + status +
                '.';
    }
}
