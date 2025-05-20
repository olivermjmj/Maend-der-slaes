package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

public class GameService {

    private final Player player;
    private final DBManager dbManager;

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
        player.setLevel(1);
        player.setHP(100);
        player.setMaxHP(100);
        player.setMoney(50);
        player.setWeapon("NONE");
        player.setStrength(1);
        player.setDefence(1);
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

        player.setRemainingSkillPoints(dbManager.getUserSkillPoints());
    }

    public void savePlayerData() {
        dbManager.saveUserData(player.getLevel(), player.getHP(), player.getWeapon(), player.getMoney(), player.getStrength(), player.getDefence(), player.getMaxHP(), player.getSpeed(), player.getRemainingSkillPoints());
    }

}
