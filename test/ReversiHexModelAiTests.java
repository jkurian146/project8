import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import discs.DiscColor;
import model.GameState;
import model.MoveDirection;
import model.MoveRules;
import model.ReversiHexModel;
import model.ReversiHexModelAI;
import model.ReversiModel;
import strategy.CornerStrategyMock;
import strategy.MaximizeCaptureStrategyMock;
import strategy.StrategyType;
import view.ReversiGUI;

/**
 * Represents a test class for an ai model.
 */
public class ReversiHexModelAiTests {

  @Test
  public void testMaximizeStrategyFunctionalityAndAiPassesWhenItHasNoMoves() {
    // Mock testing behavior:
    // Our mock works by passing in a model into the mocked strategy,
    // and then returning the available moves for whichever player's turn it is to play.

    // Since our AI model works by instantly making a move when the player makes a move, we cannot
    // test the available moves for the AI.
    // instead, we manually make the moves for our mock using a "For Test Model" to be able to
    // return the available moves for the Ai player.

    // The available moves can be found by using System.out.println(mock.availableMoves());
    // whenever it is desired to do so.
    // Consequentially, you can take a look at the string assertions for the mock.availableMoves
    // and compare them to the manually inserted "Available Moves",
    // which are commented after nearly every move.

    // Move chose by AI indicated with '*' next to move.

    // Comments were only written in this test for readability,
    // assume the above for any other mock test.

    ReversiHexModelAI aiModel = new ReversiHexModelAI(StrategyType.MAXIMIZE);
    ReversiHexModel modelForTesting = new ReversiHexModel();
    aiModel.startGame(9);
    modelForTesting.startGame(9);
    MaximizeCaptureStrategyMock mock = new MaximizeCaptureStrategyMock(modelForTesting);

    modelForTesting.makeMove(4, 2);
    aiModel.makeMove(4, 2);
    Assert.assertEquals("Move: [2, 3], discs Captured: 2, Captured Moves: [[2, 3], [3, 4]]\n" +
                    "Move: [3, 1], discs Captured: 3, Captured Moves: [[3, 1], [4, 2], [4, 3]]\n" +
                    "Move: [4, 6], discs Captured: 2, Captured Moves: [[4, 6], [4, 5]]\n" +
                    "Move: [5, 5], discs Captured: 2, Captured Moves: [[5, 5], [4, 5]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (3, 1), discs captured: 3 *
    // (5, 5), discs captured: 2
    // (4, 6), discs captured: 2
    // (2, 3), discs captured: 2

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock.
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 1).getColor());
    modelForTesting.makeMove(3, 1); // Make the Ai move to progress through test model.

    modelForTesting.makeMove(4, 1);
    aiModel.makeMove(4, 1);
    Assert.assertEquals("Move: [2, 3], discs Captured: 3, Captured Moves: " +
                    "[[2, 3], [3, 3], [3, 4]]\n" +
                    "Move: [4, 6], discs Captured: 2, Captured Moves: [[4, 6], [4, 5]]\n" +
                    "Move: [5, 1], discs Captured: 2, Captured Moves: [[5, 1], [4, 1]]\n" +
                    "Move: [5, 5], discs Captured: 2, Captured Moves: [[5, 5], [4, 5]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (2, 3), discs captured: 3 *
    // (5, 5), discs captured: 2
    // (4, 6), discs captured: 2
    // (5, 2), discs captured: 2

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    modelForTesting.makeMove(2, 3);

    modelForTesting.makeMove(2, 5);
    aiModel.makeMove(2, 5);
    Assert.assertEquals("Move: [4, 6], discs Captured: 4, Captured Moves: " +
                    "[[4, 6], [3, 5], [3, 4], [4, 5]]\n" +
                    "Move: [5, 1], discs Captured: 2, Captured Moves: [[5, 1], [4, 1]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (4, 6), discs captured: 4 *
    // (5, 1), discs captured: 2
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 6).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    modelForTesting.makeMove(4, 6);

    modelForTesting.makeMove(5, 5);
    aiModel.makeMove(5, 5);
    Assert.assertEquals("Move: [6, 6], discs Captured: 5, Captured Moves: " +
                    "[[6, 6], [5, 5], [5, 4], [4, 3], [4, 2]]\n" +
                    "Move: [2, 6], discs Captured: 2, Captured Moves: [[2, 6], [2, 5]]\n" +
                    "Move: [5, 0], discs Captured: 4, Captured Moves: " +
                    "[[5, 0], [4, 1], [4, 2], [3, 3]]\n" +
                    "Move: [5, 1], discs Captured: 2, Captured Moves: [[5, 1], [4, 1]]\n" +
                    "Move: [5, 3], discs Captured: 5, Captured Moves: " +
                    "[[5, 3], [4, 3], [3, 3], [5, 4], [4, 5]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (5, 0), discs captured: 4
    // (5, 3), discs captured: 5 *
    // (5, 1), discs captured: 2
    // (2, 6), discs captured: 2
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 3).getColor());
    modelForTesting.makeMove(5, 3);

    modelForTesting.makeMove(4, 7);
    aiModel.makeMove(4, 7);
    Assert.assertEquals("Move: [6, 5], discs Captured: 2, Captured Moves: [[6, 5], [5, 5]]\n" +
                    "Move: [6, 6], discs Captured: 2, Captured Moves: [[6, 6], [5, 5]]\n" +
                    "Move: [2, 6], discs Captured: 2, Captured Moves: [[2, 6], [2, 5]]\n" +
                    "Move: [5, 0], discs Captured: 3, Captured Moves: [[5, 0], [4, 1], [4, 2]]\n" +
                    "Move: [5, 1], discs Captured: 2, Captured Moves: [[5, 1], [4, 1]]\n" +
                    "Move: [5, 8], discs Captured: 4, Captured Moves: " +
                    "[[5, 8], [4, 7], [4, 6], [3, 5]]\n" +
                    "Move: [1, 5], discs Captured: 3, Captured Moves: [[1, 5], [2, 5], [3, 5]]\n" +
                    "Move: [3, 7], discs Captured: 2, Captured Moves: [[3, 7], [4, 6]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (5, 0), discs captured: 3
    // (5, 2), discs captured: 2
    // (3, 7), discs captured: 2
    // (2, 6), discs captured: 2
    // (6, 5), discs captured: 2
    // (5, 8), discs captured: 4 *
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 8).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 7).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 6).getColor());
    modelForTesting.makeMove(5, 8);

    modelForTesting.makeMove(3, 0);
    aiModel.makeMove(3, 0);
    Assert.assertEquals("Move: [6, 5], discs Captured: 2, Captured Moves: [[6, 5], [5, 5]]\n" +
                    "Move: [6, 6], discs Captured: 2, Captured Moves: [[6, 6], [5, 5]]\n" +
                    "Move: [2, 6], discs Captured: 2, Captured Moves: [[2, 6], [2, 5]]\n" +
                    "Move: [5, 0], discs Captured: 3, Captured Moves: [[5, 0], [4, 1], [4, 2]]\n" +
                    "Move: [1, 5], discs Captured: 2, Captured Moves: [[1, 5], [2, 5]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (5, 0), discs captured: 3 *
    // (6, 6), discs captured: 2
    // (1, 5), discs captured: 2
    // (2, 6), discs captured: 2
    // (6, 5), discs captured: 2
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 1).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 0).getColor());
    modelForTesting.makeMove(5, 0);

    // Progression moves, just some extra assertions as we progress toward a desired end game state.
    // Could have mocked the board to get to where want, but figured it'd be better to manually
    // make moves and test our code along the way.
    // Skip to next comment.
    aiModel.makeMove(5, 1);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 5).getColor());
    aiModel.makeMove(2, 6);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 1).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 6).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 7).getColor());
    aiModel.makeMove(0, 5);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 4).getColor());
    aiModel.makeMove(6, 2);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 1).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 1).getColor());
    aiModel.makeMove(1, 3);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(0, 3).getColor());
    aiModel.makeMove(2, 2);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 1).getColor());
    aiModel.makeMove(2, 1);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 5).getColor());
    aiModel.makeMove(6, 6);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 7).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 6).getColor());
    aiModel.makeMove(7, 5);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(7, 4).getColor());
    aiModel.makeMove(6, 3);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(7, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(7, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(6, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 3).getColor());
    aiModel.makeMove(3, 7);
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(5, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 6).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 7).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 8).getColor());

    // There are no moves Available for the current player (Ai).
    ReversiModel endStateModel = new ReversiHexModel(aiModel.getCurrentBoardState());
    MaximizeCaptureStrategyMock mockEndState = new MaximizeCaptureStrategyMock(endStateModel);
    Assert.assertEquals("", mockEndState.availableMoves());

    // Here the AI has no moves, it passes back to black
    aiModel.makeMove(6, 7);
    Assert.assertEquals(DiscColor.BLACK, aiModel.getDiscAt(6, 6).getColor());
    Assert.assertEquals(DiscColor.BLACK, aiModel.getDiscAt(6, 7).getColor());

    // black makes a move and then the game ends, because both players have no moves
    aiModel.makeMove(2, 7);
    Assert.assertTrue(aiModel.isGameOver());

    Assert.assertEquals(19, aiModel.getScore(aiModel.currentTurn()));
    Assert.assertEquals(23, aiModel.getScore(aiModel.getOpponent(aiModel.currentTurn())));

    Assert.assertEquals(GameState.STALEMATE, aiModel.getCurrentGameState());

    ReversiGUI gui = new ReversiGUI(aiModel);
    gui.render();
  }

  @Test
  public void testMaximizeSelectsLargestLeft() {
    ReversiHexModelAI rhmai = new ReversiHexModelAI(StrategyType.MAXIMIZE);
    rhmai.startGame(7);
    rhmai.makeMove(2, 2);
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(1, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(2, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(3, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(4, 2).getColor());
  }

  @Test
  public void testAdjacentCornerCellsNotSelected() {
    ReversiHexModelAI rhmai = new ReversiHexModelAI(StrategyType.AVOIDCORNER);
    rhmai.startGame(7);
    rhmai.makeMove(2, 2);
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(2, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(4, 4).getColor());
    // another valid move at on (1,2),(2,2),(3,2),(4,2), but 1,2 is
    // adjacent to a corner so the ai selects (2,4), (3,4), (4,4)
    Assert.assertEquals(MoveRules.applyShiftBasedOnDirection(1, 2, MoveDirection.DOWNLEFT),
            new ArrayList<>(Arrays.asList(0, 3)));
    rhmai.makeMove(3, 5);
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(2, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(4, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, rhmai.getDiscAt(5, 4).getColor());
    // another plausible valid move is
    // (3,4), (2,3), (2,2), (1,1) but (1,1) is adjacent to the top left corner
    Assert.assertEquals(MoveRules.applyShiftBasedOnDirection(1, 1, MoveDirection.UPRIGHT),
            new ArrayList<>(Arrays.asList(2, 0)));
    rhmai.makeMove(5, 2);
  }

  @Test
  public void testAIPassesAvoidCorners() {
    ReversiModel model = new ReversiHexModelAI(StrategyType.AVOIDCORNER);
    model.startGame(7);
    model.makeMove(2, 4);
    model.makeMove(5, 4);
    model.makeMove(3, 6);
    model.pass();
    Assert.assertTrue(model.isGameOver());
    Assert.assertEquals(GameState.STALEMATE, model.getCurrentGameState());
  }

  @Test
  public void testAvoidCornerStrategyFunctionality() {
    ReversiHexModelAI aiModel = new ReversiHexModelAI(StrategyType.AVOIDCORNER);
    ReversiHexModel modelForTesting = new ReversiHexModel();
    aiModel.startGame(7);
    modelForTesting.startGame(7);
    CornerStrategyMock mock = new CornerStrategyMock(modelForTesting, true);

    modelForTesting.makeMove(2, 4);
    aiModel.makeMove(2, 4);
    Assert.assertEquals("Move: [2, 2], discs Captured: 2, Captured Moves: [[2, 2], [3, 2]]\n" +
                    "Move: [5, 2], discs Captured: 2, Captured Moves: [[5, 2], [4, 3]]\n" +
                    "Move: [5, 4], discs Captured: 2, Captured Moves: [[5, 4], [4, 3]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (2, 2), discs Captured: 2 * Since they all have the same numDiscs captured, + is top-leftmost
    // (5, 2), discs Captured: 2
    // (5, 4), discs Captured: 2

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 2).getColor());
    modelForTesting.makeMove(2, 2);

    modelForTesting.makeMove(5, 4);
    aiModel.makeMove(5, 4);
    Assert.assertEquals("Move: [3, 5], discs Captured: 3, Captured Moves: [[3, 5], [3, 4], [2, 3]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (3, 5), discs Captured: 3 * only one move available, other move exist, but do not follow
    //                         this strategy
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 3).getColor());
    modelForTesting.makeMove(3, 5);

    // Ai has no more moves where it could avoid corners, it is forced to pass.
    modelForTesting.makeMove(3, 6);
    aiModel.makeMove(3, 6);
    Assert.assertEquals("", mock.availableMoves());

    // The player can still make a move, the game is still running.
    Assert.assertFalse(aiModel.isGameOver());

    // The players passes after the model passes, the game should be over.
    aiModel.pass();
    Assert.assertTrue(aiModel.isGameOver());
  }

  @Test
  public void testGoForCornerStrategyFunctionality() {
    ReversiHexModelAI aiModel = new ReversiHexModelAI(StrategyType.GOFORCORNER);
    ReversiHexModel modelForTesting = new ReversiHexModel();
    aiModel.startGame(7);
    modelForTesting.startGame(7);
    CornerStrategyMock mock = new CornerStrategyMock(modelForTesting, false);

    modelForTesting.makeMove(2, 2);
    aiModel.makeMove(2, 2);
    Assert.assertEquals("Move: [2, 4], discs Captured: 2, Captured Moves: [[2, 4], [3, 4]]\n" +
                    "Move: [5, 2], discs Captured: 2, Captured Moves: [[5, 2], [4, 3]]\n" +
                    "Move: [5, 4], discs Captured: 2, Captured Moves: [[5, 4], [4, 3]]\n" +
                    "Move: [1, 2], discs Captured: 3, Captured Moves: [[1, 2], [2, 2], [3, 2]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (2, 4), discs Captured: 2
    // (5, 2), discs Captured: 2
    // (5, 4), discs Captured: 2
    // (1, 2), discs Captured: 3 * Is the move closest to corner

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 2).getColor());
    modelForTesting.makeMove(1, 2);

    modelForTesting.makeMove(3, 1);
    aiModel.makeMove(3, 1);
    Assert.assertEquals("Move: [2, 4], discs Captured: 2, Captured Moves: [[2, 4], [3, 4]]\n" +
                    "Move: [5, 2], discs Captured: 2, Captured Moves: [[5, 2], [4, 3]]\n" +
                    "Move: [5, 4], discs Captured: 2, Captured Moves: [[5, 4], [4, 3]]\n" +
                    "Move: [3, 5], discs Captured: 3, Captured Moves: [[3, 5], [3, 4], [2, 3]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (2, 4), discs Captured: 2 * The closest to the corner
    // (5, 2), discs Captured: 2
    // (5, 4), discs Captured: 2
    // (3, 5), discs Captured: 3

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    modelForTesting.makeMove(2, 4);

    modelForTesting.makeMove(3, 5);
    aiModel.makeMove(3, 5);
    Assert.assertEquals("Move: [4, 6], discs Captured: 4, Captured Moves: " +
                    "[[4, 6], [3, 5], [3, 4], [2, 3]]\n" +
                    "Move: [5, 2], discs Captured: 2, Captured Moves: [[5, 2], [4, 3]]\n" +
                    "Move: [5, 4], discs Captured: 2, Captured Moves: [[5, 4], [4, 3]]\n" +
                    "Move: [4, 0], discs Captured: 4, Captured Moves: [[4, 0], " +
                    "[3, 1], [3, 2], [2, 3]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (4, 6), discs Captured: 4
    // (5, 2), discs Captured: 2
    // (5, 4), discs Captured: 2
    // (4, 0), discs Captured: 4 * captures the same amount of discs as the (4, 6) move does,
    //                             is the same distance from its corner than the
    //                             (4, 6) move is from its corner, are equally left as each other,
    //                             but the (4,0) move has priority because it is higher.

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 0).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 1).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 2).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 3).getColor());
    modelForTesting.makeMove(4, 0);

    modelForTesting.makeMove(1, 1);
    aiModel.makeMove(1, 1);
    Assert.assertEquals("Move: [4, 6], discs Captured: 4, Captured Moves: " +
                    "[[4, 6], [3, 5], [3, 4], [2, 3]]\n" +
                    "Move: [5, 2], discs Captured: 2, Captured Moves: [[5, 2], [4, 3]]\n" +
                    "Move: [5, 4], discs Captured: 2, Captured Moves: [[5, 4], [4, 3]]\n" +
                    "Move: [2, 0], discs Captured: 2, Captured Moves: [[2, 0], [1, 1]]",
            mock.availableMoves());

    // Available moves for the Ai after the player makes their move:
    // (4, 6), discs Captured: 4
    // (5, 2), discs Captured: 2
    // (5, 4), discs Captured: 2
    // (2, 0), discs Captured: 2 * Is tied between other moves for least amount of discs captured,
    //                         and yet should still capture the disc at the corner.

    // Assert that it made the proper move, by testing the expected captured discs that were
    // printed by the mock
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 0).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(1, 1).getColor());
    modelForTesting.makeMove(2, 0);

    modelForTesting.makeMove(1, 5);
    aiModel.makeMove(1, 5);

    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(4, 6).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 5).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(3, 4).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 3).getColor());
    Assert.assertEquals(DiscColor.WHITE, aiModel.getDiscAt(2, 2).getColor());
  }

  // note couldn't mock our MiniMax Strategy put breakpoints
  @Test
  public void miniMaxChoosesMaximize() {
    ReversiHexModelAI aiModel = new ReversiHexModelAI(StrategyType.MINIMAX);
    aiModel.startGame(7);
    // this is the maximum up-left move black can make
    // the AI will recognize and also call maximize move to
    // counteract the strategy
    aiModel.makeMove(2, 2);
    Assert.assertTrue(aiModel.getDiscAt(1, 2).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(2, 2).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(3, 2).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(4, 2).getColor() == DiscColor.WHITE);
    // go for corner
    // the ai will go for the corner
    aiModel.makeMove(1, 1);
    Assert.assertTrue(aiModel.getDiscAt(2, 0).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(1, 1).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(1, 2).getColor() == DiscColor.WHITE);

    // no inherent strategy so the AI will default and run max
    // capture
    aiModel.makeMove(5, 4);
    Assert.assertTrue(aiModel.getDiscAt(3, 5).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(3, 4).getColor() == DiscColor.WHITE);
    Assert.assertTrue(aiModel.getDiscAt(2, 3).getColor() == DiscColor.WHITE);
  }
}