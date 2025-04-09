package com.sap.ase.poker.dto;

import com.sap.ase.poker.model.Player;
import com.sap.ase.poker.model.deck.Card;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

public class GetTableResponseDto {
    private List<PlayerDto> players = new ArrayList<>();
    private List<CardDto> playerCards = new ArrayList<>();
    private PlayerDto currentPlayer;
    private List<CardDto> communityCards = new ArrayList<>();
    private int pot;
    private Map<String, Integer> bets = new HashMap<>();
    private int state;
    private PlayerDto winner;
    private List<CardDto> winnerHand;

    public GetTableResponseDto() {
    }

    private GetTableResponseDto(Builder builder) {
        setPlayers(builder.players);
        setPlayerCards(builder.playerCards);
        setCurrentPlayer(builder.currentPlayer);
        setCommunityCards(builder.communityCards);
        setPot(builder.pot);
        setBets(builder.bets);
        setState(builder.state);
        setWinner(builder.winner);
        setWinnerHand(builder.winnerHand);
    }

    public List<PlayerDto> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDto> players) {
        this.players = players;
    }

    public List<CardDto> getPlayerCards() {
        return playerCards;
    }

    public void setPlayerCards(List<CardDto> playerCards) {
        this.playerCards = playerCards;
    }

    public PlayerDto getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(PlayerDto currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public List<CardDto> getCommunityCards() {
        return communityCards;
    }

    public void setCommunityCards(List<CardDto> communityCards) {
        this.communityCards = communityCards;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public Map<String, Integer> getBets() {
        return bets;
    }

    public void setBets(Map<String, Integer> bets) {
        this.bets = bets;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public PlayerDto getWinner() {
        return winner;
    }

    public void setWinner(PlayerDto winner) {
        this.winner = winner;
    }

    public List<CardDto> getWinnerHand() {
        return winnerHand;
    }

    public void setWinnerHand(List<CardDto> winnerHand) {
        this.winnerHand = winnerHand;
    }

    @Component
    public static final class Builder {
        private List<PlayerDto> players;
        private List<CardDto> playerCards;
        private PlayerDto currentPlayer;
        private List<CardDto> communityCards;
        private int pot;
        private Map<String, Integer> bets;
        private int state;
        private PlayerDto winner;
        private List<CardDto> winnerHand = Collections.emptyList();

        public Builder players(List<Player> players) {
            this.players = players.stream().map(PlayerDto::new).collect(Collectors.toList());
            return this;
        }

        public Builder playerCards(List<Card> playerCards) {
            this.playerCards = convertToCardDtoList(playerCards);
            return this;
        }

        public Builder currentPlayer(Player currentPlayer) {
            this.currentPlayer = currentPlayer == null ? null : new PlayerDto(currentPlayer);
            return this;
        }

        public Builder communityCards(List<Card> communityCards) {
            this.communityCards = convertToCardDtoList(communityCards);
            return this;
        }

        public Builder pot(int pot) {
            this.pot = pot;
            return this;
        }

        public Builder bets(Map<String, Integer> bets) {
            this.bets = bets;
            return this;
        }

        public Builder state(int state) {
            this.state = state;
            return this;
        }

        public Builder winner(Player winner) {
            this.winner = winner == null ? null : new PlayerDto(winner);
            return this;
        }

        public Builder winnerHand(List<Card> winnerHand) {
            this.winnerHand = convertToCardDtoList(winnerHand);
            return this;
        }

        private static List<CardDto> convertToCardDtoList(List<Card> cards) {
            return cards.stream().map(CardDto::new).collect(Collectors.toList());
        }

        public GetTableResponseDto build() {
            return new GetTableResponseDto(this);
        }
    }
}
