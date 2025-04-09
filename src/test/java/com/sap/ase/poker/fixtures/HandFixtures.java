package com.sap.ase.poker.fixtures;

import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

import java.util.Arrays;
import java.util.List;

public class HandFixtures {
	public static List<Card> highCardOfAce() {
		return Arrays.asList(
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> highCardOfKing() {
		return Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.SPADES)
		);
	}

	public static List<Card> highCardOfJack() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.EIGHT, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.FOUR, Suit.DIAMONDS)
		);
	}

	public static List<Card> pairOfNines() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> pairOfSevens() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> invalidHandWith6Cards() {
		return Arrays.asList(
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> twoPairsOfSevensAndAces() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES)
		);
	}

	public static List<Card> twoPairsOfSevensAndKingsHearts() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.HEARTS)
		);
	}

	public static List<Card> twoPairsOfSevensAndKingsSpades() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.SPADES)
		);
	}

	public static List<Card> twoPairsOfAcesAndTwos() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES)
		);
	}

	public static List<Card> pairOfRedJacks() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> pairOfBlackJacks() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.SPADES),
				new Card(Rank.JACK, Suit.CLUBS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> threeOfAKindOfAces() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES)
		);
	}

	public static List<Card> threeOfAKindOfSevens() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES)
		);
	}

	public static List<Card> fourOfAKindOfAces() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
	}

	public static List<Card> fourOfAKindOfSevens() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.SEVEN, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
	}

	public static List<Card> straightWithSeven() {
		return Arrays.asList(
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.SIX, Suit.CLUBS),
				new Card(Rank.FIVE, Suit.SPADES),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS));
	}

	public static List<Card> straightWithAce() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.QUEEN, Suit.DIAMONDS)
		);
	}

	public static List<Card> straightFlushWithSeven() {
		return Arrays.asList(
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.FIVE, Suit.DIAMONDS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS));
	}

	public static List<Card> straightFlushWithKing() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.DIAMONDS),
				new Card(Rank.NINE, Suit.DIAMONDS),
				new Card(Rank.QUEEN, Suit.DIAMONDS)
		);
	}

	public static List<Card> royalFlush() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.TEN, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.QUEEN, Suit.DIAMONDS)
		);
	}

	public static List<Card> FlushWithSeven() {
		return Arrays.asList(
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.HEARTS));
	}

	public static List<Card> FlushWithKing() {
		return Arrays.asList(
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.SIX, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.FOUR, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS));
	}

	public static List<Card> fullHouseWithAces() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.SPADES),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
	}

	public static List<Card> fullHouseWithSevens() {
		return Arrays.asList(
				new Card(Rank.JACK, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.DIAMONDS),
				new Card(Rank.SEVEN, Suit.HEARTS),
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.SPADES),
				new Card(Rank.KING, Suit.DIAMONDS)
		);
	}

	public static List<Card> handWithAce() {
		return Arrays.asList(
				new Card(Rank.ACE, Suit.DIAMONDS),
				new Card(Rank.FOUR, Suit.CLUBS)
		);
	}

	public static List<Card> handWithLowCards() {
		return Arrays.asList(
				new Card(Rank.SEVEN, Suit.CLUBS),
				new Card(Rank.EIGHT, Suit.DIAMONDS)
		);
	}

	public static List<Card> handWithTenAndLowCard() {
		return Arrays.asList(
				new Card(Rank.TEN, Suit.SPADES),
				new Card(Rank.FOUR, Suit.CLUBS)
		);
	}

	public static List<Card> handWithTenAndHighCard() {
		return Arrays.asList(
				new Card(Rank.TEN, Suit.HEARTS),
				new Card(Rank.ACE, Suit.CLUBS)
		);
	}

	public static List<Card> communityCardsWithPairOfTens() {
		return Arrays.asList(
				new Card(Rank.TEN, Suit.CLUBS),
				new Card(Rank.TWO, Suit.CLUBS),
				new Card(Rank.TEN, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.SPADES),
				new Card(Rank.FIVE, Suit.DIAMONDS)
		);
	}
}
