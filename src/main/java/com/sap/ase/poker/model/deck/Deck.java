package com.sap.ase.poker.model.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Deck {
    private List<Card> cards = shuffle();

    public Card draw() {
        if (cards.isEmpty()) {
            throw new OutOfCardsException();
        }
        return cards.remove(0);
    }

    public List<Card> shuffle() {
        cards = create52Cards();
        Collections.shuffle(cards);
        return cards;
    }

    private List<Card> create52Cards() {
        List<Card> pokerCards = new ArrayList<>();

		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				pokerCards.add(new Card(rank, suit));
			}
		}
        return pokerCards;
    }

    public static class OutOfCardsException extends RuntimeException {
        private static final long serialVersionUID = -3445884916921731847L;
    }
}
