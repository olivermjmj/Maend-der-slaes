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

        //Build it so that a user can't purchase the weapon he/she has equipped

        if(player.getMoney() >= item.getPrice()) {

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
        player.setMoney(player.getMoney() + enemyWorthInMoney); //adds the enemies amount of money to the user
        return player.getMoney() + enemyWorthInMoney;           //how much money a user has
    }

    public void setDefaultStats() {
        if (player != null) {
            player.setHP(100);
            player.setMaxHP(100);
            player.setStrength(1);
            player.setDefence(1);
            player.setSpeed(1);
            player.setMoney(50);
            player.setWeapon("NONE");
            player.setLevel(1);
        }
    }

    public void loadPlayerData() {
        player.setName(dbManager.getUserName());            //lods the users name
        player.setLevel(dbManager.getUserLevel());          //loads the users level
        player.setHP(dbManager.getUserHP());                //loads the users hp
        player.setMaxHP(dbManager.getUserMaxHP());          //loads the users maxHP
        player.setMoney(dbManager.getUserGold());           //loads the users money
        player.setWeapon(dbManager.getUserWeapon());        //loads the users weapon
        player.setStrength(dbManager.getUserStrength());    //loads the users strength
        player.setDefence(dbManager.getUserDefence());      //loads the users defence
        player.setSpeed(dbManager.getUserSpeed());          //loads the users speed
    }

    public void savePlayerData() {
        dbManager.saveUserData(player.getLevel(), player.getHP(), player.getWeapon(), player.getMoney(), player.getStrength(), player.getDefence(), player.getMaxHP(), player.getSpeed());
    }
    public void initializeBattle() {
        if (player != null) {
            // Genopfrisk spillerens HP til max ved kampstart
            player.setHP(player.getMaxHP());

            // Opret ny fjende med standard værdier
            Enemy enemy = new Enemy("Skeleton", 100, 10, 0, 0, 50, 1, "NONE", 10);
        }
    }

}