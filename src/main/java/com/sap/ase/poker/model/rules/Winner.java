package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.hands.Hand;

public class Winner {
    private final Player player;
    private final Hand hand;

    public Winner(Player player, Hand hand) {
        this.player = player;
        this.hand = hand;
    }

    public Hand getHand() {
        return hand;
    }

    public Player getPlayer() {
        return player;
    }
}
