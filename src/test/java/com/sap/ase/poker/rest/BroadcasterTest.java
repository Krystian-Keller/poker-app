package com.sap.ase.poker.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import reactor.core.publisher.Flux;

public class BroadcasterTest {
    public Broadcaster<String, String> underTest;

    @BeforeEach
    void setUp() {
        underTest = new Broadcaster<>();
    }

    @Test
    public void shouldThrow_ifSubscriberNotRegistered() {
        Assertions.assertThatThrownBy(() -> underTest.asFlux("not-existing"))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @Timeout(1)
    void oneSubscriber_willReceiveMessage() {
        underTest.join("subscriber1");
        Flux<String> subscriber = underTest.asFlux("subscriber1");
        underTest.broadcast(id -> "test");
        verifyMessageWasReceived(subscriber, "test");
    }

    @Test
    @Timeout(1)
    void canSendSubscriberSpecificMessage() {
        underTest.join("subscriber1");
        Flux<String> subscriber = underTest.asFlux("subscriber1");
        underTest.broadcast(id -> "message for " + id);
        verifyMessageWasReceived(subscriber, "message for subscriber1");
    }

    @Test
    @Timeout(4)
    public void subscribingLate_willStillReceiveMessage() {
        underTest.join("subscriber1");
        underTest.broadcast(id -> "message");
        Flux<String> lateSubscriber = underTest.asFlux("subscriber1");
        verifyMessageWasReceived(lateSubscriber, "message");
    }

    @Test
    @Timeout(1)
    void joiningMultipleTimesWithSameSubscriberID_causesNoTrouble() {
        underTest.join("subscriber1");
        Flux<String> subscriber = underTest.asFlux("subscriber1");
        underTest.join("subscriber1");
        underTest.broadcast(id -> "test");
        verifyMessageWasReceived(subscriber, "test");
    }

    @Test
    @Timeout(1)
    void multipleSubscribers_allWillReceiveMessage() {
        underTest.join("subscriber1");
        underTest.join("subscriber2");
        underTest.broadcast(id -> "test");
        verifyMessageWasReceived(underTest.asFlux("subscriber1"), "test");
        verifyMessageWasReceived(underTest.asFlux("subscriber2"), "test");
    }

    @Test
    void broadcastToAll_sendsDifferentMessageToEachSink() {
        underTest.join("subscriber1");
        underTest.join("subscriber2");
        underTest.broadcast((id) -> "message for " + id);
        verifyMessageWasReceived(underTest.asFlux("subscriber1"), "message for subscriber1");
        verifyMessageWasReceived(underTest.asFlux("subscriber2"), "message for subscriber2");
    }

    private void verifyMessageWasReceived(Flux<String> subscriber, String message) {
        List<String> messages = subscriber.take(1).collectList().block();
        assertEquals(1, messages.size());
        assertEquals(message, messages.get(0));
    }
}
