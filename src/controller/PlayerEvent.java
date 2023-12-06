package controller;

import player.PlayerTurn;

/**
 * A Class that represents a singular PlayerEvent.
 */
public class PlayerEvent {
  private final PlayerEventType playerEventType;
  private final String description;
  private final PlayerTurn executingPlayer;

  /**
   * A constructor for a PlayerEvent.
   * @param playerEventType a playerEventType
   * @param description a description
   * @param executingPlayer the executing player
   */
  public PlayerEvent(PlayerEventType playerEventType, String description,
                     PlayerTurn executingPlayer) {
    this.playerEventType = playerEventType;
    this.description = description;
    this.executingPlayer = executingPlayer;
  }

  /**
   * A getter that gets the Player Event Type of player event.
   */
  public PlayerEventType getPlayerEventType() {
    return playerEventType;
  }

  /**
   * A getter that gets the description information of a player event.
   */
  public String getDescription() {
    return description;
  }

  /**
   * A getter that gets the exececuting player's player
   * type of player event.
   */
  public PlayerTurn getExecutingPlayer() {
    return executingPlayer;
  }
}
