package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

public class GameService {

    private final Player player;
    private final DBManager dbManager;

    public GameService(Player player, DBManager dbManager) {

        this.player = player;
        this.dbManager = dbManager;
    }

    public boolean buyItem(Item item) {

        //Build it so that a user can't purchase the weapon he/she has equipped

        if(player.getMoney() >= item.getPrice()) {

            player.spendMoney(item.getPrice());
            dbManager.saveUserWeapon(item.getName());
            System.out.println(item + ". Has now been equipped.");
            return true;
        }
        return false;
    }

    public int addBalance(int enemyWorthInMoney) {

        player.setMoney(player.getMoney() + enemyWorthInMoney);

        return 0;
    }

    public void loadPlayerData() {
        player.setName(dbManager.getUserName());        //lods the users name
        player.setLevel(dbManager.getUserLevel());      //loads the users level
        player.setHP(dbManager.getUserHP());            //loads the users hp
        player.setMoney(dbManager.getUserGold());       //loads the users money
        player.setWeapon(dbManager.getUserWeapon());    //loads the users weapon
    }

    public void savePlayerData() {
        dbManager.saveUserData(
                player.getLevel(),
                player.getHP(),
                player.getWeapon(),
                player.getMoney());
    }

}
