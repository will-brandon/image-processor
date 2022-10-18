package cs3500.imageprocessor.view.gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import cs3500.imageprocessor.util.image.Image;

/**
 * This class represents a Swing GUI frame (window) for the Image Processor application.
 */
class IPSwingFrame extends JFrame implements IPFrame {

  private static final FileNameExtensionFilter SUPPORTED_IMAGE_FILE_EXTENSION_FILTER =
          new FileNameExtensionFilter("PPM, PNG, JPG/JPEG, BMP",
                  "ppm", "png", "jpg", "jpeg", "bmp");

  private static final String[] FILTER_OPTIONS = new String[]{
      "No filter selected",
      "Horizontal-Flip",
      "Vertical-Flip",
      "Red",
      "Green",
      "Blue",
      "Intensity",
      "Luma",
      "Value",
      "Brighten",
      "Blur",
      "Sharpen",
      "Greyscale",
      "Sepia"
  };

  private final ActionListener actionListener;

  /*
   * PLEASE NOTE: All the GUI component fields are not final because they are initialized in the
   * buildGUI method. This method is called from the constructor but Java does not allow these
   * fields to be final if they are not initialized DIRECTLY in the constructor.
   */

  private JPanel mainPanel;

  private ImagePanel currentImagePanel;

  private JComboBox filterComboBox; // --> filterOptions

  private ImagePanel previewImagePanel;

  private RGBChannelHistogram histogram;

  /**
   * Creates a new GUI frame (window) for the Image Processor application.
   *
   * <p></p><strong>NOTE:</strong> A flag is included to determine whether the program should
   * immediately exit when the user hits the close button. This is in place because certain
   * implementations of the Image Processor controller may support multiple windows running at
   * once. In this case, the program should not exit if just one of the windows is closed.</p>
   *
   * @param actionListener the action listener for all components in this frame
   * @param exitOnClose    a flag determining whether the program should immediately exit when the
   *                       user hits the close button
   */
  IPSwingFrame(ActionListener actionListener, boolean exitOnClose) {
    super();
    this.actionListener = actionListener;

    initPresets(exitOnClose);
    buildMainPanel();
    buildRow1();
    buildRow2();
    buildRow3();
  }

  /**
   * Sets the presets for this frame.
   */
  private void initPresets(boolean exitOnClose) {
    this.setDefaultCloseOperation(exitOnClose ? EXIT_ON_CLOSE : DISPOSE_ON_CLOSE);
    this.setTitle("Image Processor v3 by Ella Isgar and Will Brandon");
    this.setSize(1500, 1000);
    this.setResizable(true);
  }

  /**
   * Builds the main panel of the GUI that contains everything else.
   */
  private void buildMainPanel() {
    // MAIN FRAME
    mainPanel = new JPanel();
    // Layout all elements of the mainPanel from top to bottom
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    // Scroll bars around this main panel
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    this.add(mainScrollPane);
  }

  /**
   * Builds all the components for the first row in the GUI.
   */
  private void buildRow1() {
    // Establish position / layout of all panels
    // ROWS OF MAIN LAYOUT
    JPanel row1Panel = new JPanel();
    row1Panel.setLayout(new BoxLayout(row1Panel, BoxLayout.LINE_AXIS)); // left to right
    row1Panel.setBorder(BorderFactory.createTitledBorder("General"));
    mainPanel.add(row1Panel);

    /*
     * ROW 1 (general) contains:
     *  - Load image BUTTON
     *  - Export image BUTTON
     *  - Help BUTTON
     */

    // ROW 1 (general) COMPONENTS
    JButton loadImageButton = new JButton("Open Image");
    loadImageButton.setActionCommand("load-image");
    loadImageButton.addActionListener(actionListener);
    row1Panel.add(loadImageButton);

    JButton exportImageButton = new JButton("Export Image");
    exportImageButton.setActionCommand("export-image");
    exportImageButton.addActionListener(actionListener);
    row1Panel.add(exportImageButton);

    JButton helpButton = new JButton("Help");
    helpButton.setActionCommand("help");
    helpButton.addActionListener(actionListener);
    row1Panel.add(helpButton);
  }

  /**
   * Builds all the components for the second row in the GUI.
   */
  private void buildRow2() {
    JPanel row2Panel = new JPanel();
    row2Panel.setLayout(new BoxLayout(row2Panel, BoxLayout.LINE_AXIS)); // left to right
    row2Panel.setBorder(BorderFactory.createTitledBorder("Image Workspace"));
    row2Panel.setPreferredSize(new Dimension(1450, 450));
    mainPanel.add(row2Panel);

    /*
     * ROW 2 (workspace) contains 2 panels, side by side:
     *  - Image panel (left) --> The current image
     *  - Inspector panel (right) --> The select filter dropdown, apply filter button
     */

    // Image Panel (left)
    // ROW 2 (workspace) COMPONENTS
    // Image panel
    JPanel imageWorkspacePanel = new JPanel();
    imageWorkspacePanel.setPreferredSize(new Dimension(500, 500));
    row2Panel.add(imageWorkspacePanel);

    currentImagePanel = new ImagePanel(); // By default, no image is shown

    JScrollPane currentImageScrollPane = new JScrollPane(currentImagePanel);
    currentImageScrollPane.setPreferredSize(new Dimension(500, 500));

    imageWorkspacePanel.add(currentImageScrollPane);

    // Inspector Panel (right)
    // Inspector Panel
    // --> imageFilterInfoPanel; // right side
    JPanel inspectorPanel = new JPanel();
    // Top to bottom
    inspectorPanel.setLayout(new BoxLayout(inspectorPanel, BoxLayout.PAGE_AXIS));
    inspectorPanel.setBorder(BorderFactory.createTitledBorder("Inspector Elements"));
    row2Panel.add(inspectorPanel);

    filterComboBox = new JComboBox(FILTER_OPTIONS);
    filterComboBox.setSelectedIndex(0); // Select the first "No filter selected" item to start
    filterComboBox.setActionCommand("filter-selected");
    filterComboBox.addActionListener(actionListener);
    inspectorPanel.add(filterComboBox);

    // Temporary Filter Image thingy ma bob
    previewImagePanel = new ImagePanel();

    JScrollPane previewImageScrollPane = new JScrollPane(previewImagePanel);
    previewImageScrollPane.setPreferredSize(new Dimension(100, 100));
    inspectorPanel.add(previewImageScrollPane);

    JButton applyFilterButton = new JButton("Apply Filter");
    applyFilterButton.setActionCommand("apply-filter");
    applyFilterButton.addActionListener(actionListener);
    inspectorPanel.add(applyFilterButton);
  }

  /**
   * Builds all the components for the third row in the GUI.
   */
  private void buildRow3() {
    // Establish position / layout of all panels
    JPanel row3Panel = new JPanel();
    row3Panel.setLayout(new BoxLayout(row3Panel, BoxLayout.PAGE_AXIS)); // top to bottom
    row3Panel.setPreferredSize(
            new Dimension(this.getWidth() / 2, this.getHeight() / 4));
    mainPanel.add(row3Panel);

    /*
     * ROW 3 contains:
     *  - Histogram
     */
    histogram = new RGBChannelHistogram();
    histogram.setBorder(BorderFactory.createTitledBorder(
            "Red, Green, Blue, and Intensity Histogram"));
    row3Panel.add(histogram);
  }

  public String getSelectedFilter() {
    return FILTER_OPTIONS[filterComboBox.getSelectedIndex()];
  }

  public void resetSelectedFilter() {
    filterComboBox.setSelectedIndex(0);
  }

  @Override
  public void updateHistogram(Image image) {
    histogram.setBitmap(image.bitmapStream().toArray());
  }

  // NOTE: This method does not need to be overridden, it is simply done here as a formality to
  // remind that it is a part of the IPFrame interface that is being implemented in this class.
  @Override
  public void setVisible(boolean visible) {
    super.setVisible(visible);
  }

  @Override
  public void showPopup(String title, String message,  int styleId) {
    JOptionPane.showMessageDialog(this, message, title, styleId);
  }

  @Override
  public int showIntegerEntryPopup(String title, String message) throws NumberFormatException {
    String stringValue = JOptionPane.showInputDialog(this, message, title,
            JOptionPane.QUESTION_MESSAGE);
    return Integer.parseInt(stringValue);
  }

  @Override
  public void setDisplayedImage(Image image) {
    currentImagePanel.setImage(image);
  }

  @Override
  public void setPreviewImage(Image image) {
    previewImagePanel.setImage(image);
  }

  @Override
  public String showFileChooser(Boolean saveDialog) {
    JFileChooser fileChooser = new JFileChooser(".");
    fileChooser.setFileFilter(SUPPORTED_IMAGE_FILE_EXTENSION_FILTER);

    int actionId;

    if (saveDialog) {
      actionId = fileChooser.showSaveDialog(this);
    } else {
      actionId = fileChooser.showOpenDialog(this);
    }

    if (actionId == JFileChooser.APPROVE_OPTION) {
      return fileChooser.getSelectedFile().getAbsolutePath();
    }

    return null;
  }

}
