package tables;

import dao.Identified;

import java.io.Serializable;
import java.sql.Date;

public class Rent implements Serializable, Identified<Integer> {
    private int idRent;
    private int idOutlet;
    private int idClient;
    private Date start_date;
    private Date finish_date;
    private double payment;

    public Rent() {
    }

    public Rent(int idRent, int idOutlet, int idClient, Date start_date, Date finish_date, double payment) {
        this.idRent = idRent;
        this.idClient = idClient;
        this.idOutlet = idOutlet;
        this.start_date = start_date;
        this.finish_date = finish_date;
        this.payment = payment;
    }

    public Rent(int idRent, int idOutlet, int idClient, Date start_date, Date finish_date, Outlets outlet) {
        this.idRent = idRent;
        this.idClient = idClient;
        this.idOutlet = idOutlet;
        this.start_date = start_date;
        this.finish_date = finish_date;
        Long days = (finish_date.getTime() - start_date.getTime())/8640000;  //функция времени
        this.payment = days * outlet.getCost();
    }

    public int getIdRent() {
        return idRent;
    }

    public void setIdRent(int idRent) {
        this.idRent = idRent;
    }

    public int getIdOutlet() {
        return idOutlet;
    }

    public void setIdOutlet(int idOutlet) {
        this.idOutlet = idOutlet;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getFinish_date() {
        return finish_date;
    }

    public void setFinish_date(Date finish_date) {
        this.finish_date = finish_date;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public void setPayment(double costDay, int i) {  //бизнесс-функции
        long days = (this.finish_date.getTime() - this.start_date.getTime())/86400000;
        this.payment = days * costDay;
    }

    public void setPayment(Outlets outlet) {
        Long days = (this.finish_date.getTime() - this.start_date.getTime())/86400000;
        this.payment = days * outlet.getCost();
    }

    @Override
    public Integer getId() {
        return idRent;
    }

    public void setStart_date(String text) {
        this.start_date= Date.valueOf(text);
    }
    public void setFinish_date(String text) {
        this.finish_date= Date.valueOf(text);
    }

    @Override
    public String toString() {
        return "Договор:" +
                "idДоговора=" + idRent +
                ", idПомещения=" + idOutlet +
                ", idКлиента=" + idClient +
                ", Дата начала аренды=" + start_date +
                ", Дата конца аренды=" + finish_date +
                ", Прибыль=" + payment +
                '.';
    }
}
