package controller;

import model.ReversiModel;
import player.Player;
import player.PlayerTurn;
import view.ReversiGUI;
import java.util.ArrayList;
import java.util.List;

/**
 * A ReversiController represents an entity that implements
 * the necessary functions a controller for Reversi would
 * need.
 */
public class ReversiController implements ControllerListener {

  private final ReversiModel reversiModel;
  private final ReversiGUI reversiView;

  private final Player player;

  private ModelListener modelListener;
  private PlayerListener playerListener;
  private List<ReversiController> controllerListeners = new ArrayList<>();

  /**
   * A constructor for a ReversiController.
   * @param reversiModel a reversiModel.
   * @param reversiView a reversiView
   * @param player a player
   */
  public ReversiController(ReversiModel reversiModel, ReversiGUI reversiView, Player player) {
    this.reversiModel = reversiModel;
    this.reversiView = reversiView;
    this.player = player;
    this.modelListener = new ModelListener();
    this.reversiModel.addListener(modelListener);
    this.playerListener = new PlayerListener();
    this.reversiView.addListener(this.playerListener);
  }

  /**
   * A method that listens for a player event to execute on the model.
   */
  public void listen() {
    PlayerTurn controllersPlayerTurn = this.player.getPlayerTurn();
    PlayerTurn modelsPlayerTurn = this.reversiModel.currentTurn();
    ModelEvent modelEvent = this.modelListener.getMostRecentEvent();
    if (modelEvent.getModelEventType() == ModelEventType.PLAYER1TURN) {
      this.reversiView.showPopup("It Is Now Your Turn: Player 1");
      if (modelsPlayerTurn == PlayerTurn.PLAYER1 && controllersPlayerTurn == PlayerTurn.PLAYER1) {
        // listen for a PlayerListener move
        PlayerEvent playerEvent = getNextPlayerAction();
        if (playerEvent.getExecutingPlayer() == PlayerTurn.PLAYER1) {
          handlePlayerEvent(playerEvent);
          this.reversiView.render();
          this.notifyListeners();
        } else {
          // display pane illegal move not your turn
          this.reversiView.showPopup(modelEvent.getMessage());
        }
      } else {
        // display pane for illegal move: not your turn
        this.reversiView.showPopup(modelEvent.getMessage());
      }
    } else if (modelEvent.getModelEventType() == ModelEventType.PLAYER2TURN) {
      this.reversiView.showPopup("It Is Now Your Turn Player 2");
      if (modelsPlayerTurn == PlayerTurn.PLAYER2 && controllersPlayerTurn == PlayerTurn.PLAYER2) {
        // listen for a PlayerListener move
        PlayerEvent playerEvent = getNextPlayerAction();
        System.out.println(playerEvent.getPlayerEventType());
        if (playerEvent.getExecutingPlayer() == PlayerTurn.PLAYER2) {
          handlePlayerEvent(playerEvent);
          this.reversiView.render();
          this.notifyListeners();
        } else {
          this.reversiView.showPopup(modelEvent.getMessage());
        }
      } else {
        this.reversiView.showPopup(modelEvent.getMessage());
      }
    }
  }

  private PlayerEvent getNextPlayerAction() {
    PlayerEvent nextPlayerAction = null;
    while (nextPlayerAction == null) {
      nextPlayerAction = this.playerListener.getMostRecentEvent();
    }
    this.playerListener.resetPlayerActions();
    return nextPlayerAction;
  }

  private void handlePlayerEvent(PlayerEvent playerEvent) {
    if (playerEvent.getPlayerEventType() == PlayerEventType.PASS) {
      this.reversiModel.pass();
      this.reversiView.showPopup(this.player.getPlayerTurn() + " passed");
    } else {
      String[] coordinates = playerEvent.getDescription().split(" ");
      this.reversiModel.makeMove(Integer.parseInt(coordinates[0]),
              Integer.parseInt(coordinates[1]));
      this.reversiView.showPopup(this.player.getPlayerTurn() + " Moved to "
              + Integer.parseInt(coordinates[0]) + " " + Integer.parseInt(coordinates[1]));
    }
  }

  /**
   * A method that adds a listener.
   */
  public void addListener(ReversiController reversiController) {
    this.controllerListeners.add(reversiController);
  }

  /**
   * A method that notifies a listener.
   */
  public void notifyListeners() {
    for (ReversiController rc : this.controllerListeners) {
      rc.handleEvent();
    }
  }

  @Override
  public void handleEvent() {
    this.reversiView.render();
  }
}
