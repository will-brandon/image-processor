package cs3500.imageprocessor.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import cs3500.imageprocessor.controller.command.bank.IPCommandBank;
import cs3500.imageprocessor.controller.command.bank.IPCommandRegistry;
import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.view.text.IPTextView;

/**
 * This class represents a controller that parses input from a {@link Readable} input source.
 */
public class IPParsingController implements IPController {

  private final IPTextView view;
  private final Scanner lineScanner;
  private final IPCommandBank commandBank;
  private boolean applicationIsRunning;

  /**
   * Creates a new controller for the Image Processor application.
   *
   * @param model       the model to use
   * @param view        the view to transmit output to
   * @param inputSource the {@link Readable} source of user input
   * @throws IllegalArgumentException if the model, view, or input source are null
   */
  public IPParsingController(IPModel model, IPTextView view, Readable inputSource)
          throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Model given to IPStandardController cannot be null");
    }
    if (view == null) {
      throw new IllegalArgumentException("View given to IPStandardController cannot be null");
    }
    if (inputSource == null) {
      throw new IllegalArgumentException("Readable input source given to IPStandardController "
              + "cannot be null");
    }

    this.view = view;
    lineScanner = new Scanner(inputSource);
    commandBank = new IPCommandRegistry(model, view);
    applicationIsRunning = false;
  }

  /**
   * Begins a new application life cycle. While the application is running, the user will be
   * continually prompted for commands until the application is issued a quit command.
   *
   * @throws IOException if this controller fails to transmit to the view
   */
  @Override
  public void begin() throws IOException {
    applicationIsRunning = true;
    view.start();

    while (applicationIsRunning) {
      List<String> commandArgs;
      try {
        commandArgs = promptForCommand();
      } catch (NoSuchElementException exception) {
        applicationIsRunning = false;
        continue;
      }

      // NOTE: Instead of doing a big ugly nested try-catch and if tree, guard clauses are used to
      // simply continue to the next loop cycle if certain conditions are not met

      if (commandArgs.size() < 1) {
        // No error message will be displayed, it is perfectly legal to not enter a command or just
        // enter a comment, which is the same as not entering a command.
        continue;
      }

      if (commandArgs.get(0).equalsIgnoreCase("q")
              || commandArgs.get(0).equalsIgnoreCase("quit")) {
        applicationIsRunning = false;
        view.display("Quitting the Image Processor");
        continue;
      }

      try {
        commandBank.execute(commandArgs);
      } catch (Exception exception) {
        view.displayError(exception.getMessage());
      }
    }

    lineScanner.close();
    view.dismiss();
  }

  /**
   * Uses the view to prompt the user for a command, then parses the given command into its
   * arguments and returns them as a list of strings. Returns an empty list if no arguments were
   * given. Extra whitespace and comments are ignored.
   *
   * @return the parsed command arguments
   * @throws IOException if the view prompt transmission fails
   * @throws NoSuchElementException if there are no more lines to be read
   */
  private List<String> promptForCommand() throws IOException {
    view.promptInput();

    try {
      String line = lineScanner.nextLine();

      if (line.isBlank()) {
        return new ArrayList<>();
      }

      // Cannot simply use line.split method because this will yield many extra empty elements if
      // there are extra whitespace characters. The parseArgumentsFromCommand method will ensure all
      // extra whitespace is ignored.
      return parseArgumentsFromCommand(line);

    } catch (NoSuchElementException exception) {
      throw new NoSuchElementException("Failed to read next line from the input source");
    }
  }

  /**
   * Breaks the given command string into its individual arguments using whitespace, ignores any
   * extra whitespace and comments.
   *
   * @param command the command string to parse
   * @return the parsed arguments
   */
  private List<String> parseArgumentsFromCommand(String command) {
    Scanner argumentScanner = new Scanner(new StringReader(command));

    List<String> args = new ArrayList<>();

    while (argumentScanner.hasNext()) {
      String arg = argumentScanner.next();

      // As soon as the '#' comment symbol is found, the rest of the line must be comment
      if (arg.contains("#")) {
        String[] hashtagSplit = arg.split("#");

        if (hashtagSplit.length > 0) {
          args.add(hashtagSplit[0]);
        }

        // At this point break from the loop because the rest of the line is comment
        break;
      }

      args.add(arg);
    }

    argumentScanner.close();

    return args;
  }

}
