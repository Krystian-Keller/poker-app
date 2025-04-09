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

class TwoPairsTest {
	@Test
	void constructor_shouldThrowException_IfNoTwoPairsPresent() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		assertThatThrownBy(() -> new TwoPairs(cards)).isInstanceOf(InvalidHandException.class);
	}

	@Test
	void constructor_shouldAddTwoPairsToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES)
		);
		TwoPairs result = new TwoPairs(cards);

		assertThat(result.getCards()).contains(
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES));
	}

	@Test
	void constructor_shouldAddHighestFillerCardToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES)
		);
		TwoPairs result = new TwoPairs(cards);

		assertThat(result.getCards()).contains(new Card(Rank.JACK, Suit.DIAMONDS));
	}

	@Test
	void twoPairsOfSevensAndAcesShouldBeGreaterThanTwoPairsOfSevensAndKings() {
		TwoPairs twoPairsOfSevensAndAces = new TwoPairs(HandFixtures.twoPairsOfSevensAndAces());
		TwoPairs twoPairsOfSevensAndKings = new TwoPairs(HandFixtures.twoPairsOfSevensAndKingsHearts());

		assertThat(twoPairsOfSevensAndAces.compareTo(twoPairsOfSevensAndKings)).isPositive();
		assertThat(twoPairsOfSevensAndKings.compareTo(twoPairsOfSevensAndAces)).isNegative();
	}

	@Test
	void twoPairsOfAcesAndSevensShouldBeGreaterThanTwoPairsOfAcesAndTwos() {
		TwoPairs twoPairsOfAcesAndSevens = new TwoPairs(HandFixtures.twoPairsOfSevensAndAces());
		TwoPairs twoPairsOfAcesAndTwos = new TwoPairs(HandFixtures.twoPairsOfAcesAndTwos());

		assertThat(twoPairsOfAcesAndSevens.compareTo(twoPairsOfAcesAndTwos)).isPositive();
		assertThat(twoPairsOfAcesAndTwos.compareTo(twoPairsOfAcesAndSevens)).isNegative();
	}

	@Test
	void twoPairsOfKingsAndSevensShouldBeEqual() {
		TwoPairs twoPairsOfSevensAndKingsSpades = new TwoPairs(HandFixtures.twoPairsOfSevensAndKingsSpades());
		TwoPairs twoPairsOfSevensAndKingsHearts = new TwoPairs(HandFixtures.twoPairsOfSevensAndKingsHearts());

		assertThat(twoPairsOfSevensAndKingsSpades.compareTo(twoPairsOfSevensAndKingsHearts)).isZero();
		assertThat(twoPairsOfSevensAndKingsHearts.compareTo(twoPairsOfSevensAndKingsSpades)).isZero();

	}
}

