package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;
import com.example.maendderslaes.util.SoundManager;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;

public class Controller {

    private int amountOfSkillPointsSpend;
    private int remainingSkillPoints = 10;

    // Ændr initialiseringen af player til at have nogle standard værdier
    Player player = new Player(null, 100, 1, 1, 0, 50, 1, "NONE", 100, 1);
    DBManager dbManager = DBManager.getInstance();
    GameService gameService = new GameService(player, DBManager.getInstance());
    private final SoundManager sound = new SoundManager();
    private Character enemy = new Enemy("NONE", 5, 1, 0, 20, 1, 1, "NONE", 10);

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Text skillPointsLeft;
    @FXML private Text strengthLevel;
    @FXML private Text defenceLevel;
    @FXML private Text maxHPLevel;
    @FXML private Text speedLevel;
    @FXML private ImageView playerSprite;
    @FXML private ImageView enemySprite;
    @FXML private ProgressBar playerHealthBar;
    @FXML private ProgressBar enemyHealthBar;
    @FXML private Label playerHealthLabel;
    @FXML private Label enemyHealthLabel;
    @FXML private HBox attackButtonsContainer;

    private AnimationHandler playerAnimations;
    private AnimationHandler enemyAnimations;
    private String currentFxmlName;

    public void setCurrentFxmlName(String fxmlName) {
        this.currentFxmlName = fxmlName;
    }

    public void initializeArenaBattle() {
        if (playerSprite != null && enemySprite != null) {
            // Hvis der er en bruger logget ind, indlæs deres data
            if (dbManager.getUserName() != null) {
                player.setName(dbManager.getUserName());
                player.setHP(dbManager.getUserHP());
                player.setMaxHP(dbManager.getUserMaxHP());
                player.setStrength(dbManager.getUserStrength());
                player.setDefence(dbManager.getUserDefence());
                player.setSpeed(dbManager.getUserSpeed());
                player.setMoney(dbManager.getUserGold());
                player.setWeapon(dbManager.getUserWeapon());
                player.setLevel(dbManager.getUserLevel());
            } else {
                // Hvis det er en gæst, brug standard værdier
                player.setHP(100);
                player.setMaxHP(100);
                player.setStrength(10);
                player.setDefence(1);
                player.setSpeed(1);
                player.setMoney(50);
                player.setWeapon("NONE");
                player.setLevel(1);
            }

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

            updateHealthBars();
        }
        
        // Opdater health bars efter initialisering
        updateHealthBars();
    }

    @FXML
    private void initialize() {
        if (playerHealthBar != null && enemyHealthBar != null) {
            updateHealthBars();
        }
    }

    private void handleAttack(String attackType) {
        if (player == null || enemy == null) return;

        attackButtonsContainer.setDisable(true);

        playerAnimations.afspilAnimation(getAnimationTypeForAttack(attackType));
        boolean playerHit = player.tryToAttack(enemy, attackType);

        if (playerHit) {
            enemyAnimations.afspilAnimation(AnimationType.GOT_HIT);
        }

        updateHealthBars();

        if (enemy.getHP() <= 0) {
            enemyAnimations.afspilAnimation(AnimationType.DEATH);
            handleVictory();
            return;
        }

        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            if (player.getHP() > 0) {
                enemyAnimations.afspilAnimation(AnimationType.LIGHT_ATTACK);
                boolean enemyHit = enemy.tryToAttack(player, null);

                if (enemyHit) {
                    playerAnimations.afspilAnimation(AnimationType.GOT_HIT);
                }

                updateHealthBars();

                if (player.getHP() <= 0) {
                    handleDefeat();
                } else {
                    attackButtonsContainer.setDisable(false);
                }
            }
        });
        pause.play();
    }

    private void handleVictory() {
        int reward = enemy.getMoney();
        player.setMoney(player.getMoney() + reward);
        System.out.println("You won! Earned " + reward + " gold!");
        gameService.savePlayerData();
    }

    private void handleDefeat() {
        playerAnimations.afspilAnimation(AnimationType.DEATH);
        System.out.println("You were defeated!");
        attackButtonsContainer.setDisable(true);
    }

    private void updateHealthBars() {
        if (playerHealthBar != null && player != null) {
            // Sikr at HP aldrig er mindre end 0
            int currentHP = Math.max(0, player.getHP());
            int maxHP = Math.max(100, player.getMaxHP()); // Brug mindst 100 som max HP
            
            double playerHealthPercent = (double) currentHP / maxHP;
            playerHealthBar.setProgress(playerHealthPercent);
            playerHealthLabel.setText(currentHP + " / " + maxHP);
        }

        if (enemyHealthBar != null && enemy != null) {
            int enemyCurrentHP = Math.max(0, enemy.getHP());
            double enemyHealthPercent = (double) enemyCurrentHP / 100;
            enemyHealthBar.setProgress(enemyHealthPercent);
            enemyHealthLabel.setText(enemyCurrentHP + " / 100");
        }
    }

    private AnimationType getAnimationTypeForAttack(String attackType) {
        return switch (attackType) {
            case "light" -> AnimationType.LIGHT_ATTACK;
            case "medium" -> AnimationType.MEDIUM_ATTACK;
            case "heavy" -> AnimationType.HEAVY_ATTACK;
            default -> AnimationType.LIGHT_ATTACK;
        };
    }

    @FXML protected void lightAttack() { handleAttack("light"); }
    @FXML protected void mediumAttack() { handleAttack("medium"); }
    @FXML protected void heavyAttack() { handleAttack("heavy"); }

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
        sound.playSound("data/musicFX/buttonPress.wav");
    }

    @FXML protected void register() {
        sound.playSound("data/musicFX/buttonPress.wav");
        String username = usernameField.getText();
        String password = passwordField.getText();

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

    @FXML protected void login(ActionEvent event) throws IOException {
        sound.playSound("data/musicFX/buttonPress.wav");
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

    @FXML protected void guestLogin(ActionEvent event) throws IOException { switchView(event, "createWarrior.fxml"); }
    @FXML protected void goBack(ActionEvent event) throws IOException { switchView(event, "MainMenu.fxml"); }
    @FXML protected void goToCityCenter(ActionEvent event) throws IOException { switchView(event, "cityCenter.fxml"); }
    @FXML protected void goToSmed(ActionEvent event) throws IOException { switchView(event, "SMED.fxml"); }
    @FXML protected void goToStatsOgEgenskaber(ActionEvent event) throws IOException { switchView(event, "StatsOgEgenskaber.fxml"); }
    @FXML protected void goToArenaMenu(ActionEvent event) throws IOException { switchView(event, "ArenaMenu.fxml"); }
    @FXML protected void goToTræningsArena(ActionEvent event) throws IOException { switchView(event, "TræningsArena.fxml"); }
    @FXML protected void goToBorgen(ActionEvent event) throws IOException { switchView(event, "Borgen.fxml"); }
    @FXML protected void goToColosseum(ActionEvent event) throws IOException { switchView(event, "Colosseum.fxml"); }
    @FXML protected void goBackToCharacterCreation(ActionEvent event) throws IOException { switchView(event, "createWarrior.fxml"); }

    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    public GameService getGameService() {
        return gameService;
    }

    private void setSkillPointsDisplay() {
        this.skillPointsLeft.setText(String.valueOf(this.remainingSkillPoints));
    }

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