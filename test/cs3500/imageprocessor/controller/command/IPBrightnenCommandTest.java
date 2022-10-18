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
 * This class performs tests on the {@link IPBrightnenCommand} class.
 */
public class IPBrightnenCommandTest extends IPModelCommandTest {

  @Override
  public IPCommand construct(IPModel model, IPView view) throws IllegalArgumentException {
    return new IPBrightnenCommand(model, view);
  }

  @Override
  public void testIllegalArgs() {
    IPModel placeholderModel = new IPStandardModel();

    assertException(IllegalArgumentException.class,
            "Brighten filter command requires three parameters: value, original id, new id",
        () -> this.execute(placeholderModel, new ArrayList<>()));
    assertException(IllegalArgumentException.class,
            "Brighten filter command requires three parameters: value, original id, new id",
        () -> this.execute(placeholderModel, List.of("1", "2")));
    assertException(IllegalArgumentException.class,
            "Brighten filter command requires three parameters: value, original id, new id",
        () -> this.execute(placeholderModel, List.of("1", "2", "3", "4")));

    assertException(IllegalArgumentException.class,
            "Value parameter for brightening filter must be an integer",
        () -> this.execute(placeholderModel, List.of("notint", "image1", "image2")));
    assertException(IllegalArgumentException.class,
            "Value parameter for brightening filter must be an integer",
        () -> this.execute(placeholderModel, List.of("", "image1", "image2")));
  }

  @Override
  public void testExecute() {
    MockModel mockModel = new MockModel();

    MockAppendable mockAppendable;
    try {
      mockAppendable = this.execute(mockModel, List.of("120", "image1", "image2"));
    } catch (IOException exception) {
      fail("Unexpected exception thrown in tests: " + exception);
      return;
    }

    Iterator<String> appendableIterator = mockAppendable.iterator();

    assertEquals("   \033[3;37mPerformed a brightening filter on the image image1 and "
            + "saved it as image2\033[0m" + System.lineSeparator(), appendableIterator.next());
    assertFalse(appendableIterator.hasNext());
  }

}