package cs3500.imageprocessor.view.text;

import java.io.IOException;

import cs3500.imageprocessor.view.text.IPTextView;

/**
 * This class represents a text-based view for the image processor application that appends text
 * logs to an appendable object.
 */
public abstract class IPAppendableView implements IPTextView {

  protected final Appendable appendable;

  /**
   * Creates a new text view for the image processor application. All displayed messages will be
   * transmitted to the given {@link Appendable}.
   *
   * @param appendable the {@link Appendable} to transmit to
   * @throws IllegalArgumentException if the given {@link Appendable} is null
   */
  public IPAppendableView(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("Appendable given to text view cannot be null");
    }

    this.appendable = appendable;
  }

  @Override
  public abstract void display(String message) throws IOException;

  @Override
  public abstract void displayError(String message) throws IOException;

  @Override
  public abstract void start() throws IOException;

  @Override
  public abstract void dismiss() throws IOException;

  @Override
  public abstract void promptInput() throws IOException;

}
