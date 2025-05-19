package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;

public class Controller {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML private ImageView characterImageView;

    private Image staticImage;
    private Image fightImage;

    @FXML
    public void initialize() {
        if (characterImageView == null) return;

        // antager at din working directory er projektroden, og at der findes en mappe "data/images/"
        staticImage = new Image("file:data/images/Gladiator1.png");
        fightImage  = new Image("file:data/images/Gladiator4.png");
        characterImageView.setImage(staticImage);
    }



    @FXML
    private void onHitButtonClick() {
        // Forudsat at disse billeder er indlæst et eller andet sted (fx i initialize)
        Image stance1 = new Image("file:data/images/Gladiator1.png");
        Image stance2 = new Image("file:data/images/Gladiator2.png");
        Image stance3 = new Image("file:data/images/Gladiator3.png");
        Image stance4 = new Image("file:data/images/Gladiator4.png");


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> characterImageView.setImage(stance1)),
                new KeyFrame(Duration.seconds(0.2), e -> characterImageView.setImage(stance2)),
                new KeyFrame(Duration.seconds(0.4), e -> characterImageView.setImage(stance3)),
                new KeyFrame(Duration.seconds(0.6), e -> characterImageView.setImage(stance4)),
                new KeyFrame(Duration.seconds(0.8), e -> characterImageView.setImage(staticImage))
        );
        timeline.play();
    }




    private final DBManager database = new DBManager();
    private SoundManager sound = new SoundManager();

    private void switchView(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent newRoot = fxmlLoader.load();
        Scene scene = ((Node) event.getSource()).getScene();

        StackPane container = new StackPane();
        container.setStyle("-fx-background-color: black;");
        container.setMaxWidth(Double.MAX_VALUE);
        container.setMaxHeight(Double.MAX_VALUE);

        Parent oldRoot = scene.getRoot();
        newRoot.setOpacity(0);
        container.getChildren().addAll(oldRoot, newRoot);

        scene.setRoot(container);

        // Længere varighed på fade transitions
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), oldRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newRoot);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.setDelay(Duration.millis(150));

        fadeOut.setOnFinished(e -> {
            container.getChildren().remove(oldRoot);
            fadeIn.play();
        });

        fadeOut.play();
        sound.playSound("data/musicFX/buttonPress.wav");
    }

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
        String username = usernameField.getText();
        String password = passwordField.getText();

        database.ensureDatabaseExists();

        if(database.doesUserExist(username, password)) {

        } else {
            System.out.println("No such user exists.");
        }

    }
    @FXML protected void guestLogin(ActionEvent event) throws IOException { switchView (event, "createWarrior.fxml"); }
    @FXML protected void goBack (ActionEvent event) throws IOException { switchView (event, "MainMenu.fxml"); }
    @FXML protected void goToCityCenter (ActionEvent event) throws IOException { switchView (event, "cityCenter.fxml"); }
    @FXML protected void goToSmed (ActionEvent event) throws IOException { switchView (event, "SMED.fxml"); }
    @FXML protected void goToStatsOgEgenskaber (ActionEvent event) throws IOException { switchView (event, "StatsOgEgenskaber.fxml"); }
    @FXML protected void goToArenaMenu (ActionEvent event) throws IOException { switchView (event, "ArenaMenu.fxml"); }
    @FXML protected void goToTræningsArena (ActionEvent event) throws IOException { switchView (event, "TræningsArena.fxml"); }
    @FXML protected void goToBorgen (ActionEvent event) throws IOException { switchView (event, "Borgen.fxml"); }
    @FXML protected void goToColosseum (ActionEvent event) throws IOException { switchView (event, "Colosseum.fxml"); }
    @FXML protected void goBackToCharacterCreation(ActionEvent event) throws IOException { switchView (event, "createWarrior.fxml"); }

}