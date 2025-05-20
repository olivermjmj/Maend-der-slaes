package com.example.maendderslaes;

public class Enemy extends Character{

    public Enemy(String username, int health, int strength, int defence, int attack, int money, int level, String weapon, int speed) {
        super(username, health, strength, defence, attack, money, level, weapon, speed);
    }

    @Override
    public void tryToAttack(Character target, String choice) {
        int roll = this.random.nextInt(1,4);
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

        int landAttack = random.nextInt(1, 6);  //80% chance to hit

        if(landAttack != 5) {
            attack = strength - this.random.nextInt(5, 21);

            return attack;
        }
        System.out.println("Enemy missed it's attack");
        return 0;
    }

    @Override
    protected int mediumAttack() {

        int landAttack = random.nextInt(1, 11); //60% chance to hit

        if(landAttack > 4) {
            attack = strength - this.random.nextInt(4, 11);

            return attack;
        }
        System.out.println("Enemy missed it's attack");
        return 0;
    }

    @Override
    protected int heavyAttack() {

        int landAttack = random.nextInt(1, 4); //1/3 chance to hit

        if(landAttack == 1) {
            attack = strength - this.random.nextInt(3, 6);

            return attack;
        }
        System.out.println("Enemy missed it's attack");
        return 0;
    }


    @Override
    protected int takeDMG(int damage) {

        int finalDamage = Math.max(0, damage - defence);
        health -= finalDamage;
        return finalDamage;
    }

    @Override
    public int getHP() {

        return health;
    }

    @Override
    public int getMoney() {

        return money;
    }
}
