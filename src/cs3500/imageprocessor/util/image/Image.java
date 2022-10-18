package cs3500.imageprocessor.util.image;

import java.awt.image.BufferedImage;
import java.util.stream.IntStream;

/**
 * An interface to represent an image. The bitmap is represented using an integer array.
 */
public interface Image {

  /**
   * Returns the width of this image measured in pixels.
   *
   * @return the width of this image measured in pixels
   */
  int width();

  /**
   * Returns the height of this image measured in pixels.
   *
   * @return the height of this image measured in pixels
   */
  int height();

  /**
   * Returns the number of channels per-pixel.
   *
   * @return the number of channels per-pixel
   */
  int channelCount();

  /**
   * Returns the maximum value of a single pixel channel.
   *
   * @return the maximum value of a single pixel chanel
   */
  int channelMax();

  /**
   * Returns the total elemental size of the bitmap, totalling all bytes for all channels in all
   * pixels.
   *
   * @return the size of the bitmap
   */
  int bitmapSize();

  /**
   * Returns a stream of the pixel data bitmap. Different image implementations will specify their
   * own restrictive definitions of exactly what size, form, and order elements of this data will
   * arrive in.
   *
   * <p><strong>NOTE:</strong>This data is provided as a stream instead of an array because copying
   * the entire bitmap array for every call would be an expensive operation.</p>
   *
   * @return a stream of the pixel data bitmap
   */
  IntStream bitmapStream();

  /**
   * Returns the index of the pixel within for the given row and column. Note that this is not the
   * channel, this is the actual index of that pixel. For example, in a 3x3 image, indices would go
   * from 0-8.
   *
   * @param row          the row of the pixel
   * @param col          the column of the pixel
   * @return the index of the pixel
   * @throws IllegalArgumentException if the row or column is invalid
   */
  int pixelIndex(int row, int col) throws IllegalArgumentException;

  /**
   * Returns a 2-dimensional vector (2 element array) containing the row and column of the pixel at
   * the given index. Note that the index is in terms of pixels not channels. For example, in a 3x3
   * image, indices would go from 0-8.
   *
   * @param pixelIndex the index of the pixel
   * @return 2-dimensional vector (2 element array) containing the row and column
   * @throws IllegalArgumentException if the index is invalid
   */
  int[] pixelPosn(int pixelIndex) throws IllegalArgumentException;

  /**
   * Returns a 3-dimensional vector (array) containing the three channels of the pixel at the given
   * row and column.
   *
   * @return a 3-dimensional vector (array) containing the three channels of the pixel
   * @throws IndexOutOfBoundsException if there is no pixel at the given row and column
   */
  int[] getPixel(int row, int col) throws IndexOutOfBoundsException;

  /**
   * Converts this image to the {@link BufferedImage} representation.
   *
   * @return the {@link BufferedImage}
   */
  BufferedImage toBufferedImage();

  /**
   * Returns a string representation of this image.
   */
  String toString();

}
