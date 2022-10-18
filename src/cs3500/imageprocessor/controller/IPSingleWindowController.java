package cs3500.imageprocessor.controller;

import java.io.IOException;

import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a controller that interacts with a single graphical user interface window
 * view.
 */
public class IPSingleWindowController implements IPController {

  private final IPView view;

  /**
   * Creates a new controller for the Image Processor application that uses a GUI.
   *
   * @param view        the view to transmit output to
   * @throws IllegalArgumentException if the model or view are null
   */
  public IPSingleWindowController(IPView view)
          throws IllegalArgumentException {
    if (view == null) {
      throw new IllegalArgumentException("View given to IPStandardController cannot be null");
    }

    this.view = view;
  }

  @Override
  public void begin() throws IOException {

    // The GUI view has complete control of the run loop, it is dispatched in a new thread in the
    // view.start method, therefore the start method will return but the window will still be active
    // on its own thread.
    view.start();
  }

}
