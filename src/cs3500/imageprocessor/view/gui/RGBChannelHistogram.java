package cs3500.imageprocessor.view.gui;

import java.util.Arrays;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.RenderingHints;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * An extension of {@link JPanel} that renders a histogram for the color channels and intensity of
 * an RGB image given its bitmap.
 */
public class RGBChannelHistogram extends JPanel {

  // Major divisions are the big labeled lines, minor divisions are the small guiding lines
  private static final int X_MAJOR_DIVISIONS = 17; // 255 / 15 (increment of 15)
  private static final int Y_MAJOR_DIVISIONS = 4;
  private static final int X_MINOR_DIVISIONS = 3;
  private static final int Y_MINOR_DIVISIONS = 3;

  private static final int LEFT_PADDING = 70;
  private static final int TOP_PADDING = 30;
  private static final int RIGHT_PADDING = 30;
  private static final int BOTTOM_PADDING = 70;
  private static final int AXIS_THICKNESS = 1;
  private static final int TICK_LENGTH = 10;

  private static final int LABEL_PADDING = 5;
  private static final float LABEL_FONT_SIZE = 10.0F;

  private static final Color BACKGROUND_COLOR = Color.WHITE;
  private static final Color AXIS_COLOR = Color.BLACK;
  private static final Color GRID_MAJOR_LINE_COLOR = Color.GRAY;
  private static final Color GRID_MINOR_LINE_COLOR = Color.LIGHT_GRAY;
  private static final Color GRID_LABEL_COLOR = Color.BLACK;

  private final int[] redCount;
  private final int[] greenCount;
  private final int[] blueCount;
  private final int[] intensityCount;

  private int maxValue;
  private int xIncrement;
  private int yIncrement;

  private boolean hasData;

  /**
   * Creates a new histogram to render a given RGB bitmap. By default, the histogram has no data
   * until the {@code setBitmap} method is called.
   */
  public RGBChannelHistogram() throws IllegalArgumentException {
    redCount = new int[256];
    greenCount = new int[256];
    blueCount = new int[256];
    intensityCount = new int[256];

    maxValue = 0;
    xIncrement = (int) Math.round(255.0 / X_MAJOR_DIVISIONS);
    yIncrement = 0; // Y-increment is determined by the data set

    hasData = false;
  }

  /**
   * Sets the RGB channel bitmap for this histogram. Giving a null bitmap will reset the histogram
   * to having no data.
   *
   * @param bitmap the bitmap of RGB channels or null to specify no data
   * @throws IllegalArgumentException if the bitmap length is not a multiple of 3
   */
  public void setBitmap(int[] bitmap) {

    if (bitmap == null) {
      hasData = false;
      return;
    }

    if (bitmap.length % 3 != 0) {
      throw new IllegalArgumentException("The length of the given bitmap must be a multiple of 3");
    }

    // If the arrays are not reset, setting a new bitmap will add the counts to the old bitmap
    // counts
    Arrays.fill(redCount, 0);
    Arrays.fill(greenCount, 0);
    Arrays.fill(blueCount, 0);
    Arrays.fill(intensityCount, 0);

    for (int i = 0; i < bitmap.length - 3; i += 3) {

      redCount[bitmap[i]] += 1;
      greenCount[bitmap[i + 1]] += 1;
      blueCount[bitmap[i + 2]] += 1;

      int intensity = (int) Math.round((bitmap[i] + bitmap[i + 1] + bitmap[i + 2]) / 3.0);
      intensityCount[intensity] += 1;
    }


    maxValue = getMaxValue();
    yIncrement = (int) Math.round((double) maxValue / Y_MAJOR_DIVISIONS);

    hasData = true;

    this.repaint();
  }

  /**
   * Determines the maximum value across all four datasets.
   *
   * @return the maximum value across all four datasets
   */
  private int getMaxValue() {
    int maxNum = 0;

    for (int i = 0; i < 256; i++) {
      maxNum = Math.max(maxNum, redCount[i]);
      maxNum = Math.max(maxNum, greenCount[i]);
      maxNum = Math.max(maxNum, blueCount[i]);
      // Intensity will be the average, therefore it will never be greater, there's no need to check
    }

    return maxNum;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // The Graphics2D will just be used to set rendering hints, specifically enabling antialiasing
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    int contentWidth = this.getWidth() - LEFT_PADDING - RIGHT_PADDING;
    int contentHeight = this.getHeight() - TOP_PADDING - BOTTOM_PADDING;

    paintBackground(g, contentWidth, contentHeight);
    paintGridLinesAndLabels(g, contentWidth, contentHeight);
    paintAxes(g, contentWidth, contentHeight);

    if (hasData) {
      paintDataset(g, contentWidth, contentHeight, redCount, Color.RED);
      paintDataset(g, contentWidth, contentHeight, greenCount, Color.GREEN);
      paintDataset(g, contentWidth, contentHeight, blueCount, Color.BLUE);
      paintDataset(g, contentWidth, contentHeight, intensityCount, Color.YELLOW);
    }
  }

  /**
   * Fills the histogram content area with a background color.
   *
   * @param g             the graphics context
   * @param contentWidth  the width of the histogram content area
   * @param contentHeight the height of the histogram content area
   */
  private void paintBackground(Graphics g, int contentWidth, int contentHeight) {
    g.setColor(BACKGROUND_COLOR);
    g.fillRect(LEFT_PADDING, TOP_PADDING, contentWidth, contentHeight);
  }

  /**
   * Draws the horizontal and vertical grid lines with their labels.
   *
   * @param g             the graphics context
   * @param contentWidth  the width of the histogram content area
   * @param contentHeight the height of the histogram content area
   */
  private void paintGridLinesAndLabels(Graphics g, int contentWidth, int contentHeight) {
    // Subtract one from each because geometrically a region evenly divided by n lines will have
    // n - 1 parts if both ends of the region have lines which is the case with this histogram.
    double majorXInterval = (double) contentWidth / X_MAJOR_DIVISIONS;
    double majorYInterval = (double) contentHeight / Y_MAJOR_DIVISIONS;

    double minorXInterval = majorXInterval / X_MINOR_DIVISIONS;
    double minorYInterval = majorYInterval / Y_MINOR_DIVISIONS;

    Font font = g.getFont().deriveFont(LABEL_FONT_SIZE);
    FontMetrics fontMetrics = g.getFontMetrics(font);
    g.setFont(font);

    int labelHeight = fontMetrics.getAscent();
    int labelYOffset = (int) Math.round(labelHeight / 4.0);

    // Vertical grid lines
    for (int majorXIndex = 0; majorXIndex <= X_MAJOR_DIVISIONS; majorXIndex++) {
      int majorX = LEFT_PADDING + (int) Math.round(majorXIndex * majorXInterval);

      // Draw major line
      g.setColor(GRID_MAJOR_LINE_COLOR);
      g.drawLine(majorX, TOP_PADDING, majorX, TOP_PADDING + contentHeight + TICK_LENGTH);

      if (majorXIndex < X_MAJOR_DIVISIONS) {
        // Draw each minor line
        for (int minorXIndex = 0; minorXIndex < X_MINOR_DIVISIONS; minorXIndex++) {
          int minorX = majorX + (int) Math.round((minorXIndex + 1) * minorXInterval);

          g.setColor(GRID_MINOR_LINE_COLOR);
          g.drawLine(minorX, TOP_PADDING, minorX, TOP_PADDING + contentHeight);
        }

        int labelLowerBound = xIncrement * majorXIndex;
        int labelUpperBound = labelLowerBound + xIncrement;

        // Draw line label
        String labelText = hasData ? (labelLowerBound + " - " + labelUpperBound) : "-";

        int labelWidth = fontMetrics.stringWidth(labelText);

        int x = majorX + (int) Math.round((majorXInterval - labelWidth) / 2.0);
        int y = TOP_PADDING + contentHeight + TICK_LENGTH + LABEL_PADDING + labelHeight;

        g.setColor(GRID_LABEL_COLOR);
        g.drawString(labelText, x, y);
      }
    }

    // Horizontal grid lines
    for (int majorYIndex = 0; majorYIndex <= Y_MAJOR_DIVISIONS; majorYIndex++) {
      int majorY = TOP_PADDING + (int) Math.round(majorYIndex * majorYInterval);

      // Draw major line
      g.setColor(GRID_MAJOR_LINE_COLOR);
      g.drawLine(LEFT_PADDING - TICK_LENGTH, majorY, LEFT_PADDING + contentWidth, majorY);

      if (majorYIndex < Y_MAJOR_DIVISIONS) {
        // Draw each minor line
        for (int minorYIndex = 0; minorYIndex < Y_MINOR_DIVISIONS; minorYIndex++) {
          int minorY = majorY + (int) Math.round((minorYIndex + 1) * minorYInterval);

          g.setColor(GRID_MINOR_LINE_COLOR);
          g.drawLine(LEFT_PADDING, minorY, LEFT_PADDING + contentWidth, minorY);
        }
      }

      // Draw line label
      String labelText = hasData
              ? Integer.toString(yIncrement * (Y_MAJOR_DIVISIONS - majorYIndex)) : "-";

      int labelWidth = fontMetrics.stringWidth(labelText);

      g.setColor(GRID_LABEL_COLOR);
      g.drawString(labelText, LEFT_PADDING - TICK_LENGTH - LABEL_PADDING - labelWidth,
              majorY + labelYOffset);
    }
  }

  /**
   * Draws the x and y axes.
   *
   * @param g             the graphics context
   * @param contentWidth  the width of the histogram content area
   * @param contentHeight the height of the histogram content area
   */
  private void paintAxes(Graphics g, int contentWidth, int contentHeight) {
    g.setColor(AXIS_COLOR);

    // X-axis
    g.fillRect(LEFT_PADDING, TOP_PADDING + contentHeight - AXIS_THICKNESS, contentWidth,
            AXIS_THICKNESS);
    // Y-axis
    g.fillRect(LEFT_PADDING, TOP_PADDING, AXIS_THICKNESS, contentHeight);
  }

  /**
   * Draws bars of the given color for 256 different values in the given dataset.
   *
   * @param g             the graphics context
   * @param contentWidth  the width of the histogram content area
   * @param contentHeight the height of the histogram content area
   * @param dataset       the data array (of 256 values) to draw
   * @param color         the color to use to draw the bars
   */
  private void paintDataset(Graphics g, int contentWidth, int contentHeight, int[] dataset,
                            Color color) {

    /*
     * Okay, this gets a little crazy so buckle your seatbelt, here's exactly what's going on here:
     * --------------------------------------------------------------------------------------------
     *
     * Previous bug #1:
     *   Bars would overflow off the chart if their width was rounded and put back to back.
     *
     * Previous bug #2:
     *   Overlapping the bars fixes problem #1 but overlapping transparent shapes of the same color
     *   looks very ugly and sometimes there are gaps instead of overlap which looks even worse.
     *
     * Crazy but perfectly wonderful and logical solution:
     *   Floor the bar width and track the rounding error over time. Every time the rounding error
     *   gets to a full pixel make that bar one wider and reset the rounding error. It all works
     *   fine and dandy now, no one even notices that some bars are one pixel wider. :)
     */

    double actualWidth = contentWidth / 256.0; // The ideal width of a bar
    int idealWidth = (int) Math.floor(actualWidth);
    double errorMargin = actualWidth - idealWidth;

    double accumulatedRoundingError = 0.0;

    int xAccumulator = LEFT_PADDING;

    g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 75));

    for (int i = 0; i < 256; i++) {
      int width = idealWidth;

      if (accumulatedRoundingError >= 1.0) {
        width = idealWidth + 1;
        accumulatedRoundingError -= 1.0;
      }

      accumulatedRoundingError += errorMargin;

      int height = (int) Math.round(((double) dataset[i] / maxValue) * contentHeight);
      int y = TOP_PADDING + contentHeight - height;

      g.fillRect(xAccumulator, y, width, height);

      xAccumulator += width;
    }
  }

}
