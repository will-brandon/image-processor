package cs3500.imageprocessor.view;

import java.util.Iterator;

import cs3500.imageprocessor.view.text.IPLoggingView;
import cs3500.imageprocessor.view.text.IPPreloadedView;

import static org.junit.Assert.assertFalse;

/**
 * This class performs tests on the {@link IPPreloadedView} class.
 */
public class IPPreloadedViewTest extends IPLoggingViewTest {

  @Override
  protected IPLoggingView create(Appendable appendable) {
    return new IPPreloadedView(appendable);
  }

  @Override
  protected void makeWelcomeAsserts(Iterator<String> logIterator) {
    assertFalse(logIterator.hasNext());
  }

  @Override
  protected void makeDismissalAsserts(Iterator<String> logIterator) {
    assertFalse(logIterator.hasNext());
  }

  @Override
  protected void makePromptAsserts(Iterator<String> logIterator) {
    assertFalse(logIterator.hasNext());
  }

}