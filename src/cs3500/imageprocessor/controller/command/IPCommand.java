package cs3500.imageprocessor.controller.command;

import java.io.IOException;
import java.util.List;

/**
 * This interface represents a command for the Image Processor application.
 */
public interface IPCommand {

  /**
   * Executes the given command. This is a very broad specification, different implementations may
   * act on various parts of the application.
   *
   * @param params the parameters for the command
   * @throws IOException              if the command causes an IOException
   * @throws IllegalArgumentException if invalid parameters were passed
   */
  void execute(List<String> params) throws IOException, IllegalArgumentException;

  /**
   * Returns the number of parameters this command requires.
   *
   * @return the number of parameters this command requires
   */
  int paramCount();

  /**
   * Returns true if this command can be masked.
   *
   * @return true if and only if the command can support masking
   */
  boolean supportsMask();

}
