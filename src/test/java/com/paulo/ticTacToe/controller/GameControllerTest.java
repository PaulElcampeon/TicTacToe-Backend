package com.paulo.ticTacToe.controller;

import com.paulo.ticTacToe.TicTacToeApplication;
import com.paulo.ticTacToe.caches.PlayerCache;
import com.paulo.ticTacToe.config.WebSocketConfig;
import com.paulo.ticTacToe.messages.JoinGame;
import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.models.BoardPlayer;
import com.paulo.ticTacToe.models.GameSession;
import com.paulo.ticTacToe.models.enums.BoardSign;
import com.paulo.ticTacToe.services.GameService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TicTacToeApplication.class, WebSocketConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameControllerTest {

    @LocalServerPort
    private int portNo;

    @Autowired
    private PlayerCache playerCache;

    @Autowired
    private GameService gameService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private CompletableFuture<GameSession> completableFuture = new CompletableFuture<>();

    private StompSession stompSession;

    @Before
    public void init() throws InterruptedException, ExecutionException, TimeoutException {
        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        stompSession = stompClient.connect(String.format("ws://localhost:%d/ticTacToe", portNo), new StompSessionHandlerAdapter() {
        }).get(1, TimeUnit.SECONDS);
    }

    @Test
    public void join_Test() {
        String name = "Dave";
        stompSession.send("/app/join", new JoinGame(name));
        waitFor(2);
        assertTrue(playerCache.getPlayerCache().contains(name));

    }

    @Test
    public void processPlayerAction_Test() throws InterruptedException, ExecutionException, TimeoutException {
        String gameId = "123Test";
        BoardPlayer boardPlayer1 = new BoardPlayer("Dave", BoardSign.NOUGHT);
        BoardPlayer boardPlayer2 = new BoardPlayer("Fred", BoardSign.CROSS);
        GameSession gameSession = new GameSession(gameId, boardPlayer1, boardPlayer2);
        gameService.addGameToCache(gameSession);
        stompSession.subscribe("/topic/" + gameId, new playerActionMessageHandler());
        stompSession.send("/app/act/" + gameId, new PlayerAction("Dave", gameId, 0));
        GameSession gameSession1 = completableFuture.get(10, TimeUnit.SECONDS);
        assertNotNull(gameSession1);
    }

    private void waitFor(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class playerActionMessageHandler implements StompFrameHandler {
        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return GameSession.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            completableFuture.complete((GameSession) o);
        }
    }
}
