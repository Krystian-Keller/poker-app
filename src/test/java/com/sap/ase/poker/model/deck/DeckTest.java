package com.sap.ase.poker.model.deck;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sap.ase.poker.model.deck.Deck.OutOfCardsException;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();
    }

    @Test
    void canDrawACard() {
        assertThat(deck.draw()).isInstanceOf(Card.class);
    }

    @Test
    void isShuffledInitially() {
        Deck deck2 = new Deck();
        int numberOfEqualCards = 0;
        for (int i = 0; i < 52; i++) {
            numberOfEqualCards += deck.draw().equals(deck2.draw()) ? 1 : 0;
        }
        assertThat(numberOfEqualCards).isLessThan(20);
    }

    @Test
    void canDraw52TimesAndAllCardsShouldBeUnique() {
        List<Card> cards = drawAll52Cards(deck);
        assertThat(cards).doesNotHaveDuplicates();
    }

    @Test
    void drawing53TimesShouldThrowAnError() {
        drawAll52Cards(deck);
        assertThatThrownBy(() -> deck.draw()).isInstanceOf(OutOfCardsException.class);
    }

    @Test
    void shuffleResetsDeck() {
        drawAll52Cards(deck);
        deck.shuffle();
        drawAll52Cards(deck);
    }

    @Test
    void shufflingChangesTheOrderOfCards() {
        // Yes, I know...it could fail - with a probability of 1/52! to be precise.
        // I'd rather accept it instead of overcomplicating things here.
        List<Card> cards1 = drawAll52Cards(deck);
        deck.shuffle();
        List<Card> cards2 = drawAll52Cards(deck);
        assertThat(cards1).isNotEqualTo(cards2);
    }

    private List<Card> drawAll52Cards(Deck deck) {
        return IntStream.range(0, 52).mapToObj(Void -> deck.draw()).toList();
    }
}