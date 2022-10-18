package cs3500.imageprocessor.model.filter;

/**
 * This class stores common matrices for kernel and color transform filters.
 */
public class CommonFilterMatrices {

  public static final double[][] BLUR_KERNEL = new double[][]
      {{0.0625, 0.125, 0.0625}, {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};
  public static final double[][] SHARPEN_KERNEL = new double[][]
      {{-0.125, -0.125, -0.125, -0.125, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, 0.25, 1.0, 0.25, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125},
      {-0.125, -0.125, -0.125, -0.125, -0.125}};
  
  public static final double[][] GREYSCALE_MATRIX = new double[][]
      {{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};
  public static final double[][] SEPIA_MATRIX = new double[][]
      {{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

}