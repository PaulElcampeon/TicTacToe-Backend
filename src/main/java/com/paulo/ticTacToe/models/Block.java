package com.paulo.ticTacToe.models;

import com.paulo.ticTacToe.models.enums.BoardSign;
import lombok.Data;

@Data
public class Block {

    private BoardSign blockState = BoardSign.EMPTY;

    public Block() {}

    public Block(BoardSign boardSign) {
        this.blockState = boardSign;
    }
}
