package com.example.maendderslaes;


import com.example.maendderslaes.util.SoundManager;

import java.util.Random;

public class Player extends Character{

    public Player(String username, int health, int strength, int defence, int attack, int money, int level, String weapon) {
        super(username, health, strength, defence, attack, money, level, weapon);
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

    public void setHP(int health) {

        this.health = health;
    }

    @Override
    public int getMoney() {
        return this.money;
    }

    public String getUsername() {
        return this.username;
    }

    public void negateMoney(int money) {
        this.money -= money;
    }

    public String getWeapon() {
        return this.weapon;
    }

    public int getLevel() {
        return level;
    }

    public void spendMoney(int amount) {
        if(money >= amount) {
            money -= amount;
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setName(String username) {
        this.username = username;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
