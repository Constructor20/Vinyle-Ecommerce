package com.vinylstore.models;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private boolean isAdmin;

    public User() {}

    public User(int id, String email, String password, String firstName, String lastName) {
        this.id = id;
        this.email = email;
        this.password = hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = BCrypt.hashpw(password, BCrypt.gensalt()); }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean admin) { isAdmin = admin; }

    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
