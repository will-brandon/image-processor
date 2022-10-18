package cs3500.imageprocessor.util.image;

/**
 * A function that takes one parameter returns an output and may throw an exception.
 *
 * @param <I> the input type
 * @param <O> the output type
 */
@FunctionalInterface
public interface ThrowingFunction<I, O> {

  /**
   * The function that accepts a parameters and returns a value.
   *
   * @param input the input parameter
   * @returns the output value
   * @throws Exception if the function throws an exception
   */
  O accept(I input) throws Exception;

}
