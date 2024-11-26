package tables;

import java.io.Serializable;

public abstract class Personal implements Serializable {
    protected String first_name;
    protected String last_name;
    protected String middle_name;

    public Personal() {
    }

    public Personal(String first_name, String last_name, String middle_name) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }
}
