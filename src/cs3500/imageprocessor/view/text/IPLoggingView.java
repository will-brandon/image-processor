package cs3500.imageprocessor.view.text;

import java.io.IOException;

import cs3500.imageprocessor.view.text.IPAppendableView;

/**
 * This class represents a view for the image processor application that logs messages to an
 * appendable object. Depending on the class that implements the interactive methods, the view may
 * or may not provide text based feedback for user interaction during the application's life cycle,
 * such as prompts to enter input.
 */
public abstract class IPLoggingView extends IPAppendableView {

  /**
   * Creates a new logging appendable view for the image processor application. All displayed
   * messages will be transmitted to the given {@link Appendable}.
   *
   * @param appendable the {@link Appendable} to transmit to
   * @throws IllegalArgumentException if the given {@link Appendable} is null
   */
  public IPLoggingView(Appendable appendable) {
    super(appendable);
  }

  /**
   * Transmits the given message to the {@link Appendable} of this text view. Includes a newline
   * character at the end to ensure that sequential calls will be put on their own lines. If the
   * given message string is null, only a newline is displayed. Only <strong>one</strong> append
   * will be made per {@code display} call, not one per line.
   *
   * @param message the message to display
   * @throws IOException if the display transmission fails
   */
  @Override
  public void display(String message) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("   ");

    if (message != null) {
      stringBuilder.append("\033[3;37m");
      stringBuilder.append(message);
      stringBuilder.append("\033[0m");
    }

    stringBuilder.append(System.lineSeparator());

    this.appendable.append(stringBuilder.toString());
  }

  /**
   * Transmits the given error message to the {@link Appendable} of this text view. Includes a
   * newline character at the end to ensure that sequential calls will be put on their own lines. If
   * the given message string is null, only a newline is displayed. Only <strong>one</strong> append
   * will be made per {@code displayError} call, not one per line.
   *
   * <p>The ANSI red escape sequence is used so that
   * the message will be differentiated from non-error messages.</p>
   *
   * @param message the error message to display
   * @throws IOException if the display transmission fails
   */
  @Override
  public void displayError(String message) throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    if (message != null) {
      stringBuilder.append("   \033[91m");
      stringBuilder.append(message);
      stringBuilder.append("\033[0m");
    }

    stringBuilder.append(System.lineSeparator());

    this.appendable.append(stringBuilder.toString());
  }

  @Override
  public abstract void start() throws IOException;

  @Override
  public abstract void dismiss() throws IOException;

  @Override
  public abstract void promptInput() throws IOException;

}
