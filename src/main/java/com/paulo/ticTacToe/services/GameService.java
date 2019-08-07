package com.paulo.ticTacToe.services;

import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.models.GameSession;

public interface GameService {

    GameSession processPlayerAction(PlayerAction playerAction);

    void addPlayerToCache(String playerName);

    void removePlayerFromCache(String playerName);

    void addGameToCache(GameSession gameSession);

    void removeGameFromCache(String gameId);

    void processPlayerCache();

    void handlePlayerDisconnect(String playerName);

}
