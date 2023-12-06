package provider.view;

import provider.controller.ControllerFeatures;
import provider.model.Coordinate;

/**
 * A view for Reversi: display the game board and provide visual interface for users.
 */
public interface ReversiView {

  /**
   * Refresh the view to reflect any changes in the game state.
   */
  void refresh();

  /**
   * Make the view visible to start the game session.
   */
  void makeVisible();

  void addFeatures(ControllerFeatures features);

  void selectCell();

  void makeMove();

  void passOnTurn();

  Coordinate getSelectedCellToMoveOn();

  void displayErrorMsg();
}

