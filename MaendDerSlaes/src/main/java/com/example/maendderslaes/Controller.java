package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


import javafx.event.ActionEvent;
import javafx.scene.Node;


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
    protected void guestLogin(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createWarrior.fxml"));

        // get the new scene through the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // create and load the new scene
        Scene scene = new Scene(fxmlLoader.load());

        // Set the stage for the existing window
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }

    @FXML
    protected void goBack(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));

        // get the new scene through the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // create and load the new scene
        Scene scene = new Scene(fxmlLoader.load());

        // Set the stage for the existing window
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }

    @FXML
    protected void goToCityCenter(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cityCenter.fxml"));

        // get the new scene through the event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // create and load the new scene
        Scene scene = new Scene(fxmlLoader.load());

        // Set the stage for the existing window
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }
    @FXML
    protected void goToSmed(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SMED.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }

    @FXML
    protected void goToStatsOgEgenskaber(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StatsOgEgenskaber.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }

    @FXML
    protected void goToArenaMenu(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ArenaMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }
    @FXML
    protected void goToTræningsArena(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TræningsArena.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }
    @FXML
    protected void goToBorgen(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Borgen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }
    @FXML
    protected void goToColosseum(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Colosseum.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        sound.playSound("data/musicFX/buttonPress.wav");
    }


}