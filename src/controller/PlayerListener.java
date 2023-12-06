package controller;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that listens for incoming events of a player
 * and stores all player events in a log.
 */
public class PlayerListener {
  private List<PlayerEvent> playereventlog = new ArrayList();

  /**
   * A method that updates the model event log.
   * @param playerEvent a playerEvent
   */
  public void update(PlayerEvent playerEvent) {
    this.playereventlog.add(playerEvent);
  }

  /**
   * A method that gets the most recent model event from the player event log.
   */
  public PlayerEvent getMostRecentEvent() {
    try {
      PlayerEvent mostRecentEvent = playereventlog.get(playereventlog.size() - 1);
      return mostRecentEvent;
    } catch (Exception e) {
      return null;
    }
  }

  public void resetPlayerActions() {
    this.playereventlog.clear();
  }
}
