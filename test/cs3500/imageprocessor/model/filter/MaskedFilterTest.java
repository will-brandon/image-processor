package cs3500.imageprocessor.model.filter;

import org.junit.Before;
import org.junit.Test;

import cs3500.imageprocessor.model.filter.channelmod.ChannelModFilter;
import cs3500.imageprocessor.model.filter.channelmod.IntensityFilter;
import cs3500.imageprocessor.model.filter.channelmod.LumaFilter;
import cs3500.imageprocessor.model.filter.channelmod.MaxValueFilter;
import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.model.filter.mirror.HorizontalFlipFilter;
import cs3500.imageprocessor.model.filter.mirror.MirrorFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;
import static util.TestUtils.assertException;

/**
 * Performs tests.
 */
public class MaskedFilterTest {

  private Image original;
  private Image masked1;
  private Image masked2;

  private RGBFilter redFilter;
  private ChannelModFilter intensityFilter;

  private MirrorFilter horizontalFlipFilter;
  private ImageFilter maxValFilter;
  private ImageFilter lumaFilter;

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

    int[] maskedPixels1 = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182,
        222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.masked1 = new RGBImage(5, 3, maskedPixels1);

    int[] maskedPixels2 = new int[]{
        0, 0, 0, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255,
        100, 0, 0, 0, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 0, 0, 0, 217, 182,
        222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.masked2 = new RGBImage(5, 3, maskedPixels2);

    // Filters
    this.redFilter = new RGBFilter(0);
    this.intensityFilter = new IntensityFilter();
    this.maxValFilter = new MaxValueFilter();
    this.lumaFilter = new LumaFilter();
    this.horizontalFlipFilter = new HorizontalFlipFilter();

    // Image redFilterOnOriginal = redFilter.perform(original);
  }

  @Test
  public void testMaskConstructors() {
    assertException(IllegalArgumentException.class,
              "Masked image cannot be null",
        () -> new MaskedFilter(null, this.intensityFilter));
    assertException(IllegalArgumentException.class,
              "Filter cannot be null",
        () -> new MaskedFilter(original, null));
    assertException(IllegalArgumentException.class,
            "Can not apply mirroredFilter to masked image",
        () -> new MaskedFilter(original, this.horizontalFlipFilter));
  }

  @Test
  public void testMaskedFilter() {
    Image redMasked1Original = new MaskedFilter(masked1, redFilter).perform(original);

    assertArrayEquals(new int[]{0, 0, 0, 15, 15, 15, 100, 100, 100, 254, 254, 254, 0, 0, 0,
        100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50,
        217, 182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127},
            redMasked1Original.bitmapStream().toArray());


    Image intensityMasked1Original = new MaskedFilter(masked2, intensityFilter).perform(original);

    assertArrayEquals(new int[]{18, 18, 18, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255, 100,
        111, 111, 111, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217,
        182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127},
            intensityMasked1Original.bitmapStream().toArray());


    Image maxValMasked1Original = new MaskedFilter(masked2, maxValFilter).perform(original);

    assertArrayEquals(new int[]{32, 32, 32, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255, 100,
        220, 220, 220, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217,
        182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127},
            maxValMasked1Original.bitmapStream().toArray());


    Image lumaMasked1Original = new MaskedFilter(masked1, lumaFilter).perform(redMasked1Original);

    for (int i = 0; i < lumaMasked1Original.bitmapSize(); i++) {
      System.out.print(lumaMasked1Original.bitmapStream().toArray()[i] + ", ");
    }

    assertArrayEquals(new int[]{0, 0, 0, 15, 15, 15, 100, 100, 100, 254, 254, 254, 0, 0, 0, 100,
        220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182, 222,
        160, 140, 150, 50, 100, 20, 0, 254, 127},
            lumaMasked1Original.bitmapStream().toArray());

  }
}
