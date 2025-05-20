package com.example.maendderslaes;

import com.example.maendderslaes.util.DBManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Casino {

    private boolean won = false;
    private int[] dice = new int[2];

    //The game hazard. The player has to guess a number that ranges from 5-9
    public void hazard(Player player, int moneyToGamble, int guess) {

        Random random = new Random();

        //First throw.
        dice[0] = random.nextInt(1, 7);
        dice[1] = random.nextInt(1, 7);

        int totalValue = dice[0] + dice[1];

        if(guess == totalValue) {
            won = true;
        } else {

            while(dice[0] + dice[1] != guess && dice[0] + dice[1] != totalValue) {
            dice[0] = random.nextInt(1, 7);
            dice[1] = random.nextInt(1, 7);

            int chance = dice[0] + dice[1];
                if(totalValue == chance) {
                    won = true;
                }
            }
        }

        //Result
        if(won) {
            int wonMoney = player.money += moneyToGamble * 2;
            player.setMoney(wonMoney);
        } else {
            player.negateMoney(moneyToGamble);
        }
    }

}
