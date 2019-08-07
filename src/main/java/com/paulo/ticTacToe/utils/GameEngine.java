package com.paulo.ticTacToe.utils;

import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.models.Block;
import com.paulo.ticTacToe.models.BoardPlayer;
import com.paulo.ticTacToe.models.GameSession;
import com.paulo.ticTacToe.models.GameState;
import com.paulo.ticTacToe.models.enums.BoardSign;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class GameEngine {

    private Logger logger = Logger.getLogger(GameEngine.class.getName());

    public void process(GameSession gameSession, PlayerAction playerAction) {
        if (checkBlockStateIsEmpty(gameSession.getBoard().getBlocks().get(playerAction.getBlockNo()))
                && checkItIsPlayerTurn(gameSession.getWhosTurn(), playerAction.getPlayerName())) {
            gameSession.setNoOfTurns(gameSession.getNoOfTurns() + 1);
            BoardPlayer boardPlayer = gameSession.getPlayers().get(playerAction.getPlayerName());
            gameSession.getBoard().getBlocks().get(playerAction.getBlockNo()).setBlockState(boardPlayer.getBoardSign());
            if (gameSession.getNoOfTurns() >= 5) {
                if (checkIfWeHaveWinner(gameSession.getBoard().getBlocks())) {
                    logger.log(Level.INFO,String.format("We have a winner %s in game session %s", playerAction.getPlayerName(), playerAction.getGameId()));
                    gameSession.setWinner(playerAction.getPlayerName());
                    gameSession.setGameState(GameState.ENDED);
                } else {
                    if (checkIfBoardIsFull(gameSession.getBoard().getBlocks())) {
                        logger.log(Level.INFO,String.format("Game session with id %s ended with no winner", playerAction.getGameId()));
                        gameSession.setGameState(GameState.ENDED);
                    }
                }
            }
            changeWhosTurn(gameSession);
        }
    }

    private boolean checkIfBoardIsFull(List<Block> board) {
        for (Block block : board) {
            if (block.getBlockState() == BoardSign.EMPTY)
                return false;
        }
        return true;
    }

    private boolean checkIfWeHaveWinner(List<Block> board) {
        //assuming board is a square 3x3
        return checkVertically(board) || checkHorizontally(board) || checkDiagonally(board);
    }

    private boolean checkVertically(List<Block> board) {
        int columns = (int) Math.sqrt(board.size());
        int[] startingNums = {0, columns, 2 * columns};
        for (int i = 0; i < columns; i++) {
            if (board.get(i + startingNums[0]).getBlockState() == board.get(i + startingNums[1]).getBlockState() &&
                    board.get(i + startingNums[1]).getBlockState() == board.get(i + startingNums[2]).getBlockState() &&
                    board.get(i + startingNums[0]).getBlockState() != BoardSign.EMPTY) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHorizontally(List<Block> board) {
        int rows = (int) Math.sqrt(board.size());
        int[] startingNums = {0, 1, 2};
        for (int i = 0; i < rows; i++) {
            if (board.get(i * rows + startingNums[0]).getBlockState() == board.get(i * rows + startingNums[1]).getBlockState() &&
                    board.get(i * rows + startingNums[1]).getBlockState() == board.get(i * rows + startingNums[2]).getBlockState() &&
                    board.get(i * rows + startingNums[0]).getBlockState() != BoardSign.EMPTY) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonally(List<Block> board) {
        int rows = (int) Math.sqrt(board.size());
        int[] possibility1 = {0, rows + 1, rows * rows - 1};
        int[] possibility2 = {rows - 1, rows + 1, 2 * rows};
        if (board.get(possibility1[0]).getBlockState() == board.get(possibility1[1]).getBlockState() &&
                board.get(possibility1[1]).getBlockState() == board.get(possibility1[2]).getBlockState() &&
                board.get(possibility1[0]).getBlockState() != BoardSign.EMPTY) {
            return true;
        } else if (board.get(possibility2[0]).getBlockState() == board.get(possibility2[1]).getBlockState() &&
                board.get(possibility2[1]).getBlockState() == board.get(possibility2[2]).getBlockState() &&
                board.get(possibility2[0]).getBlockState() != BoardSign.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkBlockStateIsEmpty(Block block) {
        return block.getBlockState() == BoardSign.EMPTY;
    }

    private boolean checkItIsPlayerTurn(String whosTurn, String currentActor) {
        return whosTurn.equals(currentActor);
    }

    private void changeWhosTurn(GameSession gameSession) {
        for (String name : gameSession.getPlayers().keySet()) {
            if (!name.equals(gameSession.getWhosTurn())) {
                gameSession.setWhosTurn(name);
                break;
            }
        }
    }
}
