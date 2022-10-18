package cs3500.imageprocessor.model.filter.mirror;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;

/**
 * This class represents an {@link ImageFilter} that mirrors each pixel about the y-axis.
 */
public class VerticalFlipFilter extends MirrorFilter {

  @Override
  protected int reflectedDimensionSize(Image input) {
    return input.height();
  }

  @Override
  protected int secondaryDimensionSize(Image input) {
    return input.width();
  }

  @Override
  protected int rowIndex(int reflectedIndex, int secondaryIndex) {
    return reflectedIndex;
  }

  @Override
  protected int colIndex(int reflectedIndex, int secondaryIndex) {
    return secondaryIndex;
  }

}