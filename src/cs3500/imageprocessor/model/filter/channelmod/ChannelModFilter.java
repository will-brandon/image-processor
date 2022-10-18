package cs3500.imageprocessor.model.filter.channelmod;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * This abstract ImageFilter class represents an ImageFilter that must modify the channels of every
 * Pixel in the given image in order to perform its own function.
 */
public abstract class ChannelModFilter implements ImageFilter {

  /**
   * Calculates the channels of a newPixel to replace the given Pixel based on this filter.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  abstract int[] calcNewPixel(Image input, int[] pixel);

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param input the image to perform this filter function on.
   * @return the output image with the filter applied
   */
  public Image perform(Image input) {
    int[] filteredBitmap = new int[input.bitmapSize()];

    for (int row = 0; row < input.height(); row++) {
      for (int col = 0; col < input.width(); col++) {

        int[] pixel = input.getPixel(row, col);
        int firstChannelIndex = input.pixelIndex(row, col) * 3;

        for (int channelIndex = 0; channelIndex < pixel.length; channelIndex++) {
          filteredBitmap[firstChannelIndex + channelIndex]
                  = this.calcNewPixel(input, pixel)[channelIndex];
        }
      }
    }

    return new RGBImage(input.width(), input.height(), filteredBitmap);
  }

  @Override
  public boolean supportsMask() {
    return true;
  }

}
