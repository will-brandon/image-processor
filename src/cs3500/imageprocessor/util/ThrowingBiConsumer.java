package cs3500.imageprocessor.util;

/**
 * A consumer that takes two parameters and may throw an exception.
 *
 * @param <A> the first parameter type
 * @param <B> the second parameter type
 */
@FunctionalInterface
public interface ThrowingBiConsumer<A, B> {

  /**
   * The function that accepts two parameters.
   *
   * @param a the first parameter
   * @param b the second parameter
   * @throws Exception if the consumer function throws an exception
   */
  void accept(A a, B b) throws Exception;

}
