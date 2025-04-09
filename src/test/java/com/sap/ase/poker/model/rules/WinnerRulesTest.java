package com.sap.ase.poker.model.rules;

import com.sap.ase.poker.fixtures.HandFixtures;
import com.sap.ase.poker.fixtures.PlayerFixtures;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;
import com.sap.ase.poker.model.hands.HighCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class WinnerRulesTest {
	WinnerRules underTest = new WinnerRules(new HandRules());

	@Test
	void findWinners_shouldCombineCommunityCardsWithHandCards() {
		Player alWithHighCard = PlayerFixtures.AL_CAPONE();

		List<Card> highCardOfKing = HandFixtures.highCardOfKing();
		List<Card> handCards = highCardOfKing.subList(0, 2);
		List<Card> communityCards = highCardOfKing.subList(2, highCardOfKing.size());
		alWithHighCard.setHandCards(handCards);

		List<Player> players = Arrays.asList(alWithHighCard);
		List<Winner> winners = underTest.findWinners(communityCards, players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(alWithHighCard);
		assertThat(winners.get(0).getHand()).isInstanceOf(HighCard.class);
	}

	@Test
	void whenPairVsHighCard_findWinners_shouldReturnPlayerWithPair() {
		Player alWithHighCard = PlayerFixtures.AL_CAPONE();
		Player patWithPair = PlayerFixtures.PAT_GARRETT();

		alWithHighCard.setHandCards(HandFixtures.highCardOfKing());
		patWithPair.setHandCards(HandFixtures.pairOfSevens());

		List<Player> players = Arrays.asList(patWithPair, alWithHighCard);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(patWithPair);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenFourOfAKindVsPair_findWinners_shouldReturnPlayerWithFourOfAKind() {
		Player alWithFourOfAKind = PlayerFixtures.AL_CAPONE();
		Player patWithPair = PlayerFixtures.PAT_GARRETT();

		alWithFourOfAKind.setHandCards(HandFixtures.fourOfAKindOfSevens());
		patWithPair.setHandCards(HandFixtures.pairOfSevens());

		List<Player> players = Arrays.asList(patWithPair, alWithFourOfAKind);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(alWithFourOfAKind);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenSamePairsCompete_findWinners_shouldReturnBothPlayers() {
		Player alWithPair = PlayerFixtures.AL_CAPONE();
		Player patWithPair = PlayerFixtures.PAT_GARRETT();

		alWithPair.setHandCards(HandFixtures.pairOfRedJacks());
		patWithPair.setHandCards(HandFixtures.pairOfBlackJacks());

		List<Player> players = Arrays.asList(patWithPair, alWithPair);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.stream().map(w -> w.getPlayer())).contains(alWithPair, patWithPair);
	}

	@Test
	void whenHighCardAceVsHighCardKing_findWinners_shouldReturnPlayerWithHighCardAce() {
		Player alWithHighCardAce = PlayerFixtures.AL_CAPONE();
		Player patWithHighCardKing = PlayerFixtures.PAT_GARRETT();

		alWithHighCardAce.setHandCards(HandFixtures.highCardOfAce());
		patWithHighCardKing.setHandCards(HandFixtures.highCardOfKing());

		List<Player> players = Arrays.asList(alWithHighCardAce, patWithHighCardKing);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(alWithHighCardAce);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenPairOfSevensVsPairOfNines_findWinners_shouldReturnPlayerWithPairOfSevens() {
		Player alWithPairOfSevens = PlayerFixtures.AL_CAPONE();
		Player patWithPairOfNines = PlayerFixtures.PAT_GARRETT();

		alWithPairOfSevens.setHandCards(HandFixtures.pairOfSevens());
		patWithPairOfNines.setHandCards(HandFixtures.pairOfNines());

		List<Player> players = Arrays.asList(patWithPairOfNines, alWithPairOfSevens);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(patWithPairOfNines);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenStraightVsThreeOfAKind_findWinners_shouldReturnPlayerWithStraight() {
		Player alWithThreeAces = PlayerFixtures.AL_CAPONE();
		Player patWithFlush = PlayerFixtures.PAT_GARRETT();

		alWithThreeAces.setHandCards(HandFixtures.threeOfAKindOfAces());
		patWithFlush.setHandCards(HandFixtures.FlushWithKing());

		List<Player> players = Arrays.asList(patWithFlush, alWithThreeAces);
		List<Winner> winners = underTest.findWinners(Collections.emptyList(), players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(patWithFlush);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenPairInCommunityCards_findWinners_shouldReturnPlayerWithHighCard() {
		Player alWithAce = PlayerFixtures.AL_CAPONE();
		Player patWithLowCards = PlayerFixtures.PAT_GARRETT();

		alWithAce.setHandCards(HandFixtures.handWithAce());
		patWithLowCards.setHandCards(HandFixtures.handWithLowCards());

		List<Card> communityCards = HandFixtures.communityCardsWithPairOfTens();

		List<Player> players = Arrays.asList(alWithAce, patWithLowCards);
		List<Winner> winners = underTest.findWinners(communityCards, players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(alWithAce);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenBothHaveThreeOfAKind_findWinners_shouldReturnPlayerWithHigherKickerCard() {
		Player alWithHighCard = PlayerFixtures.AL_CAPONE();
		Player patWithLowCard = PlayerFixtures.PAT_GARRETT();

		alWithHighCard.setHandCards(HandFixtures.handWithTenAndHighCard());
		patWithLowCard.setHandCards(HandFixtures.handWithTenAndLowCard());

		List<Card> communityCards = HandFixtures.communityCardsWithPairOfTens();

		List<Player> players = Arrays.asList(alWithHighCard, patWithLowCard);
		List<Winner> winners = underTest.findWinners(communityCards, players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(alWithHighCard);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenTwoPairsVsTwoPairs_findWinners_shouldReturnPlayerWithPairOfKings() {
		Player alWithTwoPairsOfTensAndThrees = PlayerFixtures.AL_CAPONE();
		Player patWithTwoPairsOfKingsAndTens = PlayerFixtures.PAT_GARRETT();

		alWithTwoPairsOfTensAndThrees.setHandCards(Arrays.asList(
				new Card(Rank.SIX, Suit.HEARTS),
				new Card(Rank.FOUR, Suit.SPADES)));
		patWithTwoPairsOfKingsAndTens.setHandCards(Arrays.asList(
				new Card(Rank.NINE, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.CLUBS)));

		List<Card> communityCards = Arrays.asList(
				new Card(Rank.TEN, Suit.DIAMONDS),
				new Card(Rank.KING, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.HEARTS),
				new Card(Rank.TEN, Suit.HEARTS),
				new Card(Rank.THREE, Suit.CLUBS));

		List<Player> players = Arrays.asList(alWithTwoPairsOfTensAndThrees, patWithTwoPairsOfKingsAndTens);
		List<Winner> winners = underTest.findWinners(communityCards, players);

		assertThat(winners.get(0).getPlayer()).isEqualTo(patWithTwoPairsOfKingsAndTens);
		assertThat(winners).hasSize(1);
	}

	@Test
	void whenTwoWinners_findWinners_canReturnDifferentHands() {
		Player alWithPair = PlayerFixtures.AL_CAPONE();
		Player patWithPair = PlayerFixtures.PAT_GARRETT();

		alWithPair.setHandCards(Arrays.asList(
				new Card(Rank.TWO, Suit.SPADES),
				new Card(Rank.TWO, Suit.CLUBS)));
		patWithPair.setHandCards(Arrays.asList(
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.TWO, Suit.HEARTS)));

		List<Card> communityCards = Arrays.asList(
				new Card(Rank.SEVEN, Suit.SPADES),
				new Card(Rank.EIGHT, Suit.SPADES),
				new Card(Rank.NINE, Suit.CLUBS),
				new Card(Rank.TEN, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.DIAMONDS));		

		List<Winner> winners = underTest.findWinners(communityCards, Arrays.asList(alWithPair, patWithPair));

		assertThat(winners).hasSize(2);
		assertThat(winners.get(0).getHand()).isNotEqualTo(winners.get(1).getHand());
	}

	@Test
	void whenTwoWinners_findWinners_canReturnSameHands() {
		Player alWithPair = PlayerFixtures.AL_CAPONE();
		Player patWithPair = PlayerFixtures.PAT_GARRETT();

		alWithPair.setHandCards(Arrays.asList(
				new Card(Rank.TWO, Suit.SPADES),
				new Card(Rank.THREE, Suit.CLUBS)));
		patWithPair.setHandCards(Arrays.asList(
				new Card(Rank.TWO, Suit.DIAMONDS),
				new Card(Rank.THREE, Suit.HEARTS)));

		List<Card> communityCards = Arrays.asList(
				new Card(Rank.SEVEN, Suit.SPADES),
				new Card(Rank.EIGHT, Suit.SPADES),
				new Card(Rank.NINE, Suit.CLUBS),
				new Card(Rank.TEN, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.DIAMONDS));		

		List<Winner> winners = underTest.findWinners(communityCards, Arrays.asList(alWithPair, patWithPair));

		assertThat(winners).hasSize(2);
		assertThat(winners.get(0).getHand()).isEqualTo(winners.get(1).getHand());
	}
}
