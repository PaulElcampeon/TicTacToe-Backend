package com.paulo.ticTacToe.models;

import com.paulo.ticTacToe.messages.enums.MessageType;
import lombok.Data;

import java.util.HashMap;

@Data
public class GameSession {

    private MessageType messageType = MessageType.GAMESESSION;
    private String gameId;
    private String whosTurn;
    private String winner;
    private HashMap<String, BoardPlayer> players = new HashMap<>();
    private Board board = new Board();
    private GameState gameState = GameState.NOT_STARTED;
    private int noOfTurns = 0;

    public GameSession() {
    }

    public GameSession(String gameId, BoardPlayer player1, BoardPlayer player2) {
        this.gameId = gameId;
        this.players.put(player1.getName(), player1);
        this.players.put(player2.getName(), player2);
        this.whosTurn = player1.getName();
    }

}
