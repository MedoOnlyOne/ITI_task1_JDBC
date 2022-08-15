package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String databaseUrl = "jdbc:postgresql://localhost:5432/postgres";
        String databaseUsername = "assignment_1";
        String databasePass = "test";
        DatabaseConnection connectionPool = new DatabaseConnection(databaseUrl, databaseUsername, databasePass);
        Connection con = connectionPool.getConnection();

        try{
            // add user
            User user1 = new User("User1", "123456789", "+201155282792", Date.valueOf("1993-08-15"));
            // User.addUser(user1, con);

            // get all users
            List<User> users = getAllUser(con);
            for(User user: users){
                System.out.println("Username: " + user.getUsername() + ", Password: " + user.getPassword() + ", Birthdate: " + user.getBirthdate() + ", Phone: " + user.getPhone());
            }

            // get user by phone (unique)
            getUserByPhone("+201155282790", con).ifPresent(opUser -> System.out.println(opUser.getUsername()));

            // get user by id (unique)
            System.out.println(getUser(1, con).getUsername());

            User user2 = new User("User11", "123456789", "+201255282795", Date.valueOf("1993-05-15"));

            // update user
            // updateUser(6, user2, con);

            // delete user
            // deleteUser(5, con);

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            System.out.println(e.getStackTrace());
            throw new Exception(e);
        }
    }

    private static User addUser(User user, Connection con) throws Exception {
        try{
            String query = "INSERT INTO iti.User(username, password, phone, birthdate) VALUES (?, ?, ?, ?)";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getPhone());
            st.setDate(4, user.getBirthdate());
            st.executeUpdate();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
        return user;
    }
    private static User updateUser(int id ,User user, Connection con) throws Exception {
        try{
            String query = "UPDATE iti.User SET username = ?, password = ?, phone = ?, birthdate = ? WHERE id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, user.getPassword());
            st.setString(3, user.getPhone());
            st.setDate(4, user.getBirthdate());
            st.setInt(5, id);
            st.executeUpdate();
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
        return user;
    }
    private static boolean deleteUser(int id, Connection con) throws Exception {
        try{
            String query = "DELETE FROM iti.User WHERE id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            int rowCount= st.executeUpdate();
            if (rowCount != 0){
                return true;
            } else {
                return false;
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
    }
    private static User getUser(int id, Connection con) throws Exception {
        // returns 1 user as id is unique
        try{
            String query = "SELECT * FROM iti.User WHERE id = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            User u = null;
            while(resultSet.next()){
                u = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("phone"), resultSet.getDate("birthdate"));
            }
            return u;
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
    }
    private static List<User> getAllUser(Connection con) throws Exception {
        List<User> users= new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String query = "SELECT * FROM iti.User";
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                String uName = resultSet.getString("username");
                String pass = resultSet.getString("password");
                String phoneNum = resultSet.getString("phone");
                Date userBirthdate = resultSet.getDate("birthdate");
                users.add(new User(uName, pass, phoneNum, userBirthdate));
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
        return users;
    }
    private static Optional<User> getUserByPhone(String phone, Connection con) throws Exception {
        // returns one user as phone is a unique value
        try{
            String query = "SELECT * FROM iti.User WHERE phone = ?";
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, phone);
            ResultSet resultSet = st.executeQuery();
            String uname = null;
            String pass = null;
            String phoneNum = null;
            Date BD = null;
            while(resultSet.next()){
                uname = resultSet.getString("username");
                pass = resultSet.getString("password");
                phoneNum = resultSet.getString("phone");
                BD = resultSet.getDate("birthdate");
            }
            User u = null;
            if(uname != null && pass != null && phoneNum != null && BD != null){
                u = new User(uname, pass, phoneNum, BD);
            }
            return Optional.ofNullable(u);
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage() + "!!!!");
            throw new Exception(e);
        }
    }
}

/*
Table definition

CREATE SCHEMA iti;

CREATE TABLE iti.User(
ID INT PRIMARY KEY UNIQUE AUTO_INCREMENT NOT NULL,
Username VARCHAR(50) NOT NULL,
Password VARCHAR(150) NOT NULL,
BirthDate DATE NOT NULL,
Phone VARCHAR(15) UNIQUE NOT NULL
);
* */