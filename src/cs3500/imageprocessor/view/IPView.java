package cs3500.imageprocessor.view;

import java.io.IOException;

/**
 * This interface represents a view for the image processor application.
 */
public interface IPView {

  /**
   * Displays the given message to the user interacting with the program.
   *
   * @param message the message to display
   * @throws IOException if the display transmission fails
   */
  void display(String message) throws IOException;

  /**
   * Displays the given error message to the user interacting with the program. Specific
   * implementations may choose to use a different style of displaying this message, but in some
   * implementations this could function the same as the {@code display} method.
   *
   * @param message the message to display
   * @throws IOException if the display transmission fails
   */
  void displayError(String message) throws IOException;

  /**
   * Starts the view when the application begins.
   *
   * @throws IOException if the view fails to perform a start action
   */
  void start() throws IOException;

  /**
   * Dismisses the view before the application quits.
   *
   * @throws IOException if the view fails to perform a dismissal action
   */
  void dismiss() throws IOException;

}
