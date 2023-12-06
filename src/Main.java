import controller.ReversiController;
import model.ReversiHexModel;
import model.ReversiHexModelAI;
import model.ReversiModel;
import player.Player;
import player.PlayerTurn;
import strategy.StrategyType;
import view.ReversiGUI;

import javax.swing.JOptionPane;

/**
 * The `Main` class represents a driver for our Interactive Reversi Game.
 */
public class Main {
  /**
   * Main Method that drives the program.
   */
  public static void main(String[] args) {

    int boardsize = 11;
    try {
      boardsize = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter your Board"
              + " Size:"));
    } catch (NumberFormatException nfe) {
      // do nothing
    }
    if (args.length == 2) {
      String param1 = args[0];
      String param2 = args[1];
      chooseGame(param1, param2, boardsize);
    } else {
      makeGame(boardsize, false, null);
    }
  }

  private static void chooseGame(String param1, String param2, int boardsize) {
    if (param1.equalsIgnoreCase("human") && (param2.equalsIgnoreCase("strategy1") ||
            param2.equalsIgnoreCase("strategy2")
            || param2.equalsIgnoreCase("strategy3")
            || param2.equalsIgnoreCase("strategy4"))) {
      StrategyType st = getStrategyType(param2);
      makeGame(boardsize, true, st);
    } else if (param2.equalsIgnoreCase("human") && (param1.equalsIgnoreCase("strategy1")
            || param1.equalsIgnoreCase("strategy2")
            || param1.equalsIgnoreCase("strategy3")
            || param1.equalsIgnoreCase("strategy4"))) {
      StrategyType st = getStrategyType(param1);
      makeGame(boardsize, true, st);
    } else {
      makeGame(boardsize, false, null);
    }
  }

  private static StrategyType getStrategyType(String param1) {
    StrategyType st;
    switch (param1) {
      case "strategy1":
        st = StrategyType.MAXIMIZE;
        break;
      case "strategy2":
        st = StrategyType.AVOIDCORNER;
        break;
      case "strategy3":
        st = StrategyType.GOFORCORNER;
        break;
      case "strategy4":
        st = StrategyType.MINIMAX;
        break;
      default:
        st = StrategyType.MAXIMIZE;
        break;
    }
    return st;
  }

  private static void makeGame(int boardsize, boolean isAiGame, StrategyType strategyType) {
    ReversiModel model;
    if (isAiGame) {
      model = new ReversiHexModelAI(strategyType);
    } else {
      model = new ReversiHexModel();
    }
    model.startGame(boardsize);
    ReversiGUI viewPlayer1 = new ReversiGUI(model);
    ReversiGUI viewPlayer2 = new ReversiGUI(model);
    Player player1 = new Player(PlayerTurn.PLAYER1);
    Player player2 = new Player(PlayerTurn.PLAYER2);
    ReversiController controller1 = new ReversiController(model, viewPlayer1, player1);
    ReversiController controller2 = new ReversiController(model, viewPlayer2, player2);
    controller1.addListener(controller2);
    controller2.addListener(controller1);
    while (true) {
      controller1.listen();
      if (model.isGameOver()) {
        endScreen(model, viewPlayer1, viewPlayer2);
        break;
      }
      controller2.listen();
      if (model.isGameOver()) {
        endScreen(model, viewPlayer1, viewPlayer2);
        break;
      }
    }
  }

  private static void endScreen(ReversiModel model, ReversiGUI viewPlayer1,
                                ReversiGUI viewPlayer2) {
    switch (model.getCurrentGameState()) {
      case PLAYER2WIN:
        viewPlayer1.showPopup("You Lost: Try Using A Strategy" +
                "\n-Maximize\n-Going For Corners\n-Avoiding Corner\n" +
                "-Minimizing Your Opponents Best Move");
        viewPlayer2.showPopup("You Won");
        break;
      case PLAYER1WIN:
        viewPlayer1.showPopup("You Won");
        viewPlayer2.showPopup("You Lost: Try Using A Strategy" +
                "\n-Maximize\n-Going For Corners\n-Avoiding Corner\n" +
                "-Minimizing Your Opponents Best Move");
        break;
      case STALEMATE:
        viewPlayer1.showPopup("You Tied: Try Using A Strategy" +
                "\n-Maximize\n-Going For Corners\n-Avoiding Corner\n" +
                "-Minimizing Your Opponents Best Move");
        viewPlayer2.showPopup("You Tied: Try Using A Strategy" +
                "\n-Maximize\n-Going For Corners\n-Avoiding Corner\n" +
                "-Minimizing Your Opponents Best Move");
        break;
      default:
        break;
    }
  }
}
