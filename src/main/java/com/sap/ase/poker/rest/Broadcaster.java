package com.sap.ase.poker.rest;

import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.function.Function;

@Component
public class Broadcaster<Id, Type> {
    private final HashMap<Id, Sinks.Many<Type>> sinks = new HashMap<>();

    public void join(Id id) {
        if (!sinks.containsKey(id)) {
            sinks.put(id, Sinks.many().replay().all());
        }
    }

    public Flux<Type> asFlux(Id id) {
        return sinks.get(id).asFlux();
    }

    public void broadcast(Function<Id, Type> messageBuilder) {
        sinks.keySet().forEach(id -> sinks.get(id).tryEmitNext(messageBuilder.apply(id)));
    }
}
