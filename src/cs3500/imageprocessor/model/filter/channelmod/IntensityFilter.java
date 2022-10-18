package cs3500.imageprocessor.model.filter.channelmod;

import java.util.Arrays;

import cs3500.imageprocessor.util.image.Image;

/**
 * This IntensityFilter of the ChannelModFilter represents an ImageFilter that uses the average
 * channel value of each Pixel in a given Image to create a grey-scale version of the given image.
 */
public class IntensityFilter extends ChannelModFilter {

  /**
   * Every channel of the newPixel is equal to the average of the given Pixel's channels.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  @Override
  int[] calcNewPixel(Image input, int[] pixel) {
    int average = ((int) Arrays.stream(pixel).average().getAsDouble());
    return new int[]{average, average, average};
  }

}
