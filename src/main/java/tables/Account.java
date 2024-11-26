package tables;

import dao.Identified;

import java.io.Serializable;

public class Account extends Personal implements Serializable, Identified<Integer> {
    private String login;
    private String password;
    private Role role;
    private int idusers;
    private StatusUser status;

    public Account (){}

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Account(String first_name, String last_name, String middle_name, String login,
                   String password, Role role, StatusUser status) {
        super(first_name, last_name, middle_name);
        this.login = login;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    public Account(String first_name, String last_name, String middle_name, String login,
                   String password, Role role, int idusers, StatusUser status) {
        super(first_name, last_name, middle_name);
        this.login = login;
        this.password = password;
        this.role = role;
        this.idusers = idusers;
        this.status = status;
    }

    public StatusUser getStatus() {
        return status;
    }

    public void setStatus(StatusUser status) {
        this.status = status;
    }

    public int getIdusers() {
        return idusers;
    }

    public void setIdusers(int idusers) {
        this.idusers = idusers;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {  // equals сравнивает строку с указанным объектом
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (idusers != account.idusers) return false;
        if (!login.equals(account.login)) return false;
        if (!password.equals(account.password)) return false;
        if (role != account.role) return false;
        return status == account.status;
    }

    @Override
    public int hashCode() {  //это битовая строка фиксированной длины, полученная из массива произвольной длины
        int result = login.hashCode();
        result = 31 * result + password.hashCode(); //это битовая строка фиксированной длины, полученная из массива password
        result = 31 * result + role.hashCode();
        result = 31 * result + idusers;
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Аккаунты системы:" +
                "Логин='" + login + '\'' +
                ", Пароль='" + password + '\'' +
                ", Роль=" + role +
                ", idusers=" + idusers +
                ", Статус=" + status +
                ", Имя='" + first_name + '\'' +
                ", Фамилия='" + last_name + '\'' +
                ", Отчество='" + middle_name + '\'' +
                '.';
    }

    @Override
    public Integer getId() {
        return idusers;
    }
}
