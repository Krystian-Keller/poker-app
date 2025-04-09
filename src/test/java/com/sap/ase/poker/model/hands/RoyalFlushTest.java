package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RoyalFlushTest {

	private final List<Card> royalFlushOne = Arrays.asList(
			new Card(Rank.KING, Suit.DIAMONDS),
			new Card(Rank.SIX, Suit.DIAMONDS),
			new Card(Rank.QUEEN, Suit.DIAMONDS),
			new Card(Rank.SEVEN, Suit.HEARTS),
			new Card(Rank.JACK, Suit.DIAMONDS),
			new Card(Rank.TEN, Suit.DIAMONDS),
			new Card(Rank.ACE, Suit.DIAMONDS)
	);
	private final List<Card> royalFlushTwo = Arrays.asList(
			new Card(Rank.KING, Suit.SPADES),
			new Card(Rank.SIX, Suit.SPADES),
			new Card(Rank.QUEEN, Suit.SPADES),
			new Card(Rank.SEVEN, Suit.HEARTS),
			new Card(Rank.JACK, Suit.SPADES),
			new Card(Rank.TEN, Suit.SPADES),
			new Card(Rank.ACE, Suit.SPADES)
	);

	@Test
	void constructor_shouldThrowException_IfNoRoyalFlushPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new RoyalFlush(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddRoyalFlushToCards() {
		RoyalFlush result = new RoyalFlush(royalFlushOne);

		assertThat(result.getCards()).contains(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.QUEEN, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS));
	}

	@Test
	void compareRelevantCards_alwaysReturnsZero() {
		int result = new RoyalFlush(royalFlushOne).compareTo(new RoyalFlush(royalFlushTwo));
		assertThat(result).isZero();
	}
}