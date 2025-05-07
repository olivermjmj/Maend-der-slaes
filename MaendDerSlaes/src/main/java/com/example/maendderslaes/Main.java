package com.example.maendderslaes;

import com.example.maendderslaes.util.SoundManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hovedmenu");

        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");

        stage.setScene(scene);
        SoundManager soundManager = new SoundManager();
        soundManager.playSound("data/music/startMenu_Music.mp3");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}