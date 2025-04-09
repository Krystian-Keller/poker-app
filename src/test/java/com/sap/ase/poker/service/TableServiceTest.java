package com.sap.ase.poker.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.InsufficientPlayersException;
import com.sap.ase.poker.model.InvalidPlayerNameException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Deck;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

class TableServiceTest {
    // TODO: implement me
    TableService tableService;

    @BeforeEach
    void setUp() {
        List<Player> players = new ArrayList<Player>();
        Deck deck = new Deck();
        this.tableService = new TableService(deck, players);
    }

    @Test
    void testInitialGameState() {
        assertEquals(GameState.OPEN, this.tableService.getState());
    }

    // Start Method
    @Test
    void testPreFlopGameState() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();
        assertEquals(GameState.PRE_FLOP, this.tableService.getState());
    }

    @Test
    void testStartGameWithLessThanTwoPlayers() {
        
        assertThrows(InsufficientPlayersException.class, () -> {
            tableService.start();
        });
    }

    @Test
    void TestCardsAmountPerPlayer() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        assertEquals(2, tableService.getPlayerCards("id").size());
        assertEquals(2, tableService.getPlayerCards("id2").size());
    }

    @Test
    void TestStartPlayersState() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        assertEquals(true, tableService.getPlayers().get(0).isActive());
        assertEquals(true, tableService.getPlayers().get(1).isActive());
    }

    // Current Player Method (getCurrentPlayer) 
    @Test
    void testGetCurrentPlayer() {
        this.tableService.addPlayer("id", "name");
        this.tableService.addPlayer("id2", "name2");

        this.tableService.currentPlayer = this.tableService.getPlayers().get(0);
        assertEquals(this.tableService.getPlayers().get(0), tableService.getCurrentPlayer());

        this.tableService.currentPlayer = this.tableService.getPlayers().get(1);
        assertEquals(this.tableService.getPlayers().get(1), tableService.getCurrentPlayer());
    }

    @Test
    void testGetCurrentPlayerWhenGameDidntStart() {
        this.tableService.addPlayer("id", "name");

        assertEquals(null, this.tableService.getCurrentPlayer());
    }

    // Player Cards Method (getPlayerCards)

    @Test
    void testGetPlayerCards() {
        tableService.addPlayer("id", "name");
        
        ArrayList<Card> handCards = new ArrayList<>();
        
        handCards.add(new Card(Rank.ACE, Suit.CLUBS));
        handCards.add(new Card(Rank.KING, Suit.CLUBS));
        
        tableService.players.get(0).setHandCards(handCards);

        assertEquals(handCards, tableService.getPlayerCards("id"));
    }

    @Test
    void testEmptyCardsDealt() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");

        assertEquals(new ArrayList<Card>(), tableService.getPlayerCards("id"));
        assertEquals(new ArrayList<Card>(), tableService.getPlayerCards("id2"));
    }

    // Communitty Cards Method (getCommunityCards)

    @Test
    void testGetCommunittyCardsPreFlop(){
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        assertEquals(new ArrayList<Card>(), tableService.getCommunityCards());
    }

    @Test
    void testStartCurrentPlayer() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        assertEquals("name", tableService.getCurrentPlayer().getName());
    }

    @Test
    void testEmptyGetPlayers() {
        assertEquals(0, this.tableService.getPlayers().size());
    }

    @Test
    void testAddPlayer() {
        int playersCount = tableService.getPlayers().size();

        tableService.addPlayer("id", "name");

        playersCount++;
        assertEquals(playersCount, tableService.getPlayers().size());
    }

    @Test 
    void testAddPlayerWithExistingName() {
        tableService.addPlayer("id", "Carlos");

        assertThrows(InvalidPlayerNameException.class, () -> {
            tableService.addPlayer("id", "Carlos");
        });
    }

    @Test
    void testPlayerInitialCash() {
        tableService.addPlayer("id", "name");

        assertEquals(100, tableService.getPlayers().get(0).getCash());
    }

    @Test
    void testPlayerInitialState(){
        tableService.addPlayer("id", "name");

        assertEquals(false, tableService.getPlayers().get(0).isActive());
    }

    // Perform Action Method

    // Check Action
    @Test
    void testPerformActionCheck() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();
        assertEquals(tableService.getPlayers().get(0), tableService.getCurrentPlayer());
        
        tableService.performAction("check", 0);
        assertEquals(tableService.getPlayers().get(1), tableService.getCurrentPlayer());
    }
    
    @Test
    void testPerformActionCheckLastPlayer() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.currentPlayer = tableService.getPlayers().get(1);

        tableService.performAction("check", 0);

        assertEquals(tableService.getPlayers().get(0), tableService.getCurrentPlayer());
        // assertEquals(3, tableService.getCommunityCards().size());
        // assertEquals(GameState.FLOP, tableService.getState());
    }
    // Raise Action
    @Test 
    void testPerformActionRaise() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        int betAmount = 50;

        tableService.performAction("raise", betAmount);
        
        // test deduct cash from current player
        assertEquals(50, tableService.players.get(0).getCash());

        // test trying to raise a value lower than the previous bet
        assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", betAmount);
        });

    }
    
    // test raise amount higher than the current player's cash
    @Test
    void testPerformActionRaiseHigherThanCash() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        int betAmount = 350;

        assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", betAmount);
        });
    }

    // test raise amount in case the current player set a bet higher than the amount from any other player
    @Test
    void testPerformActionRaiseCheckAmountFromPlayers(){
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.getPlayers().get(1).deductCash(50);

        int betAmount = 100;

        assertThrows(IllegalAmountException.class, () -> {
            tableService.performAction("raise", betAmount);
        });
    }

    @Test
    void testPerformActionContinuousRaise() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("raise", 20);
        tableService.performAction("raise", 30);

        assertEquals(50, tableService.getPot());
    }


    // Bets and Pot Methods

    @Test
    void testGetPotAmount() {
        assertEquals(0, tableService.getPot());
        this.tableService.pot = 100;
        assertEquals(100, tableService.getPot());
    }

    @Test
    void testGetBetsAmount() {
        assertEquals(new HashMap<String, Integer>(), tableService.getBets());
        HashMap<String, Integer> bets = new HashMap<String, Integer>() {
			{
				put("al-capone", 100);
				put("wyatt-earp", 50);
			}
		};

        this.tableService.bets = bets;

        assertEquals(bets, tableService.getBets());
    }

    // Fold Action Methods
    @Test
    void testPerformActionFoldAndBecomeInactive() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("check", 0);
        tableService.performAction("fold", 0);

        assertEquals(false, tableService.getPlayers().get(1).isActive());
    }

    @Test
    void testPerfromActionFoldAndOnlyOnePlayerLeft() {
        tableService.addPlayer("id", "name");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("fold", 0);

        assertEquals(GameState.ENDED, tableService.getState());
    }

    @Test
    void testPerformActionFoldWinnerGetAllPot() {
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.addPlayer("id3", "name3");
        tableService.start();

        tableService.performAction("raise", 25); // name1
        assertEquals(25, tableService.getPot());
        tableService.performAction("raise", 26); // name2
        assertEquals(51, tableService.getPot());

        tableService.performAction("fold", 0); // name3

        tableService.performAction("fold", 0); // name1


        assertEquals(0, tableService.getPot());
        assertEquals(125, tableService.getPlayers().get(1).getCash());
    }

    // Call Action Method

    @Test
    void testPerformActionCallBeforeRaise(){
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        assertThrows(IllegalActionException.class, () -> {
            tableService.performAction("call", 0);
        });
    }

    @Test
    void testPerformActionCallAfterRaise(){
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("call", 0);

        assertEquals(50, tableService.getPot());
        assertEquals(75, tableService.getPlayers().get(1).getCash());
    }

    @Test
    void testPerformActionCallAfterAlreadyRaised(){
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("raise", 30);
        tableService.performAction("call", 0);
        

        assertEquals(60, tableService.getPot());
        assertEquals(70, tableService.getPlayers().get(0).getCash());
    }


    // End Round Method
    @Test
    void testEndRound() {
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("call", 0);

        assertEquals(GameState.FLOP, tableService.getState());
    }


    @Test
    void testPerformActionCheckChangeCurrentPlayer() {
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("check", 0);

        assertEquals("name2", tableService.getCurrentPlayer().getName());
    }

    
    @Test
    void testChangeGameStateWhenAllPlayersPerfomedActions() {
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);

        assertEquals(GameState.FLOP, tableService.getState());
    }

    @Test
    void testAssignBetsToPot(){
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("raise", 25);
        tableService.performAction("call", 0);

        assertEquals(GameState.FLOP, tableService.getState());
        assertEquals(75, tableService.players.get(0).getCash());
        assertEquals(75, tableService.players.get(1).getCash());
        assertEquals(50, tableService.getPot());
    }

    @Test
    void testAmountOfCommunityCardsForAllGameStates(){
        tableService.addPlayer("id", "name1");
        tableService.addPlayer("id2", "name2");
        tableService.start();

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertEquals(GameState.FLOP, tableService.getState());
        assertEquals(3, tableService.getCommunityCards().size());

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertEquals(GameState.TURN, tableService.getState());
        assertEquals(4, tableService.getCommunityCards().size());

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertEquals(GameState.RIVER, tableService.getState());
        assertEquals(5, tableService.getCommunityCards().size());

        tableService.performAction("check", 0);
        tableService.performAction("check", 0);
        assertEquals(GameState.ENDED, tableService.getState());
        assertEquals(5, tableService.getCommunityCards().size());
    }
}


// Precisamos implementar uma lógica que valide as ações dos jogadores considerando que algum deles tenham dado raise
// Portanto é necessário, validar se os demais deram call ou fold respectivamente.
// fazer parte dos winners