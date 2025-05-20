package com.example.maendderslaes;

import java.util.Random;

public abstract class Character {
    protected int health;
    protected int strength;
    protected int defence;
    protected int attack;
    protected int money;
    protected int level;
    protected Random random;
    protected String username;
    protected String weapon;
    protected int speed;

    public Character(String username, int health, int strength, int defence, int attack, int money, int level, String weapon, int speed) {
        this.health = health;
        this.strength = strength;
        this.defence = defence;
        this.attack = attack;
        this.money = money;
        this.level = level;
        this.random = new Random();
        this.username = username;
        this.weapon = weapon;
        this.speed = speed;
    }

    public abstract void tryToAttack(Character target, String choice);

    protected abstract int lightAttack();

    protected abstract int mediumAttack();

    protected abstract int heavyAttack();

    protected abstract int takeDMG(int damage);

    public abstract int getHP();

    public abstract int getMoney();
}
