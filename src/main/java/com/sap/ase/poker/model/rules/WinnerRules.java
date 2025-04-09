package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.hands.Hand;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WinnerRules {
    private final HandRules handRules;

    public WinnerRules(HandRules handRules) {
        this.handRules = handRules;
    }

    public List<Winner> findWinners(List<Card> communityCards, List<Player> activePlayers) {
        List<Winner> winners = activePlayers.stream().map(p -> {
            Hand bestHand = handRules.findBestHand(combineCards(communityCards, p.getHandCards()));
            return new Winner(p, bestHand);
        }).sorted((w1, w2) -> w2.getHand().compareTo(w1.getHand())).toList();

        Hand bestHand = winners.get(0).getHand();
        return winners.stream().filter(w -> w.getHand().compareTo(bestHand) == 0).toList();
    }

    private List<Card> combineCards(List<Card> communityCards, List<Card> handCards) {
        List<Card> availableCards = new ArrayList<>(handCards);
        availableCards.addAll(communityCards);
        return availableCards;
    }
}
