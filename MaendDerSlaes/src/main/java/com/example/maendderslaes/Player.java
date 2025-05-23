package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

public class Player extends Character{

    // Attributes
    int maxHP;
    public static Player instance;

    // ________________________________________

    public Player(String username, int health, int strength, int defence, int attack, int money, int level, String weapon, int maxHP, int speed) {
        super(username, health, strength, defence, attack, money, level, weapon, speed);
        this.maxHP = maxHP;
    }

    // ________________________________________

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

    // ________________________________________

    @Override
    protected int lightAttack() {

        int landAttack = random.nextInt(1, 6);  //80% chance to hit your attack

        if(landAttack != 5) {
            attack = strength - this.random.nextInt(5, 21);

            return attack;
        }
        System.out.println("You missed your attack");
        return 0;
    }

    // ________________________________________

    @Override
    protected int mediumAttack() {

        int landAttack = random.nextInt(1, 11); //60% chance to hit your attack

        if(landAttack > 4) {
            attack = strength - this.random.nextInt(4, 11);

            return attack;
        }
        System.out.println("You missed your attack");
        return 0;
    }

    // ________________________________________

    @Override
    protected int heavyAttack() {

        int landAttack = random.nextInt(1, 4); //1/3 chance to hit your attack

        if(landAttack == 1) {
            attack = strength - this.random.nextInt(3, 6);

            return attack;
        }
        System.out.println("You missed your attack");
        return 0;
    }

    // ________________________________________

    @Override
    protected int takeDMG(int damage) {
        int finalDMG = damage - defence;
        health -= finalDMG;

        return finalDMG;
    }

    // ________________________________________

    @Override
    public int getHP() {

        return health;
    }

    // ________________________________________

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

    public int getSpeed() {
        return this.speed;
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

    public void setSpeed(int speed) {
        this.speed = speed;
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

    public void addSpeed(int speed) {
        this.speed += speed;
    }

    public void negateDefence(int defence) {
        this.defence -= defence;
    }

    public void negateStrength(int strength) {
        this.strength -= strength;
    }

    public void negateMaxHealth(int maxHealth) {
        this.maxHP -= maxHealth;
    }

    public void negateSpeed(int speed) {
        this.speed -= speed;
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
