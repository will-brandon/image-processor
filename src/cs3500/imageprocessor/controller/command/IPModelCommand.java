package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a command for the Image Processor that performs actions on the application
 * model. This fits into the MVC pattern because commands are part of the controller, and this is
 * the place where each user command interacts with the model and the view.
 */
public abstract class IPModelCommand implements IPCommand {

  protected final IPModel model;
  protected final IPView view;

  /**
   * Creates a new command that performs an action on the model.
   *
   * @param model the model to act on
   * @param view the view to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPModelCommand(IPModel model, IPView view) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model given to a command cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View given to a command cannot be null");
    }

    this.model = model;
    this.view = view;
  }

  /**
   * Executes the given command on the model.
   *
   * @param params the parameters for the command
   * @throws IOException              if the command causes an IOException
   * @throws IllegalArgumentException if invalid parameters were passed
   */
  @Override
  public abstract void execute(List<String> params) throws IOException, IllegalArgumentException;

}
