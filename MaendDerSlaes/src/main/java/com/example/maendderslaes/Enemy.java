package com.example.maendderslaes;

import java.util.Random;

public class Enemy extends Character{

    public Enemy(int health, int strength, int defence, Random random, int attack) {
        super(health, strength, defence, random, attack);
    }

    @Override
    public void tryToAttack(Character target, String choice) {
        int roll = random.nextInt(1,4);
        int damage = 0;

        switch (roll) {
            //Lambda for switch cases are only possible for java 14+
            case 1 -> damage = lightAttack();
            case 2 -> damage = mediumAttack();
            case 3 -> damage = heavyAttack();
        }

        int dealtDMG = target.takeDMG(damage);
        System.out.println("Attack hit for " + dealtDMG + " damage");
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

        return attack;
    }


    @Override
    protected int takeDMG(int damage) {
        int finalDamage = Math.max(0, damage - defence);
        health -= finalDamage;
        return finalDamage;
    }
}
