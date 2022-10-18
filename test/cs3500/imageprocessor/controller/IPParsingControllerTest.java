package cs3500.imageprocessor.controller;

import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Iterator;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.text.IPTextView;
import util.mock.MockAppendable;
import util.mock.MockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static util.TestUtils.assertException;
import static util.TestUtils.assertFileExistsAndRemove;
import static util.TestableItems.DISMISSAL_MESSAGE;
import static util.TestableItems.WELCOME_MESSAGE;

/**
 * This class performs tests on the {@link IPParsingController} class.
 */
public class IPParsingControllerTest {

  // The constructor is effectively tested in almost every one of the other tests in this class

  @Test
  public void constructFail() {
    IPModel placeholderModel = new IPStandardModel();
    IPTextView placeholderView = new IPInteractiveView(new MockAppendable());
    Readable placeholderReadable = new StringReader("");

    assertException(IllegalArgumentException.class,
            "Model given to IPStandardController cannot be null",
        () -> new IPParsingController(null, placeholderView, placeholderReadable));
    assertException(IllegalArgumentException.class,
            "View given to IPStandardController cannot be null",
        () -> new IPParsingController(placeholderModel, null, placeholderReadable));
    assertException(IllegalArgumentException.class,
            "Readable input source given to IPStandardController cannot be null",
        () -> new IPParsingController(placeholderModel, placeholderView, null));
  }

  @Test
  public void enteringNothing() {
    MockModel mockModel = new MockModel();
    // Separate lines are treated as their own command
    MockAppendable mockAppendable = simulateController(mockModel, System.lineSeparator()
            + System.lineSeparator() + System.lineSeparator() + System.lineSeparator()
            + System.lineSeparator() + "q");

    Iterator<String> modelIterator = mockModel.iterator();
    Iterator<String> appendableIterator = mockAppendable.iterator();

    assertTrue(mockModel.getLog().isEmpty());

    assertEquals(WELCOME_MESSAGE, appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("  >> ", appendableIterator.next());
    assertEquals("   \033[3;37mQuitting the Image Processor\033[0m" + System.lineSeparator(),
            appendableIterator.next());
    assertEquals(DISMISSAL_MESSAGE, appendableIterator.next());
    assertFalse(appendableIterator.hasNext());
  }

  @Test
  public void quit() {
    MockModel mockModel = new MockModel();
    MockAppendable mockAppendable1 = simulateController(mockModel, "q");
    MockAppendable mockAppendable2 = simulateController(mockModel, "quit");

    Iterator<String> appendable1Iterator = mockAppendable1.iterator();
    Iterator<String> appendable2Iterator = mockAppendable1.iterator();

    assertTrue(mockModel.getLog().isEmpty());

    assertEquals(WELCOME_MESSAGE, appendable1Iterator.next());
    assertEquals("  >> ", appendable1Iterator.next());
    assertEquals("   \033[3;37mQuitting the Image Processor\033[0m"
            + System.lineSeparator(), appendable1Iterator.next());
    assertEquals(DISMISSAL_MESSAGE, appendable1Iterator.next());
    assertFalse(appendable1Iterator.hasNext());

    assertEquals(WELCOME_MESSAGE, appendable2Iterator.next());
    assertEquals("  >> ", appendable2Iterator.next());
    assertEquals("   \033[3;37mQuitting the Image Processor\033[0m"
            + System.lineSeparator(), appendable2Iterator.next());
    assertEquals(DISMISSAL_MESSAGE, appendable2Iterator.next());
    assertFalse(appendable2Iterator.hasNext());
  }

  @Test
  public void enteringCommentAndWhitespace() {
    MockModel mockModel = new MockModel();
    // Separate lines are treated as their own command
    simulateController(mockModel,
            "#" + System.lineSeparator()
                    + "    #    " + System.lineSeparator()
                    + "   # #   #" + System.lineSeparator()
                    + "this is a command # this is not" + System.lineSeparator()
                    + "   this could    also be a      command    #butnot this right here      ##  "
                    + System.lineSeparator()
                    + "load testresources/smart.jpg smart#Command####ends here fellas"
                    + System.lineSeparator()
                    + "q");

    Iterator<String> modelIterator = mockModel.iterator();

    assertEquals("acceptImage(Image(16x16), smart)", modelIterator.next());
    assertFalse(modelIterator.hasNext());
  }

  @Test
  public void successfulCommand() {
    MockModel mockModel = new MockModel();
    MockAppendable appendable = simulateController(mockModel,
            "load testresources/smart.jpg smart" + System.lineSeparator()
                    + "red smart smart-rchannel" + System.lineSeparator()
                    + "save testresources/smart-rchannel.jpg smart-rchannel"
                    + System.lineSeparator() + "q");

    Iterator<String> modelIterator = mockModel.iterator();

    assertEquals("acceptImage(Image(16x16), smart)", modelIterator.next());
    assertEquals("applyFilter(RGBFilter, smart, smart-rchannel)", modelIterator.next());
    assertEquals("getImage(smart-rchannel)", modelIterator.next());
    assertFalse(modelIterator.hasNext());

    assertFileExistsAndRemove("testresources/smart-rchannel.jpg");
  }

  /**
   * Simulates the controller and returns a mock appendable that logs the views output.
   *
   * @param model the model to use
   * @param input the input string to parse commands out of
   * @return a mock appendable with log entries
   */
  private MockAppendable simulateController(IPModel model, String input) {
    MockAppendable mockAppendable = new MockAppendable();
    IPTextView view = new IPInteractiveView(mockAppendable);
    Readable mockReadable = new StringReader(input);

    IPController controller = new IPParsingController(model, view, mockReadable);

    try {
      controller.begin();
    } catch (IOException exception) {
      fail("Unexpected IOException thrown: " + exception.getMessage());
    }

    return mockAppendable;
  }

}