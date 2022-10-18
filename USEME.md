# Image Processor (v3) Usage
<br>

### How to Use GUI
<ul>Press the <button>OPEN BUTTON</button> to load / import an image into the Image Processor</ul>
<ul>Note your newly imported image on the left-hand side of the Image Workspace</ul>
<ul>Preview a filter effect on an image by selecting a filter from the <button>dropdown 
bar</button> in the Inspector Elements Panel</ul>
<ul>Reference the smaller image panel at the bottom of the Image Elements Panel for the effects preview</ul>
<ul>To permanently apply the selected filter, press the <button>APPLY FILTER</button></ul>
<ul>The current histogram kept updated at the bottom of the Image Processor reflects the most 
current image, specifically the image preview inside the Inspector Elements Panel</ul>
<ul>To export the newly modified image (with brilliantly selected and applied filters), select the 
<button>EXPORT IMAGE</button> to enter in the desired file name and select a destination.</ul>
<ul>For a general overview of steps within the Image Processor, select the <button>HELP</button> 
within the General Panel</ul>

## Starting the Image Processor Application
To start the Image Processor, run the "res/ImageProcessor3.jar" file. The Z-Shell "start" script located in the project
root directory is simply an alias for this operation. There are two methods of running the application determined by
the arguments passed to the jar (or Z-Shell "start") file when run. Both an interactive shell and a pre-written script
loader are offered.

### Interactive Shell
To start the interactive shell prompt mode, run the jar with no arguments provided.
#### Example:
<code>java -jar res/ImageProcessor3.jar</code><br>
or<br>
<code>./start</code>

### Load a Script
To run a pre-written script, run the jar with the "-file" flag followed by a path to the script file. See the example
below to run the "res/sample-commands.txt" script that comes along with the application (It will filter some images
and save them to the "image-output" directory).
#### Example:
<code>java -jar res/ImageProcessor3.jar -file res/sample-script.txt</code><br>
or<br>
<code>./start -file res/sample-script.txt</code>

**Note:** If you use "res/sample-script.txt" you can use the "clrimgs" Z-Shell script in the project's root directory
to clear out the generated images in the image-output directory.

### Example Script
Please see the "res" directory. It contains a "sample-commands.txt" with a script of commands to
run. To use these commands, please enter each line of this file as a separate command when prompted by the
application. (Comment lines and empty lines can be ignored but if entered will not cause any errors) The 
"sample-commands" directory comes with a "smart.ppm" image that can be manipulated using these commands.
All generated, filtered images will be saved into the provided "filteredImages" directory.

"original.ppm" image credit: Made by yours truly, Will Brandon
"flower.png" image credit: Made by yours truly, Ella Isgar


### All Supported Commands
**Note:** The application tracks images using id strings. All filtering commands save the filtered image under a
new id. To overwrite the original tracked copy enter the same id. (Ex. <code>red panda-image panda-image</code>)
| Command         | Usage                                            | Effect                                         |
| :-------------- | :----------------------------------------------- | :--------------------------------------------- |
| quit / q        | <code>quit</code> / <code>q</code>               | Quits the application                          |
| load            | <code>load [path] [id] [format]</code>           | Loads an image from a file                     |
| save            | <code>save [path] [id] [format]</code>           | Saves an image to a file                       |
| horizontal-flip | <code>horizontal-flip [from id] [to id]</code>   | Flips image horizontally                       |
| vertical-flip   | <code>vertical-flip [from id] [to id]</code>     | Flips image vertically                         |
| red             | <code>red [from id] [to id]</code>               | Greyscales the image using red channel         |
| green           | <code>green [from id] [to id]</code>             | Greyscales the image using green channel       |
| blue            | <code>blue [from id] [to id]</code>              | Greyscales the image using blue channel        |
| intensity       | <code>intensity [from id] [to id]</code>         | Greyscales the image using intensity           |
| luma            | <code>luma [from id] [to id]</code>              | Greyscales the image using luma                |
| max-value       | <code>max-value [from id] [to id]</code>         | Greyscales the image using max value           |
| brighten        | <code>brighten [value] [from id] [to id]</code>  | Brightens the image by a given value           |
| sepia           | <code>sepia [value] [from id] [to id]</code>     | Applies sepia kernel values to the given image |
| greyscale       | <code>greyscale [value] [from id] [to id]</code> | Greyscales the image                           |
| sharpen         | <code>sharpen [value] [from id] [to id]</code>   | Sharpens the image                             |
| blur            | <code>blur [value] [from id] [to id]</code>      | Blurs the image                                |
| ... maskedImage | <code>[filter] [from id] [mask id] [to id]</code>| Applies filter using mask                      |

### Example Usage
> \# Load a new file with a name<br>
> load res/original.ppm original<br>
> <br>
> \# RGB Filters<br>
> red original red-img<br>
> green original green-img<br>
> blue original blue-img<br>
> <br>
> \# Intensity Filter<br>
>intensity original intensity-img<br>
> <br>
> \# Luma Filter<br>
> luma orignal luma-img<br>
> <br>
> \# Max-value Filter<br>
> max-value original max-value-img<br>
> <br>
> \# Brighten Filter<br>
> brighten 50 original brighten-img<br>
> <br>
> \#Darken Filter (brighten filter with a negative value)<br>
> brighten -50 original darken-img<br>
> <br>
> \# Horizontal Flip Filter<br>
> horizontal-flip original horizontal-flip-img<br>
> <br>
> \# Vertical Flip Filter<br>
> vertical-flip oeiginal vertical-flip-img<br>
> <br>
> \# Sepia Filter<br>
> sepia original sepia-img<br>
> <br>
> \# Greyscale Filter<br>
> greyscale original greyscale-img<br>
> <br>
> \# Sharpen Filter<br>
> sharpen original sharpen-img<br>
> <br>
> \# Blur Filter<br>
> blur original blur-img<br>
> <br>
> \# Save a file<br>
> save image-output/sharpened-image.ppm sharpen-img<br>
