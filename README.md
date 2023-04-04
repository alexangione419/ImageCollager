# ImageProcessor

Our Processor uses a Project and a Layer class for the bulk of the processor
The layer data is a list of pixels in the layer class.

## How to Use (Text view)

Start the program with the argument "Text".

Once this occurs, and the main method runs, the console will output 
"Welcome to our Image Processor." and then "Awaiting command:".

From there the user can type in the following commands (case-sensitive):

1. **help**: Outputs every valid command the user can input along with their descriptions.
2. **?**: Does the same as the help command.
3. **quit**: Exits the program.
4. **q**: Exits the program.
3. **filter-list**: Outputs every supported filter that the user can apply to a layer.
4. **new-project**: Starts a new project once given two more additional inputs: 
   two non-zero positive integers.
   * Example:
   ``` 
   new-project 32 32
   ```

### The following commands will fail if there is no project open:

5. **save-project**: Saves the current project as a .collage file once given an additional input:
   this input is the file name and must not contain a period or be just whitespace. 
   * Example:
   ``` 
   new-project 2 2 
   save-project myProject
   ```
6. **add-layer**: Adds a new layer to an open project once given one additional input:
   a name for the layer. The layer name cannot contain any spaces or linebreaks.
   * Example:
   ``` 
   new-project 2 2 
   add-layer Layer2
   ```
7. **add-image-to-layer**: Adds an image from an existing .ppm image once given 3 additional inputs:
   the layer name to put the image on, the filepath location of the .ppm image, and two positive
   integers, representing coordinates on the canvas.
   * Example:
   ``` 
   new-project 2 2 
   add-image-to-layer Layer1 ./res/smol.ppm 0 0
   ```
8. **set-filter**: Sets a filter to a layer once given the filter name and then the layer name.
   * Example:
   ``` 
   new-project 2 2 
   set-filter red-component Layer1
   ```
9. **save-image**: Saves the image currently displayed on the canvas as a .ppm image. This removes
   the alpha component of every pixel present on the canvas.
   * Example:
   ``` 
   new-project 2 2 
   add-image-to-layer Layer1 ./res/smol.ppm 0 0
   set-filter red-component Layer1
   save-image redSmol
   ```

## Changes

* In A5 we changed how we represent pixels and colors. In A4, pixels were represented by an
integer array of 4 elements. These elements represented the red, green, blue, and alpha
components of a Pixel. While this design works, it became too unclear for us and we saw that
it could have limitations. With the Pixel interface/class, we can now much more easily
expand the functionality of our code.

* The saveImage(), saveProject(), loadProject() methods were moved from the model to the controller.

* As a result of moving the saveImage(), saveProject(), and loadProject() methods from the model
  to the controller, the code of these methods was moved into the SaveImage, SaveProject, and
  LoadProject functional objects (respectively). These objects now have an additional method
  (LoadProject has load() while the other two have save()) that does the actual saving
  and loading. These methods are made public so that both the text-based controller and the
  GUI controller can invoke them and to prevent code duplication (since regardless of the
  controller, these methods are going to do the same thing).

* Added command line arguments that determine which controller/view get used. 
  "Text" will use the text-based view and controller, while "GUI" will use the GUI view 
  and controller. 

* The currentCanvas method in our model was changed from creating a String representation of our 
current project image to creating a 2D list of Pixels, representing the image of our current 
project. This was done both to ensure that string representations of our project were not being
created in the model, and so that creating a BufferedImage of our project would be much easier.

## Bug Fixes

* The loadProject() method now works properly
* The new-project and add-image-to-layer methods would throw an exception if given a double
  for their additional inputs. These commands now check to make sure that they are only given
  integers.
* The controller will respond accordingly when given bad input, thus not causing the program to end.