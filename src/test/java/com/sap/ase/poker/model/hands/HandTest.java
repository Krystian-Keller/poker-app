package com.sap.ase.poker.model.hands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

class HandTest {
    @Test
    void equals_whenDifferent() {
        Hand hand1 = new Pair(List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        Hand hand2 = new Pair(List.of(new Card(Rank.ACE, Suit.SPADES), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        assertNotEquals(hand1, hand2);
    }

    @Test
    void equals_whenDifferentOrder() {
        Hand hand1 = new Pair(List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        Hand hand2 = new Pair(List.of(new Card(Rank.ACE, Suit.DIAMONDS), new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        assertEquals(hand1, hand2);
    }

    @Test
    void equals_whenSame() {
        Hand hand1 = new Pair(List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        assertEquals(hand1, hand1);
    }

    @Test
    void equals_whenOneNull() {
        Hand hand1 = new Pair(List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        assertNotEquals(hand1, null);
    }

    @Test
    void equals_whenOneDifferentClass() {
        Hand hand1 = new Pair(List.of(new Card(Rank.ACE, Suit.HEARTS), new Card(Rank.ACE, Suit.DIAMONDS),
                new Card(Rank.KING, Suit.CLUBS), new Card(Rank.QUEEN, Suit.SPADES), new Card(Rank.JACK, Suit.HEARTS)));
        assertNotEquals(hand1, new Object());
    }
}
