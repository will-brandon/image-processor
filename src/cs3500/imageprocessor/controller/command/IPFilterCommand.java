package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a command performs a filter on the given image and saves it under the given
 * name.
 */
public class IPFilterCommand extends IPModelCommand {

  protected ImageFilter filter;

  /**
   * Creates a new command that performs a filter on an image.
   *
   * @param model the model to act on
   * @param view  the view to display to
   * @throws IllegalArgumentException if the given model, view, or filter is null
   */
  public IPFilterCommand(IPModel model, IPView view, ImageFilter filter)
          throws IllegalArgumentException {
    super(model, view);
    if (filter == null) {
      throw new IllegalArgumentException("Filter given to command cannot be null");
    }

    this.filter = filter;
  }

  /**
   * Performs a filter on the image with the given id and saves it under the new id.
   *
   * @param params should contain the original image id and the flipped image id
   * @throws IOException              if the file could not be found
   * @throws IllegalArgumentException if the parameters given were invalid
   */
  @Override
  public void execute(List<String> params) throws IOException, IllegalArgumentException {
    if (params.size() != 2) {
      throw new IllegalArgumentException("Filter commands require two parameters: original id, new "
              + "id");
    }

    String originalId = params.get(0);
    String newId = params.get(1);

    this.model.applyFilter(filter, originalId, newId);
    this.view.display("Performed a filter on the image " + originalId + " and saved it as "
            + newId);
  }

  @Override
  public int paramCount() {
    return 2;
  }

  @Override
  public boolean supportsMask() {
    return filter.supportsMask();
  }

}
