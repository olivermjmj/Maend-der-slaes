package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final DBManager database = new DBManager();

    @FXML
    protected void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        //Ensures that the database exists, and if not creates it.
        database.ensureDatabaseExists();

        if (!database.doesUserExist(username, password)) {
            database.addUserToDB(username, password);
            System.out.println("Welcome: " + username);
        } else {
            System.out.println("A user with the name: " + username + ". Already exists, try another name");
        }
    }

    @FXML
    protected void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        database.ensureDatabaseExists();

    }

    @FXML
    protected void guestLogin() {
        // Implementer gæstelogin logik her
        System.out.println("Gæste login aktiveret");
    }
}