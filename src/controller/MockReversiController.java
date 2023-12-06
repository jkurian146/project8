package controller;

import model.ReversiHexModel;
import player.Player;
import player.PlayerTurn;
import view.MockReversiGUI;
import java.util.ArrayList;
import java.util.List;

/**
 * A MockReversiController represents a mock for an entity that implements
 * the necessary functions a controller for Reversi would
 * need.
 */
public class MockReversiController implements ControllerListener {

  private final ReversiHexModel reversiModel;
  private final MockReversiGUI reversiView;
  private final Player player;
  private ModelListener modelListener;
  private PlayerListener playerListener;
  private List<MockReversiController> controllerListeners = new ArrayList<>();

  /**
   * A constructor for a MockReversiController.
   * @param reversiModel a reversiModel
   * @param reversiView  a mockReversiGui
   * @param player a player
   */
  public MockReversiController(ReversiHexModel reversiModel,
                               MockReversiGUI reversiView, Player player) {
    this.reversiModel = reversiModel;
    this.reversiView = reversiView;
    this.player = player;
    this.modelListener = new ModelListener();
    this.reversiModel.addListener(this.modelListener);
    this.playerListener = new PlayerListener();
    this.reversiView.addListener(this.playerListener);
  }

  /**
   * A method that listens for events from listener implementations.
   */
  public void listen() {
    PlayerTurn controllersPlayerTurn = this.player.getPlayerTurn();
    PlayerTurn modelsPlayerTurn = this.reversiModel.currentTurn();
    ModelEvent modelEvent = this.modelListener.getMostRecentEvent();
    if (modelEvent.getModelEventType() ==  ModelEventType.PLAYER1TURN) {
      this.reversiView.showPopup("It Is Now Your Turn: Player 1");
      if (modelsPlayerTurn == PlayerTurn.PLAYER1 && controllersPlayerTurn == PlayerTurn.PLAYER1) {
        // listen for a PlayerListener move
        PlayerEvent playerEvent = getNextPlayerAction();
        if (playerEvent.getExecutingPlayer() == PlayerTurn.PLAYER1) {
          handlePlayerEvent(playerEvent);
          this.reversiView.render();
          this.notifyListeners();
        }
        else {
          // display pane illegal move not your turn
          this.reversiView.showPopup(modelEvent.getMessage());
        }
      } else {
        // display pane for illegal move: not your turn
        this.reversiView.showPopup(modelEvent.getMessage());
      }
    }
    else if (modelEvent.getModelEventType() == ModelEventType.PLAYER2TURN) {
      this.reversiView.showPopup("It Is Now Your Turn Player 2");
      if (modelsPlayerTurn == PlayerTurn.PLAYER2 && controllersPlayerTurn == PlayerTurn.PLAYER2) {
        // listen for a PlayerListener move
        PlayerEvent playerEvent = getNextPlayerAction();
        System.out.println(playerEvent.getPlayerEventType());
        if (playerEvent.getExecutingPlayer() == PlayerTurn.PLAYER2) {
          handlePlayerEvent(playerEvent);
          this.reversiView.render();
          this.notifyListeners();
        }
        else {
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
   * The addListener method adds relevant listeners.
   */
  public void addListener(MockReversiController reversiController) {
    this.controllerListeners.add(reversiController);
  }

  /**
   * The notifyListeners method notifies relevant listeners.
   */
  public void notifyListeners() {
    for (MockReversiController rc: this.controllerListeners) {
      rc.handleEvent();
    }
  }

  @Override
  public void handleEvent() {
    this.reversiView.render();
  }
}