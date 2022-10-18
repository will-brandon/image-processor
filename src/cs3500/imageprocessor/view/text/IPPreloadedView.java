package cs3500.imageprocessor.view.text;

import java.io.IOException;

import cs3500.imageprocessor.view.text.IPLoggingView;

/**
 * This class represents a view for the image processor application when it simply runs a preloaded
 * script and exits.
 */
public class IPPreloadedView extends IPLoggingView {

  /**
   * Creates a new preloaded script view for the image processor application. All displayed messages
   * will be transmitted to the given {@link Appendable}.
   *
   * @param appendable the {@link Appendable} to transmit to
   * @throws IllegalArgumentException if the given {@link Appendable} is null
   */
  public IPPreloadedView(Appendable appendable) {
    super(appendable);
  }

  /**
   * There is no user interaction once the program begins so no welcome message is displayed.
   */
  @Override
  public void start() throws IOException {
    // No start action is needed
  }

  /**
   * There is no user interaction once the program begins so no dismissal message is displayed.
   */
  @Override
  public void dismiss() throws IOException {
    // No dismissal action is needed
  }

  /**
   * There is no user interaction once the program begins so no prompt message is displayed.
   */
  @Override
  public void promptInput() {
    // No prompt action is needed
  }

}
