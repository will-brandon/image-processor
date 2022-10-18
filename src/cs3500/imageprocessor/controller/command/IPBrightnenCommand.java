package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.channelmod.BrightnessFilter;
import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a command that performs a brightening filter on the given image and saves
 * it under the given name.
 */
public class IPBrightnenCommand extends IPModelCommand {

  /**
   * Creates a new command that performs a brightening filter on an image.
   *
   * @param model  the model to act on
   * @param view   the view to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPBrightnenCommand(IPModel model, IPView view) throws IllegalArgumentException {
    super(model, view);
  }

  /**
   * Performs a brightening filter on the image with the given id and saves it under the new id.
   *
   * @param params should contain the value, original image id, and the flipped image id
   * @throws IOException              if the file could not be found
   * @throws IllegalArgumentException if the parameters given were invalid
   */
  @Override
  public void execute(List<String> params) throws IOException, IllegalArgumentException {
    if (params.size() != 3) {
      throw new IllegalArgumentException("Brighten filter command requires three parameters: value,"
              + " original id, new id");
    }

    int value;
    try {
      value = Integer.parseInt(params.get(0));
    } catch (NumberFormatException exception) {
      throw new IllegalArgumentException("Value parameter for brightening filter must be an "
              + "integer");
    }

    String originalId = params.get(1);
    String newId = params.get(2);

    this.model.applyFilter(new BrightnessFilter(value), originalId, newId);
    this.view.display("Performed a brightening filter on the image " + originalId + " and saved it "
            + "as " + newId);
  }

  @Override
  public int paramCount() {
    return 3;
  }

  @Override
  public boolean supportsMask() {
    return true;
  }

}