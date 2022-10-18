package cs3500.imageprocessor.model;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.model.filter.channelmod.RGBFilter;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static util.TestUtils.assertException;

/**
 * Test for IPStandardModel.
 */
public class IPStandardModelTest {
  private IPModel model;

  private Image image1;

  private Image image2;

  private Image badImage2;

  private Image image3;

  @Before
  public void setUp() throws Exception {
    this.model = new IPStandardModel();
    //   private HashMap<String, Image> imageRegistry;
    int[] image1Bitmap = new int[]{0, 32, 23, 15, 50, 254, 100, 101, 102, 254, 0, 254, 0, 255, 100,
        100, 220, 13, 200, 200, 200, 0, 23, 32, 1, 1, 1, 10, 20, 30,
        50, 50, 50, 217, 182, 222, 160, 140, 150, 50, 100, 20, 0, 254, 127};
    this.image1 = new RGBImage(5, 3, image1Bitmap);

    int[] image2Bitmap = new int[]{20, 20, 20, 15, 0, 21, 250, 232, 0, 77, 13, 228};
    this.image2 = new RGBImage(2, 2, image2Bitmap);

    int[] badImage2Bitmap = new int[]{20, 20, 20, 15, 0, 21, 250, 232, 0, 77, 13, 228};
    this.badImage2 = new RGBImage(2, 2, image2Bitmap);

    int[] image3Bitmap = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9,
        10, 11, 12, 13, 14, 15, 16, 17, 18,
        19, 20, 21, 22, 23, 24, 25, 26, 27};
    this.image3 = new RGBImage(3, 3, image3Bitmap);
  }

  @Test
  public void testAcceptImage() {
    // Before accepting any images, imageRegistry is empty.
    assertException(NoSuchElementException.class,
            "This model's image registry is empty.",
        () -> this.model.getImage(""));

    // acceptImage can not accept ANY null
    assertException(IllegalArgumentException.class,
            "Can not accept NULL for image.",
        () -> this.model.acceptImage(null, "nullImage"));

    assertException(IllegalArgumentException.class,
            "Can not accept NULL for imageID.",
        () -> this.model.acceptImage(new RGBImage(1, 1, new int[]{1, 2, 3}), null));

    // Accept images 1, 2, and 3
    this.model.acceptImage(this.image1, "image1");
    this.model.acceptImage(this.badImage2, "image2");
    this.model.acceptImage(this.image3, "image3");

    assertEquals(this.image1, this.model.getImage("image1"));
    assertEquals(this.badImage2, this.model.getImage("image2"));
    assertEquals(this.image3, this.model.getImage("image3"));

    // Overwrite badImage2 with image2 using the same imageID
    this.model.acceptImage(this.image2, "image2");
    assertEquals(this.image2, this.model.getImage("image2"));
  }

  @Test
  public void testGetImage() {
    // Before accepting any images, imageRegistry is empty.
    assertException(NoSuchElementException.class,
            "This model's image registry is empty.",
        () -> this.model.getImage(""));

    testAcceptImage();

    assertEquals(this.image1, this.model.getImage("image1"));
    assertEquals(this.image2, this.model.getImage("image2"));
    assertEquals(this.image3, this.model.getImage("image3"));

    // The case when the given imageID does not exist / is NULL.
    assertException(NoSuchElementException.class,
            "No such image with id image4",
        () -> this.model.getImage("image4"));
    assertException(IllegalArgumentException.class,
            "The given ID can not be NULL.",
        () -> this.model.getImage(null));
  }

  @Test
  public void testApplyFilter() {
    // No field of applyFilter may be NULL.
    assertException(NoSuchElementException.class,
            "Given filter does not exist",
        () -> this.model.applyFilter(null, "", ""));
    assertException(IllegalArgumentException.class,
            "Original image ID can not be NULL",
        () -> this.model.applyFilter(new RGBFilter(0), null, ""));
    assertException(IllegalArgumentException.class,
            "New image ID can not be NULL",
        () -> this.model.applyFilter(new RGBFilter(0), "", null));

    // Filters
    ImageFilter red = new RGBFilter(0);
    Image redOfImage3ByHand = new RGBImage(3, 3,
            new int[]{1, 1, 1, 4, 4, 4, 7, 7, 7,
              10, 10, 10, 13, 13, 13, 16, 16, 16,
              19, 19, 19, 22, 22, 22, 25, 25, 25});
    Image redOfImage3WithClass = red.perform(this.image3);

    assertArrayEquals(redOfImage3ByHand.bitmapStream().toArray(),
            redOfImage3WithClass.bitmapStream().toArray());
  }
}