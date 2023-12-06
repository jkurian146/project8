package provider.model;

/**
 * Features interface that represents notifications by the model to tell players it is their
 * turn, and wait for the player to choose a move.
 */
public interface ModelFeatures {

  /**
   * Notifies a Player object when it is their turn to get the next move, and waits until the
   * desired move is returned.
   */
  void notifyWhichPlayersTurn();

  /**
   * Refreshes the view after a move has been made.
   */
  void refreshView();
}
