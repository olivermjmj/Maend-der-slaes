package com.example.maendderslaes;

import java.util.Random;

public abstract class Character {
    protected int health;
    protected int strength;
    protected int defence;
    protected int attack;
    protected int money;

    public Character(int health, int strength, int defence, int attack, int money) {
        this.health = health;
        this.strength = strength;
        this.defence = defence;
        this.attack = attack;
        this.money = money;
    }

    public abstract void tryToAttack(Character target, String choice);

    protected abstract int lightAttack();

    protected abstract int mediumAttack();

    protected abstract int heavyAttack();

    protected abstract int takeDMG(int damage);

    public abstract int getHP();

    public abstract int getMoney();
}
