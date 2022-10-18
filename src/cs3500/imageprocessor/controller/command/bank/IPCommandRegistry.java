package cs3500.imageprocessor.controller.command.bank;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import cs3500.imageprocessor.controller.command.IPBrightnenCommand;
import cs3500.imageprocessor.controller.command.IPCommand;
import cs3500.imageprocessor.controller.command.IPFilterCommand;
import cs3500.imageprocessor.controller.command.IPLoadCommand;
import cs3500.imageprocessor.controller.command.IPMaskedCommand;
import cs3500.imageprocessor.controller.command.IPSaveCommand;
import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.FilterUtil;
import cs3500.imageprocessor.model.filter.KernelFilter;
import cs3500.imageprocessor.model.filter.channelmod.ColorTransformationFilter;
import cs3500.imageprocessor.model.filter.channelmod.IntensityFilter;
import cs3500.imageprocessor.model.filter.channelmod.LumaFilter;
import cs3500.imageprocessor.model.filter.channelmod.MaxValueFilter;
import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.model.filter.mirror.HorizontalFlipFilter;
import cs3500.imageprocessor.model.filter.mirror.VerticalFlipFilter;
import cs3500.imageprocessor.view.IPView;

import static cs3500.imageprocessor.model.filter.CommonFilterMatrices.BLUR_KERNEL;
import static cs3500.imageprocessor.model.filter.CommonFilterMatrices.GREYSCALE_MATRIX;
import static cs3500.imageprocessor.model.filter.CommonFilterMatrices.SEPIA_MATRIX;
import static cs3500.imageprocessor.model.filter.CommonFilterMatrices.SHARPEN_KERNEL;

/**
 * This class holds a bank of all commands currently supported by the Image Processor application.
 * It can accept commands and execute them.
 */
public class IPCommandRegistry implements IPCommandBank {

  private final Map<String, IPCommand> commandMap;
  private final IPModel model;
  private final IPView view;

  /**
   * Creates a new command registry with the preregistered commands applicable for the Image
   * Processor application.
   *
   * @param model the model for the commands to act on
   * @param view the view for the commands to display to
   * @throws IllegalArgumentException if the given model or view is null
   */
  public IPCommandRegistry(IPModel model, IPView view) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Command registry cannot be given a null model");
    }
    if (view == null) {
      throw new IllegalArgumentException("Command registry cannot be given a null view");
    }

    commandMap = new HashMap<>();
    this.model = model;
    this.view = view;
    registerCommands();
  }

  /**
   * Registers all the commands for the Image Processor application.
   */
  private void registerCommands() {
    commandMap.put("load", new IPLoadCommand(model, view));
    commandMap.put("save", new IPSaveCommand(model, view));

    commandMap.put("horizontal-flip", new IPFilterCommand(model, view, new HorizontalFlipFilter()));
    commandMap.put("vertical-flip", new IPFilterCommand(model, view, new VerticalFlipFilter()));
    commandMap.put("red", new IPFilterCommand(model, view, new RGBFilter(RGBFilter.RED_FILTER)));
    commandMap.put("green", new IPFilterCommand(model, view,
            new RGBFilter(RGBFilter.GREEN_FILTER)));
    commandMap.put("blue", new IPFilterCommand(model, view, new RGBFilter(RGBFilter.BLUE_FILTER)));
    commandMap.put("intensity", new IPFilterCommand(model, view, new IntensityFilter()));
    commandMap.put("luma", new IPFilterCommand(model, view, new LumaFilter()));
    commandMap.put("max-value", new IPFilterCommand(model, view, new MaxValueFilter()));
    commandMap.put("brighten", new IPBrightnenCommand(model, view));
    // blur, sharpen, greyscale, sepia
    commandMap.put("blur", new IPFilterCommand(model, view, new KernelFilter(BLUR_KERNEL)));
    commandMap.put("sharpen", new IPFilterCommand(model, view, new KernelFilter(SHARPEN_KERNEL)));
    commandMap.put("greyscale", new IPFilterCommand(model, view,
            new ColorTransformationFilter(GREYSCALE_MATRIX)));
    commandMap.put("sepia", new IPFilterCommand(model, view,
            new ColorTransformationFilter(SEPIA_MATRIX)));
  }

  @Override
  public void execute(List<String> commandArgs) throws NoSuchElementException, IOException,
          IllegalArgumentException {
    if (commandArgs == null) {
      throw new IllegalArgumentException("Command arguments cannot be null");
    }
    if (commandArgs.size() < 1) {
      throw new IllegalArgumentException("Cannot execute command with no arguments given");
    }

    String commandString = commandArgs.get(0);

    if (commandString.isBlank()) {
      throw new IllegalArgumentException("Cannot execute command with blank name");
    }
    if (!commandMap.containsKey(commandString)) {
      throw new NoSuchElementException("Given command does not exist: " + commandString);
    }

    List<String> commandParams = commandArgs.subList(1, commandArgs.size());
    IPCommand command = commandMap.get(commandString);

    int maskedParamCount = command.paramCount() + 1;

    // User attempting a mask (original img id, mask id, new filtered image id)
    if (commandParams.size() == maskedParamCount && command.supportsMask()) {
      IPCommand maskedCommand = new IPMaskedCommand(model, view,
              FilterUtil.filterForName(commandString));
      maskedCommand.execute(commandParams);
    } else {
      command.execute(commandParams);
    }
  }

}