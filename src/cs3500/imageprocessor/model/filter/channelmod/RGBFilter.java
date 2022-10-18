package cs3500.imageprocessor.model.filter.channelmod;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * This RGBFilter represents an ImageFilter that a given colorChannel to create a grey-scale version
 * of the given image.
 */
public class RGBFilter implements ImageFilter {

  public static final int RED_FILTER = 0;
  public static final int GREEN_FILTER = 1;
  public static final int BLUE_FILTER = 2;

  private int colorChannel; // red = 0; green = 1; blue = 2;

  /**
   * Constructor 1: Creates an RGBFilter using the given colorChannel to establish with channel of
   * color to pull from for each Pixel in the given image.
   *
   * @param colorChannel the index of the channel array that determine which color the RGBFilter
   *                     should use
   * @throws IllegalArgumentException if the given colorChannel is outside the bounds of the current
   *                                  Pixel's channel array
   */
  public RGBFilter(int colorChannel) throws IllegalArgumentException {
    if (colorChannel < 0 || colorChannel > 2) {
      throw new IllegalArgumentException("Illegal color channel argument: given " + colorChannel);
    }

    this.colorChannel = colorChannel;
  }

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param input the image to perform this filter function on.
   * @return the output image with the filter applied
   */
  @Override
  public Image perform(Image input) {
    int[] filteredBitmap = new int[input.bitmapSize()];

    for (int i = 0; i < input.bitmapSize(); i = i + 3) {
      int[] pixelPosn = input.pixelPosn(i / 3);

      int color = input.getPixel(pixelPosn[0], pixelPosn[1])[this.colorChannel];

      filteredBitmap[i] = color;
      filteredBitmap[i + 1] = color;
      filteredBitmap[i + 2] = color;
    }

    return new RGBImage(input.width(), input.height(), filteredBitmap);
  }

  @Override
  public boolean supportsMask() {
    return true;
  }

}
