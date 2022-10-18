package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.view.IPView;
import util.mock.MockAppendable;
import util.mock.MockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static util.TestUtils.assertException;

/**
 * This class performs tests on the {@link IPSaveCommand} class.
 */
public class IPLoadCommandTest extends IPModelCommandTest {

  @Override
  public IPCommand construct(IPModel model, IPView view) throws IllegalArgumentException {
    return new IPLoadCommand(model, view);
  }

  @Override
  public void testIllegalArgs() {
    IPModel placeholderModel = new IPStandardModel();

    assertException(IllegalArgumentException.class,
            "Load command requires two parameters: path, id",
        () -> this.execute(placeholderModel, new ArrayList<>()));
    assertException(IllegalArgumentException.class,
            "Load command requires two parameters: path, id",
        () -> this.execute(placeholderModel, List.of("1")));
    assertException(IllegalArgumentException.class,
            "Load command requires two parameters: path, id",
        () -> this.execute(placeholderModel, List.of("1", "2", "3", "4")));

    assertException(IllegalArgumentException.class,
            "Could not determine file format from given pathname: test.",
        () -> this.execute(placeholderModel, List.of("test.", "2")));
    assertException(IOException.class,
            "Failed to read image with the format \"what\"",
        () -> this.execute(placeholderModel, List.of("hola.what", "2")));
  }

  @Override
  public void testExecute() {
    MockModel mockModel = new MockModel();

    MockAppendable mockAppendable1;
    try {
      mockAppendable1 = this.execute(mockModel,
              List.of("testresources/smart.jpg", "image1"));
    } catch (IOException exception) {
      fail("Unexpected exception thrown in tests: " + exception);
      return;
    }

    MockAppendable mockAppendable2;
    try {
      mockAppendable2 = this.execute(mockModel,
              List.of("testresources/smart.ppm", "image1"));
    } catch (IOException exception) {
      fail("Unexpected exception thrown in tests: " + exception);
      return;
    }

    Iterator<String> appendable1Iterator = mockAppendable1.iterator();
    Iterator<String> appendable2Iterator = mockAppendable2.iterator();

    assertEquals("   \033[3;37mLoaded image from testresources/smart.jpg to image1\033[0m"
            + System.lineSeparator(), appendable1Iterator.next());
    assertFalse(appendable1Iterator.hasNext());

    assertEquals("   \033[3;37mLoaded image from testresources/smart.ppm to image1\033[0m"
            + System.lineSeparator(), appendable2Iterator.next());
    assertFalse(appendable2Iterator.hasNext());
  }

}
