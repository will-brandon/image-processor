package cs3500.imageprocessor.model.filter.channelmod;

import cs3500.imageprocessor.util.image.Image;

/**
 * This class performs a color transformation on an image based on a transform matrix.
 */
public class ColorTransformationFilter extends ChannelModFilter {

  private double[][] matrix;

  /**
   * Creates a new ColorTransformationFilter filter given the matrix to apply.
   *
   * @param matrix the matrix to apply
   * @throws IllegalArgumentException if the kernel or any of its rows are null or if its dimensions
   *                                  are invalid
   */
  public ColorTransformationFilter(double[][] matrix) throws IllegalArgumentException {
    if (matrix == null) {
      throw new IllegalArgumentException("Matrix cannot be null");
    }
    if (matrix.length != 3) {
      throw new IllegalArgumentException("Matrix row count must be 3: given " + matrix.length);
    }

    for (int row = 0; row < matrix.length; row++) {

      if (matrix[row] == null) {
        throw new IllegalArgumentException("Row of matrix cannot be null");
      }

      int rowLength = matrix[row].length;
      if (rowLength != 3) {
        throw new IllegalArgumentException("Matrix column count must be 3 for all rows: given "
                + rowLength);
      }

    }

    this.matrix = matrix;
  }

  /**
   * Calculates the channels of a newPixel to replace the given Pixel based on this filter.
   *
   * @param input the current image being modified
   * @param pixel the current pixel used to calculate the new channel value
   * @return the new value of every channel in the given pixel
   */
  @Override
  int[] calcNewPixel(Image input, int[] pixel) {
    int newRed = Math.max(0, Math.min(255, (int) (Math.round((this.matrix[0][0] * pixel[0])
            + (this.matrix[0][1] * pixel[1])
            + (this.matrix[0][2] * pixel[2])))));
    int newGreen = Math.max(0, Math.min(255, (int) (Math.round((this.matrix[1][0] * pixel[0])
            + (this.matrix[1][1] * pixel[1])
            + (this.matrix[1][2] * pixel[2])))));
    int newBlue = Math.max(0, Math.min(255, (int) (Math.round((this.matrix[2][0] * pixel[0])
            + (this.matrix[2][1] * pixel[1])
            + (this.matrix[2][2] * pixel[2])))));

    return new int[]{newRed, newGreen, newBlue};
  }

}
