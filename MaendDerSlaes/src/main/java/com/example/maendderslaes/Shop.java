package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    // Attributes
    private List<Item> items = new ArrayList<>();

    // ________________________________________

    public void showShop() {

        setShopItems();
    }

    /* //Has been moved to Player class, so that the user is the one that buys the item, instead of the shop.
    public boolean buyItem(Player player, Item item, DBManager database) {

        //Build it so that a user can't purchase the weapon he/she has equipped

        if(player.getMoney() >= item.getPrice()) {
            player.spendMoney(item.getPrice());
            database.saveUserWeapon(item.getName());
            return true;
        }
        return false;
    }

     */

    // ________________________________________

    private void setShopItems() {

        items.add(new Item("Wooden Sword", 10));
        items.add(new Item("Wooden Axe)",10));
        items.add(new Item("Wooden Spear", 10));
        items.add(new Item("Wooden dagger", 10));
    }

    // ________________________________________

    public List<Item> getItems() {
        return items;
    }

} // Class end
