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

public class ThreeOfAKindTest {
	@Test
	void constructor_shouldThrowException_IfNoThreeOfAKindPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new ThreeOfAKind(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddThreeOfAKindToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
		ThreeOfAKind result = new ThreeOfAKind(cards);

		assertThat(result.getCards()).contains(
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS));
	}

	@Test
	void constructor_shouldAddHighestFillerCardToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
		ThreeOfAKind result = new ThreeOfAKind(cards);

		assertThat(result.getCards()).contains(new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS));
	}

	@Test
	void threeOfAKindOfAcesIsGreaterThanFourOfAKindOfSevens() {
		ThreeOfAKind threeOfAKindOfSevens = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());
		ThreeOfAKind threeOfAKindOfAces = new ThreeOfAKind(HandFixtures.threeOfAKindOfAces());

		assertThat(threeOfAKindOfAces.compareTo(threeOfAKindOfSevens)).isPositive();
		assertThat(threeOfAKindOfSevens.compareTo(threeOfAKindOfAces)).isNegative();
	}

	@Test
	void sameHandsShouldBeEqual() {
		ThreeOfAKind threeOfAKindOfSevens1 = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());
		ThreeOfAKind threeOfAKindOfSevens2 = new ThreeOfAKind(HandFixtures.threeOfAKindOfSevens());

		assertThat(threeOfAKindOfSevens1.compareTo(threeOfAKindOfSevens2)).isZero();
	}
}
