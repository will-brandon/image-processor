package cs3500.imageprocessor.util.image;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * An abstract implementation of an image every single element in the bitmap is guaranteed to be
 * a pixel channel.
 */
public abstract class ChanneledImage implements Image {

  private final int width;
  private final int height;
  private final int[] bitmap;

  /**
   * Creates a new channeled image with the given width, height, and bitmap.
   *
   * @param width  the width of the image
   * @param height the height of the image
   * @param bitmap the pixel channel bitmap of the image
   * @throws IllegalArgumentException if the dimensions of the image are invalid or if the bitmap
   *                                  size is invalid
   */
  public ChanneledImage(int width, int height, int[] bitmap)
          throws IllegalArgumentException {
    if (width < 1) {
      throw new IllegalArgumentException("Image width cannot be less than 1: given " + width);
    }
    if (height < 1) {
      throw new IllegalArgumentException("Image height cannot be less than 1: given " + height);
    }

    if (bitmap == null) {
      throw new IllegalArgumentException("Bitmap for image cannot be null");
    }

    this.width = width;
    this.height = height;

    // This test MUST be performed after the width and height are set because the bitmapSize method
    // calls the width and height methods which reference the width and height fields
    if (bitmap.length != this.bitmapSize()) {
      throw new IllegalArgumentException("Invalid PPM image bitmap size: given " + bitmap.length
              + " but expected " + this.bitmapSize());
    }

    this.bitmap = bitmap;
  }

  @Override
  public int width() {
    return width;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public abstract int channelCount();

  @Override
  public abstract int channelMax();

  @Override
  public int bitmapSize() {
    return width() * height() * channelCount();
  }

  /**
   * Returns a stream of the pixel data bitmap. The stream will provide only red, green, and blue
   * channel values in order.
   *
   * <p><strong>NOTE:</strong>This data is provided as a stream instead of an array because copying
   * the entire bitmap array for every call would be an expensive operation.</p>
   *
   * @return a stream of the pixel data bitmap
   */
  @Override
  public IntStream bitmapStream() {
    return Arrays.stream(bitmap);
  }

  @Override
  public int pixelIndex(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= height()) {
      throw new IllegalArgumentException("Invalid row: given " + row);
    }
    if (col < 0 || col >= width()) {
      throw new IllegalArgumentException("Invalid column: given " + col);
    }

    return row * width() + col;
  }

  @Override
  public int[] pixelPosn(int pixelIndex) throws IllegalArgumentException {
    if (pixelIndex < 0 || pixelIndex >= bitmapSize() / 3) {
      throw new IllegalArgumentException("Invalid pixel index: given " + pixelIndex);
    }

    return new int[] {
      pixelIndex / width(),
      pixelIndex % width()
    };
  }

  @Override
  public int[] getPixel(int row, int col) throws IndexOutOfBoundsException {
    if (row < 0 || row >= height()) {
      throw new IndexOutOfBoundsException("Given row is invalid: given " + row);
    }
    if (col < 0 || col >= width()) {
      throw new IndexOutOfBoundsException("Given column is invalid: given " + col);
    }

    int firstIndex = (row * width() + col) * 3;

    // [red, green, blue]
    return Arrays.copyOfRange(bitmap, firstIndex, firstIndex + 3);
  }

  @Override
  public BufferedImage toBufferedImage() {
    BufferedImage bufferedImage = new BufferedImage(width, height, bufferedImageChannelFormatId());

    int[] bitmap = this.bitmapStream().toArray();
    bufferedImage.getRaster().setPixels(0, 0, width, height, bitmap);

    return bufferedImage;
  }

  /**
   * Determines the {@link BufferedImage} channel format id (image type id).
   *
   * @return the channel format id
   */
  protected abstract int bufferedImageChannelFormatId();

  @Override
  public String toString() {
    return "Image(" + width + "x" + height + ")";
  }

}
