package com.example.maendderslaes.util;

import com.example.maendderslaes.Character;
import com.example.maendderslaes.Player;

import java.io.File;
import java.sql.*;

public class DBManager {
    private static DBManager instance;
    private Connection conn;
    private String dbPath = "data/database/userData.db";
    private String url = "jdbc:sqlite:" + dbPath;
    private static String activeUser;

    public DBManager() {
        connect(url);
        ensureDatabaseExists();
    }

    // By ChatGPT: Will let us use the same database across multiple classes
    public static DBManager getInstance() {
        if(instance == null) {
            instance = new DBManager();
        }
        return instance;
    }


    public boolean doesUserExist(String name, String password) {

        String sql = "SELECT name, password FROM Users";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()) {

                String DBName = rs.getString("name");
                String DBPassword = rs.getString("password");

                if(DBName.equals(name) && DBPassword.equals(password)) {

                    this.activeUser = name;
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
            String sql = "INSERT INTO Users (name, password, level, weapon, strength, defence, speed) VALUES (?, ?, ?, ?, ?, ?, ?)";

            int startLevel = 1;
            try(PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, name);
                stmt.setString(2, password);
                stmt.setInt(3, startLevel);
                stmt.setString(4, "NONE");  //weapon
                stmt.setInt(5, 1);          //strength
                stmt.setInt(6, 1);          //defence
                stmt.setInt(7, 1);          //speed
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
                         password VARCHAR(12) NOT NULL,
                         level INTEGER NOT NULL,
                         health INTEGER DEFAULT 100,
                         maxHealth INTEGER DEFAULT 100,
                         money INTEGER DEFAULT 50,
                         weapon VARCHAR(24) DEFAULT 'NONE',
                         strength INTEGER NOT NULL,
                         defence INTEGER NOT NULL,
                         speed INTEGER NOT NULL
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

    public void saveUserData(int level, int health, String weapon, int money, int strength, int defence, int maxHealth, int speed) {
        if(activeUser == null) {
            System.out.println("No user is logged in.");
            return;
        }

        String sql = "UPDATE Users SET level = ?, health = ?, weapon = ?, money = ?, strength = ?, defence = ?, maxHealth = ?, speed = ? WHERE name = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setInt(1, level);
            stmt.setInt(2, health);
            stmt.setString(3, weapon);
            stmt.setInt(4, money);
            stmt.setInt(5, strength);
            stmt.setInt(6, defence);
            stmt.setInt(7, maxHealth);
            stmt.setInt(8, speed);
            stmt.setString(9, activeUser);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to update user because: " + e.getMessage());
        }
    }


    public void saveUserGold(int moneyAmount) {

        if(activeUser == null) {
            System.out.println("No user is logged in.");
            return;
        }

        String sql = "UPDATE Users SET money = ? WHERE name = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, moneyAmount);
        } catch (SQLException e) {
            System.out.println("Failed to save users gold" + e.getMessage());
        }
    }

    public int getUserLevel() {

        String sql = "SELECT level FROM Users WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("level");
        } catch (SQLException e) {
            System.out.println("Couldn't get column money, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserHP() {

        String sql = "SELECT health FROM Users WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("health");
        } catch (SQLException e) {
            System.out.println("Couldn't get column health, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserMaxHP() {

        String sql = "SELECT maxHealth FROM Users WHERE name = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();

            return rs.getInt("maxHealth");
        } catch (SQLException e) {
            System.out.println("Couldn't get column maxHP, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserGold() {

        String sql = "SELECT money FROM Users WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("money");
        } catch (SQLException e) {
            System.out.println("Couldn't get column money, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserStrength() {

        String sql = "SELECT strength FROM Users WHERE name = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("strength");
        } catch (SQLException e) {
            System.out.println("Couldn't get column strength, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserDefence() {

        String sql = "SELECT defence FROM Users WHERE name = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("defence");
        } catch (SQLException e) {
            System.out.println("Couldn't get column defence, because: " + e.getMessage());
        }

        return 0;
    }

    public int getUserSpeed() {

        String sql = "SELECT speed FROM Users WHERE name = ?";

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt("speed");
        } catch (SQLException e) {
            System.out.println("Couldn't get column speed, because: " + e.getMessage());
        }

        return 0;
    }

    public String getUserWeapon() {

        String sql = "SELECT weapon FROM Users WHERE name = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, activeUser);
            ResultSet rs = stmt.executeQuery();
            return rs.getString("weapon");
        } catch (SQLException e) {
            System.out.println("Couldn't get column weapon, because: " + e.getMessage());
        }

        return null;
    }

    public void saveUserWeapon(String weapon) {
        if(activeUser == null) {
            System.out.println("No user is logged in.");
            return;
        }

        String sql = "UPDATE Users SET weapon = ? WHERE name = ?";

        try {

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, weapon);
            stmt.setString(2, activeUser);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to update user weapon because: " + e.getMessage());
        }
    }

    public String getUserName() {

        return activeUser;
    }

    public static void setActiveUser(String user) {
        activeUser = user;
    }

}