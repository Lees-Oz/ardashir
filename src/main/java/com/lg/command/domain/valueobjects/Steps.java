package com.lg.command.domain.valueobjects;

import java.util.ArrayList;

public class Steps extends ArrayList<Integer> {

    public Steps() {
    }

    public Steps(Dice dice) {
        if (dice.getOne() == dice.getTwo()) {
            for (int i = 0; i < 4; i++) {
                this.add(dice.getOne());
            }
        } else {
            this.add(dice.getOne());
            this.add(dice.getTwo());
        }
    }
}
