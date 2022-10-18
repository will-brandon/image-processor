package cs3500.imageprocessor.model.filter;

import org.junit.Before;
import org.junit.Test;

import cs3500.imageprocessor.model.filter.channelmod.ColorTransformationFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;

/**
 * A class to perform tests.
 */
public class ColorTransformationFilterTest {
  int[] originalPixels;
  Image original;
  ColorTransformationFilter greyscaleFilter;
  ColorTransformationFilter sepiaFilter;

  @Before
  public void setUp() throws Exception {
    // original's Pixels:
    // [0, 32, 23]     [15, 50, 254]    [100, 101, 102]  [254, 0, 254]  [0, 255, 100]
    // [100, 220, 13]  [200, 200, 200]  [0, 23, 32]      [1, 1, 1]      [10, 20, 30]
    // [50, 50, 50]    [217, 182, 222]  [160, 140, 150]  [50, 100, 20]  [0, 254, 127]

    this.originalPixels = new int[]{
      0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255,
      100, 100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182,
      222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.original = new RGBImage(5, 3, originalPixels);

    // Filters
    this.greyscaleFilter = new ColorTransformationFilter(CommonFilterMatrices.GREYSCALE_MATRIX);
    this.sepiaFilter = new ColorTransformationFilter(CommonFilterMatrices.SEPIA_MATRIX);
  }

  @Test
  public void testGreyscaleFilter() {
    Image greyscaleOriginal = greyscaleFilter.perform(original);
    assertArrayEquals(new int[]{
        25, 25, 25, 57, 57, 57, 101, 101, 101, 72, 72, 72, 190, 190, 190,
        180, 180, 180, 200, 200, 200, 19, 19, 19, 1, 1, 1, 19, 19, 19,
        50, 50, 50, 192, 192, 192, 145, 145, 145, 84, 84, 84, 191, 191, 191},
            greyscaleOriginal.bitmapStream().toArray());
  }

  @Test
  public void testSepiaFilter() {
    Image sepiaOriginal = sepiaFilter.perform(original);
    assertArrayEquals(new int[]{
        29, 26, 20, 92, 82, 64, 136, 121, 94, 148, 131, 102, 215, 192, 149,
        211, 188, 146, 255, 241, 187, 24, 21, 16, 1, 1, 1, 25, 22, 17,
        68, 60, 47, 255, 238, 185, 199, 177, 138, 100, 89, 70, 219, 196, 152},
            sepiaOriginal.bitmapStream().toArray());
  }
}