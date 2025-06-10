package Model;

import java.io.Serializable;

public class Account implements Serializable {
    private String username;
    private String password;
    private AccessLevel role;
    private String location; // "HCM" hoặc "HN"

    public Account(String username, String password, AccessLevel role, String location) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.location = location;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AccessLevel getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(AccessLevel role) {
        this.role = role;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}