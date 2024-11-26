package tables;

import dao.Identified;

import java.io.Serializable;
import java.util.List;

public class Clients extends Personal implements Serializable, Identified<Integer> {
    private int idclients;
    private String company;
    private String details;//реквизиты
    private String address;
    private String phone;

    public Clients(){}

    public Clients(String first_name, String last_name, String middle_name, int idclients,
                   String company, String details, String address, List<Outlets> listOut, String phone) {
        super(first_name, last_name, middle_name);
        this.idclients = idclients;
        this.company = company;
        this.details = details;
        this.address = address;
        this.phone = phone;
    }



    public int getIdclients() {
        return idclients;
    }

    public void setIdclients(int idclients) {
        this.idclients = idclients;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public Integer getId() {
        return idclients;
    }
}
