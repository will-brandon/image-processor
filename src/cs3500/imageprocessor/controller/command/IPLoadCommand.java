package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.util.FileUtils;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a command that loads an image from a file and saves it with the specified
 * id.
 */
public class IPLoadCommand extends IPModelCommand {

  /**
   * Creates a new command that performs a load image action on the model.
   *
   * @param model the model to act on
   * @param view  the view to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPLoadCommand(IPModel model, IPView view) throws IllegalArgumentException {
    super(model, view);
  }

  /**
   * Loads a file with the given path under the given id.
   *
   * @param params should contain the path to load and the id to track it
   * @throws IOException              if the file could not be loaded
   * @throws IllegalArgumentException if the parameters given were invalid
   */
  @Override
  public void execute(List<String> params) throws IOException, IllegalArgumentException {
    if (params.size() != 2) {
      throw new IllegalArgumentException("Load command requires two parameters: path, id");
    }

    String pathname = params.get(0);
    String id = params.get(1);
    Image image = FileUtils.readImage(pathname);

    model.acceptImage(image, id);
    this.view.display("Loaded image from " + pathname + " to " + id);
  }

  @Override
  public int paramCount() {
    return 2;
  }

  @Override
  public boolean supportsMask() {
    return false;
  }

}
