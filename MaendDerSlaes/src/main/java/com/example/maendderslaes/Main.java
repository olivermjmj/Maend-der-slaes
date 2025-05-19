package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    GameService gameService;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hovedmenu");
        stage.getIcons().add(new Image("file:data/images/Game_Logo.png"));

        //fullscreen code for mac
        /*Screen screen = Screen.getPrimary();
        var bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight()); */

        // By ChatGPT: Get's a hold of the controller
        Controller controller = fxmlLoader.getController();
        this.gameService = controller.getGameService();

        SoundManager music = new SoundManager();
        music.playSoundOnRepeat("data/music/startMenu_Music.wav");
        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {

            gameService.savePlayerData();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}