package interfaces;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public interface IUser extends IId {

    enum Role {
        Admin,
        Customer;
    }


    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);

    Role getRole();

    void setRole(Role role);

}
