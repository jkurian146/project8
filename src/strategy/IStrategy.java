package strategy;

import java.util.List;

import provider.strategy.ReversiStrategy;

/**
 * Represents a General Interface for a Strategy Implementation.
 */
public interface IStrategy extends ReversiStrategy {

  /**
   * execute a strategy based on the strategy implementation.
   *
   * @return a position as a List for the model to execute.
   */
  List<Integer> executeStrategy();

  /**
   * Gets a strategies' implementation type.
   */
  StrategyType getStrategyType();

}
