package cs3500.imageprocessor.model;

import java.util.HashMap;
import java.util.NoSuchElementException;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;

/**
 * This class represents a standard implementation of the model for the image processor application.
 */
public class IPStandardModel implements IPModel {

  private HashMap<String, Image> imageRegistry;

  /**
   * Creates a new standard model, initializing an empty imageRegistry to track images using id
   * strings.
   */
  public IPStandardModel() {
    this.imageRegistry = new HashMap<String, Image>();
  }

  @Override
  public boolean containsImage(String imageId) throws IllegalArgumentException {
    if (imageId == null) {
      throw new IllegalArgumentException("Cannot find an image with a null id");
    }

    return imageRegistry.containsKey(imageId);
  }

  @Override
  public void acceptImage(Image image, String imageId) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Can not accept NULL for image.");
    }
    if (imageId == null) {
      throw new IllegalArgumentException("Can not accept NULL for imageID.");
    }

    this.imageRegistry.put(imageId, image);
  }

  @Override
  public Image getImage(String imageId) throws IllegalArgumentException, NoSuchElementException {
    if (imageId == null) {
      throw new IllegalArgumentException("The given ID can not be NULL.");
    }
    if (imageRegistry.size() < 1) {
      throw new NoSuchElementException("This model's image registry is empty.");
    }
    if (!imageRegistry.containsKey(imageId)) {
      throw new NoSuchElementException("No such image with id " + imageId);
    }

    return this.imageRegistry.get(imageId);
  }

  @Override
  public void applyFilter(ImageFilter filter, String originalImageId, String producedImageId)
          throws IllegalArgumentException, NoSuchElementException {
    if (filter == null) {
      throw new NoSuchElementException("Given filter does not exist");
    }
    if (originalImageId == null) {
      throw new IllegalArgumentException("Original image ID can not be NULL");
    }
    if (producedImageId == null) {
      throw new IllegalArgumentException("New image ID can not be NULL");
    }
    if (this.imageRegistry.get(originalImageId) == null) {
      throw new NoSuchElementException("Image's ID is NULL in this imageRegistry.");
    }

    Image filteredImage = filter.perform(this.imageRegistry.get(originalImageId));
    this.imageRegistry.put(producedImageId, filteredImage);
  }

}