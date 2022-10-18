# Image Processor (v3)
#### By Will Brandon and Ella Isgar

### NOTE: Find screen shot of program with an image loaded in the res folder

### All Supported Commands
**Note:** The application tracks images using id strings. All filtering commands save the filtered image under a
new id. To overwrite the original tracked copy enter the same id. (Ex. <code>red panda-image panda-image</code>)
| Command           | Usage                                            | Effect                                         |
| :---------------- | :----------------------------------------------- | :--------------------------------------------- |
| quit / q          | <code>quit</code> / <code>q</code>               | Quits the application                          |
| load              | <code>load [path] [id] [format]</code>           | Loads an image from a file                     |
| save              | <code>save [path] [id] [format]</code>           | Saves an image to a file                       |
| horizontal-flip   | <code>horizontal-flip [from id] [to id]</code>   | Flips image horizontally                       |
| vertical-flip     | <code>vertical-flip [from id] [to id]</code>     | Flips image vertically                         |
| red               | <code>red [from id] [to id]</code>               | Greyscales the image using red channel         |
| green             | <code>green [from id] [to id]</code>             | Greyscales the image using green channel       |
| blue              | <code>blue [from id] [to id]</code>              | Greyscales the image using blue channel        |
| intensity         | <code>intensity [from id] [to id]</code>         | Greyscales the image using intensity           |
| luma              | <code>luma [from id] [to id]</code>              | Greyscales the image using luma                |
| max-value         | <code>max-value [from id] [to id]</code>         | Greyscales the image using max value           |
| brighten          | <code>brighten [value] [from id] [to id]</code>  | Brightens the image by a given value           |
| sepia             | <code>sepia [value] [from id] [to id]</code>     | Applies sepia kernel values to the given image |
| greyscale         | <code>greyscale [value] [from id] [to id]</code> | Greyscales the image                           |
| sharpen           | <code>sharpen [value] [from id] [to id]</code>   | Sharpens the image                             |
| blur              | <code>blur [value] [from id] [to id]</code>      | Blurs the image                                |
| ... [maskedImage] | <code>[filter] [from id] [mask id] [to id]</code>| Applies filter using mask                      |

## Design Overview
The application is designed using the MVC pattern. It currently supports three forms of input; the user can specify a script file full of
commands to execute, individual commands can be dispatched from an interactive shell prompt, or, as the default with no flags given, 
opens a Graphical User Interface that includes a histogram to reflect the values of the current images channels. Depending on the given view,
commands will either be executed via the GUI or TextView to load images, perform various filters, and then save the images.
<br>

This COMPLETE Image Processor Java application is started from the main **ImageProcessor** class. From this starting point, the functionality is
distributed over 4 major packages: model, view, controller, and util.

### Model
The model tracks working images the application has loaded. It handles image filtering operations on its tracked images. It does *not*
deal with any file paths or user input.

### View
The GUI View of the parent View displays a Graphical User Interface that allows user interaction with the program to trigger ActionEvents, 
and display the model's data via a live histogram.
The TextView of the parent View handles displaying any text feedback from the application's run cycle to the user as well as prompting them to supply more input.

### Controller
The controller recieves commands that have either come from a script file or from commands passed in via the user to the View (in any format)
and passes them to command objects accordingly. The command objects then ask the model to perform the appropriate operations based on the command.
The view is instructed by the command to display useful feedback.

### Util
The utility classes supply support for reading and writing images to and from the file system in various formats. The controller makes use
of these classes as needed to load in or save images.

## Changelog for v3
 * Added support for the Graphical User Interface, creating the Image Processor Frame to represent the GUI window.
 * Added support for the implementation of the GUI, including a Histogram (class) that handles RGBImages, a Frame (class)
that utilizes the JSwing library to represent every element of the GUI, a GUI View (class) that handles the backend
components / actions of the GUI, and a Controller (class) to handle commands coming from a single GUI window 
 * Added support for EXTRA CREDIT: Partial Image Manipulation. Successfully implemented with by adding a MaskedFilter to
the filter bank, adding a MaskedCommand into our CommandRegistry, and testing results with different filters (see 
test/cs3500/imageprocessor/model/filter/MaskedFilterTest.java for reference)

## Class Overview
**Note:** Most application-specific classes are prefixed with "IP" which stands for Image Processor.

### Main
**ImageProcessor** - The class that begins the application.

### Model
**IPModel** - Represents a model for the image processor application.<br>
**IPStandardModel** - Represents a standard implementation of the model for the image processor application.<br>
**ImageFilter** - Represents a filter function performed on a given image.<br>
**KernelFilter** - Represents a filter that applies a given kernel matrix transformation to every pixel. <br>
**CommonFilterMatrices** - Stores static common matrices for kernel and color transform filters (incl. greyscale, sepia, blur, and sharpen). <br>
**FilterUtil** - Represents utilities for the filter classes incl. a reference between a String filter command and ImageFilter object. <br>
**MaskedFilter** - Represents a filter that applyies a given image manipulation filter to only a masked region of an image. <br>
**ChannelModFilter** - Represents a filter that modifies a single pixel channel.<br>
**ColorTransformationFilter** - Represents a filter that modifies a single pixel channel based off of a set kernel of constant values.<br>
**BrightnessFilter** - Represents a filter that modifies the brightenss value of every pixel.<br>
**IntensityFilter** - Represents a filter that greyscales every pixel by intensity.<br>
**LumaFilter** - Represents a filter that greyscales every pixel by luma.<br>
**MaxValueFilter** - Represents a filter that greyscales every pixel by max value.<br>
**RGBFilter** - Represents a filter that greyscales every pixel by a given color channel.<br>
**MirrorFilter** - Represents a filter that reflects each pixel about a given axis.<br>
**HorizontalFlipFilter** - Represents a filter that reflects an image about the y-axis.<br>
**VerticalFlipFilter** - Represents a filter that reflects an image about the x-axis.<br>

### View
**IPView** - Represents a view for the Image Processor application.<br>
**IPFrame** - Represents a GUI frame (window) for the Image Processor application <br>
**ImagePanel** - Represents an Image as an extension of the JPanel <br>
**IPSwingFrame** - Represents a Swing GUI frame (window) for the Image Processor application. <br>
**IPSwingGUIView** - Represents a view for the IP application that uses a GUI built on Java's Swing library <br>
**RGBChannelHistogram** - Represents a Histogram of the IP's current image as an extension of the JPanel <br>
**IPTextView** - Represents a text-based view for the image processor application. <br>
**IPAppendableView** - Represents an Image Processor application view that appends text logs to an appendable object.<br>
**IPLoggingView** - Represents an Image Processor application view that logs messages to an appendable object.<br>
**IPInteractiveView** - Represent an interactive view for an Image Processor application.<br>
**IPPreloadedView** - Represents a preloaded script view for an Image Processor application.<br>

### Controller
**IPController** - Represents a controller for the Image Processor application.<br>
**IPParsingController** - Represents a controller that parses input from a readable input source.<br>
**IPSingleWindowController** - Represents a controller that interacts with a single graphical user interface window view <br>
**IPCommandBank** - Represents a store of commands that can be run with the opportunity to execute one.<br>
**IPCommandRegistry** - Represents a store of all registered commands for the Image Processor application.<br>
**IPCommand** - Represents a command for the Image Processor application.<br>
**IPModelCommand** - Represents a command that operates on the model.<br>
**IPLoadCommand** - Represents a command that loads a file.<br>
**IPSaveCommand** - Represents a command that saves a file.<br>
**IPFilterCommand** - Represents a command that performs a filter on an image.<br>
**IPBrightenCommand** - Represents a command that performs a brighten filter on an image.<br>
**IPMaskedCommand** - Represents a command that performs a mask filter.

### Util
**FileUtils** - Holds static utility fuctions to read and write a PPM image from or to a file.<br>
**Image** - Represents a graphical image.<br>
**ChanneledImage** - Represents an image that can be reprseented by its pixel channels.<br>
**RGBImage** - Represents an image with only three channels; red, green, and blue.<br>
