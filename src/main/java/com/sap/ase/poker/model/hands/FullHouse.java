package com.sap.ase.poker.model.hands;

import com.sap.ase.poker.model.InvalidHandException;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FullHouse extends Hand {

	private static final int FULL_HOUSE_RANK = 7;

	public FullHouse(List<Card> cards) {
		super(cards);
	}

	@Override
	public int getRank() {
		return FULL_HOUSE_RANK;
	}

	@Override
	protected List<Card> findRelevantCards(List<Card> cards) {
		return findFullHouse(cards);
	}

	@Override
	protected int compareRelevantCards(Hand hand) {
		ThreeOfAKind threeOfAKind = new ThreeOfAKind(cards);
		ThreeOfAKind otherThreeOfAKind = new ThreeOfAKind(hand.getRelevantCards());

		return threeOfAKind.compareTo(otherThreeOfAKind);
	}

	private List<Card> findFullHouse(List<Card> cards) {
		Map<Rank, List<Card>> rankGroups = cards.stream().collect(Collectors.groupingBy(Card::getRank));
		List<Card> threeOfAKind =
				findHighestTreeOfAKind(rankGroups)
						.orElseThrow(() -> new InvalidHandException("FullHouse Not Found"));

		rankGroups.remove(threeOfAKind.get(0).getRank());

		List<Card> pair =
				findHighestPair(rankGroups)
						.orElseThrow(() -> new InvalidHandException("FullHouse Not Found"));

		threeOfAKind.addAll(pair.subList(0, 2));
		return threeOfAKind;
	}

	private Optional<List<Card>> findHighestPair(Map<Rank, List<Card>> rankGroups) {
		return rankGroups.values().stream().filter(group -> group.size() >= 2)
				.max(Comparator.comparing(list -> list.get(0)));
	}

	private Optional<List<Card>> findHighestTreeOfAKind(Map<Rank, List<Card>> rankGroups) {
		return rankGroups.values().stream().filter(group -> group.size() == 3)
				.max(Comparator.comparing(list -> list.get(0)));
	}
}
