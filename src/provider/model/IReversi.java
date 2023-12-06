package provider.model;

/**
 * Represents the primary model interface for playing a game of IReversi.
 * Reversi, a two-player game, is played on a standard grid comprising cells. Each player
 * selects either black or white as their color, and the game utilizes discs with one side black
 * and the other white. Initially, an equal number of both colors of discs is positioned in the
 * center of the hexagonal grid in this version of the game.
 */
public interface IReversi extends ReadonlyReversiModel {
  /**
   * Allows a player to select a legal empty cell and play a disc in that cell. A play is legal for
   * Player 1 if the disc being played is adjacent (in at least one direction) to a straight line
   * of the opponent Player 2's discs, at the far end of which is another player 1 disc.
   * The result of a legal move is that all of player 2’s discs in all directions that
   * are sandwiched between two discs of player 1 get flipped to player 1.
   * @param chosenCell the cell to make a move on
   */
  void completeMove(Cell chosenCell);


  void startGame();


  /**
   * Allows the current player to pass on their turn, and the turn moves to the other player. Keeps
   * track of whether two consecutive passes have been made.
   */
  void passMove();

}
