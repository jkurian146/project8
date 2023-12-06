package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class that listens for incoming events and stores
 * all events in a model.
 */
public class ModelListener {
  private List<ModelEvent> modeleventlog =
          new ArrayList(Arrays.asList(new ModelEvent(ModelEventType.PLAYER1TURN,
                  "It's Your Turn Player 1")));

  /**
   * A method that updates the model event log.
   * @param modelEvent a modelEvent
   */
  public void update(ModelEvent modelEvent) {
    this.modeleventlog.add(modelEvent);
  }

  /**
   * A method that gets the most recent model event from the model event log.
   */
  public ModelEvent getMostRecentEvent() {
    ModelEvent mostRecentEvent = modeleventlog.get(modeleventlog.size() - 1);
    return mostRecentEvent;
  }
}
