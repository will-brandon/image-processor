package cs3500.imageprocessor.model.filter;

import org.junit.Before;
import org.junit.Test;

import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;

/**
 * Performs tests.
 */
public class KernelFilterTest {
  private Image original;
  private KernelFilter blurFilter;
  private KernelFilter sharpenFilter;

  @Before
  public void setUp() throws Exception {
    // original's Pixels:
    // [0, 32, 23]     [15, 50, 254]    [100, 101, 102]  [254, 0, 254]  [0, 255, 100]
    // [100, 220, 13]  [200, 200, 200]  [0, 23, 32]      [1, 1, 1]      [10, 20, 30]
    // [50, 50, 50]    [217, 182, 222]  [160, 140, 150]  [50, 100, 20]  [0, 254, 127]

    int[] originalPixels = new int[]{
        0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255,
        100, 100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182,
        222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.original = new RGBImage(5, 3, originalPixels);

    // Filters
    this.blurFilter = new KernelFilter(CommonFilterMatrices.BLUR_KERNEL);
    this.sharpenFilter = new KernelFilter(CommonFilterMatrices.SHARPEN_KERNEL);
  }

  @Test
  public void testBlurFilter() {
    Image blurredOriginal = blurFilter.perform(original);
    assertArrayEquals(new int[]{
        28, 55, 53, 48, 70, 108, 72, 47, 107, 78, 47, 94, 33, 67, 61,
        71, 104, 67, 111, 130, 135, 92, 82, 112, 55, 66, 72, 22, 75, 54,
        66, 77, 56, 111, 110, 109, 86, 87, 86, 34, 77, 44, 7, 80, 39},
            blurredOriginal.bitmapStream().toArray());
  }

  @Test
  public void testSharpenFilter() {
    Image sharpenedOriginal = sharpenFilter.perform(original);
    assertArrayEquals(new int[]{
        14, 88, 70, 24, 136, 255, 146, 15, 196, 202, 0, 199, 29, 183, 118,
        189, 255, 166, 255, 255, 255, 231, 114, 255, 92, 171, 122, 55, 140, 120,
        145, 158, 88, 255, 255, 252, 212, 145, 149, 0, 111, 0, 0, 219, 60},
            sharpenedOriginal.bitmapStream().toArray());
  }

}