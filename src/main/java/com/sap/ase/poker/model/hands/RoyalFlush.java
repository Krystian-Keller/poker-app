package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

import java.util.Arrays;
import java.util.List;

public class RoyalFlush extends Hand {
	private static final int ROYAL_FLUSH_RANK = 10;

	public RoyalFlush(List<Card> cards) {
		super(cards);
	}

	@Override
	public int getRank() {
		return ROYAL_FLUSH_RANK;
	}

	@Override
	protected List<Card> findRelevantCards(List<Card> cards) {
		StraightFlush straightFlush;
		try {
			straightFlush = new StraightFlush(cards);
		} catch (InvalidHandException exception) {
			throw new InvalidHandException("No royal flush present");
		}
		List<Card> relevantCards = straightFlush.getRelevantCards();
		if (cardsContainOnlyRoyalKinds(relevantCards)) {
			return relevantCards;
		}
		throw new InvalidHandException("No royal flush present");
	}

	private boolean cardsContainOnlyRoyalKinds(List<Card> relevantCards) {
		Suit suit = relevantCards.get(0).getSuit();

		return relevantCards.containsAll(Arrays.asList(
				new Card(Rank.ACE, suit),
				new Card(Rank.KING, suit),
				new Card(Rank.QUEEN, suit),
				new Card(Rank.JACK, suit),
				new Card(Rank.TEN, suit)
		));
	}

	@Override
	protected int compareRelevantCards(Hand hand) {
		return 0;
	}
}
