package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.model.filter.MaskedFilter;
import cs3500.imageprocessor.view.IPView;

/**
 * A command that performs a mask filter.
 */
public class IPMaskedCommand extends IPModelCommand {
  private ImageFilter filter;

  /**
   * Creates a new command that performs a brightening filter on an image.
   *
   * @param model the model to act on
   * @param view  the view to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPMaskedCommand(IPModel model, IPView view, ImageFilter filter)
          throws IllegalArgumentException {
    super(model, view);
    this.filter = filter;
  }

  /**
   * Executes the given command. This is a very broad specification, different implementations may
   * act on various parts of the application.
   *
   * @param params the parameters for the command
   * @throws IOException              if the command causes an IOException
   * @throws IllegalArgumentException if invalid parameters were passed
   */
  @Override
  public void execute(List<String> params) throws IOException, IllegalArgumentException {
    if (params.size() != 3) {
      throw new IllegalArgumentException("A masked filter command requires three parameters: value,"
              + " original id, masked id, new id");
    }

    String originalId = params.get(1);
    String maskedId = params.get(2);
    String newId = params.get(3);

    this.model.applyFilter(new MaskedFilter(
            model.getImage(maskedId), this.filter), originalId, newId);
    this.view.display("Performed a masked filter on the image " + originalId + " and saved it as "
            + newId);
  }

  @Override
  public int paramCount() {
    return 3;
  }

  @Override
  public boolean supportsMask() {
    return false;
  }

}
