package com.sap.ase.poker.service;

import com.sap.ase.poker.model.GameState;
import com.sap.ase.poker.model.IllegalActionException;
import com.sap.ase.poker.model.IllegalAmountException;
import com.sap.ase.poker.model.InsufficientPlayersException;
import com.sap.ase.poker.model.InvalidPlayerNameException;
import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import com.sap.ase.poker.model.deck.Deck;
import com.sap.ase.poker.model.deck.Rank;
import com.sap.ase.poker.model.deck.Suit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class TableService {
	public TableService(Deck deck, List<Player> players) {
		this.deck = deck;
		this.players = players;
	}

	int pot = 0;
	boolean hasAnyPlayerRaised = false;
	int lastBet = 0;

	Deck deck;
	List<Player> players;
	Player currentPlayer;
	
	GameState gameState = GameState.OPEN;
	HashMap<String, Integer> bets = new HashMap<String, Integer>();
	List<Card> communityCards = new ArrayList<Card>();
	HashMap<String, String> playerActionsInRound = new HashMap<String, String>();

	public GameState getState() {
		return gameState;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Card> getPlayerCards(String playerId) {
		for (Player player : players) {
			if(player.getId().equals(playerId)){
				return player.getHandCards();
			}
		}
		return new ArrayList<Card>();
	}


	public List<Card> getCommunityCards() {
		return communityCards;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public Map<String, Integer> getBets() {
		// TODO: implement me
		return bets;
	}

	public int getPot() {
		// TODO: implement me
		return pot;
	}

	public Player getWinner() {
		// TODO: implement me
		return new Player("al-capone", "Al Capone", 500);
	}

	public List<Card> getWinnerHand() {
		// TODO: implement me
		return Arrays.asList(
				new Card(Rank.ACE, Suit.CLUBS),
				new Card(Rank.KING, Suit.CLUBS),
				new Card(Rank.QUEEN, Suit.CLUBS),
				new Card(Rank.JACK, Suit.CLUBS),
				new Card(Rank.TEN, Suit.CLUBS)
		);
	}

	public void start() throws InsufficientPlayersException{

		if(players.size() < 2){
			throw new InsufficientPlayersException("Not enough players to start the game");
		}

		this.gameState = GameState.PRE_FLOP;
		this.currentPlayer = players.get(0);

		changePlayersState();
		dealPlayerCards();
	}

	private void dealPlayerCards() {
		int i = 0;
		while(i < 2){
			for (Player player : players) {
				List<Card> playerCards = player.getHandCards();
				playerCards.add(deck.draw());
				player.setHandCards(playerCards);
			}
			i++;
		}
	}

	private void changePlayersState(){
		for (Player player : players) {
			player.setActive();
		}
	}
	
	public void addPlayer(String playerId, String playerName) throws InvalidPlayerNameException {
		if(isPlayerNameAvailable(playerName)) {
			Player newPlayer = new Player(playerId, playerName, 100);
			newPlayer.setInactive();
			this.players.add(newPlayer);

			System.out.printf("Player joined the table: %s%n", playerId);
		} else {
			throw new InvalidPlayerNameException("Player name already exists");
		}
	}

	private boolean isPlayerNameAvailable(String playerName){
		for (Player player : players) {
			if(player.getName().equals(playerName)){
				return false;
			}
		}
		return true;
	}
	
	public void performAction(String action, int amount) throws IllegalAmountException, IllegalActionException {
		switch (action) {
			case "check":
				if(hasAnyPlayerRaised){
					throw new IllegalActionException("Cannot check after a raise");
				}
				break;
			case "raise":
				raiseAction(amount);
				break;
			case "fold":
				foldAction();
				break;
			case "call":
				if(!hasAnyPlayerRaised){
					throw new IllegalActionException("Cannot call without a raise");
				}
				callAction();
				break;
			default:
				break;
		}

		playerActionsInRound.put(currentPlayer.getName(), action);
		changeStateIfNeeded();
		defineNewCurrentPlayer();

	}

	private void changeStateIfNeeded() {
		int activePlayersCount = getActivePlayersCount();
		if(playerActionsInRound.size() == activePlayersCount) {
			increasePot();
			setNextGameState();
			playerActionsInRound.clear();
		}
	}

	private int getActivePlayersCount() {
		int activeCount = 0;
		for (Player player : players) {
			if(player.isActive()) {
				activeCount++;
			}
		}

		return activeCount;
	}

	private void callAction() throws IllegalActionException{

		if(hasPlayerAlreadyBet()){
			int callBet = lastBet - bets.get(currentPlayer.getName());
			
			pot += callBet;
			currentPlayer.deductCash(callBet);
			bets.put(currentPlayer.getName(), callBet);
		} else {
			currentPlayer.deductCash(lastBet);
			bets.put(currentPlayer.getName(), lastBet);
		}
	}

	private boolean hasPlayerAlreadyBet() {
		if(bets.containsKey(currentPlayer.getName())){
			return true;
		}
		return false;
	}

	private void foldAction() {
		currentPlayer.setInactive();

		if(isOnlyOnePlayerLeft()) {
			// end game
			transferPotToWinnerAndClearPot();
			this.gameState = GameState.ENDED;
		} 
	} 

	private boolean isOnlyOnePlayerLeft() {
		int activePlayers = 0;
		for (Player player : players) {
			if(player.isActive()){
				activePlayers++;
			}
		}

		if(activePlayers == 1){
			return true;
		}

		return false;
	}

	private void transferPotToWinnerAndClearPot() {
		for (Player player : players) {
			if(player.isActive()){
				player.addCash(pot);
				clearPot();
			}
		}
	}

	private void clearPot() {
		this.pot = 0;
	}

	private boolean isCurrentPlayerLast() {
		String currentPlayerName = this.currentPlayer.getName();
		if (players.get(players.size() - 1).getName().equals(currentPlayerName)) {
			return true;
		}
		return false;
	}

	private void setNextGameState() {
		switch(gameState){
			case PRE_FLOP:
				this.gameState = GameState.FLOP;
				setComunityCards(3);
				break;
			case FLOP:
				this.gameState = GameState.TURN;
				setComunityCards(1);
				break;
			case TURN:
				this.gameState = GameState.RIVER;
				setComunityCards(1);
				break;
			case RIVER:
				this.gameState = GameState.ENDED;
				break;
			case ENDED:
				clearPot();
				break;
			default:
				break;
		}
		resetRaisedFlag();
		resetBetHashMap();

	}

	void resetBetHashMap() {
		bets.clear();
	}

	void resetRaisedFlag() {
		hasAnyPlayerRaised = false;
	}

	private void raiseAction(int amount) throws IllegalAmountException {

		int highestBet = getHighestBet();
		int lowestPlayerCash = getLowestCash();

		if(amount > lowestPlayerCash) {
			throw new IllegalAmountException("Amount is higher than the lowest player's cash");
		} else if(currentPlayer.getCash() < amount) {
			throw new IllegalAmountException("Amount is higher than the current player's cash");
		} else if(amount > highestBet) {
			this.currentPlayer.deductCash(amount);
			bets.put(currentPlayer.getName(), amount);
		} else {
			throw new IllegalAmountException("Amount is less or equal than the last bet value");
		}

		hasAnyPlayerRaised = true;
		lastBet = amount;
	}

	private void increasePot() {
		for (int bet : bets.values()) {
			pot += bet;
		}
	}

	private int getLowestCash() {
		int lowestPlayerCash = Integer.MAX_VALUE;
		for (Player player : players) {
			if (player.getCash() < lowestPlayerCash) {
				lowestPlayerCash = player.getCash();
			}
		}
		return lowestPlayerCash;
	}

	private int getHighestBet() {
		int highestBet = 0;
		for (int bet : bets.values()) {
			if (bet > highestBet) {
				highestBet = bet;
			}
		}
		return highestBet;
	}

	private void defineNewCurrentPlayer() {
		if(isCurrentPlayerLast()){
			setNewCurrentPlayer(0);
			return;
		}

		int currentPlayerIndex = getCurrentPlayerIndex();
		int newCurrentPlayerIndex = findNewCurrentPlayerIndex(currentPlayerIndex);

		setNewCurrentPlayer(newCurrentPlayerIndex);
	}

	private int findNewCurrentPlayerIndex(int currentPlayerIndex) {
		int i = currentPlayerIndex + 1;
		while(true) {	
			if (isPlayerActive(i)) {
				return i;
			} 

			if (i == players.size() - 1) {
				i = 0;
			} else {
				i++;
			}
		}
	}

	private int getCurrentPlayerIndex() {
		String currentPlayerName = this.currentPlayer.getName();
		for (int i = 0 ; i < players.size(); i++) {
			if (players.get(i).getName().equals(currentPlayerName)){
				return i;
			}
		}

		return -1;
	}

	private void setNewCurrentPlayer(int newCurrentPlayerIndex) {
		this.currentPlayer = players.get(newCurrentPlayerIndex);
	}

	private boolean isPlayerActive(int newCurrentPlayerIndex) {
		return this.players.get(newCurrentPlayerIndex).isActive();
	}

	private void setComunityCards(int amountCards) {
		for (int i = 0; i < amountCards; i++) {
			this.communityCards.add(deck.draw());
		}
	}
}
