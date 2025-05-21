package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class Controller {
    private int amountOfSkillPointsSpend;
    private int remainingSkillPoints = 10;

    Player player = new Player(null, 0, 0, 0, 0, 0, 0, null, 0, 0);
    DBManager dbManager = DBManager.getInstance();
    GameService gameService = new GameService(player, DBManager.getInstance());
    private final SoundManager sound = new SoundManager();
    private Character enemy = new Enemy("NONE", 5, 1, 0, 20, 1, 1, "NONE", 10);

    private AnimationHandler playerAnimations;
    private AnimationHandler enemyAnimations;

    @FXML private AnchorPane root;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text skillPointsLeft, strengthLevel, defenceLevel, maxHPLevel, speedLevel;
    @FXML private ImageView playerSprite, enemySprite;
    @FXML private ProgressBar playerHealthBar, enemyHealthBar;
    @FXML private Label playerHealthLabel, enemyHealthLabel;
    @FXML private HBox attackButtonsContainer;

    private String currentFxmlName;

    public void setCurrentFxmlName(String fxmlName) {
        this.currentFxmlName = fxmlName;
    }

    public void initializeArenaBattle() {
        if (playerSprite != null && enemySprite != null) {
            gameService = new GameService();
            player = gameService.getPlayer();

            String currentFxml = this.currentFxmlName != null ? this.currentFxmlName : "unknown.fxml";

            enemy = switch (currentFxml) {
                case "TræningsArena.fxml" -> new Enemy("Skeleton", 100, 10, 0, 0, 50, 1, "NONE", 10);
                case "Borgen.fxml" -> new Enemy("Werewolf", 100, 15, 2, 10, 100, 5, "None", 15);
                case "Colosseum.fxml" -> new Enemy("Minotaur", 100, 20, 5, 15, 500, 10, "None", 20);
                default -> new Enemy("Training Dummy", 50, 5, 0, 0, 0, 1, "NONE", 0);
            };

            enemyAnimations = switch (currentFxml) {
                case "TræningsArena.fxml" -> new AnimationHandler(CharacterType.SKELETON, enemySprite);
                case "Borgen.fxml" -> new AnimationHandler(CharacterType.WEREWOLF, enemySprite);
                case "Colosseum.fxml" -> new AnimationHandler(CharacterType.MINOTAUR, enemySprite);
                default -> new AnimationHandler(CharacterType.SKELETON, enemySprite);
            };

            playerAnimations = new AnimationHandler(CharacterType.PLAYER, playerSprite);
            playerAnimations.afspilAnimation(AnimationType.IDLE);
            enemyAnimations.afspilAnimation(AnimationType.IDLE);

          //  updateHealthBars();
        }
    }

    @FXML public void initialize() {
        if (root != null) {
            String backgroundPath;

            if (strengthLevel != null && maxHPLevel != null && speedLevel != null && defenceLevel != null) {
                backgroundPath = "/images/SkabDinKrigerScene.png";
            } else if (usernameField != null && passwordField != null) {
                backgroundPath = "/images/MaenDerSlaasForside.png";
            } else {
                switch (root.getId()) {
                    case "cityCenterScene" -> backgroundPath = "/images/CITYCENTER.png";
                    case "smedScene" -> backgroundPath = "/images/SMED.png";
                    case "statsScene" -> backgroundPath = "/images/statsogegenskaber.png";
                    case "arenaMenuScene" -> backgroundPath = "/images/VælgDinArena.png";
                    case "trainingScene" -> backgroundPath = "/images/TræningsArena.png";
                    case "borgenScene" -> backgroundPath = "/images/Borgen.png";
                    case "colosseumScene" -> backgroundPath = "/images/Colosseum.png";
                    default -> backgroundPath = "/images/MaenDerSlaasForside.png";
                }
            }

            String imageUrl = getClass().getResource(backgroundPath).toExternalForm();
            root.setStyle("-fx-background-image: url('" + imageUrl + "');" +
                    "-fx-background-repeat: stretch;" +
                    "-fx-background-size: 100% 100%;" +
                    "-fx-background-position: center center;" +
                    "-fx-background-color: black;");
        }

        if (skillPointsLeft != null && strengthLevel != null && maxHPLevel != null && speedLevel != null && defenceLevel != null) {
            setSkillPointsDisplay();
            strengthLevel.setText(String.valueOf(player.getStrength()));
            defenceLevel.setText(String.valueOf(player.getDefence()));
            speedLevel.setText(String.valueOf(player.getSpeed()));
            maxHPLevel.setText(String.valueOf(player.getMaxHP()));
        }
    }

    private void switchView(ActionEvent event, String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent newRoot = fxmlLoader.load();
        Controller controller = fxmlLoader.getController();
        controller.setCurrentFxmlName(fxmlPath.substring(fxmlPath.lastIndexOf("/") + 1));
        controller.initializeArenaBattle();

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
    }

    @FXML public void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        dbManager.ensureDatabaseExists();

        if (!dbManager.doesUserExist(username, password)) {
            dbManager.addUserToDB(username, password);
            gameService.setDefaultStats();
            gameService.savePlayerData();
            System.out.println("Welcome: " + username);
        } else {
            System.out.println("A user with the name: " + username + " already exists. Try another name.");
        }
    }

    @FXML public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        dbManager.ensureDatabaseExists();

        if (dbManager.doesUserExist(username, password)) {
            gameService.loadPlayerData();
            switchView(event, "createWarrior.fxml");
        } else {
            System.out.println("No such user exists.");
        }
    }

    @FXML public void guestLogin(ActionEvent event) throws IOException { switchView(event, "createWarrior.fxml"); }
    @FXML public void goBack(ActionEvent event) throws IOException { switchView(event, "MainMenu.fxml"); }
    @FXML public void goToCityCenter(ActionEvent event) throws IOException { gameService.savePlayerData(); switchView(event, "cityCenter.fxml"); }
    @FXML public void goToSmed(ActionEvent event) throws IOException { switchView(event, "SMED.fxml"); }
    @FXML public void goToStatsOgEgenskaber(ActionEvent event) throws IOException { switchView(event, "StatsOgEgenskaber.fxml"); }
    @FXML public void goToArenaMenu(ActionEvent event) throws IOException { switchView(event, "ArenaMenu.fxml"); }
    @FXML public void goToTræningsArena(ActionEvent event) throws IOException { switchView(event, "TræningsArena.fxml"); }
    @FXML public void goToBorgen(ActionEvent event) throws IOException { switchView(event, "Borgen.fxml"); }
    @FXML public void goToColosseum(ActionEvent event) throws IOException { switchView(event, "Colosseum.fxml"); }
    @FXML public void goBackToCharacterCreation(ActionEvent event) throws IOException { switchView(event, "createWarrior.fxml"); }

    public void setGameService(GameService gameService) { this.gameService = gameService; }
    public GameService getGameService() { return gameService; }

    private void setSkillPointsDisplay() { this.skillPointsLeft.setText(String.valueOf(this.remainingSkillPoints)); }

    @FXML public void addStrength() {
        if (this.remainingSkillPoints > 0) {
            player.addStrength(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;
            this.strengthLevel.setText(String.valueOf(player.getStrength()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void addDefence() {
        if (this.remainingSkillPoints > 0) {
            player.addDefence(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;
            this.defenceLevel.setText(String.valueOf(player.getDefence()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void addMaxHealth() {
        if (this.remainingSkillPoints > 0) {
            player.addMaxHealth(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;
            this.maxHPLevel.setText(String.valueOf(player.getMaxHP()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void addSpeed() {
        if (this.remainingSkillPoints > 0) {
            player.addSpeed(1);
            amountOfSkillPointsSpend++;
            remainingSkillPoints--;
            this.speedLevel.setText(String.valueOf(player.getSpeed()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void negateStrength() {
        if (this.amountOfSkillPointsSpend > 0) {
            player.negateStrength(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;
            this.strengthLevel.setText(String.valueOf(player.getStrength()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void negateDefence() {
        if (this.amountOfSkillPointsSpend > 0) {
            player.negateDefence(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;
            this.defenceLevel.setText(String.valueOf(player.getDefence()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void negateMaxHealth() {
        if (this.amountOfSkillPointsSpend > 0) {
            player.negateMaxHealth(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;
            this.maxHPLevel.setText(String.valueOf(player.getMaxHP()));
            setSkillPointsDisplay();
        }
    }

    @FXML public void negateSpeed() {
        if (this.amountOfSkillPointsSpend > 0) {
            player.negateSpeed(1);
            amountOfSkillPointsSpend--;
            remainingSkillPoints++;
            this.speedLevel.setText(String.valueOf(player.getSpeed()));
            setSkillPointsDisplay();
        }
    }
}