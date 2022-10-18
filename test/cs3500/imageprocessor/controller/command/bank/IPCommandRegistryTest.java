package cs3500.imageprocessor.controller.command.bank;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import cs3500.imageprocessor.model.IPModel;
import cs3500.imageprocessor.model.IPStandardModel;
import cs3500.imageprocessor.view.text.IPInteractiveView;
import cs3500.imageprocessor.view.IPView;
import util.mock.MockAppendable;

import static org.junit.Assert.fail;
import static util.TestUtils.assertException;
import static util.TestUtils.assertFileExistsAndRemove;

/**
 * This class performs tests on the {@link IPCommandRegistry} class.
 */
public class IPCommandRegistryTest {

  // The constructor is effectively tested in almost every one of the other tests in this class

  @Test
  public void constructFail() {
    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    assertException(IllegalArgumentException.class,
            "Command registry cannot be given a null model",
        () -> new IPCommandRegistry(null, placeholderView));
    assertException(IllegalArgumentException.class,
            "Command registry cannot be given a null view",
        () -> new IPCommandRegistry(placeholderModel, null));
  }

  @Test
  public void executeIllegalArgs() {
    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    IPCommandRegistry registry = new IPCommandRegistry(placeholderModel, placeholderView);

    assertException(IllegalArgumentException.class, "Command arguments cannot be null",
        () -> registry.execute(null));
    assertException(IllegalArgumentException.class,
            "Cannot execute command with no arguments given",
        () -> registry.execute(new ArrayList<String>()));
  }

  @Test
  public void executeNonexistentCommand() {
    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    IPCommandRegistry registry = new IPCommandRegistry(placeholderModel, placeholderView);

    assertException(IllegalArgumentException.class, "Cannot execute command with blank name",
        () -> registry.execute(List.of("", "param1...", "param2...")));
    assertException(IllegalArgumentException.class, "Cannot execute command with blank name",
        () -> registry.execute(List.of("    ", "param1...", "param2...")));
    assertException(IllegalArgumentException.class, "Cannot execute command with blank name",
        () -> registry.execute(List.of(System.lineSeparator(), "param1...", "param2...")));

    assertException(NoSuchElementException.class, "Given command does not exist: hello",
        () -> registry.execute(List.of("hello")));
    assertException(NoSuchElementException.class, "Given command does not exist: hola",
        () -> registry.execute(List.of("hola", "what's", "up")));
    assertException(NoSuchElementException.class, "Given command does not exist: testing.....",
        () -> registry.execute(List.of("testing.....", "::::", "8")));
  }

  @Test
  public void successfulCommand() {
    IPModel placeholderModel = new IPStandardModel();
    IPView placeholderView = new IPInteractiveView(new MockAppendable());

    IPCommandRegistry registry = new IPCommandRegistry(placeholderModel, placeholderView);

    try {
      registry.execute(List.of("load", "testresources/smart.jpg", "smart"));
      registry.execute(List.of("brighten", "100", "smart", "smart-bright"));
      registry.execute(List.of("save", "testresources/smart-bright.jpg", "smart-bright"));
    } catch (IOException exception) {
      fail("Unexpected exception thrown in test: " + exception.getMessage());
    }

    // PLEASE NOTE: THIS TEST WILL FAIL IF THE PROJECT DOES NOT HAVE A "testresources" DIRECTORY
    // CONTAINING A "smart.jpg" IMAGE (PREFERABLY AN IMAGE OF MARCUS SMART BECAUSE HE'S NASTY)

    assertFileExistsAndRemove("testresources/smart-bright.jpg");
  }

}