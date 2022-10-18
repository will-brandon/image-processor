package cs3500.imageprocessor.view.gui;

import java.awt.Graphics;
import javax.swing.JPanel;

import cs3500.imageprocessor.util.image.Image;

/**
 * An extension of {@link JPanel} that renders an image. The image's aspect ratio is maintained. The
 * image will fit snug inside the panel such that no part of the image is hidden and there may be a
 * margin between the image and the edge of the panel.
 */
public class ImagePanel extends JPanel {

  private Image image;

  /**
   * Creates a new panel for an image rendering.
   */
  public ImagePanel() {
    super();

    image = null;
  }

  /**
   * Sets the image to be rendered in this panel. Assigning null will make this panel empty.
   *
   * @param image the image to be rendered, or null to render no image
   */
  public void setImage(Image image) {
    this.image = image;
    this.repaint();
  }

  @Override
  protected void paintComponent(Graphics graphics) {
    super.paintComponent(graphics);

    if (image == null) {
      return;
    }

    // The aspect ratio of the image
    double imageAspectRatio = (double) image.width() / (double) image.height();

    // True if the width of the image should fill the whole panel
    boolean fullWidth = imageAspectRatio >= (double) this.getWidth() / (double) this.getHeight();

    int x = fullWidth ? 0 :
            (int) Math.round((this.getWidth() - (this.getHeight() * imageAspectRatio)) / 2.0);
    int y = !fullWidth ? 0 :
            (int) Math.round((this.getHeight() - (this.getWidth() / imageAspectRatio)) / 2.0);

    int width = fullWidth ? this.getWidth() :
            (int) Math.round(this.getHeight() * imageAspectRatio);
    int height = !fullWidth ? this.getHeight() :
            (int) Math.round(this.getWidth() / imageAspectRatio);

    graphics.drawImage(image.toBufferedImage(), x, y, width, height, null);
  }

}
