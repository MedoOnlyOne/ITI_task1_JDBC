package org.example;

import java.sql.*;

public class User {
    private String username;
    private String phone;
    private String password;
    private Date birthdate;

    User(String username, String password, String phone, Date birthdate){
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.birthdate = birthdate;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getPhone(){
        return this.phone;
    }

    public Date getBirthdate(){
        return this.birthdate;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
