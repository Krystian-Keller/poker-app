package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThreeOfAKind extends Hand {

	public static final int THREE_OF_A_KIND_RANK = 4;

	public ThreeOfAKind(List<Card> cards) {
		super(cards);
	}

	@Override
	public int getRank() {
		return THREE_OF_A_KIND_RANK;
	}

	@Override
	protected List<Card> findRelevantCards(List<Card> cards) {
		return findThreeOfAKind(cards);
	}

	@Override
	protected int compareRelevantCards(Hand hand) {
		int rank = this.getRelevantCards().get(0).getRank().getRank();
		int otherRank = hand.getRelevantCards().get(0).getRank().getRank();

		return rank - otherRank;
	}

	private List<Card> findThreeOfAKind(List<Card> cards) {
		Map<Rank, List<Card>> rankGroups = cards.stream().collect(Collectors.groupingBy(Card::getRank));
		Optional<List<Card>> quadruple =
				rankGroups.values().stream().filter(group -> group.size() == 3).findFirst();

		return quadruple.orElseThrow(() -> new InvalidHandException("ThreeOfAKind Not Found"));
	}
}
