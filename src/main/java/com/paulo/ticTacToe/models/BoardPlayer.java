package com.paulo.ticTacToe.models;

import com.paulo.ticTacToe.models.enums.BoardSign;
import lombok.Data;

@Data
public class BoardPlayer {
    private String name;
    private BoardSign boardSign;

    public BoardPlayer() {}

    public BoardPlayer(String name, BoardSign boardSign) {
        this.name = name;
        this.boardSign = boardSign;
    }
}
