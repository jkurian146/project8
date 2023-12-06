package provider.model;

//player can be either a human player or a computer player

/**
 * Represents an interface for a player in a game of Reversi. This player can be Human or a Computer
 * player.
 */
public interface Player {
  /**
   * Returns the best coordinate to make a move on for a Player. For human players, the next move
   * is determined by what cell is clicked in the view, while for machine players, the next move
   * is chosen by a strategy and returned.
   * @return the Coordinate to make the next move for
   */
  Coordinate getMeMyNextMove();


  /**
   * Returns whether this Player is Player 1 or PLayer 2 in the game of Reversi, according to the
   * model.
   * @return PlayerType, which player this Player is
   */
  PlayerType getPlayerType();

}
