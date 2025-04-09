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

class StraightFlushTest {
	@Test
	void constructor_shouldThrowException_IfNoStraightFlushPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new StraightFlush(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddStraightFlushToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS)
		);
		StraightFlush result = new StraightFlush(cards);

		assertThat(result.getCards()).contains(
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.DIAMONDS));
	}


	@Test
	void StraightWithAceIsGreaterThanFourOfAKindWithSeven() {
		StraightFlush straightFlushWithSeven = new StraightFlush(HandFixtures.straightFlushWithSeven());
		StraightFlush straightFlushWithAce = new StraightFlush(HandFixtures.straightFlushWithKing());

		assertThat(straightFlushWithAce.compareTo(straightFlushWithSeven)).isPositive();
		assertThat(straightFlushWithSeven.compareTo(straightFlushWithAce)).isNegative();
	}
}