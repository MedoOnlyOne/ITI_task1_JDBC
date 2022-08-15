package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private final String dataBaseUrl;
    private final String user;
    private final String pass;

    DatabaseConnection(String dataBaseUrl, String user, String pass){
        this.dataBaseUrl = dataBaseUrl;
        this.user = user;
        this.pass = pass;
    }

    public Connection getConnection() throws Exception{
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.dataBaseUrl, this.user, this.pass);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
        } finally {
            connection.close();
        }
        return connection;
    }
}