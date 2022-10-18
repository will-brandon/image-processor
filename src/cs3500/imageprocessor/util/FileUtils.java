package cs3500.imageprocessor.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import cs3500.imageprocessor.util.image.Image;
import cs3500.imageprocessor.util.image.RGBImage;

/**
 * This class contains utility methods to read and write a PPM image from or to a file.
 */
public final class FileUtils {

  private static final String PPM_FILE_BRANDING = ""
          + "# Created by Image Processor" + System.lineSeparator()
          + "# Image Processor application by Will Brandon and Ella Isgar, Northeastern University";

  /**
   * Reads an image file and returns an {@link Image} object. Assumes the image file type based on
   * the extension in the pathname.
   *
   * @param pathname the path string of the file
   * @return the image object read from the file
   * @throws IOException              if the given path is invalid or if the read operation fails
   * @throws IllegalArgumentException if the pathname is null
   */
  public static Image readImage(String pathname) throws IOException,
          IllegalArgumentException {
    if (pathname == null) {
      throw new IllegalArgumentException("Pathname to image cannot be null");
    }

    String format = fileExtension(pathname);

    if (format.isBlank()) {
      throw new IllegalArgumentException("Could not determine file format from given pathname: "
              + pathname);
    }

    if (format.equals("ppm")) {
      return readPPMImage(pathname);
    }

    BufferedImage bufferedImage;
    try {
      bufferedImage = ImageIO.read(new File(pathname));
    } catch (IOException exception) {
      throw new IOException("Failed to read image with the format \"" + format + "\"");
    }

    if (bufferedImage == null) {
      throw new IOException("The given file was not able to be read as an image");
    }

    int pixelLength = (bufferedImage.getAlphaRaster() == null) ? 3 : 4;
    int pixelCount = bufferedImage.getWidth() * bufferedImage.getHeight();

    int[] data = new int[pixelCount * pixelLength];
    int[] bitmap = new int[pixelCount * 3];

    bufferedImage.getRaster().getPixels(0, 0, bufferedImage.getWidth(),
            bufferedImage.getHeight(), data);

    if (pixelLength == 4) {

      for (int i = 0; i < pixelCount * 4; i += 4) {
        int bitmapIndex = i / pixelLength * 3;

        bitmap[bitmapIndex] = data[i];
        bitmap[bitmapIndex + 1] = data[i + 1];
        bitmap[bitmapIndex + 2] = data[i + 2];
      }

    } else {
      bitmap = data;
    }

    return new RGBImage(bufferedImage.getWidth(), bufferedImage.getHeight(), bitmap);
  }

  /**
   * Writes an image file in the given format from an {@link Image} object. Assumes the image file
   * type based on the extension in the pathname.
   *
   * @param pathname the path string of the file
   * @param image    the image to write
   * @throws IOException              if the given path is invalid or if the write operation fails
   * @throws IllegalArgumentException if the image or pathname is null
   */
  public static void writeImage(String pathname, Image image) throws IOException,
          IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (pathname == null) {
      throw new IllegalArgumentException("Pathname to image cannot be null");
    }

    String format = fileExtension(pathname);

    if (format.isBlank()) {
      throw new IllegalArgumentException("Could not determine file format from given pathname: "
              + pathname);
    }

    if (format.equals("ppm")) {
      writePPMImage(image, pathname);
      return;
    }

    try {
      writeImage(pathname, image.toBufferedImage(), format);

    } catch (FileNotFoundException exception) {

      throw new IllegalArgumentException("Directory path does not exist to save file to: "
              + pathname);
    }
  }

  private static void writeImage(String pathname, BufferedImage bufferedImage, String format)
          throws IOException, IllegalArgumentException {
    File file = new File(pathname);

    // JPEGs have compression enabled that must be manually disabled or else the write operation
    // will lower the quality of the image.
    if (format.equals("jpg") || format.equals("jpeg")) {

      ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();

      ImageWriteParam compressionParam = jpgWriter.getDefaultWriteParam();
      compressionParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
      // Setting max quality
      compressionParam.setCompressionQuality(1.0f);

      ImageOutputStream outputStream = new FileImageOutputStream(file);

      jpgWriter.setOutput(outputStream);

      jpgWriter.write(null,
              new IIOImage(bufferedImage, null, null), compressionParam);

      jpgWriter.dispose();
    } else {
      boolean success = ImageIO.write(bufferedImage, format, file);

      if (!success) {
        throw new IOException("Failed to write image with the format \"" + format + "\"");
      }
    }
  }

  /**
   * Returns the extension of the given pathname. If the pathname contains no "." characters or if
   * "." is at the very end not followed by an extension, an empty string is returned.
   *
   * @param pathname the pathname to pull an extension from
   * @return the extension or an empty string if no extension is present
   * @throws IllegalArgumentException if the given pathname is null
   */
  private static String fileExtension(String pathname) throws IllegalArgumentException {
    if (pathname == null) {
      throw new IllegalArgumentException("Pathname cannot be null");
    }

    int indexOfDot = pathname.indexOf(".");

    if (indexOfDot == -1 || indexOfDot == pathname.length() - 1) {
      return "";
    }

    String[] tokens = pathname.split("\\.");

    return tokens[tokens.length - 1];
  }

  /**
   * Reads an image file in the PPM format and returns an {@link Image} object.
   *
   * @param pathname the path string of the file
   * @return the image object read from the file
   * @throws IOException              if the given path is invalid or if the read operation fails
   * @throws IllegalArgumentException if the pathname is null
   */
  private static Image readPPMImage(String pathname) throws IOException, IllegalArgumentException {
    if (pathname == null) {
      throw new IllegalArgumentException("Pathname to PPM image cannot be null");
    }

    Scanner scanner = new Scanner(new FileInputStream(pathname));

    StringBuilder stringBuilder = new StringBuilder();

    // Read the file line by line, and populate a string
    while (scanner.hasNextLine()) {
      String line = scanner.nextLine();

      // Ignore lines with comments
      if (line.length() > 0 && line.charAt(0) != '#') {
        stringBuilder.append(line + System.lineSeparator());
      }
    }

    // Create a new scanner to read from the string just built
    scanner = new Scanner(stringBuilder.toString());

    // Make sure file starts with "P3"
    String firstToken = scanner.next();
    if (!firstToken.equals("P3")) {
      throw new IOException("Invalid PPM file: plain RAW file should begin with P3");
    }

    // Retrieve the image meta-data
    int width = scanner.nextInt();
    int height = scanner.nextInt();
    int maxValue = scanner.nextInt();

    int elementCount = width * height * 3;
    int[] bitmap = new int[elementCount];

    // Load all each item (channel) into the array
    for (int i = 0; i < elementCount; i++) {
      int item = scanner.nextInt();
      if (item > maxValue) {
        throw new IOException("Invalid PPM file: found item larger than the given max value (found "
                + item + " but expected no larger than " + maxValue + ")");
      }
      bitmap[i] = item;
    }

    return new RGBImage(width, height, bitmap);
  }

  /**
   * Writes an image to a PPM file given an {@link Image} object.
   *
   * @param image    the image to write
   * @param pathname the path string of the file
   * @throws IOException              if the given path is invalid or if the write operation fails
   * @throws IllegalArgumentException if the image or pathname is null
   */
  private static void writePPMImage(Image image, String pathname) throws IOException,
          IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image cannot be null");
    }
    if (pathname == null) {
      throw new IllegalArgumentException("Pathname to image cannot be null");
    }

    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathname));

    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("P3");
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(PPM_FILE_BRANDING);
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(image.width());
    stringBuilder.append(" ");
    stringBuilder.append(image.height());
    stringBuilder.append(System.lineSeparator());
    stringBuilder.append(image.channelMax());
    stringBuilder.append(System.lineSeparator());

    image.bitmapStream().forEach(element -> {
      stringBuilder.append(element);
      stringBuilder.append(System.lineSeparator());
    });

    writer.write(stringBuilder.toString());

    writer.close();
  }

}
