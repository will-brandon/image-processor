package cs3500.imageprocessor.model.filter.channelmod;

import cs3500.imageprocessor.util.image.Image;

/**
 * This LumaFilter of the ChannelModFilter represents an ImageFilter that uses the weightedSum
 * channel value of each Pixel in a given Image to create a grey-scale version of the given image.
 */
public class LumaFilter extends ChannelModFilter {
  /**
   * Calculates the weightedSum of the given Pixel to replace every channel in the newPixel.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  @Override
  int[] calcNewPixel(Image input, int[] pixel) {
    int weightedSum = (int)((0.2126 * pixel[0]) + (0.7152 * pixel[1]) + (0.0722 * pixel[2]));
    return new int[]{weightedSum, weightedSum, weightedSum};
  }
}
