package com.example.maendderslaes;

import java.util.Random;

public abstract class Character {
    protected int health;
    protected int strength;
    protected int defence;
    protected int attack;

    protected Random random;

    public Character(int health, int strength, int defence, Random random, int attack) {
        this.health = health;
        this.strength = strength;
        this.defence = defence;
        this.random = random;
        this.attack = attack;
    }
    public abstract void tryToAttack(Character target);

    protected abstract int lightAttack();

    protected abstract int mediumAttack();

    protected abstract int heavyAttack();

    protected abstract int takeDMG(int damage);
}
