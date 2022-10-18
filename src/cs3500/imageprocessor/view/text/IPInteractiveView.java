package cs3500.imageprocessor.view.text;

import java.io.IOException;

import cs3500.imageprocessor.view.text.IPLoggingView;

/**
 * This class represents a view for the image processor application that responds to interactive
 * user input.
 */
public class IPInteractiveView extends IPLoggingView {

  /**
   * Creates a new interactive view for the image processor application. All displayed messages will
   * be transmitted to the given {@link Appendable}.
   *
   * @param appendable the {@link Appendable} to transmit to
   * @throws IllegalArgumentException if the given {@link Appendable} is null
   */
  public IPInteractiveView(Appendable appendable) {
    super(appendable);
  }

  /**
   * Displays a welcome message to the user when the application begins.
   *
   * @throws IOException if the display transmission fails
   */
  @Override
  public void start() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(System.lineSeparator());
    stringBuilder.append("\033[1;96m");
    stringBuilder.append("Welcome to the Image Processor");
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append("\033[0m");
    stringBuilder.append("Application written by Will Brandon and Ella Isgar");
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append("  \033[1m[ Enter commands to the Image Processor ]\033[0m");
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(System.lineSeparator());

    this.appendable.append(stringBuilder.toString());
  }

  /**
   * Displays a dismissal "goodbye" message to the user before the application terminates.
   *
   * @throws IOException if the display transmission fails
   */
  @Override
  public void dismiss() throws IOException {
    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append(System.lineSeparator());
    stringBuilder.append("\033[1;92mThank you for using the Image Processor.\033[0m");
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(System.lineSeparator());

    this.appendable.append(stringBuilder.toString());
  }

  /**
   * Displays a message that instructs the user to enter input. This message does
   * <strong>not</strong> end in a new line. This ensures that the user will be entering a command
   * directly after the prompt message. Only <strong>one</strong> append will be made per
   * {@code promptInput} call, not one per line.
   *
   * @throws IOException if the display transmission fails
   */
  @Override
  public void promptInput() throws IOException {
    this.appendable.append("  >> ");
  }

}
