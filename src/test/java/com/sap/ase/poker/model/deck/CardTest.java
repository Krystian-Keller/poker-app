package com.sap.ase.poker.model.deck;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CardTest {

	@Test
	void kingIsGreaterThanSeven() {
		Card king = new Card(Rank.KING, Suit.DIAMONDS);
		Card seven = new Card(Rank.SEVEN, Suit.DIAMONDS);

		assertThat(king.compareTo(seven)).isPositive();
	}

	@Test
	void kingsAreEven() {
		Card kingDiamond = new Card(Rank.KING, Suit.DIAMONDS);
		Card kingHeart = new Card(Rank.KING, Suit.HEARTS);

		assertThat(kingDiamond.compareTo(kingHeart)).isZero();
	}

	@Test
	void sevenIsSmallerThanKing() {
		Card seven = new Card(Rank.SEVEN, Suit.DIAMONDS);
		Card king = new Card(Rank.KING, Suit.DIAMONDS);

		assertThat(seven.compareTo(king)).isNegative();
	}

	@Test
	void toString_returnSuitThenRank() {
		Card kingDiamond = new Card(Rank.KING, Suit.DIAMONDS);

		String result = kingDiamond.toString();

		assertThat(result).containsOnlyOnce(kingDiamond.getRank().toString());
		assertThat(result).containsOnlyOnce(kingDiamond.getSuit().toString());
	}

	@Test
	void equals_worksForSameObject() {
		Card kingDiamond = new Card(Rank.KING, Suit.DIAMONDS);
		Card kingDiamond2 = new Card(Rank.KING, Suit.DIAMONDS);
		Card sevenDiamond = new Card(Rank.SEVEN, Suit.DIAMONDS);
		Card kingHearts = new Card(Rank.KING, Suit.HEARTS);

		assertThat(kingDiamond.equals(kingDiamond)).isTrue();
		assertThat(kingDiamond.equals(null)).isFalse();
		assertThat(kingDiamond.equals("null")).isFalse();
		assertThat(kingDiamond.equals(kingDiamond2)).isTrue();
		assertThat(kingDiamond.equals(sevenDiamond)).isFalse();
		assertThat(kingDiamond.equals(kingHearts)).isFalse();
	}
}