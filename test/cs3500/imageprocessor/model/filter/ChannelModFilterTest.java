package cs3500.imageprocessor.model.filter;

import org.junit.Before;
import org.junit.Test;

import cs3500.imageprocessor.model.filter.channelmod.BrightnessFilter;
import cs3500.imageprocessor.model.filter.channelmod.ChannelModFilter;
import cs3500.imageprocessor.model.filter.channelmod.IntensityFilter;
import cs3500.imageprocessor.model.filter.channelmod.LumaFilter;
import cs3500.imageprocessor.model.filter.channelmod.MaxValueFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;

/**
 * Tests for ChannelModFilters.
 */
public class ChannelModFilterTest {
  int[] originalPixels;
  Image original;

  ChannelModFilter brightnessFilter;

  ChannelModFilter darknessFilter;

  ChannelModFilter intensityFilter;

  ChannelModFilter maxValFilter;

  ChannelModFilter lumaFilter;

  @Before
  public void setUp() throws Exception {
    // original's Pixels:
    // [0, 32, 23]     [15, 50, 254]    [100, 101, 102]  [254, 0, 254]  [0, 255, 100]
    // [100, 220, 13]  [200, 200, 200]  [0, 23, 32]      [1, 1, 1]      [10, 20, 30]
    // [50, 50, 50]    [217, 182, 222]  [160, 140, 150]  [50, 100, 20]  [0, 254, 127]

    this.originalPixels = new int[]{0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255,
        100, 100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30, 50, 50, 50, 217, 182,
        222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.original = new RGBImage(5, 3, originalPixels);

    // Filters
    this.brightnessFilter = new BrightnessFilter(50);
    this.darknessFilter = new BrightnessFilter(-50);
    this.intensityFilter = new IntensityFilter();
    this.maxValFilter = new MaxValueFilter();
    this.lumaFilter = new LumaFilter();

    // Image redFilterOnOriginal = redFilter.perform(original);
  }

  @Test
  public void testBrightnessFilter() {
    // Brighten
    Image brightenOriginal = brightnessFilter.perform(original);
    assertArrayEquals(new int[]{50, 82, 73, 65, 100, 255, 150, 151, 152, 255, 50, 255, 50, 255, 150,
        150, 255, 63, 250, 250, 250, 50, 73, 82, 51, 51, 51, 60, 70, 80,
        100, 100, 100, 255, 232, 255, 210, 190, 200, 100, 150, 70, 50, 255, 177},
            brightenOriginal.bitmapStream().toArray());

    // Darken
    Image darkenOriginal = darknessFilter.perform(brightenOriginal);
    assertArrayEquals(new int[]{0, 32, 23, 15, 50, 205, 100, 101, 102, 205, 0, 205, 0, 205, 100,
        100, 205, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30,
        50, 50, 50, 205, 182, 205, 160, 140, 150, 50, 100, 20, 0, 205, 127},
            darkenOriginal.bitmapStream().toArray());

  }


  @Test
  public void testIntensityFilter() {
    // Intensify
    Image intensifyOriginal = intensityFilter.perform(original);
    assertArrayEquals(new int[]{18, 18, 18, 106, 106, 106, 101, 101, 101, 169, 169, 169, 118, 118,
        118,  111, 111, 111, 200, 200, 200, 18, 18, 18, 1, 1, 1, 20, 20, 20,
        50, 50, 50, 207, 207, 207, 150, 150, 150, 56, 56, 56, 127, 127, 127},
            intensifyOriginal.bitmapStream().toArray());
  }

  @Test
  public void testValueFilter() {
    // Max Value
    Image maxvalOfOriginal = maxValFilter.perform(original);
    assertArrayEquals(new int[]{32, 32, 32, 254, 254, 254, 102, 102, 102, 254, 254, 254,
        255, 255, 255, 220, 220, 220, 200, 200, 200, 32, 32, 32, 1, 1, 1, 30, 30, 30,
        50, 50, 50, 222, 222, 222, 160, 160, 160, 100, 100, 100, 254, 254, 254},
            maxvalOfOriginal.bitmapStream().toArray());
  }

  @Test
  public void testLumaFilter() {
    // Luma
    Image lumaOfOriginal = lumaFilter.perform(original);
    assertArrayEquals(new int[]{24, 24, 24, 57, 57, 57, 100, 100, 100, 72, 72, 72, 189, 189, 189,
        179, 179, 179, 200, 200, 200, 18, 18, 18, 1, 1, 1, 18, 18, 18,
        50, 50, 50, 192, 192, 192, 144, 144, 144, 83, 83, 83, 190, 190, 190},
            lumaOfOriginal.bitmapStream().toArray());
  }
}