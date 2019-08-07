package com.paulo.ticTacToe.messages;

import lombok.Data;

@Data
public class PlayerAction {

    private String playerName;
    private String gameId;
    private int blockNo;

    public PlayerAction() {}

    public PlayerAction(String playerName, String gameId, int blockNo) {
        this.playerName = playerName;
        this.gameId = gameId;
        this.blockNo = blockNo;
    }
}
