package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.view.IPView;
import util.mock.MockAppendable;
import util.mock.MockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import static util.TestUtils.assertException;
import static util.TestUtils.assertFileExistsAndRemove;

/**
 * This class performs tests on the {@link IPSaveCommand} class.
 */
public class IPSaveCommandTest extends IPModelCommandTest {
  @Override
  public IPCommand construct(IPModel model, IPView view) throws IllegalArgumentException {
    return new IPSaveCommand(model, view);
  }

  @Override
  public void testIllegalArgs() {
    MockModel mockModel = new MockModel();

    assertException(IllegalArgumentException.class,
            "Save command requires two parameters: path, id",
        () -> this.execute(mockModel, new ArrayList<>()));
    assertException(IllegalArgumentException.class,
            "Save command requires two parameters: path, id",
        () -> this.execute(mockModel, List.of("1")));
    assertException(IllegalArgumentException.class,
            "Save command requires two parameters: path, id",
        () -> this.execute(mockModel, List.of("1", "2", "3", "4")));

    assertException(IllegalArgumentException.class,
            "Could not determine file format from given pathname: test.",
        () -> this.execute(mockModel, List.of("test.", "2")));
    assertException(IOException.class,
            "Failed to write image with the format \"l333\"",
        () -> this.execute(mockModel, List.of("whatsup.l333", "2")));
  }

  @Override
  public void testExecute() {
    MockModel mockModel = new MockModel();

    MockAppendable mockAppendable;
    try {
      mockAppendable = this.execute(mockModel,
              List.of("testresources/saved.jpg", "image1"));
    } catch (IOException exception) {
      fail("Unexpected exception thrown in tests: " + exception);
      return;
    }

    Iterator<String> appendableIterator = mockAppendable.iterator();

    assertEquals("   \033[3;37mSaved image image1 to testresources/saved.jpg\033[0m"
            + System.lineSeparator(), appendableIterator.next());
    assertFalse(appendableIterator.hasNext());

    assertFileExistsAndRemove("testresources/saved.jpg");
  }

}
