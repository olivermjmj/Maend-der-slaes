package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

public class GameService {

    private final Player player;
    DBManager dbManager = DBManager.getInstance();

    public GameService() {
        this.dbManager = DBManager.getInstance();
        this.player = new Player("Guest", 100, 1, 1, 0, 50, 1, "NONE", 100, 10);
        loadPlayerData(); // Indlæs gemte data hvis de findes
    }

    public GameService(Player player, DBManager dbManager) {
        this.player = player;
        this.dbManager = dbManager;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean buyAndEquipWeapon(Item item) {
        // Brugeren må ikke købe våben de allerede har
        if (player.getMoney() >= item.getPrice()) {
            player.spendMoney(item.getPrice());
            player.setWeapon(item.getName());
            player.applyWeaponBonus();
            dbManager.saveUserWeapon(item.getName());
            System.out.println(item + ". Has now been equipped.");
            return true;
        }
        return false;
    }

    public int addBalance(int enemyWorthInMoney) {
        player.setMoney(player.getMoney() + enemyWorthInMoney);
        return player.getMoney();
    }

    public void setDefaultStats() {
        player.setLevel(1);
        player.setHP(100);
        player.setMaxHP(100);
        player.setMoney(50);
        player.setWeapon("NONE");
        player.setStrength(1);
        player.setDefence(1);
    }

    public void loadPlayerData() {
        player.setName(dbManager.getUserName());
        player.setLevel(dbManager.getUserLevel());
        player.setHP(dbManager.getUserHP());
        player.setMaxHP(dbManager.getUserMaxHP());
        player.setMoney(dbManager.getUserGold());
        player.setWeapon(dbManager.getUserWeapon());
        player.setStrength(dbManager.getUserStrength());
        player.setDefence(dbManager.getUserDefence());
        player.setSpeed(dbManager.getUserSpeed());
    }

    public void savePlayerData() {
        dbManager.saveUserData(
                player.getLevel(),
                player.getHP(),
                player.getWeapon(),
                player.getMoney(),
                player.getStrength(),
                player.getDefence(),
                player.getMaxHP(),
                player.getSpeed()
        );
    }

    public void initializeBattle() {
        if (player != null) {
            player.setHP(player.getMaxHP());
            Enemy enemy = new Enemy("Skeleton", 100, 10, 0, 0, 50, 1, "NONE", 10);
            // Note: 'enemy' bliver ikke brugt – måske skal du gemme det eller returnere det
        }
    }
}