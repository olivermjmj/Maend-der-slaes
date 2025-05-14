package com.example.maendderslaes;

import java.util.Random;

public class Player extends Character{

    Random random;

    public Player(int health, int strength, int defence, Random random, int attack, int money) {
        super(health, strength, defence, attack, money);
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

    public int getHP() {

        return health;
    }

    public int getMoney() {

        return money;
    }
}
