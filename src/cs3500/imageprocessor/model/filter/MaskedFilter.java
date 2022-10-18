package cs3500.imageprocessor.model.filter;

import cs3500.imageprocessor.model.filter.mirror.MirrorFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * A class to represent a filter that apply's a given image manipulation filter to only a masked
 * region of an image.
 */
public class MaskedFilter implements ImageFilter {

  private final Image maskedImage;
  private final ImageFilter filter;

  /**
   * Creates a new mask filter.
   *
   * @param maskedImage the image to use as a mask
   * @param filter      the filter to apply to the masked region
   * @throws IllegalArgumentException if the image or filter is null or if the filter is not
   *                                  maskable
   */
  public MaskedFilter(Image maskedImage, ImageFilter filter) throws IllegalArgumentException {
    if (maskedImage == null) {
      throw new IllegalArgumentException("Masked image cannot be null");
    }
    if (filter == null) {
      throw new IllegalArgumentException("Filter cannot be null");
    }
    if (filter instanceof MirrorFilter) {
      throw new IllegalArgumentException("Can not apply mirroredFilter to masked image");
    }

    this.maskedImage = maskedImage;
    this.filter = filter;
  }

  @Override
  public Image perform(Image input) {
    Image filteredImage = filter.perform(input);

    int[] maskFilteredBitmap = new int[input.bitmapSize()];

    for (int row = 0; row < input.height(); row++) {
      for (int col = 0; col < input.width(); col++) {

        if (isPixelMasked(row, col)) {
          copyPixel(filteredImage, maskFilteredBitmap, row, col);
        } else {
          copyPixel(input, maskFilteredBitmap, row, col);
        }
      }
    }

    return new RGBImage(input.width(), input.height(), maskFilteredBitmap);
  }

  /**
   * Copies the pixel at the given location from the given image to the specified bitmap. The bitmap
   * is passed by reference and will be modified.
   *
   * @param srcImage   the image to copy from
   * @param destBitmap the bitmap to copy to
   * @param row        the row of pixel to copy
   * @param col        the column of pixel to copy
   */
  private void copyPixel(Image srcImage, int[] destBitmap, int row, int col) {
    int pixelIndex = srcImage.pixelIndex(row, col);
    int[] pixel = srcImage.getPixel(row, col);

    destBitmap[pixelIndex * 3] = pixel[0];
    destBitmap[(pixelIndex * 3) + 1] = pixel[1];
    destBitmap[(pixelIndex * 3) + 2] = pixel[2];
  }

  /**
   * Determines whether the given pixel is considered "masked" i.e. a filter should be applied to
   * the pixel. It is masked if it is completely black (0 in all 3 channels).
   *
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @return true if the given pixel is masked
   */
  private boolean isPixelMasked(int row, int col) {
    int[] pixel = maskedImage.getPixel(row, col);

    return pixel[0] == 0 && pixel[1] == 0 && pixel[2] == 0;
  }

  @Override
  public boolean supportsMask() {
    return false;
  }

}
