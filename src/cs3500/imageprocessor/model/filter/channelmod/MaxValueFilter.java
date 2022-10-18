package cs3500.imageprocessor.model.filter.channelmod;

import java.util.Arrays;

import cs3500.imageprocessor.util.image.Image;

/**
 * This MaxValueFilter of the ChannelModFilter represents an ImageFilter that uses the max value of
 * the channels in each Pixel in a given Image to create a grey-scale version of the given image.
 */
public class MaxValueFilter extends ChannelModFilter {

  /**
   * Gets the max value of a channel in the given Pixel which is to be the value of every channel in
   * the new Pixel.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  @Override
  int[] calcNewPixel(Image input, int[] pixel) {
    int max = Arrays.stream(pixel).max().getAsInt();
    return new int[]{max, max, max};
  }
}
