package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {

    Player player = new Player(null, 0, 0, 0, 0, 0, 0, null);
    DBManager dbManager = DBManager.getInstance();
    GameService gameService = new GameService(player, DBManager.getInstance());

    private final SoundManager sound = new SoundManager();

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private final Character enemy = new Enemy("NONE", 15, 3, 0, 20, 1, 2, "NONE");

    @FXML
    protected void lightAttack() {
        handleAttack("light");
    }

    @FXML
    protected void mediumAttack() {
        handleAttack("medium");
    }

    @FXML void heavyAttack() {
        handleAttack("heavy");
    }

    private void handleAttack(String attackType) {
        String playersName = dbManager.getUserName();

        if(playersName == null) {
            playersName = "Guest";
        }

        player.tryToAttack(enemy, attackType);

        if(enemy.getHP() <= 0 ) {
            int oldMoneyAmount = player.getMoney();
            player.setMoney(player.getMoney() + enemy.getMoney());
            System.out.println(playersName + ", won the battle. Your balance has gone from " + oldMoneyAmount + " to " + player.getMoney());
            return;
        }

        enemy.tryToAttack(player, null);

        if(player.getHP() <= 0) {
            System.out.println(playersName + ", has lost");
        }
    }

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
        dbManager.ensureDatabaseExists();

        if (!dbManager.doesUserExist(username, password)) {
            dbManager.addUserToDB(username, password);
            gameService.setDefaultStats();
            gameService.savePlayerData();
            System.out.println("Welcome: " + username);
        } else {
            System.out.println("A user with the name: " + username + ". Already exists, try another name");
        }
    }

    @FXML
    protected void login(ActionEvent event) throws IOException {
        sound.playSound("data/musicFX/buttonPress.wav");
        String username = usernameField.getText();
        String password = passwordField.getText();

        dbManager.ensureDatabaseExists();

        if(dbManager.doesUserExist(username, password)) {
            gameService.loadPlayerData();

            switchView(event, "createWarrior.fxml");
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


    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public GameService getGameService() {
        return gameService;
    }
}