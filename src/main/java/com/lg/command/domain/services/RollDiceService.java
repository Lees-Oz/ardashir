package com.lg.command.domain.services;

import com.lg.command.domain.valueobjects.Dice;

import java.util.Random;

public class RollDiceService implements RollDice {
    public Dice roll(){
        Random random = new Random();
        return new Dice(random.nextInt(6) + 1, random.nextInt(6) + 1);
    }
}
