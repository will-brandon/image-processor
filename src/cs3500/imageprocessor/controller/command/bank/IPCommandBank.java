package cs3500.imageprocessor.controller.command.bank;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This interface represents a bank of all commands currently supported by the Image Processor
 * application. It can accept commands and execute them.
 */
public interface IPCommandBank {

  /**
   * Accepts a command in the form of arguments, finds the proper command (from the first argument),
   * and executes it.
   *
   * @param commandArgs the arguments of the command
   * @throws NoSuchElementException   if the command found in the first argument does not exist
   * @throws IOException              if the command throws an IOException
   * @throws IllegalArgumentException if invalid arguments are given
   */
  void execute(List<String> commandArgs) throws NoSuchElementException, IOException,
          IllegalArgumentException;

}
