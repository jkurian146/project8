package provider.controller;

/**
 * Features interface describing player actions for controller to subscribe as a listener for
 * these actions.
 */
public interface ControllerFeatures {

  /**
   * Player action of making a move.
   */
  void makeMove();

  /**
   * Player action of passing on turn.
   */
  void passOnTurn();

  /**
   * Player action to exit the running program.
   */
  void exitProgram();


}
