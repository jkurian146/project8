package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import controller.ModelListener;
import discs.Disc;
import player.Player;
import player.PlayerTurn;
import provider.model.Cell;
import provider.model.Coordinate;
import provider.strategy.ReversiStrategy;
import strategy.CornersStrategy;
import strategy.IStrategy;
import strategy.MaximizeCaptureStrategy;
import strategy.MiniMaxStrategy;
import strategy.StrategyType;

/**
 * A 'ReversiHexModel' defines a hexagonal Reversi game with an AI player.
 */
public class ReversiHexModelAI extends ReversiHexModel implements ReversiModel {

  private final StrategyType strategyType;
  private final Player player1;
  private Player player2;
  private List<ReadOnlyReversiModel> gameStates;
  private List<List<Integer>> allMoves;
  private boolean firstRun;

  /**
   * A 'ReversiHexModel' defines a hexagonal Reversi game with an AI player.
   *
   * @param strategyType a strategy that the AI should implement.
   */
  public ReversiHexModelAI(StrategyType strategyType) {
    super();
    this.strategyType = strategyType;
    this.player1 = new Player(PlayerTurn.PLAYER1);
    this.gameStates = new ArrayList<>();
    this.allMoves = new ArrayList<>();
    this.firstRun = true;
  }

  /**
   * A 'ReversiHexModel' defines a hexagonal Reversi game with an AI player.
   *
   * @param r a model to be deep copied.
   */
  public ReversiHexModelAI(ReversiHexModelAI r) {
    this.gameOn = r.gameOn;
    this.numRows = r.numRows;
    this.numColumns = r.numColumns;
    this.type = r.type;
    this.gameBoard = iterateOverDiscArray(r.gameBoard);
    this.pt = r.pt;
    this.playerAction = r.playerAction;
    this.state = r.state;
    this.playerColorMap = r.playerColorMap;
    this.strategyType = r.strategyType;
    this.player1 = r.player1;
    this.gameStates = r.gameStates;
    this.allMoves = r.allMoves;
    this.firstRun = r.firstRun;
  }

  private Disc[][] iterateOverDiscArray(Disc[][] r) {
    Disc[][] res = new Disc[r.length][r.length];
    for (int i = 0; i < r.length; i++) {
      for (int j = 0; j < r.length; j++) {
        res[i][j] = r[i][j];
      }
    }
    return res;
  }

  private Player createAI() {
    switch (this.strategyType) {
      case MINIMAX:
        return new Player(PlayerTurn.PLAYER2, new MiniMaxStrategy(this, PlayerTurn.PLAYER2));
      case MAXIMIZE:
        return new Player(PlayerTurn.PLAYER2,
                new MaximizeCaptureStrategy(this, PlayerTurn.PLAYER2));
      case AVOIDCORNER:
        return new Player(PlayerTurn.PLAYER2, new CornersStrategy(this,
                PlayerTurn.PLAYER2, true));
      case GOFORCORNER:
        return new Player(PlayerTurn.PLAYER2, new CornersStrategy(this,
                PlayerTurn.PLAYER2, false));
      default:
        throw new IllegalStateException("Can't Create an AI without a strategy");
    }
  }

  private ReadOnlyReversiModel createCopyOfModel(ReversiHexModel rorm) {
    ReadOnlyReversiModel copy = rorm;
    return copy;
  }

  private void moveNonAi(int x, int y) {
    super.makeMove(x, y);
    this.allMoves.add(new ArrayList<>(Arrays.asList(x, y)));
    if (this.player2 == null) {
      this.player2 = createAI();
    }
  }

  private void moveAi() {
    IStrategy iStrategy = this.player2.getIStrategy();
    List<Integer> aiMove = iStrategy.executeStrategy();
    boolean inBounds = true;
    try {
      this.getDiscAt(aiMove.get(0), aiMove.get(1));
    } catch (IllegalArgumentException iae) {
      inBounds = false;
    }
    if (!inBounds) {
      this.pass();
    } else {
      try {
        super.makeMove(aiMove.get(0), aiMove.get(1));
        System.out.println("AI Move " + aiMove.get(0) + " " + aiMove.get(1));
        this.allMoves.add(new ArrayList<>(Arrays.asList(aiMove.get(0),
                aiMove.get(1))));
      } catch (IllegalStateException | IllegalArgumentException ise) {
        this.pass();
      }
    }
  }

  @Override
  public void pass() {
    if (super.pt == this.player1.getPlayerTurn()) {
      super.pt = this.player2.getPlayerTurn();
      this.moveAi();
    } else {
      super.pt = this.player1.getPlayerTurn();
    }
    this.allMoves.add(new ArrayList<>(Arrays.asList(-1)));
    this.playerAction.append("pass\n");
  }

  @Override
  public void addListener(ModelListener ml) {
    super.addListener(ml);
  }

  @Override
  public List<ReadOnlyReversiModel> getGameStates() {
    return this.gameStates;
  }

  @Override
  public List<List<Integer>> getMoves() {
    return this.allMoves;
  }
}