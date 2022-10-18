package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.util.FileUtils;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.view.IPView;

/**
 * This class represents a command that saves an image from the model to a specific path.
 */
public class IPSaveCommand extends IPModelCommand {

  /**
   * Creates a new command that performs a save image action on the model.
   *
   * @param model the model to act on
   * @param view  the view to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPSaveCommand(IPModel model, IPView view) throws IllegalArgumentException {
    super(model, view);
  }

  /**
   * Saves a file with the given id to the given path.
   *
   * @param params should contain the path to save to and the id for the image
   * @throws IOException              if the file could not be saved
   * @throws IllegalArgumentException if the parameters given were invalid
   */
  @Override
  public void execute(List<String> params) throws IOException, IllegalArgumentException {
    if (params.size() != 2) {
      throw new IllegalArgumentException("Save command requires two parameters: path, id");
    }

    String path = params.get(0);
    String id = params.get(1);
    Image image = this.model.getImage(id);

    FileUtils.writeImage(path, image);
    this.view.display("Saved image " + id + " to " + path);
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
