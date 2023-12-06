package controller;

/**
 * A ControllerListener represents an entity that listens from a controller for
 * the sake of other Controllers.
 */
public interface ControllerListener {

  /**
   * The Handle Event Method prepares the of each controller to sync.
   */
  void handleEvent();
}
