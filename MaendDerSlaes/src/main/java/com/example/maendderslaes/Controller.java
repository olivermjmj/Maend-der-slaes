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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class Controller {

    @FXML
    private AnchorPane root;
    private int amountOfSkillPointsSpend;
    private int remainingSkillPoints = 10;

    Player player = new Player(null, 0, 0, 0, 0, 0, 0, null, 0, 0);
    DBManager dbManager = DBManager.getInstance();
    GameService gameService = new GameService(player, DBManager.getInstance());

    private final SoundManager sound = new SoundManager();

    private final Character enemy = new Enemy("NONE", 5, 1, 0, 20, 1, 1, "NONE", 10); // dummy enemy

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text skillPointsLeft;

    @FXML
    private Text strengthLevel;

    @FXML
    private Text defenceLevel;

    @FXML
    private Text maxHPLevel;

    @FXML
    private Text speedLevel;

    //Attack types
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

        if (!fxmlPath.startsWith("/")) {

            fxmlPath = "/com/example/maendderslaes/" + fxmlPath;
        }

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

       // sound.playSoundFromResource("/musicFX/buttonPress.wav");
    }

    @FXML
    protected void register() {
        //sound.playSoundFromResource("/musicFX/buttonPress.wav");
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
        //sound.playSoundFromResource("/musicFX/buttonPress.wav");
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
    @FXML protected void goToCityCenter (ActionEvent event) throws IOException { gameService.savePlayerData(); switchView (event, "cityCenter.fxml"); }
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

    private void setSkillPointsDisplay() {
        this.skillPointsLeft.setText(String.valueOf(this.remainingSkillPoints));
    }

    private void getSkillPointsLeft() {

    }

    @FXML
    public void addHealth() {

        if(this.remainingSkillPoints > 0) {

        }
    }

    @FXML
    public void addStrength() {

        if(this.remainingSkillPoints > 0) {

            player.addStrength(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;

            this.strengthLevel.setText(String.valueOf(player.getStrength()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player does not have any more skill points to spend.");
        }
    }

    @FXML
    public void addDefence() {

        if(this.remainingSkillPoints > 0) {

            player.addDefence(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;

            this.defenceLevel.setText(String.valueOf(player.getDefence()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player does not have any more skill points to spend.");
        }
    }

    @FXML
    public void addMaxHealth() {

        if(this.remainingSkillPoints > 0) {

            player.addMaxHealth(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;

            this.maxHPLevel.setText(String.valueOf(player.getMaxHP()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player does not have any more skill points to spend.");
        }
    }

    @FXML
    public void addSpeed() {

        if(this.remainingSkillPoints > 0) {
            player.addSpeed(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;

            this.speedLevel.setText(String.valueOf(player.getSpeed()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player does not have any more skill points to spend.");
        }
    }

    @FXML
    public void negateStrength() {

        if(this.amountOfSkillPointsSpend > 0) {

            player.negateStrength(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;

            this.strengthLevel.setText(String.valueOf(player.getStrength()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player has reached the starting strength level.");
        }
    }

    @FXML
    public void negateDefence() {

        if(this.amountOfSkillPointsSpend > 0) {
            player.negateDefence(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;

            this.defenceLevel.setText(String.valueOf(player.getDefence()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player has reached the starting stat level");
        }
    }

    @FXML
    public void negateMaxHealth() {

        if(this.amountOfSkillPointsSpend > 0) {
            player.negateMaxHealth(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;

            this.maxHPLevel.setText(String.valueOf(player.getMaxHP()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player has reached the starting stat level");
        }
    }

    @FXML
    public void negateSpeed() {

        if(this.amountOfSkillPointsSpend > 0) {
            player.negateSpeed(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;

            this.speedLevel.setText(String.valueOf(player.getSpeed()));
            setSkillPointsDisplay();
        } else {
            System.out.println("Player has reached the starting stat level");
        }
    }

    //Lets us view the skill points before pressing any buttons.
    // This is a quick way to avoid CSS, even tho we should have used that.
    @FXML
    public void initialize() {
        if (root != null) {
            String backgroundPath;

            if (strengthLevel != null && maxHPLevel != null && speedLevel != null && defenceLevel != null) {
                backgroundPath = "/images/SkabDinKrigerScene.png";
            } else if (usernameField != null && passwordField != null) {
                backgroundPath = "/images/MaenDerSlaasForside.png";
            } else {
                switch (root.getId()) {
                    case "cityCenterScene"   -> backgroundPath = "/images/CITYCENTER.png";
                    case "smedScene"         -> backgroundPath = "/images/SMED.png";
                    case "statsScene"        -> backgroundPath = "/images/statsogegenskaber.png";
                    case "arenaMenuScene"    -> backgroundPath = "/images/VælgDinArena.png";
                    case "trainingScene"     -> backgroundPath = "/images/TræningsArena.png";
                    case "borgenScene"       -> backgroundPath = "/images/Borgen.png";
                    case "colosseumScene"    -> backgroundPath = "/images/Colosseum.png";
                    default                  -> backgroundPath = "/images/MaenDerSlaasForside.png";
                }
            }

            String imageUrl = getClass().getResource(backgroundPath).toExternalForm();
            root.setStyle(
                    "-fx-background-image: url('" + imageUrl + "');" +
                            "-fx-background-repeat: stretch;" +
                            "-fx-background-size: 100% 100%;" +
                            "-fx-background-position: center center;" +
                            "-fx-background-color: black;");
        }

        // Stats-skærmens tal
        if (skillPointsLeft != null && strengthLevel != null &&
                maxHPLevel != null && speedLevel != null && defenceLevel != null) {
            setSkillPointsDisplay();
            strengthLevel.setText(String.valueOf(player.getStrength()));
            defenceLevel.setText(String.valueOf(player.getDefence()));
            speedLevel.setText(String.valueOf(player.getSpeed()));
            maxHPLevel.setText(String.valueOf(player.getMaxHP()));
        }
    }
}
