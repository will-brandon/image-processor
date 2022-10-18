package util;

import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * This class holds static fields with items that can be used but <strong>not modified</strong> in
 * assertion testing.
 */
public final class TestableItems {

  public static final int[] IMG1X1BITMAP;
  public static final int[] IMG5X3BITMAP;

  public static final Image IMG1X1;
  public static final Image IMG5X3;

  public static final String WELCOME_MESSAGE;
  public static final String DISMISSAL_MESSAGE;

  // All constants should be declared above but initialized in the below static block
  static {
    IMG1X1BITMAP = new int[]{255, 0, 0};
    IMG5X3BITMAP = new int[]{
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255,
        255, 255, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 255, 255,
        255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255
    };

    IMG1X1 = new RGBImage(1, 1, IMG1X1BITMAP);
    IMG5X3 = new RGBImage(5, 3, IMG5X3BITMAP);

    WELCOME_MESSAGE = System.lineSeparator()
            + "\033[1;96mWelcome to the Image Processor" + System.lineSeparator()
            + "\033[0mApplication written by Will Brandon and Ella Isgar"
            + System.lineSeparator() + System.lineSeparator()
            + "  \033[1m[ Enter commands to the Image Processor ]\033[0m"
            + System.lineSeparator() + System.lineSeparator();

    DISMISSAL_MESSAGE = System.lineSeparator()
            + "\033[1;92mThank you for using the Image Processor.\033[0m"
            + System.lineSeparator() + System.lineSeparator();
  }

}
