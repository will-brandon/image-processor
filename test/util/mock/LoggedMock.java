package util.mock;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Represents a mock with a log to read from.
 */
public abstract class LoggedMock implements Iterable<String> {

  protected List<String> log;

  /**
   * Creates a new mock for logging appends.
   */
  public LoggedMock() {
    log = new ArrayList<>();
  }

  /**
   * Returns a copy of the log.
   *
   * @return a copy of the log
   */
  public List<String> getLog() {
    return List.copyOf(log);
  }

  /**
   * Returns the item at a specific index in the log (faster than copying the complete log every
   * time).
   *
   * @param index the index of the item to retrieve
   * @return the item
   * @throws NoSuchElementException if the index is invalid
   */
  public String getItem(int index) throws NoSuchElementException {
    if (index < 0 || index >= log.size()) {
      throw new NoSuchElementException("Cannot retrieve mock appendable log item at invalid index: "
              + index);
    }

    return log.get(index);
  }

  /**
   * Returns an iterator for the log.
   *
   * @return an iterator for the log
   */
  @Override
  public Iterator<String> iterator() {
    return log.iterator();
  }

}
