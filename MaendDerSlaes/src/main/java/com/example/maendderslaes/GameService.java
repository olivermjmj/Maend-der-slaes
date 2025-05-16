package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

public class GameService {

    private Player player;
    private DBManager dbManager;

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

}
