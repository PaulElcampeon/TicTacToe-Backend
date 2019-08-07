package com.paulo.ticTacToe.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Board {

    List<Block> blocks = new ArrayList<>(9);

    public Board() {
        for (int i = 0; i < 9; i++) {
            blocks.add(new Block());
        }
    }
}
