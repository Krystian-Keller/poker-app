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

class FourOfAKindTest {
	@Test
	void constructor_shouldThrowException_IfNoFourOfAKindPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new FourOfAKind(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddFourOfAKindToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.SEVEN, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
		FourOfAKind result = new FourOfAKind(cards);

		assertThat(result.getCards()).contains(new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.SEVEN, Suit.SPADES));
	}

	@Test
	void constructor_shouldAddHighestFillerCardToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.SEVEN, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
		FourOfAKind result = new FourOfAKind(cards);

		assertThat(result.getCards()).contains(new Card(Rank.KING, Suit.DIAMONDS));
		assertThat(result.getCards()).hasSize(5);
	}

	@Test
	void fourOfAKindOfAcesIsGreaterThanFourOfAKindOfSevens() {
		FourOfAKind fourOfAKindOfSevens = new FourOfAKind(HandFixtures.fourOfAKindOfSevens());
		FourOfAKind fourOfAKindOfAces = new FourOfAKind(HandFixtures.fourOfAKindOfAces());

		assertThat(fourOfAKindOfAces.compareTo(fourOfAKindOfSevens)).isPositive();
		assertThat(fourOfAKindOfSevens.compareTo(fourOfAKindOfAces)).isNegative();
	}
}