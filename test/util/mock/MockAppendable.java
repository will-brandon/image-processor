package util.mock;

/**
 * This class is a mock for an {@link Appendable} that will log the appends made to it.
 */
public class MockAppendable extends LoggedMock implements Appendable {

  /**
   * Appends the given character sequence to the log for reading later.
   *
   * @param csq the character sequence to log
   * @return this {@code MockAppendable} for convenience
   * @throws IllegalArgumentException if the character sequence is null
   */
  @Override
  public Appendable append(CharSequence csq) throws IllegalArgumentException {
    if (csq == null) {
      throw new IllegalArgumentException("Character sequence cannot be null");
    }
    this.log.add(csq.toString());
    return this;
  }

  /**
   * Appends the given character sequence to the log for reading later. Crops the sequence by the
   * given start (inclusive) and end (exclusive).
   *
   * @param csq   the character sequence to log
   * @param start the index to start cropping (inclusive)
   * @param end   the index to stop cropping (exclusive)
   * @return this {@code MockAppendable} for convenience
   * @throws IllegalArgumentException  if the character sequence is null
   * @throws IndexOutOfBoundsException if the given range does not fall within the character
   *                                   sequence
   */
  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IllegalArgumentException,
          IndexOutOfBoundsException {
    append(csq.subSequence(start, end));
    return this;
  }

  /**
   * Appends the given character to the log for reading later.
   *
   * @param c the character to log
   * @return this {@code MockAppendable} for convenience
   */
  @Override
  public Appendable append(char c) {
    this.log.add(Character.toString(c));
    return this;
  }

}
