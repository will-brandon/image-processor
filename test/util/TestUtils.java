package util;

import java.io.File;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * A class to hold miscellaneous utility functions for all tests.
 */
public final class TestUtils {

  private static final Random random = new Random(123);

  /**
   * Uses JUnit 4 to check if a given callable lambda throws an {@link Exception} with the proper
   * type and message.
   *
   * @param type    the class of the exception that is thrown
   * @param thrower the lambda ({@link Thrower} interface) expected to throw an exception
   * @param message the expected exception message
   */
  public static <E extends Exception> void assertException(Class<E> type,
                                                                  String message,
                                                                  Thrower thrower) {
    try {
      thrower.tryRun();
      fail("Failed to throw exception for thrower");
    } catch (Exception e) {
      assertEquals(type, e.getClass());
      assertEquals(message, e.getMessage());
    }
  }

  /**
   * Randomly (seeded) generates a string of Unicode characters that can be up to the given max
   * length (inclusive). These characters can have accents and funky symbols but the only whitespace
   * character possible is the space.
   *
   * @param maxLength the maximum length of the output string (inclusive), must be non-negative
   * @return the randomly generated string
   * @throws IllegalArgumentException if the given max length is negative
   */
  public static String randomString(int maxLength) throws IllegalArgumentException {
    if (maxLength < 0) {
      throw new IllegalArgumentException("Max length for random string must be non-negative");
    }

    StringBuilder stringBuilder = new StringBuilder();

    // String will be a random length up to bound (exclusive)
    int length = random.nextInt(maxLength + 1);

    for (int i = 0; i < length; i++) {

      // Randomly select any reasonable unicode character (some of these have crazy accents and
      // symbols but no weird meta-characters are included in this range)
      stringBuilder.append(randomCharacter(0x0020, 0x04FF));
    }

    return stringBuilder.toString();
  }

  /**
   * Randomly (seeded) selects a Unicode character between the lower bound (inclusive) and upper
   * bound (inclusive).
   *
   * <p><strong>Recommended:</strong> use a {@code char} for each bound, it will automatically
   * convert to an integer.</p>
   *
   * @param unicodeLowerBound the lower unicode character bound (inclusive)
   * @param unicodeUpperBound the upper unicode character bound (inclusive)
   * @return the random character
   */
  private static char randomCharacter(int unicodeLowerBound, int unicodeUpperBound) {
    return (char) (unicodeLowerBound + random.nextInt(
            unicodeUpperBound - unicodeLowerBound + 1));
  }

  /**
   * Removes a file that was presumably created during the test but fails the test if it never
   * existed.
   *
   * @param pathname the file's path string
   */
  public static void assertFileExistsAndRemove(String pathname) {
    File file = new File(pathname);

    if (!file.exists()) {
      fail("Expected file is nonexistent");
    }

    file.delete();
  }

}
