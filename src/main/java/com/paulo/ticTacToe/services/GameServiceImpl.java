package com.paulo.ticTacToe.services;

import com.paulo.ticTacToe.caches.GameCache;
import com.paulo.ticTacToe.caches.PlayerCache;
import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.messages.PlayerDisconnect;
import com.paulo.ticTacToe.models.BoardPlayer;
import com.paulo.ticTacToe.models.GameSession;
import com.paulo.ticTacToe.models.GameState;
import com.paulo.ticTacToe.models.enums.BoardSign;
import com.paulo.ticTacToe.utils.GameEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameServiceImpl implements GameService {

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private GameCache gameCache;

    @Autowired
    private GameEngine gameEngine;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private Logger logger = Logger.getLogger(GameServiceImpl.class.getName());

    @Override
    public GameSession processPlayerAction(PlayerAction playerAction) {
        logger.log(Level.INFO, String.format("Processing player action from %s with gameId: %s", playerAction.getPlayerName(), playerAction.getGameId()));
        GameSession gameSession = gameCache.getGameCache().get(playerAction.getGameId());
        gameEngine.process(gameSession, playerAction);
        if (gameSession.getGameState() == GameState.ENDED) {
            gameCache.getGameCache().remove(gameSession.getGameId());
        }
        return gameSession;
    }

    @Override
    public void addPlayerToCache(String playerName) {
        logger.log(Level.INFO, String.format("Adding player with id:%s to player cache", playerName));
        playerCache.getPlayerCache().add(playerName);
        logger.log(Level.INFO, String.format("player cache size is currently %d", playerCache.getPlayerCache().size()));
    }

    @Override
    public void removePlayerFromCache(String playerName) {
        logger.log(Level.INFO, String.format("Removing player with id:%s from player cache", playerName));
        playerCache.getPlayerCache().remove(playerName);
        logger.log(Level.INFO, String.format("player cache size is currently %d", playerCache.getPlayerCache().size()));
    }

    @Override
    public void addGameToCache(GameSession gameSession) {
        logger.log(Level.INFO, String.format("Adding game with id:%s to game cache", gameSession));
        gameCache.getGameCache().put(gameSession.getGameId(), gameSession);
        logger.log(Level.INFO, String.format("Game cache size is currently %d", gameCache.getGameCache().size()));
    }

    @Override
    public void removeGameFromCache(String gameId) {
        logger.log(Level.INFO, String.format("Removing game session with id:%s from game cache", gameId));
        gameCache.getGameCache().remove(gameId);
        logger.log(Level.INFO, String.format("Game cache size is currently %d", gameCache.getGameCache().size()));
    }

    @Override
    public synchronized void processPlayerCache() {
        if (playerCache.getPlayerCache().size() >= 2) {
            String player1 = playerCache.getPlayerCache().get(0);
            String player2 = playerCache.getPlayerCache().get(1);
            String gameId = player1 + "." + player2;
            GameSession gameSession = new GameSession(gameId, new BoardPlayer(player1, BoardSign.CROSS), new BoardPlayer(player2, BoardSign.NOUGHT));
            gameSession.setGameState(GameState.STARTED);
            addGameToCache(gameSession);
            playerCache.getPlayerCache().remove(player1);
            playerCache.getPlayerCache().remove(player2);
            logger.log(Level.INFO, String.format("player cache size is currently %d", playerCache.getPlayerCache().size()));

            //we will persist the gameId on the client side and use that for the client to subscribe to that channel
            simpMessagingTemplate.convertAndSend("/queue/" + player1, gameSession);
            simpMessagingTemplate.convertAndSend("/queue/" + player2, gameSession);
        }
    }

    @Override
    public void handlePlayerDisconnect(String playerName) {
        removePlayerFromCache(playerName);
        gameCache.getGameCache().forEach((key, value) -> {
            if (key.contains(playerName)) {
                simpMessagingTemplate.convertAndSend("/topic/" + key, new PlayerDisconnect());
                logger.log(Level.INFO, String.format("Just sent a disconnect message to users subscribed to gameId: %s", key));
            }
        });
        String gameSessionId = gameCache.getGameCache().keySet().stream().filter(gameId -> gameId.contains(playerName)).findFirst().orElse("");
        if (!gameSessionId.equals("")) {
            gameCache.getGameCache().remove(gameSessionId);
            logger.log(Level.INFO, String.format("Just removed game session with id: %s from game cache", gameSessionId));
        }
    }
}
