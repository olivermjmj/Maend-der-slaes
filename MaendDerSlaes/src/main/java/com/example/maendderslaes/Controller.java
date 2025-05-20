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
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
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


    Player player = new Player(null, 0, 0, 0, 0, 0, 0, null, 0, 0);
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




    @FXML
    private void initialize() {
        if (playerSprite != null && enemySprite != null) {
            // Initialiser GameService
            gameService = new GameService();
            player = gameService.getPlayer(); // Tilføj getPlayer() metode i GameService

            // Initialiser enemy med startværdier
            enemy = new Enemy("Skeleton", 100, 10, 0, 0, 50, 1, "NONE", 10);

            // Opdater health bars
            updateHealthBars();

            // Start animationer
            playerAnimations = new AnimationHandler(CharacterType.PLAYER, playerSprite);
            enemyAnimations = new AnimationHandler(CharacterType.SKELETON, enemySprite);
            playerAnimations.afspilAnimation(AnimationType.IDLE);
            enemyAnimations.afspilAnimation(AnimationType.IDLE);
        }
    }



    private void enableAttackButtons() {
        if (attackButtonsContainer != null) {
            attackButtonsContainer.setDisable(false);
        }
    }

    private void disableAttackButtons() {
        if (attackButtonsContainer != null) {
            attackButtonsContainer.setDisable(true);
        }
    }


    private void resetBattle() {
        enableAttackButtons();
        player.setHP(player.getMaxHP());
        enemy = new Enemy("NONE", 5, 1, 0, 20, 1, 1, "NONE", 10);
        updateHealthBars();

        if (playerAnimations != null && enemyAnimations != null) {
            playerAnimations.afspilAnimation(AnimationType.IDLE);
            enemyAnimations.afspilAnimation(AnimationType.IDLE);
        }
    }


    private void handleAttack(String attackType) {
        if (player == null || enemy == null) return;

        // Deaktiver knapper midlertidigt
        attackButtonsContainer.setDisable(true);

        // Spiller angriber
        playerAnimations.afspilAnimation(getAnimationTypeForAttack(attackType));
        boolean playerHit = player.tryToAttack(enemy, attackType);

        // Hvis spilleren rammer, afspil "got hit" animation på fjenden
        if (playerHit) {
            enemyAnimations.afspilAnimation(AnimationType.GOT_HIT);
        }

        updateHealthBars();

        // Hvis fjenden er død
        if (enemy.getHP() <= 0) {
            enemyAnimations.afspilAnimation(AnimationType.DEATH);
            handleVictory();
            return;
        }

        // Fjendens tur efter en kort forsinkelse
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(e -> {
            if (player.getHP() > 0) {
                enemyAnimations.afspilAnimation(AnimationType.LIGHT_ATTACK);
                boolean enemyHit = enemy.tryToAttack(player, null);

                // Hvis fjenden rammer, afspil "got hit" animation på spilleren
                if (enemyHit) {
                    playerAnimations.afspilAnimation(AnimationType.GOT_HIT);
                }

                updateHealthBars();

                if (player.getHP() <= 0) {
                    handleDefeat();
                } else {
                    // Genaktiver kun knapperne hvis spilleren stadig lever
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
        // Opdater spiller health bar
        double playerHealthPercent = (double) Math.max(0, player.getHP()) / player.getMaxHP();
        playerHealthBar.setProgress(playerHealthPercent);
        playerHealthLabel.setText(player.getHP() + " / " + player.getMaxHP());

        // Opdater fjende health bar
        double enemyHealthPercent = (double) Math.max(0, enemy.getHP()) / 100;
        enemyHealthBar.setProgress(enemyHealthPercent);
        enemyHealthLabel.setText(enemy.getHP() + " / 100");
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
        } else {
            System.out.println("Player has reached the starting stat level");
        }
    }



}