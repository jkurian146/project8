package provider.model;


/**
 * Represents the immutable model interface for playing a game of IReversi.
 * Reversi, a two-player game, is played on a standard grid comprising cells. Each player
 * selects either black or white as their color, and the game utilizes discs with one side black
 * and the other white. Initially, an equal number of both colors of discs is positioned in the
 * center of the hexagonal grid in this version of the game.This interface contains only
 * observation methods, and no mutator methods.
 */
public interface ReadonlyReversiModel {

  /**
   * Returns the game board which allows user to see it in the current game of Reversi.
   * @return the Board associated with the game
   */
  Board getGameBoard();


  /**
   * Determines whether the current game is over. If two players pass in a row, or if there
   * are no more valid moves to be made, then the game is over.
   * @return true if game is over, false if not
   */
  boolean isGameOver();

  /**
   * Gets the winner of the game after the game is over. The winner is determined by the player
   * that has filled more cells in the board. If both players fill an equal number of cells, then
   * the game is a tie.
   * @return the Player that won the game, or null if the game is tied
   */
  PlayerType getWinner();
  

  /**
   * Gets the current player that whose turn it is on.
   * @return the currentPlayer, either Player 1 or Player 2.
   */
  PlayerType getCurrentPlayer();


  /**
   * Allows the user to get the size of the board they are playing the game with.
   * @return the board's size
   */
  int getBoardSize();

  /**
   * Returns a copy of the cell at the given cell coordinates to the user, including the cell's
   * color.
   * @param q the user's desired q coordinate
   * @param r the user's desired r coordinate
   * @return a Cell with its contents
   */
  Cell getCellContentsAt(int q, int r);

  /**
   * Determines if a user can play on the coordinate given. In order for it to be playable,
   * it must be in bounds, not null, and empty.
   * @param coord the coordinate to make a move on
   * @return true or false
   */
  boolean canThisCellBePlayedOn(Coordinate coord);


  /**
   * Checks if the current player has any legal moves left in the game. Does this by looking
   * through all empty cells in the board and if any paths of those cells can be flipped in favor
   * of this current player.
   * @return true or false
   */
  boolean doesCurrentPlayerHaveAnyLegalMoves();

  /**
   * Returns the current player's score. The score for a player is determined by the total number
   * of cells in the board filled with that player's color.
   * @return the number of cells filled with the current player
   */
  int getCurrentPlayerScore(PlayerType playerWhosScoreWeWant);


}
