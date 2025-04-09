package com.sap.ase.poker.dto;

import com.sap.ase.poker.model.Player;

public class PlayerDto {
    private String id;
    private String name;
    private boolean isActive;
    private int cash;

    public PlayerDto() {
    }

    public PlayerDto(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.isActive = player.isActive();
        this.cash = player.getCash();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void isActive(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
