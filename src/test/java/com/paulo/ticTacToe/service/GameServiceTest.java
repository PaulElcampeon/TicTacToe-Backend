package com.paulo.ticTacToe.service;

import com.paulo.ticTacToe.caches.GameCache;
import com.paulo.ticTacToe.caches.PlayerCache;
import com.paulo.ticTacToe.models.GameSession;
import com.paulo.ticTacToe.services.GameServiceImpl;
import com.paulo.ticTacToe.utils.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceTest {

    @Spy
    private PlayerCache playerCache;

    @Spy
    private GameCache gameCache;

    @Spy
    private GameEngine gameEngine;

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;

    @InjectMocks
    private GameServiceImpl gameService;

    @Test
    public void addPlayerToCache_Test() {
        gameService.addPlayerToCache("Frank");
        assertTrue(playerCache.getPlayerCache().contains("Frank"));
    }

    @Test
    public void removePlayerFromCache_Test() {
        gameService.addPlayerToCache("Frank");
        assertTrue(playerCache.getPlayerCache().contains("Frank"));
        gameService.removePlayerFromCache("Frank");
        assertFalse(playerCache.getPlayerCache().contains("Frank"));
    }

    @Test
    public void addGameToCache_Test() {
        String gameId = "test1";
        GameSession gameSession = new GameSession();
        gameSession.setGameId(gameId);
        gameService.addGameToCache(gameSession);
        assertTrue(gameCache.getGameCache().containsKey(gameId));
    }

    @Test
    public void removeGameFromCache_Test() {
        String gameId = "test1";
        GameSession gameSession = new GameSession();
        gameSession.setGameId(gameId);
        gameService.addGameToCache(gameSession);
        assertTrue(gameCache.getGameCache().containsKey(gameId));
        gameService.removeGameFromCache(gameId);
        assertFalse(gameCache.getGameCache().containsKey(gameId));
    }

    @Test
    public void processPlayerCache_Test() {
        String player1 = "Frank";
        String player2 = "Dean";
        playerCache.getPlayerCache().addAll(Arrays.asList(player1, player2));
        gameService.processPlayerCache();
        assertTrue(gameCache.getGameCache().containsKey(player1.concat(".").concat(player2)));
        assertFalse(playerCache.getPlayerCache().contains(player1));
        assertFalse(playerCache.getPlayerCache().contains(player2));
    }

    @Test
    public void handlePlayerDisconnect_Test() {

    }


}
