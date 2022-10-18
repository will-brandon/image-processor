package cs3500.imageprocessor.controller.command;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.IPView;
import util.mock.MockAppendable;

import static util.TestUtils.assertException;

/**
 * This class performs tests on all subclasses of the {@link IPModelCommand} class.
 */
public abstract class IPModelCommandTest {

  /**
   * Creates a new {@link IPCommand} of the type appropriate for this test.
   *
   * @param model the model to use
   * @param view the view to use
   * @return the command
   * @throws IllegalArgumentException if invalid constructor arguments were given
   */
  public abstract IPCommand construct(IPModel model, IPView view) throws IllegalArgumentException;

  @Test
  public void constructFail() {
    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    assertException(IllegalArgumentException.class,
            "Model given to a command cannot be null",
        () -> construct(null, placeholderView));
    assertException(IllegalArgumentException.class,
            "View given to a command cannot be null",
        () -> construct(placeholderModel, null));
  }

  @Test
  public abstract void testIllegalArgs();

  @Test
  public abstract void testExecute();

  /**
   * Executes the appropriate command for this test on the given model with the given parameter list
   * and returns a mock appendable with all the view output.
   *
   * @param model the model to use
   * @param params the parameters of the command
   * @return a mock appendable for reading the view log
   * @throws IOException if the command throws an {@link IOException}
   * @throws IllegalArgumentException if the command was provided invalid parameters
   */
  protected MockAppendable execute(IPModel model, List<String> params) throws IOException,
          IllegalArgumentException {
    MockAppendable mockAppendable = new MockAppendable();
    IPView view = new IPInteractiveView(mockAppendable);

    IPCommand command = construct(model, view);
    command.execute(params);

    return mockAppendable;
  }

}
