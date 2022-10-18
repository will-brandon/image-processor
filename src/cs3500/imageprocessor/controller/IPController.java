package cs3500.imageprocessor.controller;

import java.io.IOException;

/**
 * This interface represents a controller for the image processor application.
 */
public interface IPController {

  /**
   * Begins a new application life cycle. The application should remain active until a quit action
   * is issued by the user.
   *
   * @throws IOException if this controller fails to transmit to the view
   */
  void begin() throws IOException;

}
