package cs3500.imageprocessor.view.text;

import java.io.IOException;

import cs3500.imageprocessor.view.IPView;

/**
 * This interface represents a text-based view for the image processor application.
 */
public interface IPTextView extends IPView {

  /**
   * Performs some action that encourages the user to provide input. This action will vary depending
   * on the view implementation.
   *
   * @throws IOException if the display transmission fails
   */
  void promptInput() throws IOException;

}
