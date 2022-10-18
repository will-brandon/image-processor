package cs3500.imageprocessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cs3500.imageprocessor.controller.IPController;
import cs3500.imageprocessor.controller.IPSingleWindowController;
import cs3500.imageprocessor.controller.IPParsingController;
import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.view.IPView;
import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.text.IPPreloadedView;
import cs3500.imageprocessor.view.gui.IPSwingGUIView;
import cs3500.imageprocessor.view.text.IPTextView;

/**
 * The main class that begins the Image Processor application.
 */
public final class ImageProcessor {

  /**
   * Represents the various run modes supported by the application.
   */
  private enum RunMode { GUI, FILE, TEXT }

  /**
   * Begins the Image Processor application.
   *
   * @param programArguments the command line arguments passed to begin this application
   */
  public static void main(String[] programArguments) {

    try {
      createController(programArguments).begin();
    } catch (Exception exception) {

      // At this point, the view cannot display errors because it may have been the component that
      // failed. The error message must be hardcoded to be thrown to the console. No matter what
      // type of view this application uses the console will be used if the program cannot be
      // launched.
      System.out.println("\033[91mFatal error: " + exception.getMessage() + "\033[0m");
    }
  }

  /**
   * Creates a new {@link IPController} prebuilt with an appropriate model, view, and input source
   * based off of the given command line arguments.
   *
   * @param programArguments the programs command line arguments
   * @return a prebuilt controller
   * @throws IOException              if the given script file was not found or if the program
   *                                  arguments were invalid
   * @throws IllegalArgumentException if the given program arguments array is null
   */
  private static IPController createController(String[] programArguments)
          throws IOException, IllegalArgumentException {
    if (programArguments == null) {
      throw new IllegalArgumentException("Program arguments array cannot be null");
    }

    IPModel model = new IPStandardModel();

    RunMode runMode = parseRunMode(programArguments);

    if (runMode == RunMode.GUI) {

      IPView view = new IPSwingGUIView(model);
      return new IPSingleWindowController(view);
    }

    IPTextView view;
    Readable inputSource;

    if (runMode == RunMode.FILE) {
      // The pathname is guaranteed to not be null because the parseRunMode method ensures the
      // arguments for the file run mode are valid.
      String pathname = programArguments[1];

      File file = new File(pathname);

      if (!file.exists()) {
        throw new FileNotFoundException("Could not find the given script file: " + pathname);
      }

      view = new IPPreloadedView(System.out);
      inputSource = new FileReader(pathname);

    } else {

      view = new IPInteractiveView(System.out);
      inputSource = new InputStreamReader(System.in);
    }

    return new IPParsingController(model, view, inputSource);
  }

  /**
   * Returns the run mode for this start of the Image Processor application. Ensures that only valid
   * command-line arguments are provided.
   *
   * @param programArguments the command-line arguments
   * @return the run mode
   * @throws IOException              if the command-line arguments are invalid
   * @throws IllegalArgumentException if the program arguments are null
   */
  private static RunMode parseRunMode(String[] programArguments)
          throws IOException, IllegalArgumentException {
    if (programArguments == null) {
      throw new IllegalArgumentException("Program arguments array cannot be null");
    }

    if (programArguments.length == 0) {
      return RunMode.GUI;
    }

    String firstArg = programArguments[0];

    if (firstArg.equals("-file")) {

      if (programArguments.length == 1) {
        throw new IllegalArgumentException(
                "The \"-file\" flag must be followed by a path argument");
      }

      if (programArguments.length > 2) {
        throw new IllegalArgumentException("The \"-file\" flag must be followed by only one "
                + "argument");
      }

      return RunMode.FILE;
    }

    if (firstArg.equals("-text")) {

      if (programArguments.length > 1) {
        throw new IllegalArgumentException("The \"-text\" flag must be followed no arguments");
      }

      return RunMode.TEXT;
    }

    // If this point is reached, neither the "-file" nor the "-text" flag were given but there is an
    // argument at index 0, so it must be invalid.
    throw new IOException("Illegal flag provided: " + firstArg);
  }

}
