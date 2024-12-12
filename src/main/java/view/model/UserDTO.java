package view.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Role;

public class UserDTO {

    // Username property
    private StringProperty username;

    public void setUsername(String username) {
        usernameProperty().set(username);
    }

    public String getUsername() {
        return usernameProperty().get();
    }

    public StringProperty usernameProperty() {
        if (username == null) {
            username = new SimpleStringProperty(this, "username");
        }
        return username;
    }

    private StringProperty password;

    public void setPassword(String password) {
        passwordProperty().set(password);
    }

    public String getPassword() {
        return passwordProperty().get();
    }

    public StringProperty passwordProperty() {
        if (password == null) {
            password = new SimpleStringProperty(this, "password");
        }
        return password;
    }

    private StringProperty hashedPassword;

    public void setHashedPassword(String hashedPassword) {
        hashedPasswordProperty().set(hashedPassword);
    }

    public String getHashedPassword() {
        return hashedPasswordProperty().get();
    }

    public StringProperty hashedPasswordProperty() {
        if (hashedPassword == null) {
            hashedPassword = new SimpleStringProperty(this, "hashedPassword");
        }
        return hashedPassword;
    }

    private ObservableList<Role> roles;

    public void setRoles(ObservableList<Role> roles) {
        this.roles = roles;
    }

    public ObservableList<Role> getRoles() {
        if (roles == null) {
            roles = FXCollections.observableArrayList();
        }
        return roles;
    }

    public void addRole(Role role) {
        getRoles().add(role);
    }

    public void removeRole(Role role) {
        getRoles().remove(role);
    }
}
