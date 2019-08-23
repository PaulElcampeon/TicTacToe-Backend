package com.paulo.ticTacToe.controller;

import com.paulo.ticTacToe.messages.JoinGame;
import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.models.GameSession;
import com.paulo.ticTacToe.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
@CrossOrigin("*")
public class GameController {

    @Autowired
    private GameService gameService;

    @Value("${server.port}")
    private int portNo;

    private Logger logger = Logger.getLogger(GameController.class.getName());

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage() {
        return "index.html";
    }

    @RequestMapping(value = "/port", method = RequestMethod.GET)
    public @ResponseBody String getPortNo() {
        return String.valueOf(portNo);
    }

    @MessageMapping(value = "/join")
    public void joinGame(@Payload JoinGame joinGame) {
        logger.log(Level.INFO, String.format("%s just made a request to join a game", joinGame.getPlayerName()));
        gameService.addPlayerToCache(joinGame.getPlayerName());
        gameService.processPlayerCache();
    }

    @MessageMapping(value = "/act/{gameId}")
    @SendTo(value = "/topic/{gameId}")
    public GameSession processPlayerAction(@DestinationVariable String gameId, @Payload PlayerAction playerAction) {
        logger.log(Level.INFO, String.format("We got a player action %s", playerAction));
        return gameService.processPlayerAction(playerAction);
    }

}
