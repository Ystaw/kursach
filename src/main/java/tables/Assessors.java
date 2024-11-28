package tables;

import dao.Identified;

import java.io.Serializable;

public class Assessors extends Personal implements Serializable, Identified<Integer> {
    private int idAssessors; // ID оценщика
    private String firstName; // Имя
    private String lastName; // Фамилия
    private String email; // Email
    private String phone; // Телефон

    public Assessors() {}

    public Assessors(String firstName, String lastName, String middleName, int idAssessors, String email, String phone) {
        super(firstName, lastName, middleName); // Наследуемые поля из Personal
        this.idAssessors = idAssessors;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    // Геттеры и сеттеры для полей

    public int getIdAssessors() {
        return idAssessors;
    }

    public void setId(int idAssessors) {
        this.idAssessors = idAssessors;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return idAssessors;
    }
}
