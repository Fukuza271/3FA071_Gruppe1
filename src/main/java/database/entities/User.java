package database.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
import database.daos.CustomerDao;
import interfaces.ICustomer;
import interfaces.IUser;

import java.util.UUID;

@JsonTypeName(value = "user")
public class User implements IUser, Cloneable {
    private UUID id;
    private String username;
    private String password;
    private Role role;
    private Customer customer;

    public User(UUID id, String username, String password, Role role, Customer customer) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
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

    public ICustomer getCustomer() {
        if (this.customer == null) {
            return null;
        }

        return new CustomerDao().findById(customer.getId());
    }

    public void setCustomer(ICustomer customer) {
        this.customer = (Customer) customer;
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
