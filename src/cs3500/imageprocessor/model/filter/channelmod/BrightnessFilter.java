package cs3500.imageprocessor.model.filter.channelmod;

import cs3500.imageprocessor.util.image.Image;

/**
 * This BrightnessFilter of the ChannelModFilter represents an ImageFilter that must modify the
 * brightness of every channel in a given image depending on the given constant.
 */
public class BrightnessFilter extends ChannelModFilter {

  private final int value;

  /**
   * Constructor 1: Creates a BrightnessFilter that either brightens or darkens an image based on
   * the sign of the constant.
   *
   * @param value the value of the filter
   */
  public BrightnessFilter(int value) {
    this.value = value;
  }

  /**
   * Brightens the given Pixel's channels by teh given value, not exceeding the channelMax of
   * the given image or dipping below 0.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  @Override
  int[] calcNewPixel(Image input, int[] pixel) {
    int[] newPixel = new int[3];

    for (int channelIndex = 0; channelIndex < pixel.length; channelIndex++) {
      if (pixel[channelIndex] + this.value > input.channelMax()) {
        newPixel[channelIndex] = input.channelMax();
      } else {
        newPixel[channelIndex] = Math.max(0, Math.min(255, pixel[channelIndex] + this.value));
      }
    }
    return newPixel;
  }

}
