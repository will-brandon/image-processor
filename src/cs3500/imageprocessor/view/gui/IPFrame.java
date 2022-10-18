package cs3500.imageprocessor.view.gui;

import cs3500.imageprocessor.util.image.Image;

/**
 * This class represents a GUI frame (window) for the Image Processor application.
 */
public interface IPFrame {

  /**
   * Returns the filter acting on the current image.
   *
   * @return the filter (as a {@link String})
   */
  String getSelectedFilter();

  /**
   * Resets selected filter to "No filter selected".
   */
  void resetSelectedFilter();

  /**
   * Updates the data in the RGB image histogram.
   *
   * @param image the image to use for the histogram
   */
  void updateHistogram(Image image);

  /**
   * Sets the window's visibility state.
   */
  void setVisible(boolean visible);

  /**
   * Creates a small popup window and displays the given message.
   *
   * @param title   the title of the small popup window
   * @param message the message to display
   * @param styleId the style id of the popup window from fields in the {@link JOptionPane} class
   */
  void showPopup(String title, String message, int styleId);

  /**
   * Creates a small popup window with a text field and displays the given prompt message. Integers
   * are expected
   *
   * @param message the prompt message to display
   * @param title   the title of the small popup window
   * @throws NumberFormatException if entered string cannot be converted to an integer
   */
  int showIntegerEntryPopup(String message, String title) throws NumberFormatException;

  /**
   * Sets the image that is displayed in the large panel in the window.
   *
   * @param image the image to display
   */
  void setDisplayedImage(Image image);

  /**
   * Sets the image that is displayed in the preview in the inspector panel.
   *
   * @param image the image to display
   */
  void setPreviewImage(Image image);

  /**
   * Creates a new file chooser popup and returns the pathname of the file selected or null if no
   * file was specified (e.g. the file chooser was closed).
   *
   * @return the pathname of the file selected or null if no file was selected
   */
  String showFileChooser(Boolean saveDialog);

}
