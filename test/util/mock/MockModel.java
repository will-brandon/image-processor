package util.mock;


import java.util.NoSuchElementException;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;

import static util.TestableItems.IMG5X3;

/**
 * This class represents a mock of an Image Processor model. All method calls are logged.
 */
public class MockModel extends LoggedMock implements IPModel {

  @Override
  public boolean containsImage(String imageId) throws IllegalArgumentException {
    this.log.add("containsImage(" + imageId + ")");
    return false;
  }

  @Override
  public void acceptImage(Image image, String imageId) throws IllegalArgumentException {
    this.log.add("acceptImage(" + image.toString() + ", " + imageId + ")");
  }

  @Override
  public Image getImage(String imageId) throws IllegalArgumentException, NoSuchElementException {
    this.log.add("getImage(" + imageId + ")");
    return IMG5X3;
  }

  @Override
  public void applyFilter(ImageFilter filter, String originalImageId, String producedImageId)
          throws IllegalArgumentException, NoSuchElementException {
    this.log.add("applyFilter(" + filter.getClass().getSimpleName() + ", " + originalImageId + ", "
            + producedImageId + ")");
  }

}
