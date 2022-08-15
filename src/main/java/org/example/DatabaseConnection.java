package org.example;

import com.zaxxer.hikari.*;
import java.sql.*;

public class DatabaseConnection {
    private  HikariConfig config = new HikariConfig();
    private  HikariDataSource ds;

    DatabaseConnection(String databaseUrl, String user, String pass) {
        config.setJdbcUrl(databaseUrl);
        config.setUsername(user);
        config.setPassword(pass);
        ds = new HikariDataSource(config);
    }

    public Connection getConnection() throws Exception {
        return ds.getConnection();
    }
}