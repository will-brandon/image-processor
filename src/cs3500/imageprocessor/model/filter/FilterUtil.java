package cs3500.imageprocessor.model.filter;

import cs3500.imageprocessor.model.filter.channelmod.ColorTransformationFilter;
import cs3500.imageprocessor.model.filter.channelmod.IntensityFilter;
import cs3500.imageprocessor.model.filter.channelmod.LumaFilter;
import cs3500.imageprocessor.model.filter.channelmod.MaxValueFilter;
import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.model.filter.mirror.HorizontalFlipFilter;
import cs3500.imageprocessor.model.filter.mirror.VerticalFlipFilter;

/**
 * Provides utilities for the filter classes.
 */
public class FilterUtil {

  /**
   * Returns the appropriate filter based on the name. Returns null if none was found.
   *
   * @param name the name of the filter
   * @return the appropriate filter or null if there are no matches
   */
  public static ImageFilter filterForName(String name) {
    if (name.equals("horizontal-flip")) {
      return new HorizontalFlipFilter();
    } else if (name.equals("vertical-flip")) {
      return new VerticalFlipFilter();
    } else if (name.equals("red")) {
      return new RGBFilter(RGBFilter.RED_FILTER);
    } else if (name.equals("green")) {
      return new RGBFilter(RGBFilter.GREEN_FILTER);
    } else if (name.equals("blue")) {
      return new RGBFilter(RGBFilter.BLUE_FILTER);
    } else if (name.equals("intensity")) {
      return new IntensityFilter();
    } else if (name.equals("luma")) {
      return new LumaFilter();
    } else if (name.equals("value")) {
      return new MaxValueFilter();
    } else if (name.equals("blur")) {
      return new KernelFilter(CommonFilterMatrices.BLUR_KERNEL);
    } else if (name.equals("sharpen")) {
      return new KernelFilter(CommonFilterMatrices.SHARPEN_KERNEL);
    } else if (name.equals("greyscale")) {
      return new ColorTransformationFilter(CommonFilterMatrices.GREYSCALE_MATRIX);
    } else if (name.equals("sepia")) {
      return new ColorTransformationFilter(CommonFilterMatrices.SEPIA_MATRIX);
    }

    return null;
  }

}
