package com.sap.ase.poker.dto;

import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

public class CardDto {
	private String suit;
	private String rank;

	public CardDto() {
	}

	public CardDto(Card card) {
		suit = suitToString(card.getSuit());
		rank = rankToString(card.getRank());
	}

	private String suitToString(Suit suit) {
		return suit.toString().toLowerCase();
	}

	private String rankToString(Rank rank) {
		switch (rank) {
			case ACE:
				return "ace";
			case TWO:
				return "2";
			case THREE:
				return "3";
			case FOUR:
				return "4";
			case FIVE:
				return "5";
			case SIX:
				return "6";
			case SEVEN:
				return "7";
			case EIGHT:
				return "8";
			case NINE:
				return "9";
			case TEN:
				return "10";
			case JACK:
				return "jack";
			case QUEEN:
				return "queen";
			case KING:
				return "king";
			default:
				throw new UnknownRankException(rank);
		}
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	private static class UnknownRankException extends RuntimeException {
		private static final long serialVersionUID = -2572535919726168818L;

		public UnknownRankException(Rank rank) {
			super("unknown rank: " + rank);
		}
	}
}
