package com.sap.ase.poker.model;

/*
 * This class is internally used to identify insufficient players. Example:
 * Starting the game with only one player is invalid. This is an illegal
 * usage from the client, not a server error.
 */
public class InsufficientPlayersException extends RuntimeException {
    public InsufficientPlayersException(String message) {
        super(message);
    }
}