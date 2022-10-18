package cs3500.imageprocessor.model;

import java.util.NoSuchElementException;

import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.util.image.Image;

/**
 * This interface represents a model for the image processor application.
 */
public interface IPModel {

  /**
   * Determines whether this model is tracking an image with the given id.
   *
   * @param imageId the id of the image to check for
   * @return true if and only if this model is tracking an image with the given id
   * @throws IllegalArgumentException if the given id is null
   */
  boolean containsImage(String imageId) throws IllegalArgumentException;

  /**
   * Tells this model to begin tracking a new image and reference it with the given id. If the id
   * already exists, the image is overwritten.
   *
   * @param image   the image to start
   * @param imageId the id to use to reference the image
   * @throws IllegalArgumentException if the given image or id is null
   */
  void acceptImage(Image image, String imageId) throws IllegalArgumentException;

  /**
   * Retrieves an image this model is tracking given its id.
   *
   * @param imageId the id of the image to retrieve.
   * @return the image
   * @throws IllegalArgumentException if the given id is null
   * @throws NoSuchElementException   if there is no image tracked under the given id
   */
  Image getImage(String imageId) throws IllegalArgumentException, NoSuchElementException;

  /**
   * Applies the given filter to the specified image and creates a new tracked image under the given
   * id. If the id already exists, the image is overwritten.
   *
   * @param filter          the filter to apply
   * @param originalImageId the id of the image input into the filter
   * @param producedImageId the id to give the newly created filtered output image
   * @throws IllegalArgumentException if the filter, original image id, or produces image id is null
   * @throws NoSuchElementException   if there is no image tracked under the given original image id
   */
  void applyFilter(ImageFilter filter, String originalImageId, String producedImageId)
          throws IllegalArgumentException, NoSuchElementException;

}
