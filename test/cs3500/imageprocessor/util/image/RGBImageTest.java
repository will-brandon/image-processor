package cs3500.imageprocessor.util.image;

import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static util.TestUtils.assertException;
import static util.TestableItems.IMG1X1;
import static util.TestableItems.IMG1X1BITMAP;
import static util.TestableItems.IMG5X3;
import static util.TestableItems.IMG5X3BITMAP;

/**
 * This class performs tests on the {@link RGBImage} class.
 */
public class RGBImageTest {

  @Test
  public void construct() {
    Image ppm1x1 = new RGBImage(1, 1, IMG1X1BITMAP);
    Image ppm5x3 = new RGBImage(5, 3, IMG5X3BITMAP);

    assertEquals(1, ppm1x1.width());
    assertEquals(1, ppm1x1.height());
    assertArrayEquals(IMG1X1BITMAP, ppm1x1.bitmapStream().toArray());

    assertEquals(5, ppm5x3.width());
    assertEquals(3, ppm5x3.height());
    assertArrayEquals(IMG5X3BITMAP, ppm5x3.bitmapStream().toArray());
  }

  @Test
  public void constructInvalidDimensions() {
    for (int i = 0; i > -100; i--) {

      // In order to use i in a lambda, it must be copied to a local variable in this scope
      int localI = i;

      assertException(IllegalArgumentException.class,
              "Image width cannot be less than 1: given " + i,
          () -> new RGBImage(localI, 1, new int[]{}));
      assertException(IllegalArgumentException.class,
              "Image height cannot be less than 1: given " + i,
          () -> new RGBImage(1, localI, new int[]{}));
    }
  }

  @Test
  public void constructInvalidBitmap() {
    assertException(IllegalArgumentException.class, "Bitmap for image cannot be null",
        () -> new RGBImage(1, 1, null));

    assertException(IllegalArgumentException.class,
            "Invalid PPM image bitmap size: given 4 but expected 3",
        () -> new RGBImage(1, 1, new int[] {69, 420, 8, 22}));
    assertException(IllegalArgumentException.class,
            "Invalid PPM image bitmap size: given 0 but expected 36",
        () -> new RGBImage(4, 3, new int[] {}));
    assertException(IllegalArgumentException.class,
            "Invalid PPM image bitmap size: given 5 but expected 18",
        () -> new RGBImage(2, 3, new int[] {0, 2, 4, 6, 8}));
  }

  @Test
  public void width() {
    assertEquals(1, IMG1X1.width());
    assertEquals(5, IMG5X3.width());
  }

  @Test
  public void height() {
    assertEquals(1, IMG1X1.height());
    assertEquals(3, IMG5X3.height());
  }

  @Test
  public void channelCount() {
    assertEquals(3, IMG1X1.channelCount());
    assertEquals(3, IMG5X3.channelCount());
  }

  @Test
  public void channelMax() {
    assertEquals(255, IMG1X1.channelMax());
    assertEquals(255, IMG5X3.channelMax());
  }

  @Test
  public void bitmapStream() {
    assertArrayEquals(IMG1X1BITMAP, IMG1X1.bitmapStream().toArray());
    assertArrayEquals(IMG5X3BITMAP, IMG5X3.bitmapStream().toArray());
  }

  @Test
  public void pixelIndex() {
    testPixelIndex(IMG1X1);
    testPixelIndex(IMG5X3);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertEquals(IMG1X1.width(), 1);
  }

  @Test
  public void pixelIndexInvalid() {
    assertException(IllegalArgumentException.class, "Invalid row: given -1",
        () -> IMG5X3.pixelIndex(-1, 0));
    assertException(IllegalArgumentException.class, "Invalid row: given -5",
        () -> IMG5X3.pixelIndex(-5, 1));
    assertException(IllegalArgumentException.class, "Invalid row: given 3",
        () -> IMG5X3.pixelIndex(3, 2));
    assertException(IllegalArgumentException.class, "Invalid row: given 5",
        () -> IMG5X3.pixelIndex(5, 3));

    assertException(IllegalArgumentException.class, "Invalid column: given -1",
        () -> IMG5X3.pixelIndex(0, -1));
    assertException(IllegalArgumentException.class, "Invalid column: given -5",
        () -> IMG5X3.pixelIndex(1, -5));
    assertException(IllegalArgumentException.class, "Invalid column: given 5",
        () -> IMG5X3.pixelIndex(2, 5));
    assertException(IllegalArgumentException.class, "Invalid column: given 6",
        () -> IMG5X3.pixelIndex(1, 6));
  }

  @Test
  public void pixelPosn() {
    testPixelPosn(IMG1X1);
    testPixelPosn(IMG5X3);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertEquals(IMG1X1.width(), 1);
  }

  @Test
  public void pixelPosnInvalid() {
    assertException(IllegalArgumentException.class, "Invalid pixel index: given -1",
        () -> IMG5X3.pixelPosn(-1));
    assertException(IllegalArgumentException.class, "Invalid pixel index: given -3",
        () -> IMG5X3.pixelPosn(-3));
    assertException(IllegalArgumentException.class, "Invalid pixel index: given 15",
        () -> IMG5X3.pixelPosn(15));
    assertException(IllegalArgumentException.class, "Invalid pixel index: given 16",
        () -> IMG5X3.pixelPosn(16));
  }

  @Test
  public void getPixel() {
    testGetPixel(IMG1X1);
    testGetPixel(IMG5X3);

    // THE STYLE CHECKER MAKES US DO AN ASSERT BUT I CALL THEM IN THE HELPER SO HERE'S AN ASSERT
    assertEquals(IMG1X1.width(), 1);
  }

  @Test
  public void getPixelInvalid() {
    assertException(IndexOutOfBoundsException.class, "Given row is invalid: given -1",
        () -> IMG5X3.getPixel(-1, 9));
    assertException(IndexOutOfBoundsException.class, "Given row is invalid: given 6",
        () -> IMG5X3.getPixel(6, 9));
    assertException(IndexOutOfBoundsException.class, "Given column is invalid: given -7",
        () -> IMG5X3.getPixel(2, -7));
    assertException(IndexOutOfBoundsException.class, "Given column is invalid: given 8",
        () -> IMG5X3.getPixel(1, 8));
  }

  @Test
  public void testToString() {
    assertEquals("Image(1x1)", IMG1X1.toString());
    assertEquals("Image(5x3)", IMG5X3.toString());
  }

  /**
   * Tests a given image to ensure that the {@code getPixel} method returns the proper 3D vector
   * (array) of red, green, and blue channels.
   *
   * @param image the image to test
   */
  private void testGetPixel(Image image) {
    Iterator<Integer> iterator = image.bitmapStream().iterator();

    for (int i = 0; i < image.bitmapSize(); i += 3) {

      int row = i / (image.width() * 3);
      int col = (i / 3) % image.width();

      int[] channelVec = image.getPixel(row, col);

      assertEquals(iterator.next(), Integer.valueOf(channelVec[0]));
      assertEquals(iterator.next(), Integer.valueOf(channelVec[1]));
      assertEquals(iterator.next(), Integer.valueOf(channelVec[2]));
    }
  }

  /**
   * Tests a given image to ensure that {@code pixelImage} returns the proper index given a row and
   * column.
   *
   * @param image the image to test
   */
  private void testPixelIndex(Image image) {
    int index = 0;

    for (int row = 0; row < image.height(); row++) {
      for (int col = 0; col < image.width(); col++) {
        assertEquals(index++, image.pixelIndex(row, col));
      }
    }
  }

  /**
   * Tests a given image to ensure that {@code pixelPosn} returns the proper row and column given a
   * pixel index.
   *
   * @param image the image to test
   */
  private void testPixelPosn(Image image) {
    int row = 0;
    int col = 0;

    for (int i = 0; i < image.bitmapSize() / 3; i++) {
      int[] pixelPosn = image.pixelPosn(i);
      assertEquals(row, pixelPosn[0]);
      assertEquals(col, pixelPosn[1]);

      // If it's the last item in the column reset to 0, otherwise increment
      col = (col == image.width() - 1) ? 0 : col + 1;

      if (col == 0) {
        row++;
      }
    }
  }

}