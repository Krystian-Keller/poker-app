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

class StraightTest {
	@Test
	void constructor_shouldThrowException_IfNoStraightPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new Straight(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddStraightToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		Straight result = new Straight(cards);

		assertThat(result.getCards()).contains(
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS));
	}


	@Test
	void StraightWithAceIsGreaterThanFourOfAKindWithSeven() {
		Straight straightWithSeven = new Straight(HandFixtures.straightWithSeven());
		Straight straightWithAce = new Straight(HandFixtures.straightWithAce());

		assertThat(straightWithAce.compareTo(straightWithSeven)).isPositive();
		assertThat(straightWithSeven.compareTo(straightWithAce)).isNegative();
	}
}