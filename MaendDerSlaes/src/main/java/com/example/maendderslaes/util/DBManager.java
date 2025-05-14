package com.example.maendderslaes.util;

import java.io.File;
import java.sql.*;

public class DBManager {
    private Connection conn;
    private String dbPath = "data/database/userData.db";
    private String url = "jdbc:sqlite:" + dbPath;

    public boolean doesUserExist(String name, String password) {

        String sql = "SELECT (name, password) FROM Users";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String DBName = rs.getString("name");
                String DBPassword = rs.getString("password");

                if(DBName.equals(name) && DBPassword.equals(password)) {

                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("User might already exist: " + e.getMessage());
        }

        return false;
    }

    public void addUserToDB(String name, String password) {

        if((name.length() > 2 && password.length() > 2) && (name.length() < 13 && password.length() < 13)) {
            String sql = "INSERT INTO Users (name, password) VALUES (?, ?)";

            try {

                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setString(1, name);
                stmt.setString(2, password);
                stmt.executeUpdate();

            } catch (SQLException e) {
                System.out.println("Failed to create user because: " + e.getMessage());
            }
        } else {
            System.out.println("Username and password length must be: 3 - 12 characters");
        }
    }

    //creates the database if not found
    public void ensureDatabaseExists() {
        // Insures that the folder exists
        File dbFile = new File(dbPath);
        File folder = dbFile.getParentFile();

        if (!folder.exists()) {
            folder.mkdirs();
        }

        connect(url);

        // Creates the table if not found
        try (Statement stmt = conn.createStatement()) {
            String sql = """
                     CREATE TABLE IF NOT EXISTS Users(
                         id INTEGER PRIMARY KEY AUTOINCREMENT,
                         name VARCHAR(12) NOT NULL UNIQUE,
                         password VARCHAR(12) NOT NULL
                     );
                     """;
            stmt.execute(sql);
            System.out.println("Table has been found/Created");

        } catch (Exception e) {
            System.out.println("Error when creating table: " + e.getMessage());
        }
    }

    private void connect(String url) {

        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
