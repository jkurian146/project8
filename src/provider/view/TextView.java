package provider.view;

/**
 * Represents an interface to visualize the game of Reversi.
 */
public interface TextView {

  /**
   * Returns a string-based view of the Reversi game model at any point of playing the game.
   * @return the String textual view of the board
   */
  String toString();

}