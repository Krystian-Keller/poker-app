package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class HighCardTest {

	@Test
	void constructor_shouldAddHighestFillerCardToCards() {
		List<Card> cards = Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
		Hand result = new HighCard(cards);

		assertThat(result.getCards()).containsOnly(new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS));
		assertThat(result).isInstanceOf(HighCard.class);
	}

	@Test
	void HighCardAceIsGreaterThanHighCardKing() {
		HighCard highCardAce = new HighCard(HandFixtures.highCardOfAce());
		HighCard highCardKing = new HighCard(HandFixtures.highCardOfKing());

		assertThat(highCardAce.compareTo(highCardKing)).isPositive();
		assertThat(highCardKing.compareTo(highCardAce)).isNegative();
	}

	@Test
	void SameHighCardsAreEqual() {
		HighCard highCardAce1 = new HighCard(HandFixtures.highCardOfAce());
		HighCard highCardAce2 = new HighCard(HandFixtures.highCardOfAce());

		assertThat(highCardAce1.compareTo(highCardAce2)).isZero();
	}

	@Test
	void highCardsShouldBeSortedByRelevantCards() {
		HighCard highCardAce = new HighCard(HandFixtures.highCardOfAce());
		HighCard highCardJack = new HighCard(HandFixtures.highCardOfJack());
		HighCard highCardKing = new HighCard(HandFixtures.highCardOfKing());

		List<HighCard> highCards = Arrays.asList(highCardAce, highCardJack, highCardKing);

		highCards.sort(Collections.reverseOrder());

		assertThat(highCards.get(0)).isEqualTo(highCardAce);
		assertThat(highCards.get(1)).isEqualTo(highCardKing);
		assertThat(highCards.get(2)).isEqualTo(highCardJack);
	}
}

