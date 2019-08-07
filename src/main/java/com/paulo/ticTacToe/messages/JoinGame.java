package com.paulo.ticTacToe.messages;

import lombok.Data;

@Data
public class JoinGame {

    private String playerName;

    public JoinGame() {}

    public JoinGame(String playerName) {
        this.playerName = playerName;
    }

}
