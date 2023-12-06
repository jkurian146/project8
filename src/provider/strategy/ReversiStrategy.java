package provider.strategy;

import provider.model.Coordinate;
import provider.model.PlayerType;
import provider.model.ReadonlyReversiModel;

/**
 * A Strategy interface for choosing where to play next for the given player in a game of Reversi.
 */
public interface ReversiStrategy {
  Coordinate chooseMove(ReadonlyReversiModel model, PlayerType player);

}