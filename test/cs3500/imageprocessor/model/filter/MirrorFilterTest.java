package cs3500.imageprocessor.model.filter;

import org.junit.Before;
import org.junit.Test;

import cs3500.imageprocessor.model.filter.mirror.HorizontalFlipFilter;
import cs3500.imageprocessor.model.filter.mirror.VerticalFlipFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for the {@link HorizontalFlipFilter} and {@link VerticalFlipFilter} classes.
 */
public class MirrorFilterTest {

  int[] originalPixels;
  Image original;

  ImageFilter horizontalFilter;
  ImageFilter verticalFilter;

  @Before
  public void setUp() {
    // Original's Pixels:
    // [0, 32, 23]     [15, 50, 254]    [100, 101, 102]  [254, 0, 254]  [0, 255, 100]
    // [100, 220, 13]  [200, 200, 200]  [0, 23, 32]      [1, 1, 1]      [10, 20, 30]
    // [50, 50, 50]    [217, 182, 222]  [160, 140, 150]  [50, 100, 20]  [0, 254, 127]

    this.originalPixels = new int[]{
        0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255, 100,
        100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30,
        50, 50, 50, 217, 182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.original = new RGBImage(5, 3, originalPixels);

    // Filters
    this.horizontalFilter = new HorizontalFlipFilter();
    this.verticalFilter = new VerticalFlipFilter();
  }

  @Test
  public void testHorFilter() {
    Image horizontallyFlippedOriginal = horizontalFilter.perform(original);
    assertArrayEquals(new int[]{
        0, 255, 100, 254, 0, 254, 100, 101, 102, 15, 50, 254, 0, 32, 23,
        10, 20, 30, 1, 1, 1, 0, 23, 32, 200, 200, 200, 100, 220, 13,
        0, 254, 127, 50, 100, 20, 160, 140, 150, 217, 182, 222, 50, 50, 50},
            horizontallyFlippedOriginal.bitmapStream().toArray());
  }

  @Test
  public void testVerFilter() {
    Image verticallyFlippedOriginal = verticalFilter.perform(original);
    assertArrayEquals(new int[]{
        50, 50, 50, 217, 182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127,
        100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30,
        0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255, 100},
            verticallyFlippedOriginal.bitmapStream().toArray());
  }

}