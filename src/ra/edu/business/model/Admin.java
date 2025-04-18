package ra.edu.business.model;

import ra.edu.utils.IApp;

import java.util.Scanner;

public class Admin implements IApp {
    private int id;
    private String username;
    private String password;
    private boolean status;
    private Role role;

    public enum Role {
        ADMIN, MODERATOR;

        public static Role fromString(String value) {
            return Role.valueOf(value.toUpperCase());
        }
    }

    public Admin() {
    }

    public Admin(int id, String username, String password, boolean status, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public void inputData(Scanner sc) {

    }
}
