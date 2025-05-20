package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    // Attributes

    // ________________________________________

    @Override
    public void start(Stage stage) throws IOException {

        //Loads FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/maendderslaes/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Controller controller = fxmlLoader.getController();

        GameService gameService = controller.getGameService();

        //fullscreen code for mac
        /*Screen screen = Screen.getPrimary();
        var bounds = screen.getVisualBounds();
        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight()); */

        stage.setTitle("Hovedmenu");
        stage.getIcons().add(new Image(getClass().getResource("/images/Game_Logo.png").toExternalForm()));

        SoundManager music = new SoundManager();
        URL musicUrl = getClass().getResource("/music/mp3/startMenu_Music.mp3");
        music.playBackgroundMusicFromResource(musicUrl);

        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setFullScreenExitHint("");
        stage.setScene(scene);
        stage.show();

        // Saves users data to the database, if user is logged in.
        stage.setOnCloseRequest(event -> {
            if(DBManager.getInstance().getUserName() != null) {
                gameService.savePlayerData();
                System.out.println("Game saved");
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

} // Class end