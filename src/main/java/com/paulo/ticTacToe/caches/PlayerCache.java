package com.paulo.ticTacToe.caches;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PlayerCache {

    private List<String> list = Collections.synchronizedList(new ArrayList<>(100));

    public List<String> getPlayerCache() {
        return list;
    }

}
