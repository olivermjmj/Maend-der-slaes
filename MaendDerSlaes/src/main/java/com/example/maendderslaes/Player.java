package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

import java.util.Random;

public class Player extends Character{

    Random random;

    public Player(int health, int strength, int defence, Random random, int attack, int money, int level) {
        super(health, strength, defence, attack, money, level);
    }

    @Override
    public void tryToAttack(Character target, String choice) {

        int damage = 0;

        switch (choice) {
            //Lambda for switch cases are only possible for java 14+
            case "light" -> damage = lightAttack();
            case "medium" -> damage = mediumAttack();
            case "heavy" -> damage = heavyAttack();
        }

        int dealtDMG = target.takeDMG(damage);
        System.out.println("Attack hit for " + dealtDMG + " damage");
    }

    @Override
    protected int lightAttack() {

        attack = strength - this.random.nextInt(5,21);

        return attack;
    }

    @Override
    protected int mediumAttack() {

        attack = strength - this.random.nextInt(4, 11);

        return attack;
    }

    @Override
    protected int heavyAttack() {

        attack = strength - this.random.nextInt(3,6);

        return attack;
    }


    @Override
    protected int takeDMG(int damage) {
        int finalDMG = damage - defence;
        health -= finalDMG;

        return finalDMG;
    }

    @Override
    public int getHP() {

        return health;
    }

    @Override
    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void negateMoney(int money) {
        this.money -= money;
    }

    public int getLevel() {
        return level;
    }

    public void spendMoney(int amount) {
        if(money >= amount) {
            money -= amount;
        }
    }

    public boolean buyItem(Player player, Item item, DBManager database) {

        //Build it so that a user can't purchase the weapon he/she has equipped

        if(player.getMoney() >= item.getPrice()) {
            player.spendMoney(item.getPrice());
            database.saveUserWeapon(item.getName());
            return true;
        }
        return false;
    }

}
