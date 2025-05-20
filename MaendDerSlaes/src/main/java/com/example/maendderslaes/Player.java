package com.example.maendderslaes;

public class Player extends Character{

    int maxHP;

    public Player(String username, int health, int strength, int defence, int attack, int money, int level, String weapon, int maxHP) {
        super(username, health, strength, defence, attack, money, level, weapon);
        this.maxHP = maxHP;
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

    //setters and getters
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
        return this.level;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDefence() {
        return this.defence;
    }

    public int getMaxHP() {
        return this.maxHP;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setHP(int health) {
        this.health = health;
    }

    public void setMaxHP(int maxHealth) {
        this.maxHP = maxHealth;
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

    public void setDefence(int defence) {
        this.defence = defence;
    }

    //adds and negates
    public void addDefence(int defence) {
        this.defence += defence;
    }

    public void addStrength(int strength) {
        this.strength += strength;
    }

    public void addMaxHealth(int maxHealth) {
        this.maxHP += maxHealth;
    }

    public void negateDefence(int defence) {
        this.defence -= defence;
    }

    public void negateStrength(int strength) {
        this.strength -= strength;
    }

    public void negateMaxHealth(int maxHealth) {
        this.maxHP += maxHealth;
    }


    public void applyWeaponBonus() {

        switch (this.weapon) {

            case "NONE" -> {this.strength += 1;}

            case "SwordOfIce" -> {this.strength += 2;}

            case "SwordOfBlood" -> {this.strength += 4;}

            case "diamondSword" -> {this.strength += 8;}
        }
    }

    public void spendMoney(int amount) {
        if(money >= amount) {
            money -= amount;
        }
    }

}
