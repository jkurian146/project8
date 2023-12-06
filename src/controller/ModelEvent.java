package controller;

/**
 * A Class that represents a singular ModelEvent.
 */
public class ModelEvent {
  private final ModelEventType modelEventType;
  private final String message;

  /**
   * A constructor for a ModelEvent.
   * @param modelEventType a ModelEventType
   * @param message a Message
   */
  public ModelEvent(ModelEventType modelEventType, String message) {
    this.modelEventType = modelEventType;
    this.message = message;
  }

  /**
   * A getter that gets the message information of a model event.
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * A getter that gets the ModelEventType of a model event.
   */
  public ModelEventType getModelEventType() {
    return this.modelEventType;
  }
}
