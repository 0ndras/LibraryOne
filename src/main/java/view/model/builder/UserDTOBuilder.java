package view.model.builder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Role;
import view.model.UserDTO;

public class UserDTOBuilder {
    private UserDTO userDTO;

    public UserDTOBuilder() {
        userDTO = new UserDTO();
    }

    public UserDTOBuilder setUsername(String username) {
        userDTO.setUsername(username);
        return this;
    }

    public UserDTOBuilder setPassword(String password) {
        userDTO.setPassword(password);
        return this;
    }

    public UserDTOBuilder setRoles(ObservableList<Role> roles) {
        userDTO.setRoles(roles);
        return this;
    }

    public UserDTOBuilder addRole(Role role) {
        if (userDTO.getRoles() == null) {
            userDTO.setRoles(FXCollections.observableArrayList());
        }
        userDTO.addRole(role);
        return this;
    }

    public UserDTO build() {
        return userDTO;
    }
}
