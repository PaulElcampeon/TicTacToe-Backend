package com.paulo.ticTacToe.engine;

import com.paulo.ticTacToe.messages.PlayerAction;
import com.paulo.ticTacToe.models.*;
import com.paulo.ticTacToe.models.enums.BoardSign;
import com.paulo.ticTacToe.utils.GameEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GameEngineTest {

    @Spy
    private GameEngine gameEngine;

    @Test
    public void checkIfBoardIsFull_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Block> input = Arrays.asList(new Block(BoardSign.CROSS));
        Method method = GameEngine.class.getDeclaredMethod("checkIfBoardIsFull", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, input);
        assertTrue(output);
    }

    @Test
    public void checkIfBoardIsFull_Test_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Block> input = Arrays.asList(new Block());
        Method method = GameEngine.class.getDeclaredMethod("checkIfBoardIsFull", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, input);
        assertFalse(output);
    }

    @Test
    public void checkVertically_Test1_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(0).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(3).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(6).setBlockState(BoardSign.CROSS);
        Method method = GameEngine.class.getDeclaredMethod("checkVertically", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertTrue(output);
    }

    @Test
    public void checkVertically_Test1_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(0).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(1).setBlockState(BoardSign.NOUGHT);
        board.getBlocks().get(5).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(2).setBlockState(BoardSign.NOUGHT);
        board.getBlocks().get(6).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(3).setBlockState(BoardSign.NOUGHT);
        board.getBlocks().get(4).setBlockState(BoardSign.NOUGHT);
        Method method = GameEngine.class.getDeclaredMethod("checkVertically", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkVertically_Test2_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        Method method = GameEngine.class.getDeclaredMethod("checkVertically", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkHorizontally_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(0).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(1).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(2).setBlockState(BoardSign.CROSS);
        Method method = GameEngine.class.getDeclaredMethod("checkHorizontally", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertTrue(output);
    }

    @Test
    public void checkHorizontally_Test_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        Method method = GameEngine.class.getDeclaredMethod("checkHorizontally", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkDiagonally_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(0).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(4).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(8).setBlockState(BoardSign.CROSS);
        Method method = GameEngine.class.getDeclaredMethod("checkDiagonally", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertTrue(output);
    }

    @Test
    public void checkDiagonally_Test1_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        Method method = GameEngine.class.getDeclaredMethod("checkDiagonally", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkDiagonally_Test2_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(1).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(2).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(3).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(4).setBlockState(BoardSign.NOUGHT);
        board.getBlocks().get(5).setBlockState(BoardSign.NOUGHT);
        board.getBlocks().get(6).setBlockState(BoardSign.NOUGHT);
        Method method = GameEngine.class.getDeclaredMethod("checkDiagonally", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkIfWeHaveWinner_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        board.getBlocks().get(0).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(4).setBlockState(BoardSign.CROSS);
        board.getBlocks().get(8).setBlockState(BoardSign.CROSS);
        Method method = GameEngine.class.getDeclaredMethod("checkIfWeHaveWinner", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertTrue(output);
    }

    @Test
    public void checkIfWeHaveWinner_Test_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Board board = new Board();
        Method method = GameEngine.class.getDeclaredMethod("checkIfWeHaveWinner", List.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, board.getBlocks());
        assertFalse(output);
    }

    @Test
    public void checkBlockStateIsEmpty_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = GameEngine.class.getDeclaredMethod("checkBlockStateIsEmpty", Block.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, new Block());
        assertTrue(output);
    }

    @Test
    public void checkBlockStateIsEmpty_Test_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = GameEngine.class.getDeclaredMethod("checkBlockStateIsEmpty", Block.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, new Block(BoardSign.CROSS));
        assertFalse(output);
    }

    @Test
    public void checkItIsPlayerTurn_Test_true() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = GameEngine.class.getDeclaredMethod("checkItIsPlayerTurn", String.class, String.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, "Vader", "Vader");
        assertTrue(output);
    }

    @Test
    public void checkItIsPlayerTurn_Test_false() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = GameEngine.class.getDeclaredMethod("checkItIsPlayerTurn", String.class, String.class);
        method.setAccessible(true);
        boolean output = (boolean) method.invoke(gameEngine, "Vader", "Darth");
        assertFalse(output);
    }

    @Test
    public void changeWhosTurn_Test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        BoardPlayer boardPlayer1 = new BoardPlayer("Victor", BoardSign.CROSS);
        BoardPlayer boardPlayer2 = new BoardPlayer("Dean", BoardSign.NOUGHT);
        GameSession gameSession = new GameSession("1234123", boardPlayer1, boardPlayer2);
        Method method = GameEngine.class.getDeclaredMethod("changeWhosTurn", GameSession.class);
        method.setAccessible(true);
        method.invoke(gameEngine, gameSession);
        assertTrue(gameSession.getWhosTurn().equals(boardPlayer2.getName()));
    }

    @Test
    public void process_Test() {
        BoardPlayer boardPlayer1 = new BoardPlayer("Victor", BoardSign.CROSS);
        BoardPlayer boardPlayer2 = new BoardPlayer("Dean", BoardSign.NOUGHT);
        GameSession gameSession = new GameSession("1234123", boardPlayer1, boardPlayer2);
        gameSession.setGameState(GameState.STARTED);
        PlayerAction playerAction = new PlayerAction(boardPlayer1.getName(), gameSession.getGameId(), 0);
        gameEngine.process(gameSession, playerAction);
        assertEquals("It should now be Deans turn", "Dean", gameSession.getWhosTurn());
        assertEquals("Board sign in that block should be cross", BoardSign.CROSS, gameSession.getBoard().getBlocks().get(0).getBlockState());
        assertEquals("Game should not have ended yet", GameState.STARTED, gameSession.getGameState());
        assertNull(gameSession.getWinner());
    }

    @Test
    public void process_Test_shouldHaveEndedNoWinner() {
        BoardPlayer boardPlayer1 = new BoardPlayer("Victor", BoardSign.CROSS);
        BoardPlayer boardPlayer2 = new BoardPlayer("Dean", BoardSign.NOUGHT);
        GameSession gameSession = new GameSession("1234123", boardPlayer1, boardPlayer2);
        gameSession.getBoard().setBlocks(Arrays.asList(new Block(BoardSign.EMPTY), new Block(BoardSign.CROSS), new Block(BoardSign.NOUGHT),
                new Block(BoardSign.NOUGHT), new Block(BoardSign.NOUGHT), new Block(BoardSign.CROSS), new Block(BoardSign.CROSS),
                new Block(BoardSign.CROSS), new Block(BoardSign.NOUGHT)));
        gameSession.setGameState(GameState.STARTED);
        gameSession.setNoOfTurns(8);
        PlayerAction playerAction = new PlayerAction(boardPlayer1.getName(), gameSession.getGameId(), 0);
        gameEngine.process(gameSession, playerAction);
        assertEquals("Game should not have ended yet", GameState.ENDED, gameSession.getGameState());
        assertNull(gameSession.getWinner());
    }

    @Test
    public void process_Test_shouldHaveEndedWithWinner() {
        BoardPlayer boardPlayer1 = new BoardPlayer("Victor", BoardSign.CROSS);
        BoardPlayer boardPlayer2 = new BoardPlayer("Dean", BoardSign.NOUGHT);
        GameSession gameSession = new GameSession("1234123", boardPlayer1, boardPlayer2);
        gameSession.getBoard().setBlocks(Arrays.asList(new Block(BoardSign.EMPTY), new Block(BoardSign.CROSS), new Block(BoardSign.CROSS),
                new Block(BoardSign.NOUGHT), new Block(BoardSign.NOUGHT), new Block(BoardSign.CROSS), new Block(BoardSign.NOUGHT),
                new Block(BoardSign.CROSS), new Block(BoardSign.NOUGHT)));
        gameSession.setGameState(GameState.STARTED);
        gameSession.setNoOfTurns(8);
        PlayerAction playerAction = new PlayerAction(boardPlayer1.getName(), gameSession.getGameId(), 0);
        gameEngine.process(gameSession, playerAction);
        assertEquals("Game should not have ended yet", GameState.ENDED, gameSession.getGameState());
        assertEquals("Victor", gameSession.getWinner());
    }

}