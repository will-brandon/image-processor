package cs3500.imageprocessor.util.image;

import java.awt.image.BufferedImage;

/**
 * A class to represent an RGB image. This is a simple file format that represents red, green, and
 * blue pixel channels in terms of integers with whitespace delimiters.
 */
public class RGBImage extends ChanneledImage {

  /**
   * Creates a new RGB image with the given bitmap.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param bitmap the pixel channel bitmap of the image
   * @throws IllegalArgumentException if the dimensions of the image are invalid or if the bitmap
   *                                  size is invalid
   */
  public RGBImage(int width, int height, int[] bitmap)
          throws IllegalArgumentException {
    super(width, height, bitmap);
  }

  @Override
  public int channelCount() {
    return 3;
  }

  @Override
  public int channelMax() {
    return 255;
  }

  @Override
  protected int bufferedImageChannelFormatId() {
    return BufferedImage.TYPE_INT_RGB;
  }

}
