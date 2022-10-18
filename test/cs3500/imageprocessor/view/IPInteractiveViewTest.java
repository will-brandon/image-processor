package cs3500.imageprocessor.view;

import java.util.Iterator;

import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.text.IPLoggingView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static util.TestableItems.DISMISSAL_MESSAGE;
import static util.TestableItems.WELCOME_MESSAGE;

/**
 * This class performs tests on the {@link IPInteractiveView} class.
 */
public class IPInteractiveViewTest extends IPLoggingViewTest {

  @Override
  protected IPLoggingView create(Appendable appendable) {
    return new IPInteractiveView(appendable);
  }

  @Override
  protected void makeWelcomeAsserts(Iterator<String> logIterator) {
    assertEquals(WELCOME_MESSAGE, logIterator.next());
    assertFalse(logIterator.hasNext());
  }

  @Override
  protected void makeDismissalAsserts(Iterator<String> logIterator) {
    assertEquals(DISMISSAL_MESSAGE, logIterator.next());
    assertFalse(logIterator.hasNext());
  }

  @Override
  protected void makePromptAsserts(Iterator<String> logIterator) {
    assertEquals("  >> ", logIterator.next());
    assertFalse(logIterator.hasNext());
  }

}