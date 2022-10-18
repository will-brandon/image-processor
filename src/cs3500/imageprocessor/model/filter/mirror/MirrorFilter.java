package cs3500.imageprocessor.model.filter.mirror;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * This class represents an {@link ImageFilter} that mirrors each pixel about an axis. Each
 * dimension of this operation will be implemented appropriately.
 */
public abstract class MirrorFilter implements ImageFilter {

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param input the image to perform this filter function on.
   * @return the output image with the filter applied
   */
  @Override
  public Image perform(Image input) {
    int[] filteredBitmap = new int[input.bitmapSize()];

    int aSize = reflectedDimensionSize(input);
    int bSize = secondaryDimensionSize(input);

    for (int a = 0; a < aSize / 2 + 1; a++) {
      int mirroredA = aSize - 1 - a;

      for (int b = 0; b < bSize; b++) {
        int row = rowIndex(a, b);
        int col = colIndex(a, b);
        int mirroredRow = rowIndex(mirroredA, b);
        int mirroredCol = colIndex(mirroredA, b);

        int[] pixel1 = input.getPixel(row, col);
        int pixel1FirstIndex = input.pixelIndex(row, col) * 3;

        int[] pixel2 = input.getPixel(mirroredRow, mirroredCol);
        int pixel2FirstIndex = input.pixelIndex(mirroredRow, mirroredCol) * 3;

        // Flip pixel1 && pixel2
        filteredBitmap[pixel1FirstIndex] = pixel2[0];
        filteredBitmap[pixel1FirstIndex + 1] = pixel2[1];
        filteredBitmap[pixel1FirstIndex + 2] = pixel2[2];

        filteredBitmap[pixel2FirstIndex] = pixel1[0];
        filteredBitmap[pixel2FirstIndex + 1] = pixel1[1];
        filteredBitmap[pixel2FirstIndex + 2] = pixel1[2];
      }
    }

    return new RGBImage(input.width(), input.height(), filteredBitmap);
  }

  /**
   * Returns the size of the dimension that is being reflected.
   *
   * @param input the image
   * @return the size of the dimension that is being reflected
   */
  protected abstract int reflectedDimensionSize(Image input);

  /**
   * Returns the size of the secondary dimension that is not being reflected.
   *
   * @param input the image
   * @return the size of the secondary dimension that is not being reflected
   */
  protected abstract int secondaryDimensionSize(Image input);

  /**
   * Returns the proper component index that will be used as the row index.
   *
   * @param reflectedIndex the index of the component that is reflected
   * @param secondaryIndex the index of the secondary component that is not reflected
   * @return the row index
   */
  protected abstract int rowIndex(int reflectedIndex, int secondaryIndex);

  /**
   * Returns the proper component index that will be used as the column index.
   *
   * @param reflectedIndex the index of the component that is reflected
   * @param secondaryIndex the index of the secondary component that is not reflected
   * @return the column index
   */
  protected abstract int colIndex(int reflectedIndex, int secondaryIndex);

  @Override
  public boolean supportsMask() {
    return false;
  }

}
