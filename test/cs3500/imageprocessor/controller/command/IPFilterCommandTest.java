package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.model.filter.mirror.HorizontalFlipFilter;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.model.filter.channelmod.IntensityFilter;
import cs3500.imageprocessor.model.filter.channelmod.LumaFilter;
import cs3500.imageprocessor.model.filter.channelmod.MaxValueFilter;
import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.model.filter.mirror.VerticalFlipFilter;
import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.IPView;
import util.mock.MockAppendable;
import util.mock.MockModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static util.TestUtils.assertException;

/**
 * This class holds tests for all subclasses of {@link IPFilterCommand}.
 */
public class IPFilterCommandTest extends IPModelCommandTest {

  @Override
  public IPCommand construct(IPModel model, IPView view) throws IllegalArgumentException {
    // Any filter will help run these tests in the super class, luma is justa a random choice
    return new IPFilterCommand(model, view, new LumaFilter());
  }

  @Override
  public void constructFail() {
    super.constructFail();

    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    assertException(IllegalArgumentException.class, "Filter given to command cannot be null",
        () -> new IPFilterCommand(placeholderModel, placeholderView, null));
  }

  @Override
  public void testIllegalArgs() {
    MockModel mockModel = new MockModel();

    assertException(IllegalArgumentException.class,
            "Filter commands require two parameters: original id, new id",
        () -> this.execute(mockModel, new ArrayList<>()));
    assertException(IllegalArgumentException.class,
            "Filter commands require two parameters: original id, new id",
        () -> this.execute(mockModel, List.of("1")));
    assertException(IllegalArgumentException.class,
            "Filter commands require two parameters: original id, new id",
        () -> this.execute(mockModel, List.of("1", "2", "3", "4")));
  }

  @Override
  public void testExecute() {
    testFilter(new HorizontalFlipFilter());
    testFilter(new VerticalFlipFilter());
    testFilter(new RGBFilter(RGBFilter.RED_FILTER));
    testFilter(new RGBFilter(RGBFilter.GREEN_FILTER));
    testFilter(new RGBFilter(RGBFilter.BLUE_FILTER));
    testFilter(new IntensityFilter());
    testFilter(new LumaFilter());
    testFilter(new MaxValueFilter());

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertTrue(true);
  }

  private void testFilter(ImageFilter filter) {
    MockModel mockModel = new MockModel();

    MockAppendable mockAppendable;
    try {
      mockAppendable = this.execute(mockModel, List.of("image1", "image2"), filter);
    } catch (IOException exception) {
      fail("Unexpected exception thrown in tests: " + exception);
      return;
    }

    Iterator<String> modelIterator = mockModel.iterator();
    Iterator<String> appendableIterator = mockAppendable.iterator();

    assertEquals("applyFilter(" + filter.getClass().getSimpleName() + ", image1, image2)",
            modelIterator.next());
    assertFalse(modelIterator.hasNext());

    assertEquals("   \033[3;37mPerformed a filter on the image image1 and "
            + "saved it as image2\033[0m" + System.lineSeparator(), appendableIterator.next());
    assertFalse(appendableIterator.hasNext());
  }

  /**
   * Executes the appropriate command for this test on the given model with the given parameter list
   * and returns a mock appendable with all the view output.
   *
   * @param model  the model to use
   * @param params the parameters of the command
   * @param filter the filter to use
   * @return a mock appendable for reading the view log
   * @throws IOException              if the command throws an {@link IOException}
   * @throws IllegalArgumentException if the command was provided invalid parameters
   */
  private MockAppendable execute(IPModel model, List<String> params, ImageFilter filter)
          throws IOException, IllegalArgumentException {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = new IPInteractiveView(mockAppendable);

    IPCommand command = new IPFilterCommand(model, view, filter);
    command.execute(params);

    return mockAppendable;
  }

}
