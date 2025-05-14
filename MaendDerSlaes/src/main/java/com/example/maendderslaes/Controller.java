package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;



import java.io.IOException;

public class Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final DBManager database = new DBManager();

    private SoundManager sound = new SoundManager();

    @FXML
    protected void register() {
        sound.playSound("data/musicFX/buttonPress.wav");
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
        sound.playSound("data/musicFX/buttonPress.wav");
        String username = usernameField.getText();
        String password = passwordField.getText();

        database.ensureDatabaseExists();

        if(database.doesUserExist(username, password)) {

        } else {
            System.out.println("No such user exists.");
        }

    }

    @FXML
    protected void guestLogin() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createWarrior.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
        // Implement guestLogin logic here
        System.out.println("Logging in as guest");

    }
    @FXML
    protected void goBack() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");

    }

    @FXML
    protected void goToCityCenter() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cityCenter.fxml"));

        Stage stage = new Stage();
        Scene scene = new Scene(fxmlLoader.load());

        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");


        sound.playSound("data/musicFX/buttonPress.wav");

    }

}
