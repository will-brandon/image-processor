package util;

/**
 * This is a {@link FunctionalInterface} that just has one void method that takes no parameters and
 * throws any type of exception.
 */
@FunctionalInterface
public interface Thrower {

  /**
   * Performs an operation that may throw any type of exception.
   *
   * @throws Exception if the operation failed
   */
  void tryRun() throws Exception;

}
