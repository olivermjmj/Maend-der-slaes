package com.example.maendderslaes;

import java.util.Random;

public class Player extends Character{

    public Player(int health, int strength, int defence, Random random, int attack) {
        super(health, strength, defence, random, attack);
    }

    @Override
    public void tryToAttack(Character target) {


    }

    @Override
    protected int lightAttack() {

        attack = strength - random.nextInt(5,21);

        return attack;
    }

    @Override
    protected int mediumAttack() {

        attack = strength - random.nextInt(4, 11);

        return attack;
    }

    @Override
    protected int heavyAttack() {

        attack = strength - random.nextInt(3,6);

        return 0;
    }


    @Override
    protected int takeDMG(int damage) {
        int finalDMG = Math.max(0,damage - defence);
        health -= finalDMG;

        return finalDMG;
    }
}
