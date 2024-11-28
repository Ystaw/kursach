package tables;

import dao.Identified;

import java.io.Serializable;
import java.util.List;

public class Clients extends Personal implements Serializable, Identified<Integer> {
    private static final long serialVersionUID = 1029398801879442863L;
    private int idclients;
    private String email; // Email
    private String phone; // Телефон

    public Clients(){}

    public Clients(String first_name, String last_name, String middle_name, int idclients,
                   String email,  String phone) {
        super(first_name, last_name, middle_name);
        this.idclients = idclients;
        this.email = email;
        //this.listOut = listOut;
        this.phone = phone;
    }

    public String getFirstname() {
        return first_name;
    } //получить

    public void setFirstname(String first_name) {
        this.first_name = first_name;
    }

    public int getIdclients() {
        return idclients;
    } //получить

    public void setIdclients(int idclients) {
        this.idclients = idclients;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
