package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;
import com.sap.ase.poker.model.hands.Flush;
import com.sap.ase.poker.model.hands.FourOfAKind;
import com.sap.ase.poker.model.hands.FullHouse;
import com.sap.ase.poker.model.hands.Hand;
import com.sap.ase.poker.model.hands.HighCard;
import com.sap.ase.poker.model.hands.Pair;
import com.sap.ase.poker.model.hands.RoyalFlush;
import com.sap.ase.poker.model.hands.Straight;
import com.sap.ase.poker.model.hands.StraightFlush;
import com.sap.ase.poker.model.hands.ThreeOfAKind;
import com.sap.ase.poker.model.hands.TwoPairs;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HandRulesTest {

	private final HandRules underTest = new HandRules();

	@Test
	void whenOnlySixCardsPresent_findBestHand_shouldReturnException() {

		List<Card> cards = HandFixtures.invalidHandWith6Cards();

		assertThatThrownBy(() -> underTest.findBestHand(cards)).isInstanceOf(
				InvalidAmountOfCardsException.class);
	}

	@Test
	void whenOnlyHighCardKingPresent_findBestHand_shouldReturnHighCardWithKing() {
		List<Card> cards = HandFixtures.highCardOfKing();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES));
		assertThat(hand).isInstanceOf(HighCard.class);
	}

	@Test
	void whenOnlyHighCardAcePresent_findBestHand_shouldReturnHighCardWithAce() {
		List<Card> cards = HandFixtures.highCardOfAce();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES));
		assertThat(hand).isInstanceOf(HighCard.class);
	}

	@Test
	void whenOnlyTwoSevensPresent_findBestHand_shouldReturnPairOfSeven() {
		List<Card> cards = HandFixtures.pairOfSevens();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES));
		assertThat(hand).isInstanceOf(Pair.class);
	}

	@Test
	void whenTwoSevensAndTwoAcesPresent_findBestHand_shouldReturnTwoPairsOfSevenAndAce() {
		List<Card> cards = HandFixtures.twoPairsOfSevensAndAces();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.SPADES));
		assertThat(hand).isInstanceOf(TwoPairs.class);
	}

	@Test
	void whenThreeSevensPresent_findBestHand_shouldReturnThreeOfAKindOfSevens() {
		List<Card> cards = HandFixtures.threeOfAKindOfSevens();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(ThreeOfAKind.class);
	}

	@Test
	void whenFourSevensPresent_findBestHand_shouldReturnFourOfAKindOfSevens() {
		List<Card> cards = HandFixtures.fourOfAKindOfSevens();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.JACK, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(FourOfAKind.class);
	}

	@Test
	void whenStraightPresent_findBestHand_shouldReturnStraight() {
		List<Card> cards = HandFixtures.straightWithAce();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(Straight.class);
	}

	@Test
	void whenFlushPresent_findBestHand_shouldReturnFlush() {
		List<Card> cards = HandFixtures.FlushWithKing();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.TWO, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(Flush.class);
	}

	@Test
	void whenFullHousePresent_findBestHand_shouldReturnFullHouse() {
		List<Card> cards = HandFixtures.fullHouseWithSevens();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.THREE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(FullHouse.class);
	}

	@Test
	void whenStraightFlushPresent_findBestHand_shouldReturnStraightFlush() {
		List<Card> cards = HandFixtures.straightFlushWithKing();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.ACE, Suit.SPADES),
				new Card(Rank.ACE, Suit.HEARTS));
		assertThat(hand).isInstanceOf(StraightFlush.class);
	}

	@Test
	void whenRoyalFlushPresent_findBestHand_shouldReturnRoyalFlush() {
		List<Card> cards = HandFixtures.royalFlush();

		Hand hand = underTest.findBestHand(cards);

		assertThat(hand.getCards()).hasSize(5);
		assertThat(hand.getCards()).doesNotContain(new Card(Rank.ACE, Suit.HEARTS),
				new Card(Rank.THREE, Suit.DIAMONDS));
		assertThat(hand).isInstanceOf(RoyalFlush.class);
	}
}
