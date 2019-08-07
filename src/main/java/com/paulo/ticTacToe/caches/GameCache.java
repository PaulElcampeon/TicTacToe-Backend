package com.paulo.ticTacToe.caches;

import com.paulo.ticTacToe.models.GameSession;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameCache {

    private ConcurrentHashMap<String, GameSession> gameCache = new ConcurrentHashMap<>(100);

    public ConcurrentHashMap<String, GameSession> getGameCache() {
        return gameCache;
    }
}
