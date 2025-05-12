package com.example.maendderslaes;

import java.util.Random;

public class Enemy extends Character{

    public Enemy(int health, int strength, int defence, Random random, int attack) {
        super(health, strength, defence, random, attack);
    }

    @Override
    public void tryToAttack(Character target) {

    }

    @Override
    protected int lightAttack() {
        return 0;
    }

    @Override
    protected int mediumAttack() {
        return 0;
    }

    @Override
    protected int heavyAttack() {
        return 0;
    }


    @Override
    protected int takeDMG(int damage) {
        return 0;
    }
}
