package cs3500.imageprocessor.model.filter;

import cs3500.imageprocessor.util.image.Image;

/**
 * This interface represents a filter function performed on a given image.
 */
public interface ImageFilter {

  /**
   * Applies this filter to the given image and outputs the filtered image.
   *
   * @param input the image to perform this filter function on.
   * @return the output image with the filter applied
   */
  Image perform(Image input);

  /**
   * Determine whether this filter supports masking.
   *
   * @return true if this filter supports masking
   */
  boolean supportsMask();

}
