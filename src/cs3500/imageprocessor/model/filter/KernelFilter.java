package cs3500.imageprocessor.model.filter;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * A class to represent a filter that applies a kernel matrix transform to every pixel.
 */
public class KernelFilter implements ImageFilter {

  private double[][] kernel;

  /**
   * Creates a new kernel filter given the kernel matrix to apply.
   *
   * @param kernel the kernel matrix to apply
   * @throws IllegalArgumentException if the kernel or any of its rows are null or if its dimensions
   *                                  are invalid
   */
  public KernelFilter(double[][] kernel) throws IllegalArgumentException {
    if (kernel == null) {
      throw new IllegalArgumentException("Kernel array cannot be null");
    }
    if (kernel.length < 3 || kernel.length % 2 == 0) {
      throw new IllegalArgumentException("Kernel array invalid: given " + kernel.length);
    }

    for (int row = 0; row < kernel.length; row++) {

      if (kernel[row] == null) {
        throw new IllegalArgumentException("Row of kernel cannot be null");
      }

      if (kernel[row].length != kernel.length) {
        throw new IllegalArgumentException("Kernel array cannot be jagged");
      }
    }

    this.kernel = kernel;
  }

  @Override
  public Image perform(Image image) {
    int[] filteredBitmap = new int[image.bitmapSize()];

    for (int i = 0; i < image.bitmapSize(); i += 3) {

      int newRed = 0;
      int newGreen = 0;
      int newBlue = 0;

      for (int kRow = 0; kRow < kernel.length; kRow++) {
        for (int kCol = 0; kCol < kernel[0].length; kCol++) {

          int[] pixelPosn = image.pixelPosn(i / 3);

          int kernelMiddleIndex = kernel.length / 2;

          int row = pixelPosn[0] + kRow - kernelMiddleIndex;
          int col = pixelPosn[1] + kCol - kernelMiddleIndex;

          int[] channels = channelsIfExists(image, row, col);
          double kernelValue = kernel[kRow][kCol];

          newRed += Math.round(channels[0] * kernelValue);
          newGreen += Math.round(channels[1] * kernelValue);
          newBlue += Math.round(channels[2] * kernelValue);
        }
      }

      filteredBitmap[i] = Math.max(0, Math.min(255, newRed));
      filteredBitmap[i + 1] = Math.max(0, Math.min(255, newGreen));
      filteredBitmap[i + 2] = Math.max(0, Math.min(255, newBlue));

    }

    return new RGBImage(image.width(), image.height(), filteredBitmap);
  }

  /**
   * Returns all the channels of the pixel at the given row and column if the pixel is within
   * bounds.
   *
   * @param image the image
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return the channels of the pixel or a zero vector if it doesn't exist
   */
  private int[] channelsIfExists(Image image, int row, int col) {
    // Under any of these conditions, the pixel is out of bounds
    if (row < 0 || row >= image.height() || col < 0 || col >= image.width()) {
      return new int[]{0, 0, 0};
    }

    return image.getPixel(row, col);
  }

  @Override
  public boolean supportsMask() {
    return true;
  }

}
