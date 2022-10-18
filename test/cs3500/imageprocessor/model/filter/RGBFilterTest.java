package cs3500.imageprocessor.model.filter;

import org.junit.Test;

import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertEquals;
import static util.TestUtils.assertException;

/**
 * Tests for RGBFilters.
 */
public class RGBFilterTest {

  @Test
  public void constructInvalidRGBFilters() {
    // Positive Channels
    for (int i = 3; i < 100; i++) {
      int finalI = i;
      assertException(IllegalArgumentException.class,
              "Illegal color channel argument: given " + i,
          () -> new RGBFilter(finalI));
      assertException(IllegalArgumentException.class,
              "Illegal color channel argument: given " + i,
          () -> new RGBFilter(finalI));
    }

    // Negative Channels
    for (int i = -1; i > -100; i--) {
      int finalI = i;
      assertException(IllegalArgumentException.class,
              "Illegal color channel argument: given " + i,
          () -> new RGBFilter(finalI));
      assertException(IllegalArgumentException.class,
              "Illegal color channel argument: given " + i,
          () -> new RGBFilter(finalI));
    }
  }

  @Test
  public void testPerform() {
    // original's Pixels:
    // [0, 32, 23]     [15, 50, 254]    [100, 101, 102]  [254, 0, 254]  [0, 255, 100]
    // [100, 220, 13]  [200, 200, 200]  [0, 23, 32]      [1, 1, 1]      [10, 20, 30]
    // [50, 50, 50]    [217, 182, 222]  [160, 140, 150]  [50, 100, 20]  [0, 254, 127]

    int[] originalPixels = new int[]{0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 254,
        100, 100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182,
        222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    Image original = new RGBImage(5, 3, originalPixels);
    // Check original values of Image original
    assertEquals(45, original.bitmapSize());
    assertEquals(255, original.channelMax());
    assertEquals(3, original.channelCount());

    // Check random handful of original's pixels
    assertEquals(0, original.getPixel(0, 0)[0]);
    assertEquals(32, original.getPixel(0, 0)[1]);
    assertEquals(23, original.getPixel(0, 0)[2]);

    assertEquals(0, original.getPixel(2, 4)[0]);
    assertEquals(254, original.getPixel(2, 4)[1]);
    assertEquals(127, original.getPixel(2, 4)[2]);

    assertEquals(100, original.getPixel(1, 0)[0]);
    assertEquals(220, original.getPixel(1, 0)[1]);
    assertEquals(13, original.getPixel(1, 0)[2]);

    // Apply redFilter to original
    ImageFilter redFilter = new RGBFilter(0);
    Image redFilterOnOriginal = redFilter.perform(original);

    // Apply greenFilter to original
    ImageFilter greenFilter = new RGBFilter(1);
    Image greenFilterOnOriginal = greenFilter.perform(original);

    // Apply blueFilter to original
    ImageFilter blueFilter = new RGBFilter(2);
    Image blueFilterOnOriginal = blueFilter.perform(original);

    // Apply blueFilter to redFilterOnOriginal
    Image blueOnRedFilterOnOriginal = blueFilter.perform(redFilterOnOriginal);

    // Check same selection of original's red channels in pixels with redOnOriginal
    assertEquals(original.getPixel(0, 0)[0], redFilterOnOriginal.getPixel(0, 0)[0]);
    assertEquals(original.getPixel(0, 0)[0], redFilterOnOriginal.getPixel(0, 0)[1]);
    assertEquals(original.getPixel(0, 0)[0], redFilterOnOriginal.getPixel(0, 0)[2]);

    assertEquals(original.getPixel(2, 4)[0], redFilterOnOriginal.getPixel(2, 4)[0]);
    assertEquals(original.getPixel(2, 4)[0], redFilterOnOriginal.getPixel(2, 4)[1]);
    assertEquals(original.getPixel(2, 4)[0], redFilterOnOriginal.getPixel(2, 4)[2]);

    assertEquals(original.getPixel(1, 0)[0], redFilterOnOriginal.getPixel(1, 0)[0]);
    assertEquals(original.getPixel(1, 0)[0], redFilterOnOriginal.getPixel(1, 0)[1]);
    assertEquals(original.getPixel(1, 0)[0], redFilterOnOriginal.getPixel(1, 0)[2]);

    // And check for green and blue filter effect as well
    assertEquals(original.getPixel(0, 0)[1], greenFilterOnOriginal.getPixel(0, 0)[0]);
    assertEquals(original.getPixel(0, 0)[1], greenFilterOnOriginal.getPixel(0, 0)[1]);
    assertEquals(original.getPixel(0, 0)[1], greenFilterOnOriginal.getPixel(0, 0)[2]);
    assertEquals(original.getPixel(2, 4)[1], greenFilterOnOriginal.getPixel(2, 4)[0]);
    assertEquals(original.getPixel(2, 4)[1], greenFilterOnOriginal.getPixel(2, 4)[1]);
    assertEquals(original.getPixel(2, 4)[1], greenFilterOnOriginal.getPixel(2, 4)[2]);
    assertEquals(original.getPixel(1, 0)[1], greenFilterOnOriginal.getPixel(1, 0)[0]);
    assertEquals(original.getPixel(1, 0)[1], greenFilterOnOriginal.getPixel(1, 0)[1]);
    assertEquals(original.getPixel(1, 0)[1], greenFilterOnOriginal.getPixel(1, 0)[2]);

    assertEquals(original.getPixel(0, 0)[2], blueFilterOnOriginal.getPixel(0, 0)[0]);
    assertEquals(original.getPixel(0, 0)[2], blueFilterOnOriginal.getPixel(0, 0)[1]);
    assertEquals(original.getPixel(0, 0)[2], blueFilterOnOriginal.getPixel(0, 0)[2]);
    assertEquals(original.getPixel(2, 4)[2], blueFilterOnOriginal.getPixel(2, 4)[0]);
    assertEquals(original.getPixel(2, 4)[2], blueFilterOnOriginal.getPixel(2, 4)[1]);
    assertEquals(original.getPixel(2, 4)[2], blueFilterOnOriginal.getPixel(2, 4)[2]);
    assertEquals(original.getPixel(1, 0)[2], blueFilterOnOriginal.getPixel(1, 0)[0]);
    assertEquals(original.getPixel(1, 0)[2], blueFilterOnOriginal.getPixel(1, 0)[1]);
    assertEquals(original.getPixel(1, 0)[2], blueFilterOnOriginal.getPixel(1, 0)[2]);

    // Check multiple filters on one image
    assertEquals(original.getPixel(0, 0)[0], blueOnRedFilterOnOriginal.getPixel(0, 0)[0]);
    assertEquals(original.getPixel(0, 0)[0], blueOnRedFilterOnOriginal.getPixel(0, 0)[1]);
    assertEquals(original.getPixel(0, 0)[0], blueOnRedFilterOnOriginal.getPixel(0, 0)[2]);

    assertEquals(original.getPixel(2, 4)[0], blueOnRedFilterOnOriginal.getPixel(2, 4)[0]);
    assertEquals(original.getPixel(2, 4)[0], blueOnRedFilterOnOriginal.getPixel(2, 4)[1]);
    assertEquals(original.getPixel(2, 4)[0], blueOnRedFilterOnOriginal.getPixel(2, 4)[2]);

    assertEquals(original.getPixel(1, 0)[0], blueOnRedFilterOnOriginal.getPixel(1, 0)[0]);
    assertEquals(original.getPixel(1, 0)[0], blueOnRedFilterOnOriginal.getPixel(1, 0)[1]);
    assertEquals(original.getPixel(1, 0)[0], blueOnRedFilterOnOriginal.getPixel(1, 0)[2]);

  }
}