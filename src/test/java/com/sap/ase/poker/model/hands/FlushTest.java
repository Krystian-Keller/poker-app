package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FlushTest {
	@Test
	void constructor_shouldThrowException_IfNoFlushPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new Flush(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddFlushToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS)
		);
		Flush result = new Flush(cards);

		assertThat(result.getCards()).contains(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS));
	}


	@Test
	void FlushWithAceIsGreaterThanFourOfAKindWithSeven() {
		Flush FlushWithSeven = new Flush(HandFixtures.FlushWithSeven());
		Flush FlushWithAce = new Flush(HandFixtures.FlushWithKing());

		assertThat(FlushWithAce.compareTo(FlushWithSeven)).isPositive();
		assertThat(FlushWithSeven.compareTo(FlushWithAce)).isNegative();
	}
}