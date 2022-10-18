package cs3500.imageprocessor.view.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.filter.FilterUtil;
import cs3500.imageprocessor.model.filter.ImageFilter;
import cs3500.imageprocessor.model.filter.channelmod.BrightnessFilter;
import cs3500.imageprocessor.util.FileUtils;
import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.view.IPView;

import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.PLAIN_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 * This class represents a view for the image processor application that uses a graphical user
 * interface built on Java's Swing library. This GUI view implementation only supports a single
 * window per process.
 */
public class IPSwingGUIView implements IPView, ActionListener {

  private static final String CURRENT_WORKING_IMAGE_ID = "current-working-image";
  private static final String TEMP_IMAGE_ID = "temp-image";

  private final IPModel model;
  private final IPFrame frame;

  /**
   * Creates a new view that uses a GUI window.
   *
   * @param model the model to use
   * @throws IllegalArgumentException if the given model is null
   */
  public IPSwingGUIView(IPModel model) throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    frame = new IPSwingFrame(this, true);
  }

  @Override
  public void actionPerformed(ActionEvent event) {
    switch (event.getActionCommand()) {

      case "load-image":

        String imageFilePathname = frame.showFileChooser(false);

        if (imageFilePathname == null) { // user didn't completely save file
          break;
        }

        try {
          Image image = FileUtils.readImage(imageFilePathname);
          model.acceptImage(image, CURRENT_WORKING_IMAGE_ID);
          model.acceptImage(image, TEMP_IMAGE_ID);

          frame.setDisplayedImage(image);
          frame.setPreviewImage(image);
          frame.updateHistogram(image);
          frame.resetSelectedFilter();

        } catch (IOException exception) {
          displayError(exception.getMessage());
        }

        break;

      case "export-image":

        if (!model.containsImage(CURRENT_WORKING_IMAGE_ID)) {
          break;
        }

        // select file type to save as
        try {
          // Get path name split up to give to SAVE command
          String exportFilePathname = frame.showFileChooser(true);

          // User has canceled saving file
          if (exportFilePathname == null) {
            break;
          }

          Image image = model.getImage(CURRENT_WORKING_IMAGE_ID);
          FileUtils.writeImage(exportFilePathname, image);

        } catch (IOException exception) {
          displayError(exception.getMessage());
        }

        break;

      case "help":
        frame.showPopup("Help",
                "How to use this Image Processor:\n"
                        + "- Press [OPEN IMAGE] to import an image from your computer\n"
                        + "- Select a filter from the drop down filter menu to preview the effect "
                        + "on your image\n"
                        + "- Press [APPLY FILTER] to apply to selected filter to your current image"
                        + "\n- The histogram below reflects the values of the image with any "
                        + "filters in use (permanent or temporary)",
                PLAIN_MESSAGE);

        break;

      case "filter-selected":

        if (!model.containsImage(CURRENT_WORKING_IMAGE_ID)) {
          break;
        }

        Image image = model.getImage(CURRENT_WORKING_IMAGE_ID);

        if (this.frame.getSelectedFilter().equals("No filter selected")) {

          frame.setDisplayedImage(image);
          frame.setPreviewImage(image);
          frame.updateHistogram(image);
          break; // return to the current image

        }

        ImageFilter filter = null;
        String selectedFilterOption = frame.getSelectedFilter();

        if (selectedFilterOption.equals("Brighten")) {
          // Request additional number for amount to brighten image by
          int value = frame.showIntegerEntryPopup("Brightnes Filter Value",
                  "Enter value to modify brightness. \n"
                          + "(Positive value brightens image. Negative value darkens image.)");

          filter = new BrightnessFilter(value);

        } else {
          filter = FilterUtil.filterForName(selectedFilterOption.toLowerCase());
        }

        model.applyFilter(filter, CURRENT_WORKING_IMAGE_ID, TEMP_IMAGE_ID);

        frame.setPreviewImage(model.getImage(TEMP_IMAGE_ID));
        frame.updateHistogram(model.getImage(TEMP_IMAGE_ID));

        break;

      case "apply-filter":

        if (!model.containsImage(CURRENT_WORKING_IMAGE_ID) || !model.containsImage(TEMP_IMAGE_ID)) {
          break;
        }

        // Update current image to match temp filtered image
        model.acceptImage(model.getImage(TEMP_IMAGE_ID), CURRENT_WORKING_IMAGE_ID);
        frame.setDisplayedImage(model.getImage(CURRENT_WORKING_IMAGE_ID));
        frame.setPreviewImage(model.getImage(TEMP_IMAGE_ID));
        frame.resetSelectedFilter();
        frame.updateHistogram(model.getImage(CURRENT_WORKING_IMAGE_ID));

        break;

      default:
        break;
    }
  }

  // PLEASE NOTE: The display and displayError methods are final because they are used within this
  // class and therefore should not be overridden to avoid reimplementation behavioral conflicts
  // within this class.

  /**
   * Opens a display popup in a little window that conveys the given message.
   *
   * @param message the message to display
   */
  @Override
  public final void display(String message) {
    frame.showPopup("Image Processor Application Message", message, INFORMATION_MESSAGE);
  }

  /**
   * Opens a warning display popup in a little window that conveys the given message.
   *
   * @param message the message to display
   */
  @Override
  public final void displayError(String message) {
    frame.showPopup("Image Processor Application Warning", message, WARNING_MESSAGE);
  }

  /**
   * Makes the frame visible.
   */
  @Override
  public void start() {
    frame.setVisible(true);
  }

  /**
   * Makes the frame invisible.
   */
  @Override
  public void dismiss() {
    frame.setVisible(false);
  }

}
