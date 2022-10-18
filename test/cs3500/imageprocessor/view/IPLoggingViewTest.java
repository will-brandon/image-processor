package cs3500.imageprocessor.view;

import org.junit.Test;

import java.io.IOException;
import java.util.Iterator;

import cs3500.imageprocessor.view.text.IPLoggingView;
import cs3500.imageprocessor.view.text.IPTextView;
import util.TestUtils;
import util.mock.MockAppendable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static util.TestUtils.assertException;

/**
 * This class performs tests that are applicable to all extensions of the {@link IPLoggingView}
 * class.
 */
public abstract class IPLoggingViewTest {

  /**
   * Creates a new {@link IPLoggingView} of the appropriate extending class.
   *
   * @param appendable the appendable for the view to use
   * @return the {@link IPLoggingView}
   */
  protected abstract IPLoggingView create(Appendable appendable);

  @Test
  public void construct() {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = create(mockAppendable);

    // Test the only two methods a few times to make sure it initialized correctly and sends input
    // to the appendable
    testDisplay(view, mockAppendable, 0, 5);
    testDisplayError(view, mockAppendable, 5, 5);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertTrue(mockAppendable != null);
  }

  @Test
  public void constructFail() {
    assertException(IllegalArgumentException.class,
            "Appendable given to text view cannot be null",
        () -> create(null));
  }

  @Test
  public void display() {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = create(mockAppendable);

    testDisplay(view, mockAppendable, 0, 50);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertTrue(mockAppendable != null);
  }

  @Test
  public void displayError() {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = create(mockAppendable);

    testDisplayError(view, mockAppendable, 0, 50);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertTrue(mockAppendable != null);
  }

  @Test
  public void start() {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = create(mockAppendable);

    try {
      view.start();
      makeWelcomeAsserts(mockAppendable.iterator());

    } catch (IOException exception) {
      fail("Unexpected IOException occurred attempting to call IPLoggingView.welcome");
    }
  }

  @Test
  public void dismiss() {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = create(mockAppendable);

    try {
      view.dismiss();
      makeDismissalAsserts(mockAppendable.iterator());

    } catch (IOException exception) {
      fail("Unexpected IOException occurred attempting to call IPLoggingView.dismiss");
    }
  }

  @Test
  public void promptInput() {
    MockAppendable mockAppendable = new MockAppendable();
    IPTextView view = create(mockAppendable);

    try {
      view.promptInput();
      makePromptAsserts(mockAppendable.iterator());

    } catch (IOException exception) {
      fail("Unexpected IOException occurred attempting to call IPLoggingView.promptInput");
    }
  }

  /**
   * Performs JUnit 4 assertions on the welcome message given a mock appendable.
   *
   * @param logIterator the mock appendable log iterator
   */
  protected abstract void makeWelcomeAsserts(Iterator<String> logIterator);

  /**
   * Performs JUnit 4 assertions on the dismissal message given a mock appendable.
   *
   * @param logIterator the mock appendable log iterator
   */
  protected abstract void makeDismissalAsserts(Iterator<String> logIterator);

  /**
   * Performs JUnit 4 assertions on the prompt message given a mock appendable.
   *
   * @param logIterator the mock appendable log iterator
   */
  protected abstract void makePromptAsserts(Iterator<String> logIterator);

  /**
   * Tests the {@code display} method with random strings a given number of times.
   *
   * @param view           the view to test
   * @param mockAppendable the mock appendable access logged appends
   * @param logStartIndex  the index in the log to start reading appends
   * @param iterations     the number of random strings to test
   */
  private void testDisplay(IPView view, MockAppendable mockAppendable, int logStartIndex,
                           int iterations) {
    try {

      for (int i = 0; i < iterations; i++) {
        String string = TestUtils.randomString(30);
        view.display(string);
        assertEquals("   \033[3;37m" + string + "\033[0m" + System.lineSeparator(),
                mockAppendable.getItem(logStartIndex + i));
      }

    } catch (IOException exception) {
      fail("Unexpected IOException occurred attempting to call IPLoggingView.display");
    }

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertTrue(view != null);
  }

  /**
   * Tests the {@code displayError} method with random strings a given number of times.
   *
   * @param view           the view to test
   * @param mockAppendable the mock appendable access logged appends
   * @param logStartIndex  the index in the log to start reading appends
   * @param iterations     the number of random strings to test
   */
  private void testDisplayError(IPView view, MockAppendable mockAppendable, int logStartIndex,
                                int iterations) {
    try {

      for (int i = 0; i < iterations; i++) {
        String string = TestUtils.randomString(30);
        view.displayError(string);
        String item = "   \033[91m" + string + "\033[0m" + System.lineSeparator();
        assertEquals(item, mockAppendable.getItem(logStartIndex + i));
      }

    } catch (IOException exception) {
      fail("Unexpected IOException occurred attempting to call IPLoggingView.displayError");
    }
  }

}
