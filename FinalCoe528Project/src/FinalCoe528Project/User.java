package FinalCoe528Project;

import java.io.File;
import javafx.beans.property.SimpleStringProperty;

abstract class User {

    private SimpleStringProperty password;
    private SimpleStringProperty username;

    public String getPassword() {
        return password.get();
    }

    public abstract void read(File f);

    public abstract void write(File f);

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username = new SimpleStringProperty(username);
    }

}
